// @@author A0125417L
package application.backend;

import java.util.ArrayList;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.storage.Task;

/**
 * 
 * Class to search by priority
 *
 */

public class SearchByPriority implements Command {

	// Logger Messages
	private static final String EXECUTE_SEARCH_BY_PRIORITY_LOGGER_MSG = "Executing search by priority function";

	// Messages
	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search! Use home command to view all tasks!";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// Variables
	private String priority;
	private ArrayList<Task> taskList;

	SearchByPriority(String priority) {
		this.priority = priority;
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		logger.info(EXECUTE_SEARCH_BY_PRIORITY_LOGGER_MSG);
		taskList = storageConnector.searchTaskByPriority(priority);
		return checkIfListEmpty(storageConnector);
	}

	/*
	 * Check if list is empty
	 */
	private Feedback checkIfListEmpty(StorageConnector storageConnector) {
		if (taskList != null) {
			return searchFound(MESSAGE_SEARCH_RESULTS, taskList, null);
		} else {
			assert (taskList == null);
			return searchNotFound(storageConnector);
		}
	}

	/*
	 * Create feedback message for search not found
	 */
	private Feedback searchNotFound(StorageConnector storageConnector) {
		Feedback fb = new Feedback(MESSAGE_SEARCH_NOT_FOUND, storageConnector.getOpenList(), null);
		fb.setCalFlag();
		return fb;
	}

	/*
	 * Create feedback message for search found
	 */
	private Feedback searchFound(String message, ArrayList<Task> tasks, Task task) {
		Feedback fb = new Feedback(message, tasks, task);
		fb.setListFlag();
		return fb;
	}

}