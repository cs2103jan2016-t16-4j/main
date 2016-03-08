package application.parser;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByName implements Command {
	private String taskName;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchByName(String[] args) {
		taskName = getString(args, 0, args.length - 1);
	}

	@Override
	public String execute(Storage storage) {
		// TODO Auto-generated method stub
		taskList = storage.searchByTask(taskName);
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

	private String getString(String[] args, int start, int end) {
		String string = "";
		for (int i = start; i <= end; i++) {
			string = string + args[i] + " ";
		}
		return string.trim();
	}

}
