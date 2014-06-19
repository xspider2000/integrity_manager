package ru.mmk.scriptmanager.client.request;

import com.google.gwt.http.client.RequestBuilder;

public class RequestPost extends CommonRequest {
	public RequestPost(String url) {
		super(RequestBuilder.POST, url);
	}
}
