package application.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import application.storage.Storage;
import application.storage.Task;

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
	Calendar date;

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
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		Feedback feedback = searchObj.execute(storage,tasks );
		return feedback;
	}

}
