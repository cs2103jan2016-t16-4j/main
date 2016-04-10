package application.backend;

//@@author A0078688A

import application.storage.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class is an UndoableCommand object which closes a task that the user has specified by name.
 * Check UndoableCommand documentation for insight on what the public methods do.
 * @author RuiMing
 *
 */
public class DoneByName implements UndoableCommand {
    private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_CLOSE = "Closed Task: %1$s";
    private static final String MESSAGE_WHICH_CLOSE = "Which task would you like to close?";
    private static final String MESSAGE_CLOSE_ERROR = "We encountered some "
            + "problem while closing this task. We apologise for the inconvenience.";
    private static final String MESSAGE_NOTHING_TO_CLOSE = "There is no task with that description.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_UNDO_FEEDBACK = "Unclosed: %1$s";

    Logger logger = null;

    String taskToClose;
    StorageConnector storageConnector;
    Task closedTask;

    DoneByName(String taskToClose) {
        this.taskToClose = taskToClose;
        logger = Logger.getLogger("logfile");
        try {
            logger.info("initial DoneByName");
        } catch (SecurityException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
        try {
            ArrayList<Task> taskList = storageConnector.searchTaskByName(taskToClose);
            Feedback feedback = takeAction(taskList, storageConnector);
            logger.info("execute DoneByName");
            return feedback;
        } catch (IOException e) {
            logger.info("MESSAGE_CLOSE_ERROR");
            Feedback feedback = new Feedback(MESSAGE_CLOSE_ERROR, storageConnector.getOpenList(), null);
            feedback.setCalFlag();
            return feedback;
        }
    }

    public Feedback takeAction(ArrayList<Task> taskList, StorageConnector storageConnector) throws IOException {
        assert (taskList != null);
        assert (storageConnector != null);
        assert (taskList.size() > 0);
        this.storageConnector = storageConnector;
        if (taskList.size() == 0) {
            Feedback fb = new Feedback(MESSAGE_NOTHING_TO_CLOSE, storageConnector.getOpenList(), null);
            fb.setCalFlag();
            return fb;
        } else if (taskList.size() == 1) {
            closedTask = storageConnector.closeTask(taskList.get(FIRST_INDEX).getTaskIndex());
            String feedbackMessage = String.format(FEEDBACK_CLOSE, closedTask.toString());
            Feedback fb = new Feedback(feedbackMessage, storageConnector.getOpenList(), null);
            fb.setCalFlag();
            return fb;
        } else {
            Feedback fb = new Feedback(MESSAGE_WHICH_CLOSE, taskList, null);
            fb.setListFlag();
            return fb;
        }
    }

    // @@author A0132632R

    public Feedback undo() throws NothingToUndoException {
        try {
            if (closedTask != null) {
                storageConnector.uncloseTask(closedTask.getTaskIndex());
                String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK, closedTask.toString());
                return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), closedTask);
            } else {
                throw new NothingToUndoException();
            }
        } catch (IOException e) {
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        }
    }

    // @@author A0132632R

    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task) {
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }

    private Feedback getFeedbackList(String message, ArrayList<Task> tasks, Task task) {
        Feedback fb = new Feedback(message, tasks, task);
        fb.setListFlag();
        return fb;
    }
}

// @@author

/*
 * private String closeSingleTask(ArrayList<Task> taskList, Storage storage)
 * throws IOException { assert (taskList != null); assert (storage != null);
 * 
 * String feedback = String.format(FEEDBACK_CLOSE,
 * taskList.get(FIRST_INDEX).getTaskDescription()); try {
 * storage.closeTaskInList(FIRST_INDEX); logger.info("closeSingleTask"); return
 * feedback; } catch (Exception e) { logger.info(e.getMessage()); return
 * feedback; } }
 * 
 * private String listTasks(ArrayList<Task> taskList) { assert (taskList !=
 * null); String listed = "\nWhich task would you like to close?"; try { int i =
 * 1; for (Task task : taskList) { listed = listed + "\n" + i + ") " +
 * task.getTaskDescription(); i++; } logger.info("listTasks"); return listed; }
 * catch (Exception e) { logger.info(e.getMessage()); return listed; } }
 * 
 * }
 */