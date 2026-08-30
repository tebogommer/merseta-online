package haj.com.api.builder;

import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MethodTypeEnum {

	private String uriBase;
	private String methodType;
	private Map<String, String> params;
	private Map<String, String> headers;

	protected MethodTypeEnum(ApiCallsBuilder builder) {
		this.uriBase = builder.uriBase;
		this.params = builder.params;
		this.headers = builder.headers;
	}

	public JSONObject callCognitiveAzure(HttpEntity reqEntity) {
		return doCall(reqEntity);
	}

	public JSONObject callCognitiveAzure() {
		return doCall(null);
	}

	private JSONObject doCall(HttpEntity reqEntity) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		JSONObject jsonObject = null;
		try {
			URIBuilder builder = new URIBuilder(uriBase);
			// Request parameters. All of them are optional.
			for (Entry<String, String> entry : params.entrySet())
				builder.setParameter(entry.getKey(), entry.getValue());
			// Prepare the URI for the REST API call.
			URI uri = builder.build();
			
			HttpPut request = new HttpPut(uri);
			// Request headers.
			setHeadersForPut(request);
			// Request body.
			if (reqEntity != null) request.setEntity(reqEntity);
			// Execute the REST API call and get the response entity.
			HttpResponse response = httpClient.execute(request);

			jsonObject = parseResponse(jsonObject, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonObject;
	}

	private JSONObject doPut(HttpEntity reqEntity) {
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		JSONObject jsonObject = null;
		try {
			URIBuilder builder = new URIBuilder(uriBase);
			// Request parameters. All of them are optional.

			for (Entry<String, String> entry : params.entrySet())
				builder.setParameter(entry.getKey(), entry.getValue());

			// Prepare the URI for the REST API call.
			URI uri = builder.build();
			HttpPut request = new HttpPut(uri);
			// Request headers.
			setHeadersForPut(request);
			// Request body.
			if (reqEntity != null) request.setEntity(reqEntity);
			// Execute the REST API call and get the response entity.
			HttpResponse response = httpClient.execute(request);

			jsonObject = parseResponse(jsonObject, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonObject;
	}

	private JSONObject parseResponse(JSONObject jsonObject, HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {

			// Format and display the JSON response.
			System.out.println("REST Response:\n");
			String jsonString = EntityUtils.toString(entity).trim();
			if (jsonString.charAt(0) == '[') {
				JSONArray jsonArray = new JSONArray(jsonString);
				jsonObject = jsonArray.getJSONObject(2);
			} else if (jsonString.charAt(0) == '{') {
				jsonObject = new JSONObject(jsonString);
				System.out.println(jsonObject.toString(2));
			} else {
				System.out.println(jsonString);
			}
		}
		return jsonObject;
	}

	private void setHeaders(HttpPost request) {
		for (Entry<String, String> entry : headers.entrySet())
			request.setHeader(entry.getKey(), entry.getValue());

	}

	private void setHeadersForPut(HttpPut request) {
		for (Entry<String, String> entry : headers.entrySet())
			request.setHeader(entry.getKey(), entry.getValue());

	}

}
