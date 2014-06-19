package ru.mmk.scriptmanager.client.ui;

import ru.mmk.scriptmanager.client.presenter.ToolBarPresenter;

import com.google.gwt.core.client.GWT;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.util.ValueCallback;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Dialog;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.events.VisibilityChangedEvent;
import com.smartgwt.client.widgets.events.VisibilityChangedHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;

public class ToolBarLayout extends ToolStrip {
	private ToolStripButton addNodeButton = new AddButton();
	private ToolStripButton removeNodeButton = new RemoveButton();
	private ToolStripButton settingButton = new SettingButton();
	private ToolStripButton startButton = new StartButton();
	private ToolStripButton stopButton = new StopButton();
	private Window scheduleWindow = new ScheduleWindow();
	private ToolBarPresenter presenter;
	private SideBarLayout sideBarLayout;

	public ToolBarLayout() {
		addButton(addNodeButton);
		addButton(removeNodeButton);
		addSeparator();
		addButton(startButton);
		addButton(stopButton);
		addButton(settingButton);
		startButton.disable();
		stopButton.disable();
	}

	public void selectedNodeIsScheduled(boolean isScheduled) {
		if (isScheduled) {
			startButton.disable();
			stopButton.enable();
		} else {
			startButton.enable();
			stopButton.disable();
		}
	}

	public void setSideBarLayout(SideBarLayout sideBarLayout) {
		this.sideBarLayout = sideBarLayout;
		presenter = new ToolBarPresenter(this, sideBarLayout);
	}

	private class AddButton extends ToolStripButton {

		public AddButton() {
			setIcon(GWT.getModuleBaseURL() + "../resources/img/icon/icon-plus.png");
			addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					SC.askforValue("Введите имя нового элемента.", new ToolBarValueCallback());
				}
			});
		}

		private final class ToolBarValueCallback implements ValueCallback {
			@Override
			public void execute(String name) {
				if (pressCancel(name)) {
					return;
				} else if (nameIsValid(name)) {
					int parentId = sideBarLayout.getSelectedNode().getId();
					presenter.addNewNode(parentId, name);
				} else
					SC.say("Имя элемента не может быть пустым!");
			}

			private boolean pressCancel(String name) {
				return name == null;
			}

			private boolean nameIsValid(String name) {
				return !name.trim().equals("");
			}
		}
	}

	private class RemoveButton extends ToolStripButton {
		public RemoveButton() {
			setIcon(GWT.getModuleBaseURL() + "../resources/img/icon/icon-minus.png");
			addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					SC.ask("Элемент будет удален безвозвратно. Вы уверены что хотите удалить элемент?",
							new BooleanCallback() {
								@Override
								public void execute(Boolean confirmed) {
									if (confirmed) {
										int nodeId = sideBarLayout.getSelectedNode().getId();
										presenter.removeSelectedNode(nodeId);
									}
								}
							});
				}
			});
		}
	}

	private class StartButton extends ToolStripButton {
		public StartButton() {
			setIcon(GWT.getModuleBaseURL() + "../resources/img/icon/icon-play.png");
			addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.startSchedulingSelectedNode();
				}
			});
		}
	}

	private class StopButton extends ToolStripButton {
		public StopButton() {
			setIcon(GWT.getModuleBaseURL() + "../resources/img/icon/icon-pause.png");
			addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					presenter.stopSchedulingSelectedNode();
				}
			});
		}
	}

	private class SettingButton extends ToolStripButton {
		public SettingButton() {
			setIcon(GWT.getModuleBaseURL() + "../resources/img/icon/icon-setting.png");
			addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					scheduleWindow.show();
				}
			});
		}
	}

	private class ScheduleWindow extends Dialog {
		private Button okButton = new Button("OK");
		private Button cancelButton = new Button("Cancel");
		private ScheduleForm scheduleForm = new ScheduleForm();

		public ScheduleWindow() {
			initAppearance();

			addItem(scheduleForm);
			okButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					String newSchedule = scheduleForm.scheduleTextItem.getValueAsString();
					presenter.setScheduleToSelectedNode(newSchedule);
					hide();
				}
			});
			cancelButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					hide();
				}
			});
			addVisibilityChangedHandler(new VisibilityChangedHandler() {
				@Override
				public void onVisibilityChanged(VisibilityChangedEvent event) {
					if (event.getIsVisible()) {
						String schedule = sideBarLayout.getSelectedNode().getSchedule();
						scheduleForm.scheduleTextItem.setValue(schedule);
					}
				}
			});
		}

		private void initAppearance() {
			setButtons(okButton, cancelButton);
			setWidth(400);
			setHeight(200);
			setTitle("Введите cron-расписание");
			setShowMinimizeButton(false);
			setIsModal(true);
			setShowModalMask(true);
			centerInPage();
		}

		private class ScheduleForm extends DynamicForm {
			private TextItem scheduleTextItem = new TextItem();

			public ScheduleForm() {
				setHeight100();
				setWidth100();
				setPadding(5);
				setLayoutAlign(VerticalAlignment.CENTER);
				scheduleTextItem.setTitle("Расписание");
				setFields(scheduleTextItem);
			}
		}
	}

}