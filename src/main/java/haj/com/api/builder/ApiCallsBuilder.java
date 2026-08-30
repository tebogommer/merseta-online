package haj.com.api.builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.FormBodyPartBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpMethod;

public abstract class ApiCallsBuilder {
	protected String              subscriptionKey;
	protected String              uriBase;
	protected Map<String, String> params;
	protected Map<String, String> headers;
	protected HttpMethod          methodType;
	protected JSONObject          jsonObject;
	protected List<FormBodyPart>  formParts;
	protected String              userName;
	protected String              password;
	protected String              jsonString;

	public ApiCallsBuilder() {
		params = new HashMap<>();
		headers = new HashMap<>();
		jsonObject = new JSONObject();
	}

	public ApiCallsBuilder setAuthCredentials(String username, String password) {
		this.userName = username;
		this.password = password;
		return this;
	}

	public ApiCallsBuilder subscriptionKey(String subscriptionKey) {
		this.subscriptionKey = subscriptionKey;
		return this;
	}

	public ApiCallsBuilder setupUrl(Map<String, String> params) {
		for (Entry<String, String> entry : params.entrySet()) {
			uriBase = uriBase.replace(entry.getKey(), entry.getValue());
		}
		return this;
	}

	public ApiCallsBuilder replaceUrlKey(String key, String value) {
		uriBase = uriBase.replace(key, value);
		return this;
	}

	public ApiCallsBuilder addUrlPath(String url) {
		uriBase += "/" + url;
		return this;
	}

	public ApiCallsBuilder replaceUrl(String url) {
		uriBase = url;
		return this;
	}

	public ApiCallsBuilder setJsonString(String jsonString) {
		this.jsonString = jsonString;
		return this;
	}

	public ApiCallsBuilder addParams(String key, String value) {
		checkMaps();
		params.put(key, value);
		return this;
	}

	public ApiCallsBuilder addParams(Map<String, String> params) {
		checkMaps();
		for (Entry<String, String> entry : params.entrySet())
			addParams(entry.getKey(), entry.getValue());
		return this;
	}

	public ApiCallsBuilder setContentType(ContentType contentType) {
		headers.put("Content-Type", contentType.getMimeType());
		return this;
	}

	public ApiCallsBuilder addDefaultHeader() {
		checkMaps();
		headers.put("Content-Type", "application/json");
		headers.put("Ocp-Apim-Subscription-Key", subscriptionKey);
		return this;
	}

	public ApiCallsBuilder addHeader(String key, String value) {
		checkMaps();
		headers.put(key, value);
		return this;
	}

	public ApiCallsBuilder addMethodType(HttpMethod methodType) {
		this.methodType = methodType;
		return this;
	}

	public ApiCallsBuilder addJsonParam(String key, String value) {
		jsonObject.put(key, value);
		return this;
	}

	public ApiCallsBuilder addJsonArrayToObject(String key, String value) {
		JSONArray jsonArray = new JSONArray();
		if (jsonObject.has(key)) jsonArray = jsonObject.getJSONArray(key);
		jsonArray.put(value);
		jsonObject.put(key, jsonArray);
		return this;
	}

	public ApiCallsBuilder addFormPart(ContentBody formBodyPart, String key) {
		if (this.formParts == null) formParts = new ArrayList<>();
		formParts.add(FormBodyPartBuilder.create(key, formBodyPart).build());
		return this;
	}

	public ApiCalls build() {
		checkMaps();
		return new ApiCalls(this);
	}

	private void checkMaps() {
		if (this.params == null) {
			params = new HashMap<>();
		}
		if (this.headers == null || this.headers.size() == 0) {
			headers = new HashMap<>();
		}
	}
}
