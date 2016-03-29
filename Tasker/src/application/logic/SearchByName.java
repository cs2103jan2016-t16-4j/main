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
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		// TODO Auto-generated method stub
		taskList = storage.searchTaskByName(taskName);
		return checkIfListEmpty(storage);
	}

	private Feedback checkIfListEmpty(Storage storage) {
		if (taskList != null) {
			return new Feedback(MESSAGE_SEARCH_RESULTS, taskList);
		} else {
			return new Feedback(MESSAGE_SEARCH_NOT_FOUND, storage.getOpenList());
		}
	}
}
