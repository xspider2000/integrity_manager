package ru.mmk.scriptmanager.client.presenter;

import ru.mmk.scriptmanager.client.request.RequestGet;
import ru.mmk.scriptmanager.client.ui.ComparisonTab;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class ComparisonTabPresenter {
	private final String COMPARE_REQUEST = "rest/api/comparesources?nodeId={nodeId}";
	private ComparisonTab comparisonTab;

	public ComparisonTabPresenter(ComparisonTab comparisonTab) {
		this.comparisonTab = comparisonTab;
	}

	public void compareDataOfSources() {
		Integer nodeId = comparisonTab.getSelectedNode().getId();
		RequestGet request = new RequestGet(COMPARE_REQUEST.replace("{nodeId}", nodeId.toString()));
		request.setRequestCallback(new ComparisonRequestCallback());
		request.send();
	}

	class ComparisonRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				comparisonTab.setComparisonData(response.getText());
			} else {
				reportBadResponse(response);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			reportError(request, exception);
		}
	}
}
