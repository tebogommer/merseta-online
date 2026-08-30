package za.co.merseta.nsdms.batch.grant.gp.payments;

import java.util.Date;
import java.util.Enumeration;

import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfVendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesDocument;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddressKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.entity.lookup.Chamber;
import haj.com.gpservices.GPAddressTypeEnum;
import haj.com.gpservices.GPVendorClassEnum;
import haj.com.service.AddressService;
import haj.com.service.CompanyService;
import haj.com.service.GpGrantBatchListService;
import haj.com.service.JAXBService;
import haj.com.service.lookup.ChamberService;
import za.co.merseta.nsdms.batch.grant.gp.DiskBackedBatchMap;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPRequestHandler;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.CreatePayablesDocumentAdapter;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.CreateVendorAdapter;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.GetVendorByKeyAdapter;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.EntityAlreadyExistsException;
import za.co.merseta.nsdms.framework.exception.EntityNotFoundException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;
import za.co.merseta.nsdms.jms.NSDMSJMSListener;

public class GrantBatchPaymentsMessageListener extends NSDMSJMSListener {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(GrantBatchPaymentsMessageListener.class);
    private String wspType;
    private int batchNumber;
    private long batchID;
    private int deliveryCount;
    public final static String JMS_HEADER_WSP_TYPE = "WSP_TYPE";
    public final static String JMS_HEADER_BATCH_ID = "BATCH_ID";
    public final static String JMS_HEADER_BATCH_NUMBER = "BATCH_NUMBER";
    private GpGrantBatchListService gpGrantBatchListService = new GpGrantBatchListService();
    private GetVendorByKeyAdapter getVendorByKeyAdapter = new GetVendorByKeyAdapter();
    private CreateVendorAdapter createVendorAdapter = new CreateVendorAdapter();
    private GpGrantBatchEntry entry;
    private ChamberService chamberService = new ChamberService();

    public GrantBatchPaymentsMessageListener(Session session, int maxRedeliveries) {
        super(session, maxRedeliveries);
    }

    @Override
    public void onMessage(Message message) {
        logger.info("onMessage() - {}", "START");
        // Tracing the actual object message is pointless as it shows a binary
        // representation, and is quite huge
        // It's more useful to debug the properties instead
        try {
            @SuppressWarnings("rawtypes")
            Enumeration propertyNames = message.getPropertyNames();
            while (propertyNames.hasMoreElements()) {
                String propertyName = propertyNames.nextElement().toString();
                logger.debug("JMS PROPERTY [{}]={}", propertyName, message.getStringProperty(propertyName));
            }
        } catch (Exception e) {
            logger.error("An exception occured while debugging headers,continuing with processing", e);
        }

        if (message instanceof ObjectMessage) {
            try {
                ObjectMessage batchMessage = (ObjectMessage) message;

                deliveryCount = message.getIntProperty("JMSXDeliveryCount");
                wspType = batchMessage.getStringProperty(JMS_HEADER_WSP_TYPE);
                batchID = batchMessage.getLongProperty(JMS_HEADER_BATCH_ID);
                batchNumber = batchMessage.getIntProperty(JMS_HEADER_BATCH_NUMBER);
                this.entry = (GpGrantBatchEntry) batchMessage.getObject();
                processMessage();
            } catch (Exception e) {
                logger.error("An exception occured while processing the message", e);
                try {
                    session.rollback();
                } catch (Exception innerException) {
                    logger.error("An exception occured while processing the message", innerException);
                }
            } finally {
                if (deliveryCount > maxRedeliveries) {
                    updateBatchStatus();
                }
            }

        } else {
            logger.error("Received a text message in a queue that only processes object messages");
            logger.error(JAXBService.marshallToStringNoException(message));
        }

        logger.info("onMessage() - {}", "END");
    }

    private void processMessage() throws Exception {
        logger.info("processMessage() - {}", "START");

        boolean error = false;
        String errorMessage = "SUCCESS";
        boolean rollback = false;

        try {
            Vendor vendor = getVendorByKeyAdapter.getVendorByKey(entry.getLevyNumber());

            if (!Boolean.TRUE.equals(vendor.isIsActive())) {
                error = true;
                errorMessage = String.format("Not Active On GP. vendor.isIsActive() value is: %s", vendor.isIsActive());
            } else {
                String chamberCode = getChamberCode();

                try {
                    CreatePayablesDocumentAdapter createPayablesDocumentAdapter = new CreatePayablesDocumentAdapter();
                    PayablesDocument document = CreatePayablesDocumentAdapter.createPayablesDocumentFromBatchEntry(
                            this.batchID,
                            this.batchNumber, this.wspType, chamberCode, entry);
                    createPayablesDocumentAdapter.createPayablesDocument(document);
                } catch (EntityAlreadyExistsException e) {
                    logger.error("The payment has already succeeded", e);
                    errorMessage = "SUCCESS. Invoice already paid";
                } catch (BusinessException e) {
                    logger.error(String.format("An exception occured %s:%s", e.getClass(), e.getMessage()), e);
                    error = true;
                    errorMessage = String.format("%s:%s", e.getClass(), e.getMessage());
                } catch (Exception e) {
                    logger.error(String.format("An exception occured %s:%s", e.getClass(), e.getMessage()), e);
                    logger.error("ROLLING BACK THE MESSAGE FOR RETRY");
                    error = true;
                    errorMessage = String.format("%s:%s", e.getClass(), e.getMessage());
                    rollback = true;
                }
            }

        } catch (EntityNotFoundException e) {
            error = true;
            errorMessage = "Company Not On GP";
            logger.error("The vendor does not exist on GP, will attempt to create it", e);
            try {
                createVendor();
            } catch (BusinessException businessException) {
                logger.error("A valid error occured while creating a vendor on GP", businessException);
                error = true;
                errorMessage = String.format("%s. On attempting to create a new vendor on GP, we got error [%s : %s]",
                        errorMessage, businessException.getClass(), businessException.getMessage());
            } catch (Exception innerException) {
                logger.error("A technical error error occured while creating a vendor on GP, will retry",
                        innerException);
                logger.error("ROLLING BACK THE MESSAGE FOR RETRY");
                error = true;
                errorMessage = String.format("%s. On attempting to create a new vendor on GP, we got error [%s : %s]",
                        errorMessage, innerException.getClass(), innerException.getMessage());
                rollback = true;
            }
        } catch (BusinessException e) {
            logger.error("An exception occured while processing message", e);
            error = true;
            errorMessage = String.format("%s:%s", e.getClass(), e.getMessage());
        } catch (Exception e) {
            logger.error("An exception occured while processing message", e);
            logger.error("ROLLING BACK THE MESSAGE FOR RETRY");
            error = true;
            errorMessage = String.format("%s:%s", e.getClass(), e.getMessage());
            rollback = true;
        }

        entry.setErrorEntry(error);
        entry.setErrorMessage(errorMessage);

        try {
            gpGrantBatchListService.updateEntry(entry);
        } catch (Exception e) {
            logger.error("An exception occured while updating batch entry", e);
            throw new TechnicalException(
                    String.format("An error [%s:%s]occured while trying to update the batch entry [%s]", e.getClass(),
                            e.getMessage(), entry.getId()));
        }

        if (rollback) {
            session.rollback();
        } else {
            updateBatchStatus();
            session.commit();
        }

        logger.info("processMessage() - {}", "END");
    }

    private void createVendor() throws ValidationException, TechnicalException, EntityAlreadyExistsException,
            BusinessException, RequestTimeOutException {
        logger.info("createVendor() - {}", "START");
        CompanyService companyService = new CompanyService();
        AddressService addressService = new AddressService();
        Company company = null;
        Address residentialAddress = null;

        try {
            company = companyService.findByLevyNumber(entry.getLevyNumber());
        } catch (Exception e) {
            throw new TechnicalException(String.format(
                    "An exception occurred while trying to get company data for L Number: %s", entry.getLevyNumber()),
                    e);
        }

        if (company == null || company.getLevyNumber() == null || company.getLevyNumber().isEmpty()) {
            throw new ValidationException(
                    String.format("Unable to locate company from DB with L Number: %s", entry.getLevyNumber()));
        }

        if (company.getResidentialAddress() == null || company.getResidentialAddress().getId() == null) {
            throw new ValidationException(
                    String.format("Unable to locate residential address for company from DB with L Number: %s",
                            entry.getLevyNumber()));
        }

        try {
            residentialAddress = addressService.findByKey(company.getResidentialAddress().getId());
        } catch (Exception e) {
            throw new TechnicalException(String.format(
                    "An exception occurred while trying to get the residential address for L Number: %s",
                    entry.getLevyNumber()), e);
        }

        if (residentialAddress == null) {
            throw new ValidationException(
                    String.format("Unable to locate residential address for company from DB with L Number: %s",
                            entry.getLevyNumber()));
        }

        VendorKey vendorKey = new VendorKey();
        vendorKey.setId(company.getLevyNumber());

        Vendor vendor = new Vendor();
        vendor.setKey(vendorKey);
        vendor.setName(company.getCompanyName());
        vendor.setIsActive(Boolean.TRUE);
        vendor.setIsOneTime(Boolean.FALSE);

        // sets banking details
        vendor.setTaxIdentificationNumber("");
        vendor.setTaxRegistrationNumber("");
        vendor.setIsOnHold(Boolean.TRUE);
        vendor.setUserDefined1("NO BANK DETAILS");

        ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
        addresses.getVendorAddress().add(mapAddress(residentialAddress));
        vendor.setAddresses(addresses);
        VendorClassKey vendorClassKey = new VendorClassKey();
        String chamberName = GPVendorClassEnum.PLASTICS.getGPName();
        if (company.getSicCode() != null && company.getSicCode().getChamber() != null
                && company.getSicCode().getChamber().getId() != null
                && company.getSicCode().getChamber().getGpVendorClass() != null) {
            chamberName = company.getSicCode().getChamber().getGpVendorClass().getGPName();
        } else if (company.getChamber() != null && company.getChamber().getGpVendorClass() != null) {
            chamberName = company.getChamber().getGpVendorClass().getGPName();
        }
        vendorClassKey.setId(chamberName);
        CompanyKey companyKey = new CompanyKey();
        companyKey.setId(GPRequestHandler.MERSETA_COMPANY_ID);
        vendorClassKey.setCompanyKey(companyKey);
        vendor.setClassKey(vendorClassKey);
        createVendorAdapter.createVendor(vendor);
        logger.info("createVendor() - {}", "END");
    }

    private VendorAddress mapAddress(Address residentialAddress) {
        logger.info("mapAddress() - {}", "START");
        logger.trace("Parameters Received \n \t - residentialAddress={}",
                JAXBService.marshallToStringNoException(residentialAddress));
        VendorAddress address = new VendorAddress();
        VendorKey vendorKey = new VendorKey();
        vendorKey.setId(entry.getLevyNumber());
        CompanyKey companyKey = new CompanyKey();
        companyKey.setId(GPRequestHandler.MERSETA_COMPANY_ID);
        vendorKey.setCompanyKey(companyKey);
        VendorAddressKey key = new VendorAddressKey();
        key.setCompanyKey(vendorKey.getCompanyKey());
        key.setVendorKey(vendorKey);
        key.setId(GPAddressTypeEnum.POSTAL.getGPName());
        address.setKey(key);
        address.setDeleteOnUpdate(false);
        if (residentialAddress.getTown() != null) {
            address.setCity(residentialAddress.getTown().getDescription());
        }
        address.setLine1(
                String.format("%s %s", residentialAddress.getAddressLine1(), residentialAddress.getAddressLine2()));
        address.setLine2(
                String.format("%s %s", residentialAddress.getAddressLine3(), residentialAddress.getAddressLine4()));
        address.setPostalCode(residentialAddress.getPostcode());
        address.setName("POSTAL");
        address.setCountryRegion("ZA");
        address.setUPSZone("");
        logger.trace("Response Returned - {}", JAXBService.marshallToStringNoException(address));
        logger.info("mapAddress() - {}", "END");
        return address;
    }

    private String getChamberCode() {
        logger.info("getChamberCode() - {}", "START");
        String chamberString = null;

        if (WspTypeEnum.Mandatory.equals(WspTypeEnum.valueOf(this.wspType)) && entry.getChamberLinked() != null
                && entry.getChamberLinked().getId() != null) {
            try {
                Chamber chamber = chamberService.findByKey(entry.getChamberLinked().getId());
                if (chamber != null && chamber.getId() != null && chamber.getGpAccountNumberCode() != null
                        && !chamber.getGpAccountNumberCode().trim().isEmpty()) {
                    chamberString = chamber.getGpAccountNumberCode().trim();
                }
            } catch (Exception e) {
                logger.error("An exception occured while getting chamber code, will default to 99", e);
            }
        }

        if (chamberString == null || chamberString.trim().isEmpty()) {
            chamberString = "99";
        }
        logger.debug("Response Returned - chamberString = {}", chamberString);
        logger.info("getChamberCode() - {}", "START");
        return chamberString;
    }

    private void updateBatchStatus() {
        logger.info("updateBatchStatus() - {}", "START");
        GpGrantBatchList batch = null;
        try {
            batch = gpGrantBatchListService.findByKey(this.batchID);
            int batchCount = DiskBackedBatchMap.getInstance().remove(GrantPaymentsBatchMessageProducer.class,
                    this.batchID,
                    this.entry.getId());
            if (batchCount == 0) {
                batch.setCompletedGpTransation(true);
                batch.setDateCompletedGpTransation(new Date());
                gpGrantBatchListService.update(batch);
            }
            gpGrantBatchListService.notifyDajoTeam("POSTING TO GP COMPLETE",
                    "Be advised: Posting to GP has finished", false, null);
        } catch (Exception e) {
            logger.error(String.format("Exception occured while updating the batch [id=%s]", batchID), e);
        }
        logger.info("updateBatchStatus() - {}", "END");
    }

}