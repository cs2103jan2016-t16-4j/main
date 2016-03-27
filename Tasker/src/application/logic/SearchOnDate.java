package application.logic;

import java.util.ArrayList;
import java.util.Calendar;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchOnDate implements Command {
	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";

	private Calendar date;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchOnDate(Calendar date) {
		this.date = date;
	}

	@Override
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		System.out.println(date);
		taskList = storage.searchTaskOnDate(date);
		return checkIfListEmpty(storage);
	}

	private Feedback checkIfListEmpty(Storage storage) {
		if (taskList != null) {
			return new Feedback(MESSAGE_SEARCH_RESULTS, taskList);
		} else {
			return new Feedback("Search Not Found", storage.getOpenList());
		}
	}
}
