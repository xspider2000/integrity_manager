package ru.mmk.scriptmanager.client.ui;

import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class PageLayout extends VLayout {
	private HeaderLayout headerLayout = new HeaderLayout();
	private ToolBarLayout toolBarLayout = new ToolBarLayout();
	private MainLayout mainLayout = new MainLayout();

	public PageLayout() {
		initAppearance();
		toolBarLayout.setSideBarLayout(mainLayout.sideBarLayout);
		addMembers(headerLayout, toolBarLayout, mainLayout);
	}

	private void initAppearance() {
		setMembersMargin(3);
		setHeight100();
		setWidth100();
	}

	private class MainLayout extends HLayout {
		private final ContentLayout contentLayout = new ContentLayout();
		private final SideBarLayout sideBarLayout = new SideBarLayout(toolBarLayout, contentLayout);

		public MainLayout() {
			setMargin(5);
			contentLayout.setSideBarLayout(sideBarLayout);
			addMembers(sideBarLayout, contentLayout);
		}
	}
}