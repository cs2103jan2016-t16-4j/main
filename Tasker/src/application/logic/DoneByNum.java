package application.logic;

import application.storage.Storage;
import application.storage.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoneByNum implements Command {
    private static final String MESSAGE_CLOSE_FAILURE = "We encountered a problem while closing this task.";
    private static final String MESSAGE_CLOSE_FEEDBACK = "Closed Task: %1$s";
    private static final String MESSAGE_INDEX_PROBLEM = "Please enter a valid number.";

    Logger logger = null;

    Task closedTask;
    int numToClose;
    Storage storage;

    DoneByNum(int numToClose) {
        this.numToClose = numToClose;
        logger = Logger.getLogger("Logfile");
        try {
            logger.info("initial DoneByNum");
        } catch (SecurityException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Feedback execute(Storage storage, ArrayList<Task> tasks) {
        assert storage != null;
        this.storage = storage;
        try {
            int idOfTaskToClose = tasks.get(numToClose).getTaskIndex();
            closedTask = storage.closeTask(idOfTaskToClose);
            String feedbackMessage = String.format(MESSAGE_CLOSE_FEEDBACK,closedTask.toString());
            return new Feedback(feedbackMessage, storage.getOpenList());
        } catch (IOException e) {
            return new Feedback(MESSAGE_CLOSE_FAILURE, storage.getOpenList());
        } catch (IndexOutOfBoundsException e) {
            return new Feedback(MESSAGE_INDEX_PROBLEM, storage.getOpenList());
        }
    }

}