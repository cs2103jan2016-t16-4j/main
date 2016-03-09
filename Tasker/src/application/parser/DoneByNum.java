package application.parser;

import java.io.IOException;

import application.storage.Storage;

public class DoneByNum implements Command {
    private static final String MESSAGE_DONE_FAILURE = "We encountered a problem while closing this task.";
    private static final String MESSAGE_CLOSED_FEEDBACK = "Closed Task: %1$s";
    
    
    int numToClose;
    
    DoneByNum(int numToClose) {
        this.numToClose = numToClose;
    }
    
    public Feedback execute(Storage storage){
        try {
            String feedbackFromStorage = storage.closeTaskFromSearch(numToClose);
            String feedbackMessage = String.format(MESSAGE_CLOSED_FEEDBACK, feedbackFromStorage);
            return new Feedback(feedbackMessage, storage.getAllTasks()); 
        } catch (Exception e) {
            return new Feedback(MESSAGE_DONE_FAILURE, storage.getAllTasks());
        }
    }
    

}