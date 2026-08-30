package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;

import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPUpdateHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class UpdateVendorAdapter extends GPUpdateHandler<Vendor, Void>{
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(UpdateVendorAdapter.class);
    private Vendor vendor;

    public void updateVendor(Vendor vendor) throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException{
        logger.info("updateVendor() - {}", "START");
        logger.trace("Parameters Received \n \t address ={}", JAXBService.marshallToStringNoException(vendor));

        this.vendor = vendor;
        processRequest();

        logger.info("updateVendor() - {}", "END");
    }
    
    @Override
    protected Vendor createGPRequest() {
        logger.info("createGPRequest() - {}", "START");
        logger.info("createGPRequest() - {}", "END");
        return vendor;
    }

    @Override
    protected Void callGP() throws Exception {
        logger.info("callGP() - {}", "START");
        getGPProxy().updateVendor(vendor, getContext(), createPolicy("66e1feb2-68db-4fd5-a431-70f2fe128cd2"));
        logger.info("callGP() - {}", "END");
        return null;
    }
    
    public static void main(String[] args) throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException {
        CompanyKey companyKey = new CompanyKey(2);
        Vendor vendor = new Vendor(new VendorKey("L4707131341"));
        vendor.getKey().setCompanyKey(companyKey);
        vendor.setIsActive(true);
        // vendor.setName("MNCEDISI MAWABO KASPER1");
        // vendor.setClassKey(new VendorClassKey("METAL"));
        // vendor.getClassKey().setCompanyKey(companyKey);        
        new UpdateVendorAdapter().updateVendor(vendor);
    }
}
