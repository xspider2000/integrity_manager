package ru.mmk.scriptmanager.client.ui;

import ru.mmk.scriptmanager.client.presenter.SourceTabPresenter;
import ru.mmk.scriptmanager.server.domain.Source;

import com.google.codemirror2_gwt.client.CodeMirrorConfig;
import com.google.codemirror2_gwt.client.CodeMirrorWrapper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.TextAreaElement;
import com.google.gwt.user.client.ui.TextArea;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLFlow;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.DrawEvent;
import com.smartgwt.client.widgets.events.DrawHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;

public final class SourceTab extends Tab {
	private TopPanel topPanel;
	private CodeEditor codeEditor;
	private CodeOutput codeOutput = new CodeOutput();

	private Source source;
	private SourceTabPresenter presenter = new SourceTabPresenter(this);

	private class TopPanel extends HLayout {
		private TextItem newCodeName = new TextItem("name", "Наименование");
		private IButton saveButton = new IButton("Сохранить");
		private IButton setMasterButton = new IButton("Назн. источником");
		private IButton runButton = new IButton("Тест");

		public TopPanel(final String name) {
			initAppearance();
			setMasterButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.setSourceAsMaster();
				}
			});
			runButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.executeSource();
				}

			});
			saveButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.saveSource();
				}

			});
			addMember(new DynamicForm() {
				{
					setNumCols(2);
					newCodeName.setValue(name);
					setFields(newCodeName);
				}
			});
			addMembers(saveButton, setMasterButton, runButton);
		}

		public String getNewName() {
			return newCodeName.getValueAsString();
		}

		private void initAppearance() {
			setHeight("1px");
			setMembersMargin(10);
			setPadding(10);
		}
	}

	private class CodeEditor extends VLayout {
		private final Integer CODE_AREA_HEIGHT = 450;
		private CodeMirrorWrapper wrapper;
		private TextArea textArea;

		public CodeEditor(final String code) {
			initAppearance();
			initTextArea(code);
			addMember(textArea);

			addDrawHandler(new DrawHandler() {
				@Override
				public void onDraw(DrawEvent event) {
					wrapper = CodeMirrorWrapper.createEditorFromTextArea(getTextAreaElement(), initCodeMirrorConfig());
					wrapper.setSize("*", (CODE_AREA_HEIGHT - 2) + "px");
				}
			});
		}

		public String getCodeText() {
			return wrapper.getValue();
		}

		private CodeMirrorConfig initCodeMirrorConfig() {
			return CodeMirrorConfig.makeBuilder().setMode("text/x-groovy").setShowLineNumbers(true)
					.setMatchBrackets(true).setTheme("neat");
		}

		private void initAppearance() {
			setHeight(CODE_AREA_HEIGHT);
			setBorder("1px solid");
		}

		private void initTextArea(final String code) {
			textArea = new TextArea();
			textArea.setHeight("100%");
			textArea.setText(code);
		}

		private TextAreaElement getTextAreaElement() {
			return textArea.getElement().cast();
		}
	}

	private class CodeOutput extends HTMLFlow {
		public CodeOutput() {
			initAppearance();
		}

		private void initAppearance() {
			setOverflow(Overflow.AUTO);
			setHeight("30%");
			setBorder("1px solid");
		}
	}

	public SourceTab(Source source) {

		setTitle(source.getName());
		topPanel = new TopPanel(source.getName());
		codeEditor = new CodeEditor(source.getCode());
		setPane(new VLayout() {
			{
				setOverflow(Overflow.HIDDEN);
				setMembersMargin(10);
				addMembers(topPanel, codeEditor, codeOutput);
			}
		});

		this.source = source;

		if (source.isMaster()) {
			showAsMaster();
		}
	}

	private void showAsMaster() {
		setIcon(GWT.getModuleBaseURL() + "../resources/img/icon/pawn_blue.png");
	}

	public Source getSource() {
		return source;
	}

	public void setOutput(String outputText) {
		codeOutput.setContents(outputText.replaceAll("\n", "<br>"));
	}

	public String getCodeText() {
		return codeEditor.getCodeText();
	}

	public String getNewName() {
		return topPanel.getNewName();
	}
}
