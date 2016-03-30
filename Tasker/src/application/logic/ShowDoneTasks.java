package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class ShowDoneTasks implements Command{

    private static final String MESSAGE_CLOSED = "Here are the tasks you have closed.";
    private static final String MESSAGE_NO_CLOSED = "You have not closed any tasks.";
    
    @Override
    public Feedback execute(Storage storage, ArrayList<Task> tasksOnScreen) {
        ArrayList<Task> tasks = storage.getCloseList();
        return createAppropFeedback(storage, tasks);
    }
    
    private static Feedback createAppropFeedback(Storage storage, ArrayList<Task> tasks){
        if (tasks.size() == 0){
            return new Feedback(MESSAGE_NO_CLOSED, storage.getOpenList());
        } else{
            return new Feedback(MESSAGE_CLOSED, tasks);
        }
    }
}
