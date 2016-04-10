package application.backend;
//@@author A0132632R

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import application.storage.Task;



public class Update implements UndoableCommand{
    public static final int NOT_FOUND = -1;
    public static final int INDEX_TASK_POSITION = 0;
    public static final int INDEX_UPDATED_TASK = 1;
    public static final int INDEX_ORIGINAL_TASK = 0;
    public static final int ARRAY_INDEXING_OFFSET = 1;
    public static final String EMPTY = "";
    private static final String MESSAGE_UPDATE_ERROR = 
            "We encountered an error while updating the task. Sorry for the inconvenience.";    
    private static final String MESSAGE_UPDATE_FEEDBACK = 
            "Updated Task: %1$s";    
    private static final String MESSAGE_UNDO_FEEDBACK = 
            "Reverted: %1$s";  
    private static final String MESSAGE_UNDO_FAILURE = "We encountered an error while undoing.";
    private static final String MESSAGE_INDEX_ERROR = "Please enter a valid task number.";
            
    
    Task origTask;
    Task updatedTask;
    StorageConnector storageConnector;
    
    private int taskPosition = -1;
    private String description = EMPTY;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private String location = EMPTY;
    private Calendar remindDate;
    private String priority = EMPTY;
    
    Update(int taskPosition, String description, Calendar start, Calendar end, String location, Calendar remindDate, String priority){
        this.taskPosition = taskPosition - ARRAY_INDEXING_OFFSET;
        this.description = description;
        this.startDateTime = start;
        this.endDateTime = end;
        this.location = location;
        this.remindDate = remindDate;
        this.priority = priority;
    }
  
   
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        try{
            return updateTask(storageConnector, tasks);
        } catch (IOException e){
            return getFeedbackCal(MESSAGE_UPDATE_ERROR, storageConnector.getOpenList(), null);
        } catch (CloneNotSupportedException e){
            return getFeedbackCal(MESSAGE_UPDATE_ERROR, storageConnector.getOpenList(), null);
        } catch (IndexOutOfBoundsException e){
            return getFeedbackCal(MESSAGE_INDEX_ERROR, storageConnector.getOpenList(), null);
        }
    }


    private Feedback updateTask(StorageConnector storageConnector, ArrayList<Task> tasks) throws IOException, CloneNotSupportedException {
        this.storageConnector = storageConnector;
        int idTaskToDelete = tasks.get(taskPosition).getTaskIndex();
        ArrayList<Task> returnedTasks = storageConnector.updateTask(idTaskToDelete, description, 
                startDateTime, endDateTime, location , remindDate, priority);
        origTask = returnedTasks.get(INDEX_ORIGINAL_TASK);
        updatedTask = returnedTasks.get(INDEX_UPDATED_TASK);
        String feedbackMessage = String.format(MESSAGE_UPDATE_FEEDBACK, "From: " 
                + origTask.toString() + "\n" + "To: " + updatedTask.toString());
        return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), updatedTask);
    }
    
    
    public Feedback undo(){
        try {
            return revertBack();
        }catch(IOException e){
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        } catch (CloneNotSupportedException e) {
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        }
    }


    private Feedback revertBack() throws IOException, CloneNotSupportedException {
        ArrayList<Task> returnedTasks = storageConnector.updateTask(updatedTask.getTaskIndex(), 
                origTask.getTaskDescription(), origTask.getStartDate(),
                origTask.getEndDate(),  origTask.getLocation(),  origTask.getRemindDate(),
                origTask.getPriority());
        origTask = returnedTasks.get(INDEX_ORIGINAL_TASK);
        updatedTask = returnedTasks.get(INDEX_UPDATED_TASK);
        String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,"From: " 
                + origTask.toString() + "\n" + "To: " + updatedTask.toString());
        return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), updatedTask);
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
