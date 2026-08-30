package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter;

import java.math.BigDecimal;
import java.util.Date;
import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfPayablesDistribution;
import com.microsoft.schemas.dynamics.gp._2006._01.BatchKey;
import com.microsoft.schemas.dynamics.gp._2006._01.DistributionTypeKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GLAccountNumberKey;
import com.microsoft.schemas.dynamics.gp._2006._01.MoneyAmount;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesCreditMemo;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesDistribution;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesDocument;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesDocumentKey;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesDocumentType;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesInvoice;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesReturn;
import com.microsoft.schemas.dynamics.gp._2006._01.Policy;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;
import haj.com.entity.ActiveContractDetail;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.enums.GpDocumentType;
import haj.com.entity.enums.WspTypeEnum;
import haj.com.service.JAXBService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPRequestHandler;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPUpdateHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.EntityAlreadyExistsException;
import za.co.merseta.nsdms.framework.exception.NSDMSException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class CreatePayablesDocumentAdapter extends GPUpdateHandler<PayablesDocument, Void> {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(CreatePayablesDocumentAdapter.class);
    private PayablesDocument payablesDocument;
    private static final String PAYMENTS_POLICY_ID = "f39624cb-97f1-4d5a-a67f-9b66e95d8930";

    public void createPayablesDocument(PayablesDocument payablesDocument) throws ValidationException, BusinessException,
            TechnicalException, RequestTimeOutException, EntityAlreadyExistsException {
        logger.info("createPayablesDocument() - {}", "START");
        logger.trace("Parameters Received \n \t payablesDocument ={}",
                JAXBService.marshallToStringNoException(payablesDocument));

        this.payablesDocument = payablesDocument;
        processRequest();

        logger.info("createPayablesDocument() - {}", "END");
    }

    @Override
    protected PayablesDocument createGPRequest() {
        logger.info("createGPRequest() - {}", "START");
        logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(this.payablesDocument));
        logger.info("createGPRequest() - {}", "END");

        return this.payablesDocument;
    }

    @Override
    protected Void callGP() throws Exception {
        logger.info("callGP() - {}", "END");

        Policy policy = createPolicy(PAYMENTS_POLICY_ID);
        
        if (this.payablesDocument instanceof PayablesInvoice) {
            getGPProxy().createPayablesInvoice((PayablesInvoice) this.payablesDocument, getContext(),policy);
        } else if (this.payablesDocument instanceof PayablesReturn) {
            getGPProxy().createPayablesReturn((PayablesReturn) this.payablesDocument, getContext(),policy);
        } else {
            getGPProxy().createPayablesCreditMemo((PayablesCreditMemo) this.payablesDocument, getContext(),policy);
        }

        logger.info("callGP() - {}", "END");
        return null;
    }

    @Override
    protected void customiseNSDMSException(NSDMSException exception)
            throws BusinessException, TechnicalException, RequestTimeOutException {
        logger.info("customiseNSDMSException() - {}");
        if (exception instanceof BusinessException &&(
                exception.getMessage().contains("VendorDocumentNumber already exists"))|| exception.getMessage().contains("Key already exists in PM Keys Master")) {
            throw new EntityAlreadyExistsException(exception.getMessage(), exception.getCause());
        } else {
            downCastException(exception);
        }
    }

    public static PayablesDocument createPayablesDocumentFromBatchEntry(Long batchID, int batchNumber, String wspType,String chamberCode,
            GpGrantBatchEntry entry) throws ValidationException {

        PayablesDocument payablesDocument = null;
        Double purchaseAmount = null;
        Date invoiceDate = null;
        String documentNumber = null;

        if (entry.getDocumentTypeMandatory() == GpDocumentType.Invoice ||entry.getDocumentTypeDiscretionary() == GpDocumentType.Invoice ) {
            payablesDocument = new PayablesInvoice();
            payablesDocument.setType(PayablesDocumentType.INVOICE);
        } else {
            if (WspTypeEnum.Mandatory.equals(WspTypeEnum.valueOf(wspType))) {
                payablesDocument = new PayablesReturn();
                payablesDocument.setType(PayablesDocumentType.RETURN);
            } else {
                payablesDocument = new PayablesCreditMemo();
                payablesDocument.setType(PayablesDocumentType.CREDIT_MEMO);
            }
        }

        if (WspTypeEnum.Mandatory.equals(WspTypeEnum.valueOf(wspType))) {
            purchaseAmount = entry.getMandatoryLevy();
            invoiceDate = entry.getArrivalDateFromSars();
            documentNumber = entry.getGpDocumentNumer();
        } else if (WspTypeEnum.Discretionary.equals(WspTypeEnum.valueOf(wspType))) {
            purchaseAmount = entry.getDiscretionaryLevy();
            invoiceDate = entry.getActiveContractDetail().getApprovalDate();
            documentNumber = entry.getDocumentNumber();
        } else {
            purchaseAmount = 0d;
        }

        payablesDocument.setPurchasesAmount(
                new MoneyAmount(GenericUtility.roundToPrecision(BigDecimal.valueOf(purchaseAmount), 2)));
        payablesDocument.setVendorDocumentNumber(documentNumber);

        try {
            payablesDocument.setDate(JAXBService.convertDateToXml(GenericUtility.getStartOfDay(invoiceDate)));
        } catch (Exception e) {
            throw new ValidationException("Invoice date is not valid");
        }

        mapCommonEntryDetails(batchID, batchNumber, wspType, chamberCode,purchaseAmount,payablesDocument, entry);

        return payablesDocument;
    }

    private static void mapCommonEntryDetails(Long batchID, int batchNumber, String wspType,String chamberCode, Double purchaseAmount,
            PayablesDocument payablesDocument, GpGrantBatchEntry entry) {

        String documentKey = String.format("EID%s-BN%d-BID%s-%s",entry.getId(),batchNumber, batchID,entry.getLevyNumber());
        payablesDocument.setKey(new PayablesDocumentKey(documentKey));
        payablesDocument.setVendorKey(new VendorKey(entry.getLevyNumber()));
        payablesDocument.setDescription(entry.getDescription());
        String batchKey = String.format("%s %d",
                (WspTypeEnum.Mandatory == WspTypeEnum.valueOf(wspType)) ? "GRANTS" : "DISC GRANT", batchNumber);
        try {
            payablesDocument.setBatchKey(
                    new BatchKey(batchKey, JAXBService.convertDateToXml(GenericUtility.getStartOfDay(new Date()))));
        } catch (Exception e) {
            logger.error("Failed to set BatchKey date", e);
        }

        ArrayOfPayablesDistribution payablesDistributionList = new ArrayOfPayablesDistribution();

        PayablesDistribution payablesDistribution = new PayablesDistribution();
        CompanyKey companyKey = new CompanyKey();
        companyKey.setId(GPRequestHandler.MERSETA_COMPANY_ID);
        payablesDistribution.setCompanyKey(companyKey);

        try {
            payablesDistribution.setPostingDate(JAXBService.convertDateToXml(GenericUtility.getStartOfDay(new Date())));
        } catch (Exception e) {
            logger.error("Failed to set PostingDate", e);
        }
        payablesDistribution.setGLAccountKey(new GLAccountNumberKey("00-00-1811"));
        DistributionTypeKey distributionTypeKey = new DistributionTypeKey();
        distributionTypeKey.setId(2);
        payablesDistribution.setDistributionTypeKey(distributionTypeKey);

        if (entry.getDocumentTypeMandatory() == GpDocumentType.Invoice) {
            payablesDistribution.setCreditAmount(
                    new MoneyAmount(GenericUtility.roundToPrecision(BigDecimal.valueOf(purchaseAmount), 2)));
        } else {
            payablesDistribution.setDebitAmount(new MoneyAmount(
                    GenericUtility.roundToPrecision(BigDecimal.valueOf(purchaseAmount), 2)));
        }

        payablesDistributionList.getPayablesDistribution().add(payablesDistribution);

        payablesDistribution = new PayablesDistribution();
        payablesDistribution.setCompanyKey(companyKey);

        if (WspTypeEnum.Mandatory == WspTypeEnum.valueOf(wspType)) {
            payablesDistribution.setGLAccountKey(new GLAccountNumberKey(String.format("%s-%d-4000", chamberCode, (entry.getSchemeYear()%100)+1)));
        } else if (WspTypeEnum.Discretionary == WspTypeEnum.valueOf(wspType)) {
            payablesDistribution.setGLAccountKey(new GLAccountNumberKey("00-00-1601"));
        }
        try{
        payablesDistribution.setPostingDate(JAXBService.convertDateToXml(GenericUtility.getStartOfDay(new Date())));
        }catch(Exception e){
            logger.error("Failed to set PostingDate", e);
        }
        distributionTypeKey = new DistributionTypeKey();
        distributionTypeKey.setId(6);
        payablesDistribution.setDistributionTypeKey(distributionTypeKey);

        if (entry.getDocumentTypeMandatory() == GpDocumentType.Invoice) {
            payablesDistribution.setDebitAmount(
                    new MoneyAmount(GenericUtility.roundToPrecision(BigDecimal.valueOf(purchaseAmount), 2)));
        } else {
            payablesDistribution.setCreditAmount(new MoneyAmount(
                    GenericUtility.roundToPrecision(BigDecimal.valueOf(purchaseAmount), 2)));
        }    
        payablesDistributionList.getPayablesDistribution().add(payablesDistribution);
        payablesDocument.setDistributions(payablesDistributionList);
                    
    }

    public static void main(String[] args) throws EntityAlreadyExistsException, BusinessException, TechnicalException, RequestTimeOutException {
        ActiveContractDetail acd = new ActiveContractDetail();
        acd.setApprovalDate(new Date());
        GpGrantBatchEntry entry = new GpGrantBatchEntry();
        entry.setActiveContractDetail(acd);
        entry.setId(189L);
        entry.setDocumentTypeMandatory(GpDocumentType.Invoice);
        entry.setDocumentNumber("MYDOCNO");
        entry.setMandatoryLevy(8d);
        entry.setArrivalDateFromSars(new Date());
        entry.setLevyNumber("L470713134");
        entry.setDescription("MY TEST COMPANY");
        entry.setSchemeYear(2010);
        entry.setDiscretionaryLevy(100d);
        PayablesDocument document =createPayablesDocumentFromBatchEntry(1L, 2, "Mandatory","METAL", entry);
        new CreatePayablesDocumentAdapter().createPayablesDocument(document);
    }
    // public static void main(String[] args)
    // throws ValidationException, BusinessException, TechnicalException,
    // RequestTimeOutException, Exception {
    // PayablesDocument payablesDocument = new PayablesInvoice();
    // payablesDocument.setType(PayablesDocumentType.INVOICE);

    // VendorKey vendorKey = new VendorKey("L470713134");

    // BatchKey batchKey = new BatchKey();
    // batchKey = new BatchKey();
    // batchKey.setSource("MIS-System");
    // batchKey.setId("LMIS00000000248");

    // PayablesDocumentKey invoiceKey = new PayablesDocumentKey("MNCEDISIKASPER");

    // payablesDocument.setKey(invoiceKey);
    // payablesDocument.setVendorKey(vendorKey);
    // payablesDocument.setDescription("DISC GRANT DG MOA - CASPER");
    // payablesDocument.setBatchKey(batchKey);
    // payablesDocument.setPurchasesAmount(new
    // MoneyAmount(BigDecimal.valueOf(100000)));
    // payablesDocument
    // .setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar("2022-07-31T00:00:00.000+02:00"));
    // payablesDocument.setVendorDocumentNumber("MNCEDISI KASPER DOC NUMBER");

    // new CreatePayablesDocumentAdapter().createPayablesDocument(payablesDocument);
    // }
}
