package application.parser;

import application.storage.Storage;

/**
 * 
 * @author Shawn
 *
 */

public class Search implements Command {
	public static final int NOT_FOUND = -1;
	public static final String EMPTY = "";

	Storage storage;
	String[] arguments;
	String description = EMPTY;

	Search(Storage storage, String[] arguments) {
		this.storage = storage;
		this.arguments = arguments;
		interpretArguments(arguments);
	}

	/*
	 * Search <Name> Search Priority <Level> Search <Task> By <Date>
	 */
	private void interpretArguments(String[] arguments) {

	}

	// Search <Name>
	private void getTasksByName(String[] args) {

	}

	// Search Priority <Level>
	private void getTasksByPriority(String[] args) {

	}

	// Search <Task> By <Date>
	private void getTasksByDate(String[] args) {

	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return null;
	}

}
