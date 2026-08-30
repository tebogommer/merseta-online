package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfBehavior;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfBehaviorOption;
import com.microsoft.schemas.dynamics.gp._2006._01.Behavior;
import com.microsoft.schemas.dynamics.gp._2006._01.BehaviorKey;
import com.microsoft.schemas.dynamics.gp._2006._01.BehaviorOption;
import com.microsoft.schemas.dynamics.gp._2006._01.BehaviorOptionKey;
import com.microsoft.schemas.dynamics.gp._2006._01.Policy;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;
import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPUpdateHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.EntityAlreadyExistsException;
import za.co.merseta.nsdms.framework.exception.NSDMSException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class CreateVendorAdapter extends GPUpdateHandler<Vendor, Void> {
    private static final NSDMSLogger logger = NSDMSLogger.getLogger(CreateVendorAdapter.class);
    private Vendor vendor;
    private Policy policy;

    public void createVendor(Vendor vendor) throws EntityAlreadyExistsException, ValidationException, BusinessException,
            TechnicalException, RequestTimeOutException {
        logger.info("createVendor() - {}", "START");
        logger.trace("Parameters Received \n \t vendor ={}", JAXBService.marshallToStringNoException(vendor));

        this.vendor = vendor;
        processRequest();

        logger.info("createVendor() - {}", "END");
    }

    @Override
    protected Vendor createGPRequest() {
        logger.info("createGPRequest() - {}", "START");

        policy = createPolicy("66e1feb2-68db-4fd5-a431-70f2fe128cd1");
        policy.setBehaviors(new ArrayOfBehavior());

        Behavior behavior = new Behavior();
        BehaviorKey behaviorKey = new BehaviorKey();
        behaviorKey.setPolicyKey(policy.getKey());
        behaviorKey.setId("7b5d30f0-e152-4818-a196-6ba3accd8d32");

        BehaviorOption behaviorOption = new BehaviorOption();
        BehaviorOptionKey behaviorOptionKey = new BehaviorOptionKey();
        behaviorOptionKey.setBehaviorKey(behaviorKey);
        behaviorOptionKey.setId(1);
        behaviorOption.setKey(behaviorOptionKey);

        behavior.setKey(behaviorKey);
        behavior.setOptions(new ArrayOfBehaviorOption());
        behavior.getOptions().getBehaviorOption().add(behaviorOption);

        behavior.setKey(behaviorKey);
        policy.getBehaviors().getBehavior().add(behavior);
        behavior.setSelectedOption(behaviorOption);

        if (vendor.getAddresses() != null) {
            for (VendorAddress address : vendor.getAddresses().getVendorAddress()) {
                vendor.setDefaultAddressKey(address.getKey());
                break;
            }
        }

        logger.trace("Created a policy \n ", () -> JAXBService.marshallToStringNoException(policy));
        logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(vendor));
        logger.info("createGPRequest() - {}", "END");
        return vendor;
    }

    @Override
    protected Void callGP() throws Exception {
        logger.info("callGP() - {}", "START");

        getGPProxy().createVendor(vendor, getContext(), policy);

        logger.info("callGP() - {}", "START");
        return null;
    }

    @Override
    protected void validate() throws ValidationException {
        if (this.vendor.getClassKey() == null) {
            throw new ValidationException("You need to specify the Vendor Class ID");
        }
    }

    @Override
    protected void customiseNSDMSException(NSDMSException exception)
            throws BusinessException, TechnicalException, RequestTimeOutException {

        logger.info("customiseNSDMSException() - {}");                
        if(exception instanceof BusinessException &&
           exception.getMessage().contains("VendorKey already exists")){
            throw new EntityAlreadyExistsException(exception.getMessage(),exception.getCause());
        }else{
            downCastException(exception);
        }

    }

    public static void main(String[] args) throws Exception {
        CompanyKey companyKey = new CompanyKey(2);
        Vendor vendor = new Vendor(new VendorKey("L4707131341"));
        vendor.getKey().setCompanyKey(companyKey);
        vendor.setName("MNCEDISI MAWABO KASPER");
        vendor.setClassKey(new VendorClassKey("METAL"));
        vendor.getClassKey().setCompanyKey(companyKey);
        new CreateVendorAdapter().createVendor(vendor);
    }
}
