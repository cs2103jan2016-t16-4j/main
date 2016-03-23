package application.logic;

import java.util.ArrayList;
import java.util.logging.Logger;

import application.gui.Ui;
import application.storage.Task;

public class Feedback {
    private static final String LOGGER_NAME = "logfile";

    
    private String feedbackMessage;
    private ArrayList<Task> tasksToDisplay;
    private static Logger logger = Logger.getLogger(LOGGER_NAME);

    
    Feedback(String message, ArrayList<Task> tasks){
        this.feedbackMessage = message;
        this.tasksToDisplay = tasks;
    }
    
    public void display(Ui ui){
        logger.info("feedback object using ui to display itself to user");
        ui.showToUser(feedbackMessage, tasksToDisplay);
    }
    
}
