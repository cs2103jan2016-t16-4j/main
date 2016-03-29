package application.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

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
			Calendar cal = parseToDate(arguments);
			searchObj = new SearchByDate(cal);
		} else if (Arrays.asList(arguments).toString().toLowerCase().contains("on")) {
			Calendar cal = parseToDate(arguments);
			searchObj = new SearchOnDate(cal);
		} else {
			searchObj = new SearchByName(arguments);
		}
	}

	private Calendar parseToDate(String[] arguments) {
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(FORMAT_DATE.parse(arguments[1] + " 23:59:59"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}

	@Override
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		Feedback feedback = searchObj.execute(storage, tasks);
		return feedback;
	}

}
