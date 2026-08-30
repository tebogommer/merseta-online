package za.co.merseta.nsdms.externalsystems.greatplains.transactions.adapters;

import haj.com.gptransations.ArrayOfInfo;
import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.transactions.GPListQueryHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class OutStandingTransactionsAdapter extends GPListQueryHandler<String, ArrayOfInfo>{
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(OutStandingTransactionsAdapter.class);
    private String vendorID;

    public ArrayOfInfo getOutstandingTransactions(String vendorId) throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException{
        logger.info("getOutstandingTransactions() - {}", "START");
        logger.debug("Parameters Received \n \t vendorId ={}", vendorId);        
        this.vendorID = vendorId;
        ArrayOfInfo outstandingTransactions= processRequest();
        logger.info("getOutstandingTransactions() - {}", "END");
        return outstandingTransactions;
    }


    @Override
    protected String createGPRequest() {
        logger.info("createGPRequest() - {}", "START");        
        logger.info("Returned Response \n \t {}",vendorID);                
        logger.info("createGPRequest() - {}", "END");        
        return this.vendorID.trim();
    }

    @Override
    protected ArrayOfInfo callGP() throws Exception {
        logger.info("callGP() - {}", "START");        
                
        ArrayOfInfo outstandingTransactions = getGPProxy().outStandingTransactions(username, password, vendorID);

        logger.trace("Returned Response \n \t {}", JAXBService.marshallToStringNoException(outstandingTransactions));    
        logger.info("callGP() - {}", "END");    
        return outstandingTransactions;
    }
 
    public static void main(String[] args) throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException {
        new OutStandingTransactionsAdapter().getOutstandingTransactions("L4707131341");
    }
}
