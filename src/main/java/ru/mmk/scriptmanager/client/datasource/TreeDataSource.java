package ru.mmk.scriptmanager.client.datasource;

import com.smartgwt.client.data.OperationBinding;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.DSOperationType;
import com.smartgwt.client.types.DSProtocol;

public class TreeDataSource extends RestDataSource {

	private static TreeDataSource instance = null;

	public static TreeDataSource getInstance() {
		if (instance == null) {
			instance = new TreeDataSource("treeDS");
		}
		return instance;
	}

	private TreeDataSource(String id) {
		setDataFormat(DSDataFormat.XML);
		setID(id);

		DataSourceIntegerField nodeIdField = new DataSourceIntegerField("id", "id");
		nodeIdField.setPrimaryKey(true);
		nodeIdField.setCanEdit(false);
		nodeIdField.setRequired(true);

		DataSourceIntegerField parentIdField = new DataSourceIntegerField("parentId", "parentId");
		parentIdField.setForeignKey(id + ".id");

		DataSourceTextField nameField = new DataSourceTextField("name", "name", 20);
		setFields(nodeIdField, parentIdField, nameField);

		OperationBinding fetch = new OperationBinding();
		fetch.setOperationType(DSOperationType.FETCH);
		fetch.setDataProtocol(DSProtocol.POSTXML);

		OperationBinding add = new OperationBinding();
		add.setOperationType(DSOperationType.ADD);
		add.setDataProtocol(DSProtocol.POSTXML);

		OperationBinding update = new OperationBinding();
		update.setOperationType(DSOperationType.UPDATE);
		update.setDataProtocol(DSProtocol.POSTXML);

		OperationBinding remove = new OperationBinding();
		remove.setOperationType(DSOperationType.REMOVE);
		remove.setDataProtocol(DSProtocol.POSTXML);

		setOperationBindings(fetch, add, update, remove);

		setFetchDataURL("rest/api/nodes/fetch");
		setAddDataURL("rest/api/nodes/add");
		setUpdateDataURL("rest/api/nodes/update");
		setRemoveDataURL("rest/api/nodes/remove");
	}

	/*
	 * Пока не возможно передавать данные в формате JSON потому, что Jackson не
	 * поддерживает некоторые xml аннотации например:
	 * 
	 * @XmlElementWrapper, @XmlElement
	 */

	// @Override
	// protected Object transformRequest(DSRequest dsRequest) {
	// Map<String, String> headers = new HashMap<String, String>();
	// headers.put("Accept", "application/json");
	// headers.put("Content-Type", "application/json");
	// dsRequest.setHttpHeaders(headers);
	// return super.transformRequest(dsRequest);
	// }
}
