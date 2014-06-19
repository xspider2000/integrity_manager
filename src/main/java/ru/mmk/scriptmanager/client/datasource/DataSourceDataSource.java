package ru.mmk.scriptmanager.client.datasource;

import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;

public class DataSourceDataSource extends RestDataSource {
	private static DataSourceDataSource instance = null;

	public static DataSourceDataSource getInstance() {
		if (instance == null) {
			instance = new DataSourceDataSource("ConnectionDataDS");
		}
		return instance;
	}

	private DataSourceDataSource(String id) {
		setDataFormat(DSDataFormat.XML);
		setID(id);
		initDataSourceFields();
		initFetchMethod();
	}

	private void initDataSourceFields() {
		DataSourceTextField nameField = new DataSourceTextField("poolName", "name");
		DataSourceTextField driverField = new DataSourceTextField("driverClass", "driver");
		DataSourceTextField urlField = new DataSourceTextField("connectionURL", "url");
		setFields(nameField, urlField, driverField);
	}

	private void initFetchMethod() {
		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		fetch.setDataProtocol(DSProtocol.POSTXML);
		setOperationBindings(fetch);
		setFetchDataURL("rest/api/connection_data/fetch");
	}

}
