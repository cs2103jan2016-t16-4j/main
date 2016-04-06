// @@author A0125417L
package application.logic;

import java.io.File;
import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class ChangeStorageLocation implements Command {
	private static final String EMPTY_STRING = "";
	private static final String MESSAGE_STORAGE_URL_NOT_FOUND = "Storage Location Invalid: Opening Directory Chooser";
	private static final String MESSAGE_STORAGE_URL_FOUND = "Storage changed: ";
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
			if (new File(arguments).isDirectory()) {
				return new Feedback(MESSAGE_STORAGE_URL_FOUND, tasksOnScreen);
			}
		}
		return new Feedback(MESSAGE_STORAGE_URL_NOT_FOUND, tasksOnScreen);

	}
}
// @@author A0125417L