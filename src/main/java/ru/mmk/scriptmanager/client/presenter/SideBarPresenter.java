package ru.mmk.scriptmanager.client.presenter;

import java.util.List;

import ru.mmk.scriptmanager.client.request.CommonRequest;
import ru.mmk.scriptmanager.client.request.RequestGet;
import ru.mmk.scriptmanager.client.ui.ContentLayout;
import ru.mmk.scriptmanager.client.ui.SideBarLayout;
import ru.mmk.scriptmanager.client.ui.SourceTabs;
import ru.mmk.scriptmanager.client.ui.ToolBarLayout;
import ru.mmk.scriptmanager.client.util.JsonResponseParser;
import ru.mmk.scriptmanager.server.domain.Node;
import ru.mmk.scriptmanager.server.domain.Source;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

public class SideBarPresenter {
	private final String SOURCES_OF_NODE = "rest/api/nodes/{nodeId}/sources";
	private final String COMPARE_SOURCES_OF_NODE = "rest/api/compare/nodes/{id}/sources";

	private SideBarLayout sideBarLayout;
	private ToolBarLayout toolBarLayout;
	private ContentLayout contentLayout;

	public SideBarPresenter(SideBarLayout sideBarLayout, ToolBarLayout toolBarLayout, ContentLayout contentLayout) {
		this.sideBarLayout = sideBarLayout;
		this.toolBarLayout = toolBarLayout;
		this.contentLayout = contentLayout;
	}

	public void onNodeCellClick() {
		setSelectedNodeToTabs();
		getSourcesOfSelectedNode();
		initToolBar();
		clearComparisonTab();
		if (contentLayout.getSelectedTab().equals(contentLayout.getJournalTab())) {
			contentLayout.getJournalTab().getLogsOfSelectedNode();
		}
	}

	private void setSelectedNodeToTabs() {
		Node selectedNode = sideBarLayout.getSelectedNode();
		contentLayout.getSourceTabs().setSelectedNode(selectedNode);
		contentLayout.getJournalTab().setSelectedNode(selectedNode);
		contentLayout.getComparisonTab().setSelectedNode(selectedNode);
	}

	private void initToolBar() {
		Node selectedNode = sideBarLayout.getSelectedNode();
		boolean isScheduled = selectedNode.isScheduled();
		toolBarLayout.selectedNodeIsScheduled(isScheduled);
	}

	private void clearComparisonTab() {
		contentLayout.getComparisonTab().hideTable();
	}

	private void getSourcesOfSelectedNode() {
		Integer nodeId = sideBarLayout.getSelectedNode().getId();
		final String requestUrl = SOURCES_OF_NODE.replace("{nodeId}", nodeId.toString());
		CommonRequest request = new RequestGet(requestUrl);
		request.setRequestCallback(new GetSourcesCallback());
		request.send();
	}

	private class GetSourcesCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				SourceTabs sourceTabs = contentLayout.getSourceTabs();
				List<Source> sourceList = JsonResponseParser.parseSourceListResponse(response.getText());
				sourceTabs.removeAllSourceTabs();
				sourceTabs.addSourceTabs(sourceList);
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
		Integer nodeId = sideBarLayout.getSelectedNode().getId();
		CommonRequest request = new RequestGet(COMPARE_SOURCES_OF_NODE.replace("{id}", nodeId.toString()));
		request.setRequestCallback(new ComparisonRequestCallback());
		request.send();
	}

	private class ComparisonRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {

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
