package application.parser;

import java.io.IOException;

import application.storage.Storage;

public class DeleteByNum implements Command {
    private static final String MESSAGE_DELETE_FAILURE = "We encountered a problem while deleting this task.";
    
    
    int numToDelete;
    
    DeleteByNum(int numToDelete) {
        this.numToDelete = numToDelete;
    }
    
    public Feedback execute(Storage storage){
        try {
            String feedbackMessage = storage.deleteTaskFromSearch(numToDelete);
            return new Feedback(feedbackMessage, storage.getAllTasks());
        } catch (IOException e) {
            return new Feedback(MESSAGE_DELETE_FAILURE, storage.getAllTasks());
        }
    }

}
