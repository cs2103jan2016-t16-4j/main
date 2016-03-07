package application.parser;

import java.util.Arrays;

import application.storage.Storage;

/**
 * 
 * @author Shawn
 *
 */

public class Search implements Command {
	Command searchObj;

	public static final int NOT_FOUND = -1;
	public static final String EMPTY = "";

	private Storage storage = new Storage();

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
		if (Arrays.asList(arguments).contains("Priority")) {
			searchObj = new SearchByPriority(arguments);
		} else if (Arrays.asList(arguments).contains("By")) {
			searchObj = new SearchByDate(arguments);
		} else {
			searchObj = new SearchByName(arguments);
		}
	}

	@Override
	public String execute(Storage storage) {
		String feedback = searchObj.execute(storage);
		return feedback;
	}

}
