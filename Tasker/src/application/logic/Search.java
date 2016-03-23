package application.logic;

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
		if (Arrays.asList(arguments).toString().toLowerCase().contains("priority")) {
			searchObj = new SearchByPriority(arguments);
		} else if (Arrays.asList(arguments).toString().toLowerCase().contains("by")) {
			searchObj = new SearchByDate(arguments);
		} else {
			searchObj = new SearchByName(arguments);
		}
	}

	@Override
	public Feedback execute(Storage storage) {
		Feedback feedback = searchObj.execute(storage);
		return feedback;
	}

}
