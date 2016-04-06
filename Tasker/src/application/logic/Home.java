package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;
//@@author A0132632R

public class Home implements Command{

    private static final String MESSAGE_BACK_HOME = "You are back home!";
    
    @Override
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen) {
        return getFeedbackCal(MESSAGE_BACK_HOME, storageConnector.getOpenList(), null);
    }

    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }
}
