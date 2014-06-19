package ru.mmk.scriptmanager.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.smartgwt.client.types.TreeModelType;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tree.Tree;
import com.smartgwt.client.widgets.tree.TreeGrid;
import com.smartgwt.client.widgets.tree.TreeGridField;
import com.smartgwt.client.widgets.tree.TreeNode;

public class ComparisonViewTab extends Tab {
	TreeGrid treeGrid = new MyTreeGrid();
	Tree tree = new Tree();

	public ComparisonViewTab() {
		setCanClose(true);
		initTreeGrid();
		setPane(treeGrid);
	}

	private void initTreeGrid() {
		treeGrid.setFields(new TreeGridField("name", 200), new TreeGridField("value"));
		treeGrid.setShowOpenIcons(false);
		treeGrid.setShowDropIcons(false);
		treeGrid.setNodeIcon(null);
		treeGrid.setFolderIcon(null);
		treeGrid.setIconSize(0);
		treeGrid.setShowAllRecords(true);
	}

	public void view(String comparisonJson) {
		initTree();
		MyTreeNode rootNode = new MyTreeNode("root");
		JSONObject jsonObject = JSONParser.parseStrict(comparisonJson).isObject();

		buildTreeFromJsonObject(jsonObject, rootNode);
		tree.setRoot(rootNode);
		treeGrid.setData(tree);
	}

	private void initTree() {
		tree.setModelType(TreeModelType.CHILDREN);
		tree.setNameProperty("name");
		tree.setChildrenProperty("parentOf");
	}

	private static class MyTreeGrid extends TreeGrid {
		protected String getCellCSSText(ListGridRecord record, int rowNum, int colNum) {
			String nodeName = record.getAttributeAsString("name");
			if (nodeName.equals("Источник")) {
				return "background-color:LawnGreen;";
			}
			if (nodeName.equals("Приемник")) {
				return "background-color:Salmon;";
			}
			return super.getCellCSSText(record, rowNum, colNum);

		};
	}

	private static class MyTreeNode extends TreeNode {
		public MyTreeNode(String name) {
			setAttribute("name", name);
		}

		public void addChildren(MyTreeNode... children) {
			setAttribute("parentOf", children);
		}

		public void setValue(String value) {
			setAttribute("value", value);
		}
	}

	private void buildTreeFromJsonObject(JSONObject jsonObject, MyTreeNode node) {
		List<MyTreeNode> siblingNodes = new ArrayList<MyTreeNode>();
		for (String key : jsonObject.keySet()) {
			if (key.equals("meta"))
				continue;
			JSONValue jsonValue = jsonObject.get(key);
			switch (key) {
			case "differentRows":
				key = "Различающиеся строки";
				break;
			case "missingRows":
				key = "Потерянные строки";
				break;
			case "extraRows":
				key = "Лишние строки";
				break;
			case "masterValue":
				key = "Источник";
				break;
			case "sourceValue":
				key = "Приемник";
			}
			MyTreeNode childNode = new MyTreeNode(key);
			siblingNodes.add(childNode);
			if (jsonValue.isObject() != null) {
				buildTreeFromJsonObject(jsonValue.isObject(), childNode);
			} else if (jsonValue.isArray() != null) {
				buildTreeFromJsonArray(jsonValue.isArray(), childNode);
			} else {
				childNode.setValue(jsonValue.toString());
			}
		}
		node.addChildren(siblingNodes.toArray(new MyTreeNode[] {}));
	}

	private void buildTreeFromJsonArray(JSONArray jsonArray, MyTreeNode node) {
		List<MyTreeNode> siblingNodes = new ArrayList<MyTreeNode>();
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONValue jsonValue = jsonArray.get(i);
			MyTreeNode childNode = new MyTreeNode(String.valueOf(i));
			siblingNodes.add(childNode);
			if (jsonValue.isObject() != null) {
				buildTreeFromJsonObject(jsonValue.isObject(), childNode);
			} else if (jsonValue.isArray() != null) {
				buildTreeFromJsonArray(jsonValue.isArray(), childNode);
			} else {
				childNode.setValue(jsonValue.toString());
			}
		}
		node.addChildren(siblingNodes.toArray(new MyTreeNode[] {}));
	}
}
