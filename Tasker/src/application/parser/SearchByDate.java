package application.parser;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByDate implements Command {
	private String date;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchByDate(String[] args) {
		date = args[1];
	}

	@Override
	public String execute(Storage storage) {
		// TODO Auto-generated method stub
		taskList = storage.searchByDate(date, true);
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
