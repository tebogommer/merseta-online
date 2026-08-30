package haj.com.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import haj.com.framework.AbstractService;
import haj.com.json.Weather;

// TODO: Auto-generated Javadoc
/**
 * The Class WeatherService.
 */
public class WeatherService extends AbstractService {

	/** The Constant USER_AGENT. */
	private String USER_AGENT = "Mozilla/5.0";
	
	/** The apixu url. */
	private String apixu_url="http://api.apixu.com/v1/forecast.json?key=169a83d2eb664aac8b0131325172209&q=";
	
	/** The weather service. */
	private  static WeatherService weatherService = null;
	
	/**
	 * Instance.
	 *
	 * @return the weather service
	 */
	public static synchronized WeatherService instance() {
		if (weatherService == null) {
			weatherService = new WeatherService();
		}
		return weatherService;
	}
	
	/**
	 * By lat lang.
	 *
	 * @param latitude the latitude
	 * @param longitude the longitude
	 * @return the weather
	 * @throws Exception the exception
	 */
	public  Weather byLatLang(Double latitude, Double longitude) throws Exception {
		return sendGet(latitude.toString()+","+longitude.toString());
	}
	
	/**
	 * By city.
	 *
	 * @param city the city
	 * @return the weather
	 * @throws Exception the exception
	 */
	public  Weather byCity(String city) throws Exception {
		return sendGet(city);
	}
	
	/**
	 * Send get.
	 *
	 * @param parms the parms
	 * @return the weather
	 * @throws Exception the exception
	 */
	private  Weather sendGet(String parms) throws Exception {

		String url =  apixu_url+URLEncoder.encode(parms, "UTF-8");
		
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

		//print result
		//System.out.println(response.toString());
	    Gson gson = new GsonBuilder().create();
	    return gson.fromJson(response.toString(), Weather.class);
	}
	
	/**
/*
Key: 169a83d2eb664aac8b0131325172209 
current: http://api.apixu.com/v1/current.json?key=169a83d2eb664aac8b0131325172209&q=Benoni
forecat: http://api.apixu.com/v1/forecast.json?key=169a83d2eb664aac8b0131325172209&q=Benoni
 */
}
