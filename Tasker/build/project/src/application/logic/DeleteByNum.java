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
    Storage storage;
    
    DeleteByNum(int numToDelete) {
        this.numToDelete = numToDelete;
    }
    
    public Feedback execute(Storage storage, ArrayList<Task> tasks){
        this.storage = storage;
        try {
            int idOfTaskToDelete = tasks.get(numToDelete).getTaskIndex();
            deletedTask = storage.deleteTask(idOfTaskToDelete);
            String feedbackMessage = String.format(MESSAGE_DELETE_FEEDBACK,deletedTask.toString());
            return new Feedback(feedbackMessage, storage.getFileList());
        } catch (IOException e) {
            return new Feedback(MESSAGE_DELETE_FAILURE, storage.getFileList());
        } catch (IndexOutOfBoundsException e) {
            return new Feedback(MESSAGE_INDEX_PROBLEM, storage.getFileList());
        }
    }
    
    public Feedback undo(){
        try {
            storage.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
            deletedTask.getEndDate(),  deletedTask.getLocation(),  deletedTask.getRemindDate(),
            deletedTask.getPriority());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,deletedTask.toString());
            return new Feedback(feedbackMessage, storage.getFileList());
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storage.getFileList());
        }
    }
    

}
