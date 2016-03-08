package application.parser;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByPriority implements Command {
	private String priority;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchByPriority(String[] arguments) {
		priority = arguments[1];
	}

	@Override
	public String execute(Storage storage) {
		taskList = storage.searchByPriority(priority);
		return checkIfListEmpty();
	}

	private String checkIfListEmpty() {
		if (taskList != null) {
			return readArrayList(taskList);
		} else {
			return "Search Not Found";
		}
	}
	
	private String readArrayList(ArrayList<Task> tasks) {
		for (Task item : tasks) {
			feedback = feedback + item.getTaskDescription() + "\n";
		}
		return feedback.trim();
	}

}
