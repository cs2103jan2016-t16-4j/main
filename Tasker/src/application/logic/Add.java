package application.logic;

import java.util.Arrays;

import application.storage.Storage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;

public class Add implements Command{
    public static final int NOT_FOUND = -1;
    public static final String EMPTY = "";
    private static final String MESSAGE_ADD_FEEDBACK= "Added Task: %1$s";
    private static final String MESSAGE_ADD_ERROR = 
            "We encountered an error while adding the task. Sorry for the inconvenience.";    
    
    
    String description = EMPTY;
    DateTime startDateTime;
    DateTime endDateTime;
    String location = EMPTY;
    String remindDate = EMPTY;
    String priority = EMPTY;
    
    Add(String description, DateTime startDateTime, DateTime endDateTime, String location) throws NoDescriptionException{
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.location = location;
    }
    
   
    public Feedback execute(Storage storage){
        String feedbackMessageFromStorage = storage.addTaskInList(description, startDateTime, startTime
                ,endDateTime, endTime, location, remindDate, priority);
        if (feedbackMessageFromStorage != null){
            String feedbackMessage = String.format(MESSAGE_ADD_FEEDBACK, feedbackMessageFromStorage);
            Feedback feedback = new Feedback(feedbackMessage, storage.getAllTasks()); 
            return feedback;
        } else {
            return new Feedback(MESSAGE_ADD_ERROR, storage.getAllTasks());
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
