package ru.mmk.scriptmanager.client.ui;

import java.util.Date;

import ru.mmk.scriptmanager.client.datasource.LogDataSource;
import ru.mmk.scriptmanager.server.domain.Node;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.smartgwt.client.data.Criteria;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DoubleClickEvent;
import com.smartgwt.client.widgets.events.DoubleClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.events.TabSelectedEvent;
import com.smartgwt.client.widgets.tab.events.TabSelectedHandler;

public class JournalTab extends Tab {
	private ListGrid listGrid = new ListGrid();
	private IButton applyButton = new IButton("Применить");
	private DateItem dateItemFrom = new DateItem("От");
	private DateItem dateItemTo = new DateItem("До");
	private ContentLayout contentLayout;
	private Node selectedNode;

	public JournalTab(ContentLayout contentLayout) {
		this.contentLayout = contentLayout;
		setTitle("Журнал");
		setName("journalTab");
		initListGrid();
		initHandlers();
		initAppearance();
	}

	private void initAppearance() {
		setPane(new VLayout() {
			{
				setMembersMargin(10);

				addMember(new HLayout() {
					{
						setMembersMargin(10);
						setDefaultLayoutAlign(VerticalAlignment.BOTTOM);
						addMember(new DynamicForm() {
							{
								setLayoutAlign(Alignment.LEFT);
								dateItemFrom.setUseTextField(true);
								dateItemTo.setUseTextField(true);
								setFields(dateItemFrom, dateItemTo);
							}
						});
						addMember(applyButton);
					}
				});
				addMember(listGrid);
			}
		});
	}

	private void initHandlers() {
		addTabSelectedHandler(new TabSelectedHandler() {
			@Override
			public void onTabSelected(TabSelectedEvent event) {
				getLogsOfSelectedNode();
			}
		});

		listGrid.addDoubleClickHandler(new DoubleClickHandler() {
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				openTabWithComparisonResult();
			}
		});

		applyButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Criteria criteria = new Criteria();
				criteria.setAttribute("nodeId", selectedNode.getId());
				Date dateFrom = dateItemFrom.getValueAsDate();
				Date dateTo = dateItemTo.getValueAsDate();

				DateTimeFormat formatter = DateTimeFormat.getFormat("yyyy-MM-dd");
				if (dateFrom != null)
					criteria.setAttribute("dateFrom", formatter.format(dateFrom));
				if (dateTo != null)
					criteria.setAttribute("dateTo", formatter.format(dateTo));
				listGrid.filterData(criteria);
			}
		});
	}

	private void openTabWithComparisonResult() {
		String json = listGrid.getSelectedRecord().getAttribute("message");
		String date = listGrid.getSelectedRecord().getAttribute("date");
		DateTimeFormat dateParser = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
		DateTimeFormat formatter = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");
		String formatedDate = formatter.format(dateParser.parse(date));
		ComparisonViewTab tab = new ComparisonViewTab();
		tab.setTitle("Результат сравнения: " + formatedDate);
		tab.view(json);
		contentLayout.addTab(tab);
		contentLayout.selectTab(tab);
	}

	private void initListGrid() {
		ListGridField dateField = new ListGridField("date", 150);
		ListGridField priorityField = new ListGridField("priority", 80);
		ListGridField messageField = new ListGridField("message");
		final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");
		final DateTimeFormat dateParser = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
		dateField.setCellFormatter(new CellFormatter() {
			@Override
			public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
				if (value != null) {
					Date date = dateParser.parse((String) value);
					return dateFormatter.format(date);
				}
				return null;
			}
		});

		listGrid.setWidth("100%");
		listGrid.setHeight("100%");
		listGrid.setSelectionType(SelectionStyle.SINGLE);
		listGrid.setDataSource(LogDataSource.getInstance());
		listGrid.setFields(dateField, priorityField, messageField);
		listGrid.setSortField(0);
		listGrid.setSortDirection(SortDirection.DESCENDING);
		listGrid.setDataPageSize(50);
		listGrid.setAutoFetchData(false);

		listGrid.setShowFilterEditor(true);
	}

	public Node getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(Node selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void getLogsOfSelectedNode() {
		if (selectedNode == null)
			return;
		Criteria criteria = new Criteria();
		criteria.setAttribute("nodeId", selectedNode.getId());
		listGrid.setCriteria(criteria);
		listGrid.filterData(criteria);
	}
}
