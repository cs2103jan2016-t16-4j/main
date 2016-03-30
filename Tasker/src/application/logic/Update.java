package application.logic;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

import application.storage.Storage;
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
            
    
    Task origTask;
    Task updatedTask;
    Storage storage;
    
    private int taskPosition = -1;
    private String description = EMPTY;
    private Calendar startDateTime;
    private Calendar endDateTime;
    private String location = EMPTY;
    private Calendar remindDate;
    private String priority = EMPTY;
    
    Update(int taskPosition, String description, Calendar start, Calendar end, String location, Calendar remindDate){
        this.taskPosition = taskPosition - ARRAY_INDEXING_OFFSET;
        this.description = description;
        this.startDateTime = start;
        this.endDateTime = end;
        this.location = location;
        this.remindDate = remindDate;
    }
  
   
    public Feedback execute(Storage storage, ArrayList<Task> tasks){
        try{
            this.storage = storage;
            
            int idTaskToDelete = tasks.get(taskPosition).getTaskIndex();
            ArrayList<Task> returnedTasks = storage.updateTask(idTaskToDelete, description, 
                    startDateTime, endDateTime
                    ,location
                    , remindDate, priority);
            origTask = returnedTasks.get(INDEX_ORIGINAL_TASK);
            updatedTask = returnedTasks.get(INDEX_UPDATED_TASK);
            String feedbackMessage = String.format(MESSAGE_UPDATE_FEEDBACK, "From: " 
                    + origTask.toString() + "\n" + "To: " + updatedTask.toString());
            System.out.println("Orig:" + origTask.getStartDate());
            System.out.println("Updated:" + updatedTask.getStartDate());
            
            return new Feedback(feedbackMessage, storage.getOpenList());
        } catch (IOException e){
            return new Feedback(MESSAGE_UPDATE_ERROR, storage.getOpenList());
        } catch (CloneNotSupportedException e){
            return new Feedback(MESSAGE_UPDATE_ERROR, storage.getOpenList());
        }
    }
    
    
    public Feedback undo(){
        try {
            Task deletedTask = storage.deleteTask(updatedTask.getTaskIndex());
            Task revertedTask = storage.addTaskInList(origTask.getTaskDescription(), origTask.getStartDate(),
            origTask.getEndDate(),  origTask.getLocation(),  origTask.getRemindDate(),
            origTask.getPriority());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,"From: " 
                    + deletedTask.toString() + "\n" + "To: " + revertedTask.toString());
            return new Feedback(feedbackMessage, storage.getOpenList());
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storage.getOpenList());
        }
    }
    
    /*
    private String makeFeedback(){
        if (startDateTime.equals(EMPTY)){
            return String.format(MESSAGE_UPDATE_FEEDBACK_NO_START_DATE, description, endDateTime, location);
        } else {
            return String.format(MESSAGE_UPDATE_FEEDBACK_WITH_START_DATE, description, startDateTime, endDateTime, location);
        }
        
    }
    */
}
