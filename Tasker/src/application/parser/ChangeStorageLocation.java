package application.parser;

import application.storage.Storage;

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
	public String execute(Storage storage) {
		// TODO Auto-generated method stub
		return null;
	}

}
