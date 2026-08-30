package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter;

import java.util.List;

import com.microsoft.schemas.dynamics.gp._2006._01.LikeRestrictionOfString;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesInvoiceCriteria;
import com.microsoft.schemas.dynamics.gp._2006._01.PayablesInvoiceSummary;

import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPListQueryHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.EntityNotFoundException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class GetPayablesInvoiceListAdapter extends GPListQueryHandler<PayablesInvoiceCriteria,List<PayablesInvoiceSummary>>{
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(GetPayablesInvoiceListAdapter.class);    
    private String referenceNumber;

    public List<PayablesInvoiceSummary> getPayablesInvoiceList(String referenceNumber) throws BusinessException, TechnicalException, RequestTimeOutException, EntityNotFoundException {
		logger.info("getPayablesInvoiceList() - {}", "START");
		logger.debug("Parameters Received \n \t referenceNumber ={}", referenceNumber);

        this.referenceNumber = referenceNumber;
        List<PayablesInvoiceSummary> invoiceSummaryList = (List<PayablesInvoiceSummary>) processRequest();

		logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(invoiceSummaryList));
		logger.info("getPayablesInvoiceList() - {}", "END");        
        return invoiceSummaryList;
    }    

    @Override
    protected PayablesInvoiceCriteria createGPRequest() {
        logger.info("createGPRequest() - {}", "START");

		PayablesInvoiceCriteria criteria = new PayablesInvoiceCriteria();
		LikeRestrictionOfString like = new LikeRestrictionOfString();
		like.setEqualValue(this.referenceNumber);
		criteria.setVendorId(like);
		
        logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(criteria));
		logger.info("createGPRequest() - {}", "END");        
        return criteria;
    }

    @Override
    protected List<PayablesInvoiceSummary> callGP() throws Exception {
		logger.info("callGP() - {}", "START");

		List<PayablesInvoiceSummary> invoiceSummaryList = getGPProxy().getPayablesInvoiceList((PayablesInvoiceCriteria) getRequest(), getContext()).getPayablesInvoiceSummary();
		
        logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(invoiceSummaryList));
		logger.info("callGP() - {}", "END");
		return invoiceSummaryList;
    }
}
