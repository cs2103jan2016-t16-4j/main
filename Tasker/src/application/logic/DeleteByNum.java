package application.logic;

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;
//@@author A0132632R

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
            Feedback feedback = new Feedback(feedbackMessage, storageConnector.getOpenList(), null);
            feedback.setCalFlag();
            return feedback;
        } catch (IOException e) {
            return getFeedbackCal(MESSAGE_DELETE_FAILURE, storageConnector.getOpenList(), null);
        } catch (IndexOutOfBoundsException e) {
            return getFeedbackCal(MESSAGE_INDEX_PROBLEM, storageConnector.getOpenList(), null);
        }
    }
    
    public Feedback undo(){
        try {
            storageConnector.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
            deletedTask.getEndDate(),  deletedTask.getLocation(),  deletedTask.getRemindDate(),
            deletedTask.getPriority());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,deletedTask.toString());
            return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), deletedTask);
        }catch(IOException e){
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        }
    }
    
    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }
    
    
    private Feedback getFeedbackList(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setListFlag();
        return fb;
    }
   
}
