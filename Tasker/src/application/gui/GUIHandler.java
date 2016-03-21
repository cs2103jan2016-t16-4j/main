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

	public void textFieldSetUp(TextField txtField, TaskListView taskList, GUIHandler guiH, Label helpLabel) {
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
		String oldLetter = "";
		String newLetter = "";

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
				helpLabel.setText("add");
				break;
			case "h":
				helpLabel.setText("help");
				break;
			case "d":
				helpLabel.setText("delete");
				// done
				break;
			case "u":
				helpLabel.setText("update");
				// undo
				break;
			case "e":
				helpLabel.setText("exit");
				break;
			case "s":
//				if (!newValue.isEmpty() && newValue.length() > 1) {
//					if (getSecondLetter(newValue).equalsIgnoreCase("st")) {
//						helpLabel.setText("storage");
//					}
//				} else {
					helpLabel.setText("search");
//				}
				break;
			default:
				helpLabel.setText("");
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
