// @@author A0125417L
package application.backend;

import java.util.ArrayList;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.storage.Task;

/**
 * 
 * Class to search by name
 *
 */

public class SearchByName implements Command {

	// Logger Messages
	private static final String EXECUTE_SEARCH_BY_NAME_LOGGER_MSG = "Executing search by name function";

	// Constants
	private static final int EMPTY = 0;

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// Messages
	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";

	// Variables
	private String taskName;
	private ArrayList<Task> taskList;

	SearchByName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		logger.info(EXECUTE_SEARCH_BY_NAME_LOGGER_MSG);
		taskList = storageConnector.searchTaskByName(taskName);
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