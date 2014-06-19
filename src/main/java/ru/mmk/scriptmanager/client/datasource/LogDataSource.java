package ru.mmk.scriptmanager.client.datasource;

import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;

public class LogDataSource extends RestDataSource {
	private static LogDataSource instance = null;

	public static LogDataSource getInstance() {
		if (instance == null) {
			instance = new LogDataSource("logDS");
		}
		return instance;
	}

	private LogDataSource(String string) {
		setDataFormat(DSDataFormat.XML);
		setID(id);

		DataSourceTextField dateField = new DataSourceTextField("date", "date");
		DataSourceTextField priorityField = new DataSourceTextField("priority", "priority");
		DataSourceTextField messageField = new DataSourceTextField("message", "message");
		setFields(dateField, priorityField, messageField);

		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		fetch.setDataProtocol(DSProtocol.POSTMESSAGE);
		setOperationBindings(fetch);
		setFetchDataURL("rest/api/log/fetch");
	}

	// @Override
	// protected Object transformRequest(DSRequest dsRequest) {
	// Map<String, String> httpHeaders = new HashMap<String, String>();
	// httpHeaders.put("Accept", "application/json");
	// httpHeaders.put("Content-Type", "application/json");
	// dsRequest.setHttpHeaders(httpHeaders);
	// return super.transformRequest(dsRequest);
	// }

}
