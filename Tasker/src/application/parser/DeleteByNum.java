package application.parser;

import java.io.IOException;

import application.storage.Storage;

public class DeleteByNum implements Command {
    private static final String MESSAGE_DELETE_FAILURE = "We encountered a problem while deleting this task.";
    private static final String MESSAGE_DELETE_FEEDBACK = "Deleted Task: %1$s";
    
    
    int numToDelete;
    
    DeleteByNum(int numToDelete) {
        this.numToDelete = numToDelete;
    }
    
    public Feedback execute(Storage storage){
        try {
            String feedbackMessageFromStorage = storage.deleteTaskFromSearch(numToDelete);
            String feedbackMessage = String.format(MESSAGE_DELETE_FEEDBACK,feedbackMessageFromStorage);
            return new Feedback(feedbackMessage, storage.getAllTasks());
        } catch (IOException e) {
            return new Feedback(MESSAGE_DELETE_FAILURE, storage.getAllTasks());
        }
    }

}
