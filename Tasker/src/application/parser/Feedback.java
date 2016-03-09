package application.parser;

import java.util.ArrayList;

import application.gui.Ui;
import application.storage.Task;

public class Feedback {
    private String feedbackMessage;
    private ArrayList<Task> tasksToDisplay;
    
    Feedback(String message, ArrayList<Task> tasks){
        this.feedbackMessage = message;
        this.tasksToDisplay = tasks;
    }
    
    public void display(Ui ui){
        ui.showToUser(feedbackMessage, tasksToDisplay);
    }
    
}
