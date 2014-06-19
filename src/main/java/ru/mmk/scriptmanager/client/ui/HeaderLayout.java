package ru.mmk.scriptmanager.client.ui;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLPane;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class HeaderLayout extends ToolStrip {
	private HTMLPane headerText = new HTMLPane();
	private Img mainLogo = new Img(GWT.getModuleBaseURL()
			+ "../resources/img/icon/db_checked_logo.png");
	private Img isLogo = new Img(GWT.getModuleBaseURL()
			+ "../resources/img/icon/empty-logo.png");

	public HeaderLayout() {
		initAppearance();
		initMainLogo();
		initHeaderText();
		initIsLogo();

		addMember(mainLogo);
		addMember(headerText);
		addMember(isLogo);
	}

	private void initAppearance() {
		setWidth100();
		setHeight(70);

	}

	private void initHeaderText() {
		final String headerLeftTextContent = "<div style=\"line-height: 0.1em;\"><h1> Контрольный интерфейс </h1><br><h3>Корпоративная сервисная шина (ESB)</h3></div>";
		headerText.setContents(headerLeftTextContent);
		headerText.setOverflow(Overflow.HIDDEN);
	}

	private void initIsLogo() {
		isLogo.setHeight(47);
		isLogo.setWidth(223);
	}

	private void initMainLogo() {
		mainLogo.setHeight(65);
		mainLogo.setWidth(70);
	}
}
