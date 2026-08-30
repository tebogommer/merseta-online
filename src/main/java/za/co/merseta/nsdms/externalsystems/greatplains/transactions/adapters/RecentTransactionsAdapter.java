package za.co.merseta.nsdms.externalsystems.greatplains.transactions.adapters;

import haj.com.gptransations.ArrayOfRecentPayments;
import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.transactions.GPListQueryHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class RecentTransactionsAdapter extends GPListQueryHandler<String, ArrayOfRecentPayments>{
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(OutStandingTransactionsAdapter.class);
    private String vendorID;

    public ArrayOfRecentPayments getRecentTransactions(String vendorId) throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException{
        logger.info("getRecentTransactions() - {}", "START");
        logger.debug("Parameters Received \n \t vendorId ={}", vendorId);        
        this.vendorID = vendorId;
        ArrayOfRecentPayments recentTransactions= processRequest();
        logger.info("getRecentTransactions() - {}", "END");
        return recentTransactions;
    }


    @Override
    protected String createGPRequest() {
        logger.info("createGPRequest() - {}", "START");        
        logger.info("Returned Response \n \t {}",vendorID);                
        logger.info("createGPRequest() - {}", "END");        
        return this.vendorID.trim();
    }

    @Override
    protected ArrayOfRecentPayments callGP() throws Exception {
        logger.info("callGP() - {}", "START");        
                
        ArrayOfRecentPayments recentTransactions = getGPProxy().recentTransactions(username, password, vendorID);

        logger.trace("Returned Response \n \t {}", JAXBService.marshallToStringNoException(recentTransactions));    
        logger.info("callGP() - {}", "END");    
        return recentTransactions;
    }
 
    public static void main(String[] args) throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException {
        new RecentTransactionsAdapter().getRecentTransactions("L4707131341");
    }
}
