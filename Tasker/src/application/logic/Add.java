package application.logic;

import java.util.ArrayList;
import java.util.Calendar;

import application.storage.Storage;
import application.storage.Task;

import java.io.IOException;

//@@author A0132632R

public class Add implements UndoableCommand{
    public static final int NOT_FOUND = -1;
    public static final String EMPTY = "";
    private static final String MESSAGE_ADD_FEEDBACK= "Added Task: %1$s";
    private static final String MESSAGE_ADD_ERROR = 
            "We encountered an error while adding the task. Sorry for the inconvenience.";    
    
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_UNDO_FEEDBACK = "Unadded: %1$s";
    
    Task addedTask;
    StorageConnector storageConnector;
    
    String description = EMPTY;
    Calendar startDateTime;
    Calendar endDateTime;
    String location = EMPTY;
    Calendar remindDate;
    String priority = EMPTY;
    
    Add(String description, Calendar startDateTime, Calendar endDateTime, String location, Calendar remindDate, String priority) throws NoDescriptionException{
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
        this.remindDate = remindDate;
        this.priority = priority;
    }
    
   
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        try{
            this.storageConnector = storageConnector;
            addedTask = storageConnector.addTaskInList(description, startDateTime
                    ,endDateTime, location, remindDate, priority);
            String feedbackMessage = String.format(MESSAGE_ADD_FEEDBACK, addedTask.toString());
            Feedback feedback = new Feedback(feedbackMessage, storageConnector.getOpenList());
            System.out.println(feedbackMessage);
            return feedback;
        } catch(IOException e) {
            return new Feedback(MESSAGE_ADD_ERROR, storageConnector.getOpenList());
        }
    }
    
    public Feedback undo(){
        try {
            storageConnector.deleteTask(addedTask.getTaskIndex());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,addedTask.toString());
            return new Feedback(feedbackMessage, storageConnector.getOpenList());
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList());
        }
    }
    
    /*
    private String makeFeedback(){
        if (startDateTime.equals(EMPTY)){
            return String.format(MESSAGE_ADD_FEEDBACK_NO_START_DATE, description, endDateTime, location);
        } else {
            return String.format(MESSAGE_ADD_FEEDBACK_WITH_START_DATE, description, startDateTime, endDateTime, location);
        }
        
    }*/
}
