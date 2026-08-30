import java.math.BigDecimal;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfVendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddressKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;

import haj.com.gpservices.GPAddressTypeEnum;
import haj.com.gpservices.GPVendorClassEnum;

public class GPWSTester {

	private int companyId = 2;

	public VendorAddress crtAddress(String vendorId) throws Exception{



		VendorAddress address = new VendorAddress();


		VendorKey vendorKey = new VendorKey();
		vendorKey.setId(vendorId);
		CompanyKey companyKey = new CompanyKey();
		companyKey.setId(companyId);
		vendorKey.setCompanyKey(companyKey);
		VendorAddressKey key = new VendorAddressKey();
		key.setCompanyKey(vendorKey.getCompanyKey());
		key.setVendorKey(vendorKey);
		key.setId(GPAddressTypeEnum.POSTAL.getGPName());


		address.setKey(key);
		address.setDeleteOnUpdate(false);
		address.setCity("Benoni");
		address.setLine1("6 Malherbe Street");
		address.setLine2("Rynfield");
		address.setPostalCode("1501");
		address.setName("POSTAL");
		address.setCountryRegion("ZA");
		address.setUPSZone("");
		return address;
	}

	public void testCases() throws Exception {

	/*
		  System.setProperty(
		  "com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		  System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		  System.setProperty(
		  "com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump",
		  "true");
		  System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump",
		  "true");
	 */
		// GPService.instance().allCompanies();
		// GPService.instance().allCustomerForCompany(2);
		// GPService.instance().allAccountsForCompany(2);
		// GPService.instance().allGLPostingAccountsForCompany(2);
		// GPService.instance().allVendorListForCompany(2);

		// Create a new vendor
		VendorKey vendorKey = new VendorKey();
		vendorKey.setId("L000000003");
		Vendor vendor = new Vendor();
		vendor.setKey(vendorKey);
		vendor.setName("Hendrik Test Vendor 22");

		vendor.setIsActive(Boolean.TRUE);
		vendor.setIsOneTime(Boolean.FALSE);
		vendor.setIsOnHold(Boolean.FALSE);




	ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
		addresses.getVendorAddress().add(crtAddress("L000000003"));

		vendor.setAddresses(addresses);

		VendorClassKey vendorClassKey = new VendorClassKey();
		vendorClassKey.setId(GPVendorClassEnum.PLASTICS.getGPName());

		CompanyKey companyKey = new CompanyKey();
		companyKey.setId(companyId);
		vendorClassKey.setCompanyKey(companyKey);
		vendor.setClassKey(vendorClassKey);

		/*
		 * ADDITONAL STUFF
		 *
		 */
/*		BankAccountKey  bankAccountKey = new BankAccountKey();
		bankAccountKey.setCompanyKey(companyKey);
		bankAccountKey.setId("GRANTS");
		vendor.setBankAccountKey(bankAccountKey);
*/

/*
		vendor.setMinimumPayment(null);
		MaximumWriteoff maximumWriteoff = new MaximumWriteoff();
		maximumWriteoff.setSpecialAmount(MaximumWriteoffSpecialAmount.NOT_ALLOWED);
		vendor.setMaximumWriteoff(maximumWriteoff);
		vendor.setIsActive(Boolean.TRUE);
		vendor.setIsOnHold(Boolean.FALSE);
		vendor.setIsOneTime(Boolean.FALSE);
*/
	//	GPService.instance().createVendorByKeyForCompany(companyId, vendor);

	/*
		  //Create new address for Vendor
		  VendorAddress address = crtAddress("L000000022");



		  GPService.instance().createVendorAddressForCompany(companyId, address);



		Vendor v = GPService.instance().vendorByKeyForCompany(2, "L000000022");
		System.out.println(v.getName());

		// Update a  vendor

		Vendor vendor = GPService.instance().vendorByKeyForCompany(2, "L000000022");
		vendor.setName("Hendrik Test Vendor 2 Update");

		ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
		List<VendorAddress> vendorAddress = new ArrayList<VendorAddress>();
		vendorAddress.add(crtAddress("L000000022"));
		vendor.setAddresses(addresses);

		GPService.instance().updateVendorByKeyForCompany(companyId, vendor);

		Vendor v = GPService.instance().vendorByKeyForCompany(2, "L000000022");
		System.out.println(v.getName());
*/
	}

	public static void main(String[] args) {
		try {
			System.out.println("START: " +(new java.util.Date()));
		//GPWSTester tester =  new GPWSTester();
		//	tester.testCases();
		 // GPService.instance().vendorAddessList(2, "L000000022");
	//		Vendor vendor = GPService.instance().vendorByKeyForCompany(2, "L000000022");
	//		vendor.setName("Hendrik Test Vendor 1A");
	//		GPService.instance().updateVendorByKeyForCompany(2, vendor);
		//	System.out.println("================");
		  //  int vendorId = 7;Date t =  new Date();
		//	String s = "PMIS"+vendorId+SARSConstants.dds.format(t);
		//	System.out.println(s);
			//GPService.instance().getGLTransactionList(2, 2018, 2019);

			//Vendor vendor = GPService.instance().vendorByKeyForCompany(2, "L000000022");
			//System.out.println(	ReflectionToStringBuilder.toString(vendor,ToStringStyle.MULTI_LINE_STYLE,true,true));
		//	GPService.instance().getPayablesInvoiceList(2, "L280733215");
		//	GPService.instance().updateVendorByKeyForCompany(2, vendor);
			//Vendor vendor = GPService.instance().vendorByKeyForCompany(2, "L000700016");


	//		System.out.println(	ReflectionToStringBuilder.toString(vendor,ToStringStyle.MULTI_LINE_STYLE,true,true));
		//System.out.println(ObjectUtils.toString(vendor, true));
			//GPService.instance().allGLPostingAccountsForCompany(2);

		//	GPService.instance().getGLTransactionList(2,2018,2018);
		//	GPService.instance().postSarsLevies(2, 182);
		//	GPService.instance().createPayablesInvoice(2, "L000000022", BigDecimal.valueOf(100.19));
		//	SimpleDateFormat sdf = new SimpleDateFormat("MMyyyy");
		//	GPService.instance().postSarsLevies(2, 173,sdf.parse("122017"));
		//	Vendor vendor = GPService.instance().vendorByKeyForCompany(GPService.instance().COMPANY_ID,"L000746562");
			
			// GPService.instance().createPayablesInvoice(2, "L000725046", BigDecimal.valueOf(100.0));
			
			System.out.println("DONE: " +(new java.util.Date()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
