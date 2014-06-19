package ru.mmk.scriptmanager.client.request;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;

public class CommonRequest {
	private RequestBuilder requestBuilder;

	public CommonRequest(Method httpMethod, String url) {
		requestBuilder = new RequestBuilder(httpMethod, url);
		requestBuilder.setHeader("Accept", "application/json");
		requestBuilder.setHeader("Content-Type", "application/json");
	}

	public void setRequestData(String requestData) {
		requestBuilder.setRequestData(requestData);
	}

	public void setRequestCallback(RequestCallback callback) {
		requestBuilder.setCallback(callback);
	}

	public void send() {
		try {
			requestBuilder.send();
		} catch (RequestException e) {
			throw new RuntimeException(e);
		}
	}
}
