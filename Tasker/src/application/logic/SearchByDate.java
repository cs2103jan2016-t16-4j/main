package application.logic;

import java.util.ArrayList;
import java.util.Calendar;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByDate implements Command {
    private static final String MESSAGE_SEARCH_RESULTS = "Here are the results of your search!";
    
	private Calendar date;
	private String feedback = "";

	private ArrayList<Task> taskList;

	SearchByDate(String[] args) {
		date = args[1];
	}

	@Override
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		// TODO Auto-generated method stub
		taskList = storage.searchTaskByDate(date);
		return checkIfListEmpty(storage);
	}

	private Feedback checkIfListEmpty(Storage storage) {
		if (taskList != null) {
			return new Feedback(MESSAGE_SEARCH_RESULTS,taskList);
		} else {
			return new Feedback("Search Not Found", storage.getFileList());
		}
	}
/*
	private Feedback readArrayList(ArrayList<Task> tasks) {
		int i = 0;
		for (Task item : tasks) {
			i++;
			feedback = feedback + i + ") " + item.getTaskDescription() + "\n";
		}
		return feedback.trim();
	}
*/
}
