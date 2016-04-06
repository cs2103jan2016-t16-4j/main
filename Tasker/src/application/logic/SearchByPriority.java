// @@author A0125417L
package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class SearchByPriority implements Command {
	private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";
	private static final String MESSAGE_SEARCH_NOT_FOUND = "Search Not Found";

	private String priority;

	private ArrayList<Task> taskList;

	SearchByPriority(String[] arguments) {
		priority = arguments[1];
	}

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		taskList = storageConnector.searchTaskByPriority(priority);
		return checkIfListEmpty(storageConnector);
	}

	private Feedback checkIfListEmpty(StorageConnector storageConnector) {
		if (taskList != null) {
			return getFeedbackList(MESSAGE_SEARCH_RESULTS, taskList, null);
		} else {
			Feedback fb = new Feedback(MESSAGE_SEARCH_NOT_FOUND, storageConnector.getOpenList(), null);
			fb.setCalFlag();
			return fb;
		}
	}
	private Feedback getFeedbackList(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setListFlag();
        return fb;
    }
	
}
// @@author A0125417L