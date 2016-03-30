package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class ShowDoneTasks implements Command{

    private static final String CLOSED_MESSAGE = "Here are the tasks you have closed.";
    
    @Override
    public Feedback execute(Storage storage, ArrayList<Task> tasksOnScreen) {
        ArrayList<Task> tasks = storage.getClosedList();
        return new Feedback(CLOSED_MESSAGE, tasks);
    }
    
}
