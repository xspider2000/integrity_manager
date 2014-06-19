package ru.mmk.scriptmanager.client.request;

import com.google.gwt.http.client.RequestBuilder;

public class RequestPut extends CommonRequest {
	public RequestPut(String url) {
		super(RequestBuilder.PUT, url);
	}
}
