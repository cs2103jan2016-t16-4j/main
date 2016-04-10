package application.backend;
//@@author A0132632R

import java.util.ArrayList;

import application.storage.Task;

/**
 * This class is used to send back feedback from the execution of a command
 * to the logic component, which then sends it back to the GUI, which uses it 
 * to display information to the user.
 * @author Pratyush
 *
 */

public class Feedback {
    private static final String LIST_FLAG = "list";
    private static final String CAL_FLAG = "cal";
    private static final String HELP_FLAG = "help";
    private static final String STORAGE_FLAG = "storage";
    private static final String VIEW_CHANGE_FLAG = "view";
    private static final String SUMMARY_FLAG = "summary";

    private String flag; 
    private Task taskToScroll;
    private String feedbackMessage;
    private ArrayList<Task> tasksToDisplay;
    
    Feedback(String message, ArrayList<Task> tasks, Task taskToScroll){
        this.feedbackMessage = message;
        this.tasksToDisplay = tasks;
        this.taskToScroll = taskToScroll;
    }
    
    public void setListFlag(){
        flag = LIST_FLAG;
    }
    
    public void setViewChangeFlag(){
        flag = VIEW_CHANGE_FLAG;
    }
    
    public void setCalFlag(){
        flag = CAL_FLAG;
    }
    
    public void setSummaryFlag(){
        flag = SUMMARY_FLAG;
    }
    
    public void setHelpFlag(){
        flag = HELP_FLAG;
    }

    public void setStorageFlag(){
        flag = STORAGE_FLAG;
    }
    
    public ArrayList<Task> getTasks(){
    	return this.tasksToDisplay;
    }
    
    public String getFlag(){
        return flag;
    }
    
    public Task getTaskToScrollTo(){
        return taskToScroll;
    }
    
    public String getMessage(){
    	return this.feedbackMessage;
    }
  
}
