package za.co.merseta.nsdms.batch.grant.gp.validation;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfVendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddressKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;
import haj.com.bean.AttachmentBean;
import haj.com.datatakeon.GenericDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.TransactionsCompanyValidiation;
import haj.com.entity.Users;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.gpservices.GPAddressTypeEnum;
import haj.com.gpservices.GPVendorClassEnum;
import haj.com.service.AddressService;
import haj.com.service.CompanyService;
import haj.com.service.GpGrantBatchListService;
import haj.com.service.JAXBService;
import haj.com.service.TransactionsCompanyValidiationService;
import haj.com.service.UsersService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.batch.grant.gp.DiskBackedBatchMap;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPRequestHandler;
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

public class GrantBatchValidationMessageListener extends NSDMSJMSListener {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(GrantBatchValidationMessageListener.class);
    private String levyNumber;
    private String wspType;
    private long userID;
    private long batchID;
    private int deliveryCount;
    public final static String JMS_HEADER_LEVY_NUMBER = "LEVY_NUMBER";
    public final static String JMS_HEADER_WSP_TYPE = "WSP_TYPE";
    public final static String JMS_HEADER_USER_ID = "USER_ID";
    public final static String JMS_HEADER_BATCH_ID = "BATCH_ID";
    private TransactionsCompanyValidiationService transactionsCompanyValidiationService = new TransactionsCompanyValidiationService();
    private GpGrantBatchListService batchListService = new GpGrantBatchListService();
    private boolean error = false;
    private String errorMessage = "SUCCESS";
    private boolean newCompany = false;

    public GrantBatchValidationMessageListener(Session session, int maxRedeliveries) {
        super(session, maxRedeliveries);
    }

    @Override
    public void onMessage(Message message) {
        logger.info("onMessage() - {}", "START");
        // logger.trace("Parameters Received \n \t message = {}", JAXBService.marshallToStringNoException(message));
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
        
        if (message instanceof TextMessage) {
            try {
                TextMessage batchEntry = (TextMessage) message;

                deliveryCount = message.getIntProperty("JMSXDeliveryCount");
                levyNumber = batchEntry.getStringProperty(JMS_HEADER_LEVY_NUMBER);
                wspType = batchEntry.getStringProperty(JMS_HEADER_WSP_TYPE);
                userID = batchEntry.getLongProperty(JMS_HEADER_USER_ID);
                batchID = batchEntry.getLongProperty(JMS_HEADER_BATCH_ID);

                if (WspTypeEnum.Mandatory.toString().equals(this.wspType)) {
                    handleMandatoryGrant();
                } else {
                    handleDiscretionatyGrant();
                }

            } catch (Exception e) {
                logger.error("An exception occured while processing the message", e);
                error = true;
                errorMessage = String.format("An exception occured while processing the message [%s:%s]", e.getClass(),
                        e.getMessage());
                try {
                    session.rollback();
                } catch (Exception innerException) {
                    logger.error("An exception occured while rolling back transaction", e);
                }
            } finally {
                if (deliveryCount > maxRedeliveries) {
                    updateBatchStatus(false, error, errorMessage);
                }
            }

        } else {
            logger.error("Received a text message in a queue that only processes object messages");
            logger.error(JAXBService.marshallToStringNoException(message));
        }
        logger.info("onMessage() - {}", "END");
    }

    private void handleMandatoryGrant() {
        logger.info("handleMandatoryGrant() - {}", "START");
        GetVendorByKeyAdapter getVendorByKeyAdapter = new GetVendorByKeyAdapter();
        GenericDAO dao = new GenericDAO();
        boolean rollback = false;

        try {
            Vendor vendor = getVendorByKeyAdapter.getVendorByKey(this.levyNumber);

            if (!Boolean.TRUE.equals(vendor.isIsActive())) {
                error = true;
                errorMessage = String.format("Not Active On GP. vendor.isIsActive() value is: %s", vendor.isIsActive());
            }
            error = false;
            errorMessage = "SUCCESS";
        } catch (EntityNotFoundException e) {
            error = true;
            errorMessage = "Company Not On GP";
            logger.error("The vendor does not exist on GP, will attempt to create it", e);
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

        if (rollback) {
            try {
                session.rollback();
            } catch (Exception e) {
                logger.error("An exception occured while rolling back transaction", e);
            }
        } else {
            updateBatchStatus(newCompany, error, errorMessage);
            try {
                session.commit();
            } catch (Exception e) {
                logger.error("An exception occured while committing transaction", e);
            }
        }
        logger.info("handleMandatoryGrant() - {}", "END");
    }

    private void handleDiscretionatyGrant() throws Exception {
        logger.info("handleDiscretionatyGrant() - {}", "START");
        GetVendorByKeyAdapter getVendorByKeyAdapter = new GetVendorByKeyAdapter();
        GenericDAO dao = new GenericDAO();
        boolean rollback = false;
        try {
            Vendor vendor = getVendorByKeyAdapter.getVendorByKey(this.levyNumber);

            if (vendor.getTaxRegistrationNumber() == null) {

            }

            if (!Boolean.TRUE.equals(vendor.isIsActive())) {
                error = true;
                errorMessage = String.format("Not Active On GP. vendor.isIsActive() value is: %s", vendor.isIsActive());
            }
            error = false;
            errorMessage = "SUCCESS";
        } catch (EntityNotFoundException e) {
            error = true;
            errorMessage = "Company Not On GP";
            logger.error("The vendor does not exist on GP, will attempt to create it", e);
            try {
                createVendor();
                newCompany = true;
                error = false;
                errorMessage = "SUCCESS";
            } catch (BusinessException businessException) {
                logger.error("A valid error occured while creating a vendor on GP", businessException);
                errorMessage = String.format("%s. On attempting to create a new vendor on GP, we got error [%s : %s]",
                        errorMessage, businessException.getClass(), businessException.getMessage());
            } catch (Exception innerException) {
                logger.error("A technical error error occured while creating a vendor on GP, will retry",
                        innerException);
                logger.error("ROLLING BACK THE MESSAGE FOR RETRY");
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

        if (rollback) {
            try {
                session.rollback();
            } catch (Exception e) {
                logger.error("An exception occured while rolling back transaction", e);
            }
        } else {
            updateBatchStatus(newCompany, error, errorMessage);
            try {
                session.commit();
            } catch (Exception e) {
                logger.error("An exception occured while committing transaction", e);
            }
        }
        logger.info("handleDiscretionatyGrant() - {}", "END");
    }

    private void createVendor() throws ValidationException, TechnicalException, EntityAlreadyExistsException,
            BusinessException, RequestTimeOutException {
        logger.info("createVendor() - {}", "START");
        CompanyService companyService = new CompanyService();
        AddressService addressService = new AddressService();
        Company company = null;
        Address residentialAddress = null;
        CreateVendorAdapter createVendorAdapter = new CreateVendorAdapter();

        try {
            company = companyService.findByLevyNumber(this.levyNumber);
            // company = companyService.findByLevyNumber("L000746919");
        } catch (Exception e) {
            throw new TechnicalException(String.format(
                    "An exception occurred while trying to get company data for L Number: %s", this.levyNumber), e);
        }

        if (company == null || company.getLevyNumber() == null || company.getLevyNumber().isEmpty()) {
            throw new ValidationException(
                    String.format("Unable to locate company from DB with L Number: %s", this.levyNumber));
        }

        if (company.getResidentialAddress() == null || company.getResidentialAddress().getId() == null) {
            throw new ValidationException(
                    String.format("Unable to locate residential address for company from DB with L Number: %s",
                            this.levyNumber));
        }

        try {
            residentialAddress = addressService.findByKey(company.getResidentialAddress().getId());
        } catch (Exception e) {
            throw new TechnicalException(String.format(
                    "An exception occurred while trying to get the residential address for L Number: %s",
                    this.levyNumber), e);
        }

        if (residentialAddress == null) {
            throw new ValidationException(
                    String.format("Unable to locate residential address for company from DB with L Number: %s",
                            this.levyNumber));
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
        VendorAddress address = new VendorAddress();
        VendorKey vendorKey = new VendorKey();
        vendorKey.setId(this.levyNumber);
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

    private void updateBatchStatus(boolean newCompany, boolean error, String errorMessage) {
        logger.info("updateBatchStatus() - {}", "START");
        GpGrantBatchList batch = null;
        GenericDAO dao = new GenericDAO();
        try {
            try {
                TransactionsCompanyValidiation transactionCompanyValidation = transactionsCompanyValidiationService
                        .prepNewTransactionsCompanyValidiation(new Users(this.userID), this.levyNumber,
                                GpGrantBatchList.class.getName(), this.batchID, newCompany, error, errorMessage);
                dao.create(transactionCompanyValidation);
            } catch (Exception e) {
                logger.error("An exception occured while trying to save the record into TransactionsCompanyValidiation",
                        e);
            }

            batch = batchListService.findByKey(this.batchID);
            int batchCount = DiskBackedBatchMap.getInstance().remove(GrantBatchValidationMessageProducer.class,
                    this.batchID,
                    this.levyNumber);
            if (batchCount == 0) {
                batch.setValidiationRun(true);
                batch.setValidiationUnderway(false);
                batchListService.update(batch);
                sendMail(batch);
            }
        } catch (Exception e) {
            logger.error(String.format("Exception occured while updating the batch [id=%s]", batchID), e);
        }
        
        logger.info("updateBatchStatus() - {}", "END");
    }

    public void sendMail(GpGrantBatchList batch) {
        try {
            Integer countFailedEntries = transactionsCompanyValidiationService
                    .countTransactionsCompanyValidiationByTargetClassKeyAndErrorEntry(batch.getClass().getName(),
                            batch.getId(), true);
            UsersService usersService = new UsersService();
            Users user = usersService.findByKey(this.userID);

            if (countFailedEntries == 0) {
                String subject = "";
                String msg = "";
                if (batch.getWspType() == WspTypeEnum.Mandatory) {
                    subject = "SYSTEM GENERATED NOTIFICATION: GP VALIDATION COMPLETED";
                    msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that no entry(ies) have failed on the GP validation for Batch: #BATCH_DESC#.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that you can proceed with the batch payment approval.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">NSDMS System Email</p>";
                    msg = msg.replace("#BATCH_DESC#", batch.getBatchDescription().trim());
                } else {
                    subject = "SYSTEM GENERATED NOTIFICATION: GP VALIDATION COMPLETED";
                    msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that no entry(ies) have failed on the GP validation for Batch: #BATCH_DESC#.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that you can proceed with the final approval for the batch payment.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">NSDMS System Email</p>";
                    msg = msg.replace("#BATCH_DESC#", batch.getBatchDescription().trim());
                }
                GenericUtility.sendMail(user.getEmail(), subject, msg.replace("#FULL_NAME#",
                        user.getFirstName().trim() + " " + user.getLastName().trim()), null);
                batchListService.notifyDajoTeam(subject, msg, false, null);

            } else {

                List<Users> emailReceivers = new ArrayList<>();
                emailReceivers = batchListService.populateEmailRecivers(user);
                String subject = "";
                String msg = "";
                if (batch.getWspType() == WspTypeEnum.Mandatory) {
                    subject = "SYSTEM GENERATED NOTIFICATION: GP VALIDATION FAILURE";
                    msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that the following entry(ies) supplied on the attached document have not passed the GP validation for Batch: #BATCH_NUMBER#.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#COUNT_FAILED_ENTRIES# entry(ies) have not passed the validation.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that the entry(ies) need to be resolved before the batch payment can be processed and sent to GP. Please may Finance attend to the errors and once resolved, please proceed with the final approval.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">NSDMS System Email</p>";
                    msg = msg.replace("#COUNT_FAILED_ENTRIES#", countFailedEntries.toString());
                    msg = msg.replace("#BATCH_NUMBER#", batch.getBatchDescription().trim());
                    msg = msg.replace("#FAILED_COUNT_ENTRIES#", batch.getBatchDescription().trim());
                } else {
                    subject = "SYSTEM GENERATED NOTIFICATION: GP VALIDATION FAILURE";
                    msg = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Dear #FULL_NAME#, </p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Kindly be advised that the following entry(ies) supplied on the attached document have not passed the GP validation for Batch: #BATCH_NUMBER#.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">#COUNT_FAILED_ENTRIES# entry(ies) have not passed the validation.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that the entry(ies) need to be resolved before the batch payment can be processed and sent to GP. Please may Finance attend to the errors and once resolved, notify the CEO to proceed with the final approval.</p>"
                            + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">NSDMS System Email</p>";
                    msg = msg.replace("#COUNT_FAILED_ENTRIES#", countFailedEntries.toString());
                    msg = msg.replace("#BATCH_NUMBER#", batch.getBatchDescription().trim());
                    msg = msg.replace("#FAILED_COUNT_ENTRIES#", batch.getBatchDescription().trim());
                }
                boolean addExcel = false;
                byte[] byteArray = batchListService.populateResults(batch);
                if (byteArray == null) {
                    addExcel = false;
                } else {
                    addExcel = true;
                }

                List<AttachmentBean> attachmentBeans = new ArrayList<>();
                if (Boolean.TRUE.equals(addExcel)) {
                    AttachmentBean letter = new AttachmentBean();
                    String fileName = "BATCH_VALIDATION_FAIL_ENTRIES.xlsx";
                    letter.setExt("xlsx");
                    letter.setFile(byteArray);
                    letter.setFilename(fileName);
                    attachmentBeans.add(letter);
                }

                for (Users emailReceiver : emailReceivers) {
                    if (Boolean.TRUE.equals(addExcel)) {
                        GenericUtility.sendMailWithAttachementTempWithLog(emailReceiver.getEmail(), subject,
                                msg.replace("#FULL_NAME#",
                                        emailReceiver.getFirstName().trim() + " " + emailReceiver.getLastName().trim()),
                                attachmentBeans, null);
                    } else {
                        GenericUtility.sendMail(emailReceiver.getEmail(), subject,
                                msg.replace("#FULL_NAME#",
                                        emailReceiver.getFirstName().trim() + " " + emailReceiver.getLastName().trim()),
                                null);
                    }
                }
                batchListService.notifyDajoTeam(subject, msg, addExcel, attachmentBeans);
            }
        } catch (Exception e) {
            logger.error("An exception occured while trying to send email", e);
        }
    }
}