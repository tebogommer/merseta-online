package haj.com.api.builder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpMethod;

public class ApiCalls {

	private String              uriBase;
	private String              userName;
	private String              password;
	private HttpMethod          methodType;
	private Map<String, String> params;
	private Map<String, String> headers;
	private StringEntity        reqEntity;
	private List<FormBodyPart>  formParts;

	protected ApiCalls(ApiCallsBuilder builder) {
		this.uriBase = builder.uriBase;
		this.userName = builder.userName;
		this.password = builder.password;
		this.params = builder.params;
		this.headers = builder.headers;
		this.methodType = builder.methodType;
		this.formParts = builder.formParts;

		try {
			if (builder.jsonString != null && !builder.jsonString.isEmpty()) {
				this.reqEntity = new StringEntity(builder.jsonString);
			} else {
				this.reqEntity = new StringEntity(builder.jsonObject.toString());
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public JSONObject callService() {
		CloseableHttpClient httpClient = getClient();
		JSONObject          jsonObject = null;
		try {
			URIBuilder builder = new URIBuilder(uriBase);
			// Request parameters. All of them are optional.
			for (Entry<String, String> entry : params.entrySet())
				builder.setParameter(entry.getKey(), entry.getValue());

			// Prepare the URI for the REST API call.
			URI          uri      = builder.build();
			HttpResponse response = getResponseObject(uri, httpClient);
			jsonObject = parseObjectResponse(jsonObject, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonObject;
	}

	public JSONArray callServiceList() {
		CloseableHttpClient httpClient = getClient();
		JSONArray           jsonObject = null;
		try {
			URIBuilder builder = new URIBuilder(uriBase);
			// Request parameters. All of them are optional.
			for (Entry<String, String> entry : params.entrySet())
				builder.setParameter(entry.getKey(), entry.getValue());
			// Prepare the URI for the REST API call.
			URI          uri      = builder.build();
			HttpResponse response = getResponseObject(uri, httpClient);
			jsonObject = parseArrayResponse(jsonObject, response);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return jsonObject;
	}

	private CloseableHttpClient getClient() {
		HttpClientBuilder   clientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpClient;
		if (userName != null && !userName.isEmpty()) {
			CredentialsProvider         provider    = new BasicCredentialsProvider();
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, password);
			provider.setCredentials(AuthScope.ANY, credentials);
			httpClient = clientBuilder.setDefaultCredentialsProvider(provider).build();
		} else {
			httpClient = clientBuilder.build();
		}
		return httpClient;
	}

	private HttpResponse getResponseObject(URI uri, CloseableHttpClient httpClient) throws Exception {
		HttpUriRequest request = null;
		switch (methodType) {
			case PUT:
				request = new HttpPut(uri);
				break;
			case POST:
				request = new HttpPost(uri);
				break;
			case DELETE:
				request = new HttpDelete(uri);
				break;
			case PATCH:
				request = new HttpPatch(uri);
				break;
			case HEAD:
				request = new HttpHead(uri);
				break;
			case OPTIONS:
				request = new HttpOptions(uri);
				break;
			default:
				request = new HttpGet(uri);
				break;
		}

		setHeaders(request);

		if (request instanceof HttpEntityEnclosingRequestBase) {
			HttpEntityEnclosingRequestBase herb = (HttpEntityEnclosingRequestBase) request;
			checkMultiPart(herb);
			herb.setEntity(reqEntity);
		}
		return httpClient.execute(request);
	}

	private void checkMultiPart(HttpEntityEnclosingRequestBase httppost) {
		if (formParts != null) {
			MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
			for (FormBodyPart formBodyPart : formParts) {
				reqEntity.addPart(formBodyPart);
			}
			httppost.setEntity(reqEntity.build());
		}
	}

	private JSONObject parseObjectResponse(JSONObject jsonObject, HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// Format and display the JSON response.
			String jsonString = EntityUtils.toString(entity).trim();

			if (jsonString.charAt(0) == '[') {
				jsonObject = new JSONObject();
				JSONArray jsonArray = new JSONArray(jsonString);
				jsonObject.put("array", jsonArray);
			} else if (jsonString.charAt(0) == '{') {
				jsonObject = new JSONObject(jsonString);
			} else {
				System.out.println(jsonString);
			}

		}
		return jsonObject;
	}

	private JSONArray parseArrayResponse(JSONArray jsonObject, HttpResponse response) throws IOException {
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			// Format and display the JSON response.
			String jsonString = EntityUtils.toString(entity).trim();

			if (jsonString.charAt(0) == '[') {
				jsonObject = new JSONArray(jsonString);
			} else {
				System.out.println(jsonString);
			}

		}
		return jsonObject;
	}

	private void setHeaders(HttpUriRequest request) {
		for (Entry<String, String> entry : headers.entrySet())
			request.setHeader(entry.getKey(), entry.getValue());

	}

	public static String prettify(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json   = parser.parse(json_text).getAsJsonObject();
		Gson       gson   = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}
}
