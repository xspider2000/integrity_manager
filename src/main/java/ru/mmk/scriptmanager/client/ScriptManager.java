package ru.mmk.scriptmanager.client;

import ru.mmk.scriptmanager.client.ui.PageLayout;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class ScriptManager implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().clear(true);
		new PageLayout().draw();
	}
}