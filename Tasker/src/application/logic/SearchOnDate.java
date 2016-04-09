// @@author A0125417L
package application.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.storage.Task;

/**
 * 
 * Class to search on date
 *
 */

public class SearchOnDate implements Command {

	// Logger Messages
	private static final String EXECUTE_SEARCH_ON_DATE_LOGGER_MSG = "Executing search on date function";

	// Constants
	private static final int EMPTY = 0;

	// Messages
	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();
	private Calendar date;

	// Variables
	private ArrayList<Task> taskList;

	SearchOnDate(Calendar date) {
		this.date = date;
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		logger.info(EXECUTE_SEARCH_ON_DATE_LOGGER_MSG);
		taskList = storageConnector.searchTaskOnDate(date);
		return checkIfListEmpty(storageConnector);
	}

	// Check if list is empty
	private Feedback checkIfListEmpty(StorageConnector storageConnector) {
		if (taskList.size() != EMPTY) {
			return searchFound();
		} else {
			return searchNotFound(MESSAGE_SEARCH_NOT_FOUND, storageConnector.getOpenList(), null);
		}
	}

	// Create feedback message for search found
	private Feedback searchFound() {
		Feedback fb = new Feedback(MESSAGE_SEARCH_RESULTS, taskList, null);
		fb.setListFlag();
		return fb;
	}

	// Create feedback message for search not found
	private Feedback searchNotFound(String message, ArrayList<Task> tasks, Task task) {
		Feedback fb = new Feedback(message, tasks, task);
		fb.setCalFlag();
		return fb;
	}
}