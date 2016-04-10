package application.backend;
//@@author A0132632R

import java.util.ArrayList;

import application.storage.Task;

/**
 * This class is a Command object which shows the user all his completed tasks. 
 * Check Command class documentation for insight on
 * what the public methods do.
 * 
 * @author Pratyush
 *
 */
public class ShowDoneTasks implements Command{

    private static final String MESSAGE_CLOSED = "Here are the tasks you have closed.";
    private static final String MESSAGE_NO_CLOSED = "You have not closed any tasks.";
    
    @Override
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen) {
        ArrayList<Task> tasks = storageConnector.getClosedList();
        return createAppropFeedback(storageConnector, tasks);
    }
    
    private Feedback createAppropFeedback(StorageConnector storageConnector, ArrayList<Task> tasks){
        if (tasks.size() == 0){
            return getFeedbackCal(MESSAGE_NO_CLOSED, storageConnector.getOpenList(), null);
        } else{
            Feedback fb = new Feedback(MESSAGE_CLOSED, tasks, null);
            fb.setListFlag();
            return fb;
        }
    }
    
    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }
}
