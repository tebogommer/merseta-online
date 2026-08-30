package za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;
import haj.com.service.JAXBService;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPItemQueryHandler;
import za.co.merseta.nsdms.framework.exception.BusinessException;
import za.co.merseta.nsdms.framework.exception.EntityNotFoundException;
import za.co.merseta.nsdms.framework.exception.RequestTimeOutException;
import za.co.merseta.nsdms.framework.exception.TechnicalException;
import za.co.merseta.nsdms.framework.exception.ValidationException;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

public class GetVendorByKeyAdapter extends GPItemQueryHandler<VendorKey, Vendor> {
	private static final NSDMSLogger logger = NSDMSLogger.getLogger(GetVendorByKeyAdapter.class);
	private String vendorId;

	public GetVendorByKeyAdapter() {
		super();
	}

	public Vendor getVendorByKey(String vendorId)
			throws ValidationException, BusinessException, TechnicalException, RequestTimeOutException,
			EntityNotFoundException {
		logger.info("getVendorByKey() - {}", "START");
		logger.debug("Parameters Received \n \t vendorId ={}", vendorId);
		this.vendorId = vendorId;
		Vendor vendor = (Vendor) processRequest();
		logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(vendor));
		logger.info("getVendorByKey() - {}", "END");
		return vendor;
	}

	protected VendorKey createGPRequest() {
		logger.info("createGPRequest() - {}", "START");
		VendorKey vendorKey = new VendorKey();
		vendorKey.setId(vendorId);
		logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(vendorKey));
		logger.info("createGPRequest() - {}", "END");
		return vendorKey;
	}

	protected Vendor callGP() throws Exception {
		logger.info("callGP() - {}", "START");
		Vendor vendor = getGPProxy().getVendorByKey((VendorKey) getRequest(), getContext());
		logger.trace("Response Returned \n ", () -> JAXBService.marshallToStringNoException(vendor));
		logger.info("callGP() - {}", "END");
		return vendor;
	}

	@Override
	final protected void validate() throws ValidationException {
		logger.info("validate() - {}", "START");

		if (this.vendorId == null || this.vendorId.trim().length() == 0) {
			throw new ValidationException("VendorID cannot be empty");
		}

		logger.info("validate() - {}", "END");
	}

	public static void main(String[] args) throws Exception {
		while (true) {
			try {
				new GetVendorByKeyAdapter().getVendorByKey("L4707131341");
				Thread.currentThread().sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}