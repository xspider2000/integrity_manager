package ru.mmk.scriptmanager.client.datasource;

import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;

public class RestTreeDataSource extends AbstractRestDataSource {

	private static RestTreeDataSource instance = null;

	public static RestTreeDataSource getInstance() {
		if (instance == null) {
			instance = new RestTreeDataSource("treeDS");
		}
		return instance;
	}

	private RestTreeDataSource(String id) {
		super(id);
		setDataURL("rest/api/nodes");
	}

	protected void init() {
		setDataFormat(DSDataFormat.JSON);
		setJsonRecordXPath("/");

		DataSourceIntegerField nodeIdField = new DataSourceIntegerField("id", "id");
		nodeIdField.setPrimaryKey(true);
		nodeIdField.setCanEdit(false);
		nodeIdField.setRequired(true);

		DataSourceIntegerField parentIdField = new DataSourceIntegerField("parentId", "parentId");
		parentIdField.setForeignKey(id + ".id");

		DataSourceTextField nameField = new DataSourceTextField("name", "name", 20);
		setFields(nodeIdField, parentIdField, nameField);
	}

	@Override
	protected String getRestServiceUrl() {
		return "rest/api/nodes";
	}

}
