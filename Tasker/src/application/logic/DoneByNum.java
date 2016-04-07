package application.logic;

//@@author A0078688A

import application.storage.Storage;
import application.storage.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoneByNum implements UndoableCommand {
    private static final String MESSAGE_CLOSE_FAILURE = "We encountered a problem while closing this task.";
    private static final String MESSAGE_CLOSE_FEEDBACK = "Closed Task: %1$s";
    private static final String MESSAGE_INDEX_PROBLEM = "Please enter a valid number.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_UNDO_FEEDBACK = "Unclosed: %1$s";
   
    
    
    Logger logger = null;

    Task closedTask;
    int numToClose;
    StorageConnector storageConnector;

    DoneByNum(int numToClose) {
        this.numToClose = numToClose;
        logger = Logger.getLogger("Logfile");
        try {
            logger.info("initial DoneByNum");
        } catch (SecurityException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
        assert storageConnector != null;
        this.storageConnector = storageConnector;
        try {
            int idOfTaskToClose = tasks.get(numToClose).getTaskIndex();
            closedTask = storageConnector.closeTask(idOfTaskToClose);
            String feedbackMessage = String.format(MESSAGE_CLOSE_FEEDBACK,closedTask.toString());
            return getFeedbackCal(feedbackMessage, storageConnector.getOpenList(), null);
        } catch (IOException e) {
            return getFeedbackCal(MESSAGE_CLOSE_FAILURE, storageConnector.getOpenList(), null);
        } catch (IndexOutOfBoundsException e) {
            return getFeedbackCal(MESSAGE_INDEX_PROBLEM, storageConnector.getOpenList(), null);
        }
    }

    //@@author A0132632R

    
    public Feedback undo() throws NothingToUndoException{
        try {
            storageConnector.uncloseTask(closedTask.getTaskIndex());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,closedTask.toString());
            return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), closedTask);
        }catch(IOException e){
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        }
    }

  //@@author A0132632R

    private Feedback getFeedbackList(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setListFlag();
        return fb;
    }
    
    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }
    
}