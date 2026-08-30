package haj.com.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import haj.com.framework.AbstractService;
import haj.com.json.IPLocation;
import haj.com.json.Weather;

public class ResolveIPService extends AbstractService {



	private String USER_AGENT = "Mozilla/5.0";

	private String ip_url="http://ip-api.com/json/";

	private  static ResolveIPService resolveIPService = null;

	public static synchronized ResolveIPService instance() {
		if (resolveIPService == null) {
			resolveIPService = new ResolveIPService();
		}
		return resolveIPService;
	}



	public  String sendGet(String ipAddress) throws Exception {
        if (StringUtils.isEmpty(ipAddress)) throw new Exception("IP address is empty");
		String url =  ip_url+URLEncoder.encode(ipAddress, "UTF-8");

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		//int responseCode = con.getResponseCode();
		//System.out.println("\nSending 'GET' request to URL : " + url);
		//System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
	    return response.toString();
	}

	public  IPLocation resolveAddress(String ipAddress) throws Exception {
		String response = sendGet(ipAddress);
	    Gson gson = new GsonBuilder().create();
	    return gson.fromJson(response, IPLocation.class);
	}

	public  IPLocation descodeString(String string) throws Exception {
		 Gson gson = new GsonBuilder().create();
		 return gson.fromJson(string, IPLocation.class);
	 }


}
