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
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";

	private Calendar date;

	private ArrayList<Task> taskList;

	SearchOnDate(Calendar date) {
		this.date = date;
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		taskList = storageConnector.searchTaskOnDate(date);
		return checkIfListEmpty(storageConnector);
	}

	private Feedback checkIfListEmpty(StorageConnector storageConnector) {
		if (taskList.size() != 0) {
			return new Feedback(MESSAGE_SEARCH_RESULTS, taskList);
		} else {
			return new Feedback(MESSAGE_SEARCH_NOT_FOUND, storageConnector.getOpenList());
		}
	}
}
