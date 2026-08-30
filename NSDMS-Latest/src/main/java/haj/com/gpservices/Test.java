package haj.com.gpservices;

import java.net.Authenticator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.MessageContext;

import com.microsoft.schemas.dynamics._2006._01.Context;
import com.microsoft.schemas.dynamics.gp._2006._01.BetweenRestrictionOfNullableOfInt32;
import com.microsoft.schemas.dynamics.gp._2006._01.CompanyCriteria;
import com.microsoft.schemas.dynamics.gp._2006._01.DynamicsGP;
import com.microsoft.schemas.dynamics.gp._2006._01.DynamicsGP_Service;
import com.microsoft.schemas.dynamics.security._2006._01.NtlmAuthenticator;

public class Test {

	public static void main(String[] args) {
		try {
			
		     System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
		        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
		 
			
			String username = "MERSETA\\NSDMS";
			String password = "Merseta1!";

			NtlmAuthenticator authenticator = new NtlmAuthenticator(username, password);
			Authenticator.setDefault(authenticator);
			DynamicsGP_Service service = new DynamicsGP_Service();
			DynamicsGP dynamicsGP = service.getLegacyDynamicsGP();
			
			
			   Map<String, Object> req_ctx = ((BindingProvider)dynamicsGP).getRequestContext();
			   req_ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, "http://105.255.170.218:48620/DynamicsGPWebServices/DynamicsGPService.asmx");
			     Map<String, List<String>> headers = new HashMap<String, List<String>>();
			        headers.put("Username", Collections.singletonList(username));
			        headers.put("Password", Collections.singletonList(password));
			        req_ctx.put(MessageContext.HTTP_REQUEST_HEADERS, headers);			   
			   
				    
			Context context =new Context();
			context.setOrganizationKey(null);
			context.setCultureName("en-US");

			BetweenRestrictionOfNullableOfInt32  companyRestriction = new BetweenRestrictionOfNullableOfInt32();
			companyRestriction.setFrom(-32766);
			companyRestriction.setTo(32766);
			
			CompanyCriteria criteria = new CompanyCriteria();
			criteria.setId(companyRestriction);
			dynamicsGP.getCompanyList(criteria, context);
			
			/*

			
			VendorCriteria criteria = new VendorCriteria();
			//criteria.setId(companyRestriction);
			RestrictionOfNullableOfBoolean xx =  new RestrictionOfNullableOfBoolean();
			xx.setEqualValue(true);
			criteria.setIsActive(xx);
			
			 ArrayOfVendorSummary s =	dynamicsGP.getVendorList(criteria, context);
			 List<VendorSummary> l =	s.getVendorSummary();
			 for (VendorSummary vendorSummary : l) {
				System.out.println(vendorSummary.getName());
			}
			*/
			    System.out.println("Done");
//	dynamicsGP.getVendorList(criteria, context)
		
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
