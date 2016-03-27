package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByPriority implements Command {
    private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";

    private String priority;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchByPriority(String[] arguments) {
		priority = arguments[1];
	}

	@Override
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		taskList = storage.searchTaskByPriority(priority);
		return checkIfListEmpty(storage);
	}

	private Feedback checkIfListEmpty(Storage storage) {
		if (taskList != null) {
		    return new Feedback(MESSAGE_SEARCH_RESULTS,taskList);
        } else {
            return new Feedback("Search Not Found", storage.getOpenList());
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
}
