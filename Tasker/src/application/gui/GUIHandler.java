package application.gui;

import java.io.File;
import java.io.IOException;

import application.logic.Logic;
import application.parser.Feedback;
import javafx.event.EventHandler;
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

	public void textFieldSetUp(TextField txtField, TaskListView taskList, GUIHandler guiH) {
		txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						text = txtField.getText();
						if (text.equalsIgnoreCase("storage")) {

						} else {
							Feedback feedback = guiH.executeCommands(text);
							taskList.clearList(feedback.getTasks());
						}
						txtField.clear();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	/*
	 * Checks if save location already exists If exists - use that location If
	 * does not exist then prompt for new location
	 */
	public void firstLaunchDirectoryPrompt(Stage primaryStage, DirectoryChooser dirChooser, GUIHandler guiH)
			throws IOException {
		if (!logic.checkIfFileExists()) {
			final File selectedDirectory = dirChooser.showDialog(primaryStage);
			if (selectedDirectory != null) {
				logic.startDirectoryPrompt(selectedDirectory.getPath().toString() + "\\");
			} else {
				logic.startDirectoryPrompt("");
			}
		} else {
			logic.loadDataFile();
		}
	}

}
