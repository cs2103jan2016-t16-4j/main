package application.logic;

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class DeleteByNum implements UndoableCommand {
    private static final String MESSAGE_DELETE_FAILURE = "We encountered a problem while deleting this task.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_DELETE_FEEDBACK = "Deleted Task: %1$s";
    private static final String MESSAGE_UNDO_FEEDBACK = "Re-added: %1$s";
    private static final String MESSAGE_INDEX_PROBLEM = "Please enter a valid number.";
    
    Task deletedTask;
    int numToDelete;
    StorageConnector storageConnector;
    
    DeleteByNum(int numToDelete) {
        this.numToDelete = numToDelete;
    }
    
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        this.storageConnector = storageConnector;
        try {
            int idOfTaskToDelete = tasks.get(numToDelete).getTaskIndex();
            deletedTask = storageConnector.deleteTask(idOfTaskToDelete);
            String feedbackMessage = String.format(MESSAGE_DELETE_FEEDBACK,deletedTask.toString());
            return new Feedback(feedbackMessage, storageConnector.getOpenList());
        } catch (IOException e) {
            return new Feedback(MESSAGE_DELETE_FAILURE, storageConnector.getOpenList());
        } catch (IndexOutOfBoundsException e) {
            return new Feedback(MESSAGE_INDEX_PROBLEM, storageConnector.getOpenList());
        }
    }
    
    public Feedback undo(){
        try {
            storageConnector.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
            deletedTask.getEndDate(),  deletedTask.getLocation(),  deletedTask.getRemindDate(),
            deletedTask.getPriority());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,deletedTask.toString());
            return new Feedback(feedbackMessage, storageConnector.getOpenList());
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList());
        }
    }
    

}
