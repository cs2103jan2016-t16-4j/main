// @@author A0125417L
package application.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.storage.Task;

/**
 * 
 * Class to search by date
 *
 */

public class SearchByDate implements Command {

	// Logger Messages
	private static final String EXECUTE_SEARCH_BY_DATE_LOGGER_MSG = "Executing search by date function";

	// Constants
	private static final int EMPTY = 0;

	// Messages
	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// Variables
	private Calendar date;
	private ArrayList<Task> taskList;

	SearchByDate(Calendar date) {
		this.date = date;
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		logger.info(EXECUTE_SEARCH_BY_DATE_LOGGER_MSG);
		taskList = storageConnector.searchTaskByDate(date);
		return checkIfListEmpty(storageConnector);
	}

	// Check if list is empty
	private Feedback checkIfListEmpty(StorageConnector storageConnector) {
		if (taskList.size() != EMPTY) {
			return searchFound(MESSAGE_SEARCH_RESULTS, taskList, null);
		} else {
			return searchNotFound(storageConnector);
		}
	}

	// Create feedback message for search not found
	private Feedback searchNotFound(StorageConnector storageConnector) {
		Feedback fb = new Feedback(MESSAGE_SEARCH_NOT_FOUND, storageConnector.getOpenList(), null);
		fb.setCalFlag();
		return fb;
	}

	// Create feedback message for search found
	private Feedback searchFound(String message, ArrayList<Task> tasks, Task task) {
		Feedback fb = new Feedback(message, tasks, task);
		fb.setListFlag();
		return fb;
	}
}