package ru.mmk.scriptmanager.client.presenter;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.Response;
import com.smartgwt.client.util.SC;

public class CommonRequestCallback {
	protected void reportBadResponse(Response response) {
		SC.say(response.getStatusText());
	}

	protected void reportError(Request request, Throwable exception) {
		SC.say("Error on send request");
		SC.logInfo(exception.getMessage());
	}
}
