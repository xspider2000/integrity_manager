package ru.mmk.scriptmanager.client.ui;

import ru.mmk.scriptmanager.client.datasource.DataSourceDataSource;

import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.tab.Tab;

public class DataSourceTab extends Tab {
	final private ListGrid listGrid = new ListGrid();

	public DataSourceTab() {
		setTitle("Данные подключения");
		initAppearance();
		initListGrid();
		setPane(listGrid);
	}

	private void initAppearance() {
		listGrid.setWidth("100%");
		listGrid.setHeight("100%");
	}

	private void initListGrid() {
		ListGridField nameField = new ListGridField("poolName", "name");
		ListGridField driverField = new ListGridField("driverClass", "driver");
		ListGridField urlField = new ListGridField("connectionURL", "url");

		listGrid.setDataSource(DataSourceDataSource.getInstance());
		listGrid.setFields(nameField, urlField, driverField);
		listGrid.setSortField(0);
		listGrid.setDataPageSize(50);
		listGrid.setAutoFetchData(true);
	}

}
