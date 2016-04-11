package application.backend;

import java.util.ArrayList;
import application.storage.Task;

public class Summary implements Command {
	Summary(){
		
	}
	
	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen) {
		String message = "Here is a summary of your tasks.";
		Feedback fb = new Feedback(message, tasksOnScreen, null);
		fb.setSummaryFlag();
		return fb;
	}

}
