package ru.mmk.scriptmanager.client.ui;

import com.smartgwt.client.types.Side;
import com.smartgwt.client.widgets.tab.TabSet;

public class ContentLayout extends TabSet {
	private ComparisonTab comparisonTab = new ComparisonTab();
	private SourceTabs sourceTabs = new SourceTabs();
	private DataSourceTab connectionDataTab = new DataSourceTab();
	private JournalTab journalTab = new JournalTab(this);

	public ContentLayout() {
		initAppearance();
		addTab(sourceTabs);
		addTab(connectionDataTab);
		addTab(journalTab);
	}

	private void initAppearance() {
		setTabBarPosition(Side.TOP);
	}

	public SourceTabs getSourceTabs() {
		return sourceTabs;
	}

	public ComparisonTab getComparisonTab() {
		return comparisonTab;
	}

	public JournalTab getJournalTab() {
		return journalTab;
	}

	public void setSideBarLayout(SideBarLayout sideBarLayout) {
		sourceTabs.setSideBarLayout(sideBarLayout);
	}
}