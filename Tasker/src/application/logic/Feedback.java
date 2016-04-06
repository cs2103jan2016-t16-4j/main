package application.logic;

import java.util.ArrayList;
import java.util.logging.Logger;

import application.gui.Ui;
import application.storage.Task;

//@@author A0132632R


public class Feedback {
    private static final String LOGGER_NAME = "logfile";
    private static final String LIST_FLAG = "list";
    private static final String CAL_FLAG = "cal";
    private static final String HELP_FLAG = "help";
    private static final String STORAGE_FLAG = "storage";

    private String flag; 
    private Task taskToScroll;
    private String feedbackMessage;
    private ArrayList<Task> tasksToDisplay;
    private static Logger logger = Logger.getLogger(LOGGER_NAME);
    
    Feedback(String message, ArrayList<Task> tasks, Task taskToScroll){
        this.feedbackMessage = message;
        this.tasksToDisplay = tasks;
        this.taskToScroll = taskToScroll;
    }
    
    public void setListFlag(){
        flag = LIST_FLAG;
    }
    

    public void setCalFlag(){
        flag = CAL_FLAG;
    }
    

    public void setHelpFlag(){
        flag = HELP_FLAG;
    }

    public void setStorageFlag(){
        flag = STORAGE_FLAG;
    }
    public void display(Ui ui){
        logger.info("feedback object using ui to display itself to user");
        ui.showToUser(feedbackMessage, tasksToDisplay);
    }
    
    public ArrayList<Task> getTasks(){
    	return this.tasksToDisplay;
    }
    
    public String getFlag(){
        return flag;
    }
    
    public Task getIndexToScroll(){
        return taskToScroll;
    }
    
    public String getMessage(){
    	return this.feedbackMessage;
    }
  
}
