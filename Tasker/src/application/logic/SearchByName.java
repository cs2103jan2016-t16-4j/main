// @@author A0125417L
package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByName implements Command {

	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = " ";

	private String taskName;

	private ArrayList<Task> taskList;

	SearchByName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		// TODO Auto-generated method stub
		taskList = storageConnector.searchTaskByName(taskName);
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
// @@author A0125417L