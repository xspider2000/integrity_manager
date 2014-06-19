package ru.mmk.scriptmanager.client.presenter;

import ru.mmk.scriptmanager.client.request.CommonRequest;
import ru.mmk.scriptmanager.client.request.RequestDelete;
import ru.mmk.scriptmanager.client.request.RequestGet;
import ru.mmk.scriptmanager.client.request.RequestPost;
import ru.mmk.scriptmanager.client.ui.SideBarLayout;
import ru.mmk.scriptmanager.client.ui.SourceTab;
import ru.mmk.scriptmanager.client.ui.SourceTabs;
import ru.mmk.scriptmanager.client.util.JsonRequestBuilder;
import ru.mmk.scriptmanager.client.util.JsonResponseParser;
import ru.mmk.scriptmanager.server.domain.Source;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.smartgwt.client.util.SC;

public class SourceTabsPresenter {
	private final static String SOURCES = "rest/api/sources";
	private final static String COMPARE_SOURCES = "rest/api/comparesources?nodeId={nodeId}";
	private SourceTabs sourceTabs;
	private SideBarLayout sideBarLayout;

	public SourceTabsPresenter(SourceTabs sourceTabs) {
		this.sourceTabs = sourceTabs;
	}

	public void addSource() {
		Integer nodeId = sourceTabs.getSelectedNode().getId();
		Source source = new Source(nodeId, "Новый Источник", "");
		CommonRequest request = new RequestPost(SOURCES);
		request.setRequestData(JsonRequestBuilder.buildSourceRequest(source));
		request.setRequestCallback(new AddSourceRequestCallback());
		request.send();
	}

	public void setSideBarLayout(SideBarLayout sideBarLayout) {
		this.sideBarLayout = sideBarLayout;
	}

	class AddSourceRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_CREATED) {
				Source addedSource = JsonResponseParser.parseSourceResponse(response.getText());
				SourceTab tabSource = new SourceTab(addedSource);
				sourceTabs.getTabSet().addTab(tabSource);
			} else {
				reportBadResponse(response);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			reportError(request, exception);
		}
	}

	public void removeSource() {
		final SourceTab selectedTab = (SourceTab) sourceTabs.getTabSet().getSelectedTab();
		Integer sourceId = selectedTab.getSource().getId();
		CommonRequest request = new RequestDelete(SOURCES + "/" + sourceId);
		request.setRequestCallback(new RemoveSourceRequestCallback(selectedTab));
		request.send();
	}

	class RemoveSourceRequestCallback extends CommonRequestCallback implements RequestCallback {
		private final SourceTab selectedTab;

		public RemoveSourceRequestCallback(SourceTab selectedTab) {
			this.selectedTab = selectedTab;
		}

		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				sourceTabs.getTabSet().removeTab(selectedTab);
			} else {
				reportBadResponse(response);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			reportError(request, exception);
		}
	}

	public void compareSources() {
		Integer nodeId = sourceTabs.getSelectedNode().getId();
		CommonRequest request = new RequestGet(COMPARE_SOURCES.replace("{nodeId}", nodeId.toString()));
		request.setRequestCallback(new CompareRequestCallback());
		request.send();
	}

	class CompareRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				sideBarLayout.updateTree();
				SC.say("Сравнение источников закончено");
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
