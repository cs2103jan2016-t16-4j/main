package application.logic;

import application.storage.Storage;
import application.storage.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoneByName implements Command {
    private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_CLOSE = "Closed Task: %1$s";
    private static final String MESSAGE_WHICH_CLOSE = "Which task would you like to close?";
    private static final String MESSAGE_CLOSE_ERROR = "We encountered some "
            + "problem while closing this task. We apologise for the inconvenience.";
    private static final String MESSAGE_NOTHING_TO_CLOSE = "There is no task with that description.";

    Logger logger = null;

    String taskToClose;
    Storage storage;
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

    public Feedback execute(Storage storage, ArrayList<Task> tasks) {
        try {
            ArrayList<Task> taskList = storage.searchTaskByName(taskToClose);
            Feedback feedback = takeAction(taskList, storage);
            logger.info("execute DoneByName");
            return feedback;
        } catch (IOException e) {
            logger.info("MESSAGE_CLOSE_ERROR");
            return new Feedback(MESSAGE_CLOSE_ERROR, storage.getOpenList());
        }
    }

    public Feedback takeAction(ArrayList<Task> taskList, Storage storage) throws IOException {
        assert (taskList != null);
        assert (storage != null);
        assert (taskList.size() > 0);
        this.storage = storage;
        if(taskList.size()== 0){
            return new Feedback(MESSAGE_NOTHING_TO_CLOSE, storage.getOpenList());
        } else if (taskList.size() == 1) {
            closedTask = storage.closeTask(taskList.get(FIRST_INDEX).getTaskIndex());
            String feedbackMessage = String.format(FEEDBACK_CLOSE, closedTask.toString());
            return new Feedback(feedbackMessage, storage.getOpenList());
        } else {
            return new Feedback(MESSAGE_WHICH_CLOSE, taskList);
        }
    }
}

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