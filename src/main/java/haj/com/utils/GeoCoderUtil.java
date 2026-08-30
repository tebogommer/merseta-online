package haj.com.utils;

import java.io.Serializable;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.LatLng;

// TODO: Auto-generated Javadoc
/**
 * The Class GeoCoderUtil.
 */
public class GeoCoderUtil implements Serializable {
	
	/** The Constant geocoder. */
	public static final Geocoder geocoder = new Geocoder();
	
	/**
	 * Calc lat lng.
	 *
	 * @param address the address
	 * @return the lat lng
	 * @throws Exception the exception
	 */
	public static LatLng calcLatLng(String address) throws Exception {
		System.out.println("calcLatLng---");
		LatLng ll = null;
		address += ", South Africa";
		System.out.println("address---"+address);
		//via Proxy
	/*	final AdvancedGeoCoder advancedGeoCoder = new AdvancedGeoCoder();
		advancedGeoCoder.getHttpClient().getHostConfiguration().setProxy("172.17.4.37", 8080);
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = advancedGeoCoder.geocode(geocoderRequest);
	*/	
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(address).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
		System.out.println("geocoderResponse---"+geocoderResponse);
		System.out.println("geocoderResponse---"+geocoderResponse.getStatus());
		for (GeocoderResult result : geocoderResponse.getResults()){
			ll = result.getGeometry().getLocation();
		}
		return ll;
	}
}
