package haj.com.rest.idverification;

import org.apache.http.entity.ContentType;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpMethod;

import haj.com.api.builder.ApiCalls;
import haj.com.api.builder.ApiCallsBuilder;
import haj.com.entity.Users;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;
import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class AddressService.
 */
public class IdVerificationService extends ApiCallsBuilder {
	private static final NSDMSLogger logger = NSDMSLogger.getLogger(IdVerificationService.class);
	public IdVerificationService() {
		super();
		this.uriBase = NSDMSConfiguration.getString("system.verifyid.pbverify.url");
	}

	public void authenticate() throws Exception {
		ApiCalls   api = this.addUrlPath("authenticate")
							 .addMethodType(HttpMethod.POST)
							 .setContentType(ContentType.APPLICATION_JSON)
							 .addParams("memberkey", NSDMSConfiguration.getString("system.verifyid.pbverify.username"))
							 .addParams("password", NSDMSConfiguration.getString("system.verifyid.pbverify.password"))
							 .build();
		JSONObject jo  = api.callService();
		System.out.println(ApiCalls.prettify(jo.toString()));
	}

	public IdVerificationRealTime idVerificationRealTime(String idNUmber, String reference) throws Exception {
		ApiCalls   api = this.addUrlPath("pbverify-real-time-id-verification")
							 .addMethodType(HttpMethod.POST)
							 .setContentType(ContentType.APPLICATION_JSON)
							 .addParams("memberkey", NSDMSConfiguration.getString("system.verifyid.pbverify.username"))
							 .addParams("password", NSDMSConfiguration.getString("system.verifyid.pbverify.password"))
							 .addParams("consumer_details[idNumber]", idNUmber)
							 .addParams("consumer_details[yourReference]", reference)
							 .build();
		JSONObject jo  = api.callService();
		System.out.println(ApiCalls.prettify(jo.toString()));
		Gson gson = new Gson();
		return gson.fromJson(api.prettify(jo.toString()), IdVerificationRealTime.class);
	}

	public IdVerificationRealTime idVerificationRealTime(Users users) throws Exception {		
		return idVerificationRealTime(users.getRsaIDNumber(), users.getFirstName() + users.getLastName());
	}

	public IdVerification idVerification(String idNUmber, String reference) throws Exception {
		ApiCalls   api = this.addUrlPath("pbverify-profile-id-verification")
							 .addMethodType(HttpMethod.POST)
							 .setContentType(ContentType.APPLICATION_JSON)
							 .addParams("memberkey", NSDMSConfiguration.getString("system.verifyid.pbverify.username"))
							 .addParams("password", NSDMSConfiguration.getString("system.verifyid.pbverify.password"))
							 .addParams("consumer_details[idNumber]", idNUmber)
							 .addParams("consumer_details[yourReference]", reference)
							 .build();
		JSONObject jo  = api.callService();
		Gson gson = new Gson();
		return gson.fromJson(api.prettify(jo.toString()), IdVerification.class);
	}

	public static void main(String[] args) {
		try {
			Users user = new Users();
			user.setRsaIDNumber("9002221026088");
			user.setFirstName("");
			user.setLastName("");
			IdVerificationService ivs = new IdVerificationService();
			IdVerificationRealTime irt = ivs.idVerificationRealTime(user);
			System.out.println(irt.getRealTimeResults().getFirstNames());
			System.out.println(irt.getRealTimeResults().getDob());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
