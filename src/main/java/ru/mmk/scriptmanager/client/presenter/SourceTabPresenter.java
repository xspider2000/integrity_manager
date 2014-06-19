package ru.mmk.scriptmanager.client.presenter;

import ru.mmk.scriptmanager.client.request.CommonRequest;
import ru.mmk.scriptmanager.client.request.RequestPost;
import ru.mmk.scriptmanager.client.request.RequestPut;
import ru.mmk.scriptmanager.client.ui.SourceTab;
import ru.mmk.scriptmanager.client.util.JsonRequestBuilder;
import ru.mmk.scriptmanager.client.util.JsonResponseParser;
import ru.mmk.scriptmanager.server.domain.Source;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.smartgwt.client.util.SC;

public class SourceTabPresenter {
	private final static String SOURCES = "rest/api/sources";
	private final static String EXECUTE_SOURCE = "rest/api/execute/source";
	private SourceTab sourceTab;

	public SourceTabPresenter(SourceTab sourceTab) {
		this.sourceTab = sourceTab;
	}

	public void saveSource() {
		if (sourceTabNewNameIsValid()) {
			Source source = sourceTab.getSource();
			source.setName(sourceTab.getNewName());
			source.setCode(sourceTab.getCodeText());

			CommonRequest request = new RequestPut(SOURCES);
			request.setRequestCallback(new SaveSourceRequestCallback());
			request.setRequestData(JsonRequestBuilder.buildSourceRequest(source));
			request.send();
		} else {
			SC.say("Имя источника не может быть пустым!");
		}
	}

	private boolean sourceTabNewNameIsValid() {
		return sourceTab.getNewName() != null && !sourceTab.getNewName().equals("");
	}

	private class SaveSourceRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				Source savedSource = JsonResponseParser.parseSourceResponse(response.getText());
				sourceTab.setTitle(savedSource.getName());
				SC.say("Источник сохранен");
			} else {
				reportBadResponse(response);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			reportError(request, exception);
		}
	}

	public void setSourceAsMaster() {
		if (sourceTabNewNameIsValid()) {
			Source source = sourceTab.getSource();
			CommonRequest request = new RequestPut(SOURCES + "/setasmaster");
			request.setRequestData(JsonRequestBuilder.buildSourceRequest(source));
			request.setRequestCallback(new SetAsMasterRequestCallback());
			request.send();
		} else {
			SC.say("Имя источника не может быть пустым!");
		}
	}

	private class SetAsMasterRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				SC.say("Источник установлен как мастер");
			} else {
				reportBadResponse(response);
			}
		}

		@Override
		public void onError(Request request, Throwable exception) {
			reportError(request, exception);
		}
	}

	public void executeSource() {
		Source source = sourceTab.getSource();
		source.setName(sourceTab.getNewName());
		source.setCode(sourceTab.getCodeText());

		CommonRequest request = new RequestPost(EXECUTE_SOURCE);
		request.setRequestData(JsonRequestBuilder.buildSourceRequest(source));
		request.setRequestCallback(new ExecuteRequestCallback());
		request.send();
	}

	private class ExecuteRequestCallback extends CommonRequestCallback implements RequestCallback {
		@Override
		public void onResponseReceived(Request request, Response response) {
			if (response.getStatusCode() == Response.SC_OK) {
				String codeOutput = JsonResponseParser.parseOutputFromExecuteResponse(response.getText());
				sourceTab.setOutput(codeOutput);
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
