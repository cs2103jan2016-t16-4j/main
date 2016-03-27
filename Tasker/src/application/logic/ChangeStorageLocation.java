package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class ChangeStorageLocation implements Command {
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
	public Feedback execute(Storage storage, ArrayList<Task> tasksOnScreen) {
		// TODO Auto-generated method stub
		return null;
	}

}
