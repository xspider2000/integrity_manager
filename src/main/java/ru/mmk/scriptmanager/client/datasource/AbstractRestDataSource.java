package ru.mmk.scriptmanager.client.datasource;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;
import com.smartgwt.client.util.JSOHelper;
import com.smartgwt.client.util.JSON;

public abstract class AbstractRestDataSource extends RestDataSource {
	public AbstractRestDataSource(String id) {
		setID(id);
		setClientOnly(false);
		setDataProtocol(DSProtocol.POSTXML);

		// set up FETCH to use GET requests
		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		DSRequest fetchProps = new DSRequest();
		fetchProps.setHttpMethod("GET");
		fetch.setRequestProperties(fetchProps);

		// set up ADD to use POST requests
		OperationBinding add = new OperationBinding();
		add.setOperationType(DSOperationType.ADD);
		DSRequest addProps = new DSRequest();
		addProps.setHttpMethod("POST");
		add.setRequestProperties(addProps);

		// set up UPDATE to use PUT
		OperationBinding update = new OperationBinding();
		update.setOperationType(DSOperationType.UPDATE);
		DSRequest updateProps = new DSRequest();
		updateProps.setHttpMethod("PUT");
		update.setRequestProperties(updateProps);

		// set up REMOVE to use DELETE
		OperationBinding remove = new OperationBinding();
		remove.setOperationType(DSOperationType.REMOVE);
		DSRequest removeProps = new DSRequest();
		removeProps.setHttpMethod("DELETE");
		remove.setRequestProperties(removeProps);

		// apply all the operational bindings
		setOperationBindings(fetch, add, update, remove);

		init();
	}

	@Override
	protected void transformResponse(DSResponse dsResponse, DSRequest dsRequest, Object data) {
		Record[] records = Record.convertToRecordArray((JavaScriptObject) data);
		dsResponse.setData(records);
		super.transformResponse(dsResponse, dsRequest, data);
	}

	@Override
	protected Object transformRequest(DSRequest dsRequest) {
		super.transformRequest(dsRequest);
		// now post process the request for our own means
		postProcessTransform(dsRequest);
		return JSON.encode(dsRequest.getData());
	}

	/*
	 * Implementers can override this method to create a different override.
	 */
	@SuppressWarnings("rawtypes")
	protected void postProcessTransform(DSRequest dsRequest) {
		initRequestHeaders(dsRequest);

		StringBuilder url = new StringBuilder(getRestServiceUrl());
		Map dataMap = dsRequest.getAttributeAsMap("data");
		if (dsRequest.getOperationType() == DSOperationType.ADD) {
			JavaScriptObject jsoData = dsRequest.getData();
			JSOHelper.deleteAttributeIfExists(jsoData, "__module");
		} else if (dsRequest.getOperationType() == DSOperationType.REMOVE) {
			// append the primary key
			url.append("/").append(dataMap.get("id"));
		}
		dsRequest.setActionURL(URL.encode(url.toString()));
	}

	protected void initRequestHeaders(DSRequest dsRequest) {
		Map<String, String> httpHeaders = new HashMap<String, String>();
		httpHeaders.put("Accept", "application/json");
		httpHeaders.put("Content-Type", "application/json");
		dsRequest.setHttpHeaders(httpHeaders);
	}

	protected abstract void init();

	protected abstract String getRestServiceUrl();
}
