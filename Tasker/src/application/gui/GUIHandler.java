package application.gui;

import java.io.File;
import java.io.IOException;

import application.logic.Logic;
import application.parser.Feedback;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Shawn
 *
 */

public class GUIHandler {
	Logic logic;

	String text = "";
	private static final String ADD_HINT_MESSAGE = "To add: [task description] from [start] to [end] at [location]";
	private static final String HELP_HINT_MESSAGE = "To get help: help";
	private static final String DELETE_HINT_MESSAGE = "To delete: delete [task description/number]";
	private static final String SEARCH_HINT_MESSAGE = "To search: search [task decription/priority [level]/[task description] by [date]]";
	private static final String EXIT_HINT_MESSAGE = "To exit: exit";
	private static final String UPDATE_HINT_MESSAGE = "To update: update [task number] [new task desc]";
	private static final String UNDO_HINT_MESSAGE = "To undo: undo";
	private static final String STORAGE_HINT_MESSAGE = "To change storage: storage";
	private static final String DONE_HINT_MESSAGE = "To mark task as complete: done [task number]";
	private static final String EMPTY_STRING = "";

	public GUIHandler(Logic logic) {
		this.logic = logic;
		// launchGUI();
	}

	// private void launchGUI() {
	// new Thread() {
	// @Override
	// public void run() {
	// javafx.application.Application.launch(GUI.class);
	// }
	// }.start();
	// }

	public Feedback executeCommands(String cmd) {
		Feedback feedBack = logic.executeCommand(cmd);
		return feedBack;
	}

	// public boolean checkIfFileExists() throws IOException {
	// return logic.checkIfFileExists();
	// }
	//
	// public ArrayList<Task> loadDataFile() throws IOException {
	// return logic.loadDataFile();
	// }

	public void startDirectoryPrompt(String file) {
		try {
			logic.startDirectoryPrompt(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void textFieldSetUp(TextField txtField, TaskListView taskList, GUIHandler guiH, Label helpLabel,
			Label feedbackLabel) {
		txtField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				getHints(oldValue, newValue, helpLabel);
			}
		});
		txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						text = txtField.getText();
						if (text.equalsIgnoreCase("storage")) {

						} else {
							Feedback feedback = guiH.executeCommands(text);
							taskList.updateList(feedback.getTasks());
							feedbackLabel.setText(feedback.getMessage());
						}
						txtField.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void getHints(String oldValue, String newValue, Label helpLabel) {
		String oldLetter = EMPTY_STRING;
		String newLetter = EMPTY_STRING;

		if (!oldValue.isEmpty() && oldValue != null) {
			oldLetter = getFirstLetter(oldValue);
		}
		if (!newValue.isEmpty() && newValue != null) {
			newLetter = getFirstLetter(newValue);
		}

		if (newLetter == null) {
			return;
		}

		if (newLetter.equals(oldLetter)) {
			return;
		} else {
			switch (newLetter.toLowerCase()) {
			case "a":
				helpLabel.setText(ADD_HINT_MESSAGE);
				break;
			case "h":
				helpLabel.setText(HELP_HINT_MESSAGE);
				break;
			case "d":
				helpLabel.setText(DELETE_HINT_MESSAGE);
				// DONE_HINT_MESSAGE
				break;
			case "u":
				helpLabel.setText(UPDATE_HINT_MESSAGE);
				// UNDO_HINT_MESSAGE
				break;
			case "e":
				helpLabel.setText(EXIT_HINT_MESSAGE);
				break;
			case "s":
				// if (!newValue.isEmpty() && newValue.length() > 1) {
				// if (getSecondLetter(newValue).equalsIgnoreCase("st")) {
				// helpLabel.setText("storage");
				// }
				// } else {
				helpLabel.setText(SEARCH_HINT_MESSAGE);
				// }
				break;
			default:
				helpLabel.setText(ADD_HINT_MESSAGE);
				break;
			}
		}
	}

	private String getFirstLetter(String input) {
		String firstLetter = input.substring(0, 1);
		return firstLetter;
	}

	private String getSecondLetter(String input) {
		String secondLetter = input.substring(0, 2);
		return secondLetter;
	}

	/*
	 * Checks if save location already exists If exists - use that location If
	 * does not exist then prompt for new location
	 */
	public void firstLaunchDirectoryPrompt(Stage primaryStage, DirectoryChooser dirChooser, GUIHandler guiH,
			TaskListView taskList) throws IOException {
		if (!logic.checkIfFileExists()) {
			final File selectedDirectory = dirChooser.showDialog(primaryStage);
			if (selectedDirectory != null) {
				logic.startDirectoryPrompt(selectedDirectory.getPath().toString() + "\\");
			} else {
				logic.startDirectoryPrompt("");
			}
		} else {
			taskList.updateList(logic.loadDataFile());
		}
	}

}
