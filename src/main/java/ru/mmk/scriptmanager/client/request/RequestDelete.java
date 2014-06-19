package ru.mmk.scriptmanager.client.request;

import com.google.gwt.http.client.RequestBuilder;

public class RequestDelete extends CommonRequest {
	public RequestDelete(String url) {
		super(RequestBuilder.DELETE, url);
	}
}
