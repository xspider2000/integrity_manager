package ru.mmk.scriptmanager.client.request;

import com.google.gwt.http.client.RequestBuilder;

public class RequestGet extends CommonRequest {
	public RequestGet(String url) {
		super(RequestBuilder.GET, url);
	}
}
