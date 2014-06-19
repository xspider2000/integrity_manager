package ru.mmk.scriptmanager.client.datasource;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.util.JSOHelper;

public class RestLogDataSource extends AbstractRestDataSource {
	private static RestLogDataSource instance = null;
	private static Integer nodeId;

	public static RestLogDataSource getInstance() {
		if (instance == null) {
			instance = new RestLogDataSource("logDS");
		}
		return instance;
	}

	public static void setNodeId(Integer nodeId) {
		RestLogDataSource.nodeId = nodeId;
	}

	private RestLogDataSource(String id) {
		super(id);
		setDataURL("rest/api/log");
	}

	protected void init() {
		setDataFormat(DSDataFormat.JSON);
		setJsonRecordXPath("/");

		DataSourceIntegerField nodeIdField = new DataSourceIntegerField("id", "id");
		nodeIdField.setPrimaryKey(true);
		nodeIdField.setCanEdit(false);
		nodeIdField.setRequired(true);

		DataSourceTextField nameField = new DataSourceTextField("name", "name", 20);
		setFields(nodeIdField, nameField);
	}

	@SuppressWarnings("rawtypes")
	protected void postProcessTransform(DSRequest dsRequest) {
		initRequestHeaders(dsRequest);

		StringBuilder url = new StringBuilder(getRestServiceUrl());
		Map dataMap = dsRequest.getAttributeAsMap("data");
		if (dsRequest.getOperationType() == DSOperationType.FETCH) {
			url.append("/").append(nodeId);
		}
		if (dsRequest.getOperationType() == DSOperationType.ADD) {
			JavaScriptObject jsoData = dsRequest.getData();
			JSOHelper.deleteAttributeIfExists(jsoData, "__module");
		} else if (dsRequest.getOperationType() == DSOperationType.REMOVE) {
			// append the primary key
			url.append("/").append(dataMap.get("id"));
		}
		dsRequest.setActionURL(URL.encode(url.toString()));
	}

	@Override
	protected String getRestServiceUrl() {
		return "rest/api/log";
	}

}
