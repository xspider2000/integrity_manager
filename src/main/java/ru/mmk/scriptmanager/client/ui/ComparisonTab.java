package ru.mmk.scriptmanager.client.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ru.mmk.scriptmanager.client.presenter.ComparisonTabPresenter;
import ru.mmk.scriptmanager.server.domain.Node;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.data.RecordList;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public class ComparisonTab extends Tab {
	private Header header = new Header();
	private Content content = new Content();

	private ComparisonTabPresenter comparisonTabPresenter = new ComparisonTabPresenter(this);
	private Node selectedNode;

	public ComparisonTab() {
		setTitle("Сравнение");
		setPane(new VLayout() {
			{
				addMembers(header, content);
			}
		});
	}

	public Node getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(Node selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void setComparisonData(String data) {
		content.setComparisonData(data);
	}

	public void hideTable() {
		content.hideGrid();
	}

	private class Header extends HLayout {
		private IButton compareButton = new IButton("Сравнить");

		public Header() {
			initAppearance();
			addMember(compareButton);
			compareButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					comparisonTabPresenter.compareDataOfSources();
				}
			});
		}

		private void initAppearance() {
			setHeight(1);
			setPadding(10);
			setAlign(Alignment.RIGHT);
		}
	}

	private class Content extends VLayout {
		ListGrid listGrid = new ListGrid();

		public Content() {
			initAppearance();
		}

		private void initAppearance() {
			setMargin(10);
			setBorder("1px solid");
			addMember(listGrid);
			listGrid.setVisible(false);
		}

		public void hideGrid() {
			listGrid.setData(new RecordList() {
			});
			setBorder("1px solid");
			listGrid.setVisible(false);
		}

		public void setComparisonData(String data) {
			JSONObject comparisonResult = JSONParser.parseStrict(data).isObject();
			Set<String> sourceNames = comparisonResult.keySet();

			RecordList listGridData = new RecordList();
			for (String name : sourceNames) {
				JSONObject source = comparisonResult.get(name).isObject();
				JSONArray missingRows = source.get("missingRows").isArray();
				JSONArray extraRows = source.get("extraRows").isArray();

				setFieldsToGrid(missingRows.get(0).isObject());

				for (int i = 0; i < missingRows.size(); i++) {
					JSONObject missingRow = missingRows.get(i).isObject();
					Record row = initMissingRow(name, missingRow);
					listGridData.add(row);
				}

				for (int i = 0; i < extraRows.size(); i++) {
					JSONObject extraRow = extraRows.get(i).isObject();
					Record row = initExtraRow(name, extraRow);
					listGridData.add(row);
				}
			}
			listGrid.setData(listGridData);
			listGrid.setVisible(true);
			setBorder("0px");
		}

		private Record initMissingRow(String name, JSONObject missingRow) {
			Record row = new Record(missingRow.getJavaScriptObject());
			row.setAttribute("source", name);
			row.setAttribute("status", "missed");
			return row;
		}

		private Record initExtraRow(String name, JSONObject missingRow) {
			Record row = new Record(missingRow.getJavaScriptObject());
			row.setAttribute("source", name);
			row.setAttribute("status", "extra");
			return row;
		}

		private void setFieldsToGrid(JSONObject missingRow) {
			List<ListGridField> fields = new ArrayList<ListGridField>();
			Set<String> columnNames = missingRow.keySet();
			fields.add(new ListGridField("source"));
			fields.add(new ListGridField("status"));
			for (String columnName : columnNames) {
				fields.add(new ListGridField(columnName));
			}
			listGrid.setFields(fields.toArray(new ListGridField[0]));
		}
	}
}
