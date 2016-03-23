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

    private String taskName;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchByName(String[] args) {
		taskName = getString(args, 0, args.length - 1);
	}

	@Override
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		// TODO Auto-generated method stub
		taskList = storage.searchByTask(taskName);
		return checkIfListEmpty(storage);
	}
	
	private Feedback checkIfListEmpty(Storage storage) {
		if (taskList != null) {
		    return new Feedback(MESSAGE_SEARCH_RESULTS,taskList);
        } else {
            return new Feedback("Search Not Found", storage.getAllTasks());
        }
	}
/*
	private String readArrayList(ArrayList<Task> tasks) {
		int i = 0;
		for (Task item : tasks) {
			i++;
			feedback = feedback + i + ") " + item.getTaskDescription() + "\n";
		}
		return feedback.trim();
	}
*/
	private String getString(String[] args, int start, int end) {
		String string = "";
		for (int i = start; i <= end; i++) {
			string = string + args[i] + " ";
		}
		return string.trim();
	}

}
