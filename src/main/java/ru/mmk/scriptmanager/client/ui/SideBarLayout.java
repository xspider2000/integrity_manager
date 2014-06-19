package ru.mmk.scriptmanager.client.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ru.mmk.scriptmanager.client.datasource.TreeDataSource;
import ru.mmk.scriptmanager.client.presenter.SideBarPresenter;
import ru.mmk.scriptmanager.server.domain.Node;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.smartgwt.client.data.DSCallback;
import com.smartgwt.client.data.DSRequest;
import com.smartgwt.client.data.DSResponse;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.SortDirection;
import com.smartgwt.client.widgets.grid.CellFormatter;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.CellClickEvent;
import com.smartgwt.client.widgets.grid.events.CellClickHandler;
import com.smartgwt.client.widgets.grid.events.DataArrivedEvent;
import com.smartgwt.client.widgets.grid.events.DataArrivedHandler;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class SideBarLayout extends VLayout {
	private SideBarTreeGreed treeGrid = new SideBarTreeGreed();
	private SideBarPresenter presenter;

	Timer timer = new Timer() {
		@Override
		public void run() {
			updateTree();
		}
	};

	public SideBarLayout(ToolBarLayout toolBarLayout, ContentLayout contentLayout) {
		presenter = new SideBarPresenter(this, toolBarLayout, contentLayout);
		initAppearance();
		addMembers(treeGrid);
	}

	private void initAppearance() {
		setWidth(555);
		setShowResizeBar(true);
		treeGrid.setShowOpenIcons(false);
		treeGrid.setShowDropIcons(false);
		treeGrid.setNodeIcon(null);
		treeGrid.setFolderIcon(null);
		treeGrid.setIconSize(0);
	}

	public Node getSelectedNode() {
		Record selectedNode = treeGrid.getSelectedRecord();
		Node result = new Node();
		result.setId(selectedNode.getAttributeAsInt("id"));
		result.setParentId(selectedNode.getAttributeAsInt("parentId"));
		result.setName(selectedNode.getAttributeAsString("name"));
		result.setSchedule(selectedNode.getAttributeAsString("schedule"));
		result.setScheduled(Boolean.valueOf(selectedNode.getAttributeAsString("scheduled")));
		return result;
	}

	public void selectedNodeIsScheduled(boolean isScheduled) {
		Record selectedNode = treeGrid.getSelectedRecord();
		selectedNode.setAttribute("scheduled", isScheduled);
	}

	public void addNewNode(int parentId, String name) {
		Record node = new Record();
		node.setAttribute("parentId", parentId);
		node.setAttribute("name", name);
		treeGrid.addData(node);
	}

	public void removeNode(int nodeId) {
		Record node = new Record();
		node.setAttribute("id", nodeId);
		treeGrid.removeData(node);
	}

	public void updateTree() {
		treeGrid.getDataSource().fetchData(null, new DSCallback() {

			@Override
			public void execute(DSResponse dsResponse, Object data, DSRequest dsRequest) {
				Record[] fetchedData = dsResponse.getData();
				Map<Integer, Record> mapFetchedData = new HashMap<Integer, Record>();
				for (Record record : fetchedData) {
					int id = record.getAttributeAsInt("id");
					mapFetchedData.put(id, record);
				}

				Tree treeData = treeGrid.getData();
				for (TreeNode treeNode : treeData.getAllNodes()) {
					int id = treeNode.getAttributeAsInt("id");
					Record record = mapFetchedData.get(id);

					@SuppressWarnings("rawtypes")
					Map recordMap = record.toMap();
					for (Object key : recordMap.keySet()) {
						treeNode.setAttribute((String) key, recordMap.get(key));
					}
				}

				treeGrid.refreshFields();
			}
		});
	}

	public void updateNode(Node node) {
		Record nodeData = new Record();
		nodeData.setAttribute("id", node.getId());
		nodeData.setAttribute("name", node.getName());
		nodeData.setAttribute("parentId", node.getParentId());
		nodeData.setAttribute("schedule", node.getSchedule());
		treeGrid.updateData(nodeData);
	}

	private class SideBarTreeGreed extends TreeGrid {
		public SideBarTreeGreed() {
			initTreeGrid();
			addCellClickHandler(new CellClickHandler() {
				@Override
				public void onCellClick(CellClickEvent event) {
					presenter.onNodeCellClick();
				}
			});
			addDataArrivedHandler(new DataArrivedHandler() {
				@Override
				public void onDataArrived(DataArrivedEvent event) {
					getData().openAll();
					timer.scheduleRepeating(10000);
				}
			});
		}

		private void initTreeGrid() {
			TreeGridField nameField = new TreeGridField("name", "Название", 270);
			TreeGridField childCountField = new TreeGridField("sourceCount", "Источники", 70);
			TreeGridField runCount = new TreeGridField("runCount", "Запуски", 50);
			TreeGridField errorCount = new TreeGridField("errorCount", "Ошибки", 50);
			TreeGridField lastRunDate = new TreeGridField("lastRunDate", "Посл. запуск", 100);

			final DateTimeFormat dateFormatter = DateTimeFormat.getFormat("dd.MM.yyyy HH:mm");
			final DateTimeFormat dateParser = DateTimeFormat.getFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ");
			lastRunDate.setCellFormatter(new CellFormatter() {
				@Override
				public String format(Object value, ListGridRecord record, int rowNum, int colNum) {
					if (value != null) {
						Date date = dateParser.parse((String) value);
						// Date date = new Date((Long) value);
						return dateFormatter.format(date);
					}
					return null;
				}
			});

			setFields(nameField, childCountField, runCount, errorCount, lastRunDate);
			setDataSource(TreeDataSource.getInstance());
			setAutoFetchData(true);
			setLoadDataOnDemand(false);
			setSortField("name");
			setSortDirection(SortDirection.ASCENDING);
			setCanEdit(false);
		}

		@Override
		protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
			int errorCount = Integer.valueOf(record.getAttribute("errorCount"));
			boolean isScheduled = Boolean.valueOf(record.getAttributeAsString("scheduled"));

			String cssStyle = "";
			if (isScheduled) {
				cssStyle += "color:SpringGreen;";
			}
			if (errorCount > 0) {
				cssStyle += "background-color:PeachPuff;";
			}

			if (!cssStyle.equals(""))
				return cssStyle;
			return super.getCellCSSText(record, rowNum, colNum);
		}
	}
}
