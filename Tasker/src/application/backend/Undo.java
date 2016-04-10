package application.backend; 
//@@author A0132632R

import java.util.ArrayList;
import java.util.EmptyStackException;

import application.storage.Task;


public class Undo implements Command {
    private static final String MESSAGE_NOTHING_TO_UNDO = "There are no commands left to undo!";
    
    
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        try{
            History history = History.getInstance();
            return history.undo();
        }catch(EmptyStackException e){
            return getFeedbackCal(MESSAGE_NOTHING_TO_UNDO, storageConnector.getOpenList(), null);
        }
    }
    
    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }
}
