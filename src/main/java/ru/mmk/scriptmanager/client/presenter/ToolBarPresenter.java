package ru.mmk.scriptmanager.client.presenter;

import ru.mmk.scriptmanager.client.request.CommonRequest;
import ru.mmk.scriptmanager.client.request.RequestDelete;
import ru.mmk.scriptmanager.client.request.RequestPost;
import ru.mmk.scriptmanager.client.ui.SideBarLayout;
import ru.mmk.scriptmanager.client.ui.ToolBarLayout;
import ru.mmk.scriptmanager.server.domain.Node;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.smartgwt.client.util.SC;

public class ToolBarPresenter {
	private final static String SCHEDULLED_NODES = "rest/api/scheduler/nodes/";
	private ToolBarLayout toolBarLayout;
	private SideBarLayout sideBarLayout;

	public ToolBarPresenter(ToolBarLayout toolBarLayout, SideBarLayout sideBarLayout) {
		this.toolBarLayout = toolBarLayout;
		this.sideBarLayout = sideBarLayout;
	}

	public void addNewNode(Integer parentId, String name) {
		sideBarLayout.addNewNode(parentId, name);
	}

	public void removeSelectedNode(Integer nodeId) {
		sideBarLayout.removeNode(nodeId);
	}

	public void setScheduleToSelectedNode(String schedule) {
		Node node = sideBarLayout.getSelectedNode();
		node.setSchedule(schedule);
		sideBarLayout.updateNode(node);
	}

	public void startSchedulingSelectedNode() {
		Integer nodeId = sideBarLayout.getSelectedNode().getId();
		String url = SCHEDULLED_NODES + nodeId;
		CommonRequest request = new RequestPost(url);
		request.setRequestCallback(new StartNodeSchedullingCallback());
		request.send();
	}

	public void stopSchedulingSelectedNode() {
		Integer nodeId = sideBarLayout.getSelectedNode().getId();
		String url = SCHEDULLED_NODES + nodeId;
		CommonRequest request = new RequestDelete(url);
		request.setRequestCallback(new StopNodeSchedullingCallback());
		request.send();
	}

	private class StartNodeSchedullingCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_CREATED) {
				sideBarLayout.selectedNodeIsScheduled(true);
				toolBarLayout.selectedNodeIsScheduled(true);
				SC.say("Запланирован к выполнению");
			} else {
				reportBadResponse(response);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			reportError(request, exception);
		}
	}

	private class StopNodeSchedullingCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				sideBarLayout.selectedNodeIsScheduled(false);
				toolBarLayout.selectedNodeIsScheduled(false);
				SC.say("Остановлено выполнение по расписанию");
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
