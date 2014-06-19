package ru.mmk.scriptmanager.client.ui;

import java.util.List;

import ru.mmk.scriptmanager.client.presenter.SourceTabsPresenter;
import ru.mmk.scriptmanager.server.domain.Node;
import ru.mmk.scriptmanager.server.domain.Source;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.ImgButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

public class SourceTabs extends Tab {
	private Header header = new Header();
	private TabSet tabs = new TabSet();

	private SourceTabsPresenter presenter = new SourceTabsPresenter(this);
	private Node selectedNode;

	public SourceTabs() {
		setTitle("Источники");
		setPane(new VLayout() {
			{
				setPadding(10);
				setWidth100();
				setHeight100();
				addMembers(header, tabs);
			}
		});
	}

	public void setSideBarLayout(SideBarLayout sideBarLayout) {
		presenter.setSideBarLayout(sideBarLayout);
	}

	public void removeAllSourceTabs() {
		for (Tab tab : getTabSet().getTabs()) {
			getTabSet().removeTab(tab);
		}
	}

	public void addSourceTabs(List<Source> sourceList) {
		for (Source source : sourceList) {
			getTabSet().addTab((Tab) new SourceTab(source));
		}
	}

	public Node getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(Node node) {
		this.selectedNode = node;
	}

	public TabSet getTabSet() {
		return tabs;
	}

	private class Header extends HLayout {
		private IButton startButton = new IButton("Запустить");
		private ImgButton addButton = new AddButton();
		private ImgButton removeButton = new RemoveButton();

		public Header() {
			initAppearance();
			initClickHandlers();
			addMembers(startButton, addButton, removeButton);
		}

		private void initAppearance() {
			setHeight(1);
			setMembersMargin(10);
			setAlign(Alignment.RIGHT);
		}

		private void initClickHandlers() {
			startButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.compareSources();
				}
			});

			addButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.addSource();
				}
			});
			removeButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.removeSource();
				}
			});
		}
	}

}

class AddButton extends ImgButton {
	public AddButton() {
		setSrc(GWT.getModuleBaseURL() + "../resources/img/icon/icon-plus.png");
		setWidth(20);
		setHeight(20);
		setShowRollOver(false);
		setShowDown(false);
	}
}

class RemoveButton extends ImgButton {
	public RemoveButton() {
		setSrc(GWT.getModuleBaseURL() + "../resources/img/icon/icon-minus.png");
		setWidth(20);
		setHeight(20);
		setShowRollOver(false);
		setShowDown(false);
	}
}