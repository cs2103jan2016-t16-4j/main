package application.logic;

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
            return new Feedback(feedbackMessage, storageConnector.getOpenList());
        } catch (IOException e) {
            return new Feedback(MESSAGE_CLOSE_FAILURE, storageConnector.getOpenList());
        } catch (IndexOutOfBoundsException e) {
            return new Feedback(MESSAGE_INDEX_PROBLEM, storageConnector.getOpenList());
        }
    }

    //@@author A0132632R

    
    public Feedback undo() throws NothingToUndoException{
        try {
            storageConnector.uncloseTask(closedTask.getTaskIndex());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,closedTask.toString());
            return new Feedback(feedbackMessage, storageConnector.getOpenList());
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList());
        }
    }

    
    
}