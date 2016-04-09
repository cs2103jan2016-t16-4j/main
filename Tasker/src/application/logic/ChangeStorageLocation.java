// @@author A0125417L
package application.logic;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import application.storage.Task;

public class ChangeStorageLocation implements Command {
	private static final String EMPTY_STRING = "";
	private static final String MESSAGE_STORAGE_URL_NOT_FOUND = "Storage Location Invalid: Opening Directory Chooser";
	private static final String MESSAGE_STORAGE_URL_FOUND = "Storage changed: ";
	private static final String BACKSLASH = "\\";
	String arguments;

	ChangeStorageLocation(String arguments) {
		this.arguments = arguments;
		interpretArguments(arguments);
	}

	private void interpretArguments(String arguments) {
		setNewStorageLocation(arguments);
	}

	// storage and click on a new folder after prompt
	private void setNewStorageLocation(String arguments) {

	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen) {
		if (arguments != EMPTY_STRING) {
			arguments = arguments.replaceAll("/", "\\\\");
			if (new File(arguments).isDirectory()) {
				try {
					storageConnector.setDirectory(arguments + BACKSLASH);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Feedback feedback = new Feedback(MESSAGE_STORAGE_URL_FOUND + arguments + BACKSLASH, tasksOnScreen,
						null);
				feedback.setCalFlag();
				return feedback;
			}
		}
		Feedback feedback = new Feedback(MESSAGE_STORAGE_URL_NOT_FOUND, tasksOnScreen, null);
		feedback.setStorageFlag();
		return feedback;
	}
}
// @@author A0125417L