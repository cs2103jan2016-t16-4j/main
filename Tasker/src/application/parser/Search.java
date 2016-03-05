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

	String[] arguments;
	String description = EMPTY;
	String priority = EMPTY;
	String date = EMPTY;

	Search(String[] arguments) {
		this.arguments = arguments;
		interpretArguments(arguments);
	}

	/*
	 * Search <Name> Search Priority <Level> Search <Task> By <Date>
	 */
	private void interpretArguments(String[] arguments) {

	}

	// Search <Name>
	private void getTasksByName(String[] args, Storage storage) {
		storage.searchByTask(args[0]);
	}

	// Search Priority <Level>
	private void getTasksByPriority(String[] args, Storage storage) {
		
	}

	// Search <Task> By <Date>
	private void getTasksByDate(String[] args, Storage storage) {
		storage.searchByDate(args[0], true);
	}

	@Override
	public String execute(Storage storage) {
		
		return null;
	}

}
