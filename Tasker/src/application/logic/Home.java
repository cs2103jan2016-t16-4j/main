package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class Home implements Command{

    private static final String MESSAGE_BACK_HOME = "You are back home!";
    
    @Override
    public Feedback execute(Storage storage, ArrayList<Task> tasksOnScreen) {
        return new Feedback(MESSAGE_BACK_HOME, storage.getOpenList());
    }

    
}
