package application.logic;

import java.util.ArrayList;

import application.storage.Task;

public class ListDisplay implements Command {

    private static final String MESSAGE_LIST = "Here are your tasks.";
    
    @Override
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen) {
        Feedback feedback = new Feedback(MESSAGE_LIST, storageConnector.getOpenList(),null);
        feedback.setListFlag();
        return feedback;
    }

}
