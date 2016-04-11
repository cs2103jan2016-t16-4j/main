package application.backend;
//@@author A0132632R

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Task;

/**
 * This class is an UndoableCommand object which deletes a task that the user
 * has specified by its task number in the list of tasks displayed to him/her. 
 * Check UndoableCommand documentation for insight on what the public methods do.
 * 
 * @author Pratyush
 *
 */
public class DeleteByNum implements UndoableCommand {
    private static final String MESSAGE_DELETE_FAILURE = "We encountered a problem while deleting this task.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_DELETE_FEEDBACK = "Deleted Task: %1$s";
    private static final String MESSAGE_UNDO_FEEDBACK = "Re-added: %1$s";
    private static final String MESSAGE_INDEX_PROBLEM = "Please enter a valid number.";

    Task deletedTask;
    int numToDelete;
    StorageConnector storageConnector;

    DeleteByNum(int numToDelete) {
        this.numToDelete = numToDelete;
    }

    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
        this.storageConnector = storageConnector;
        try {
            return deleteTask(storageConnector, tasks);
        } catch (IOException e) {
            return getFeedbackCal(MESSAGE_DELETE_FAILURE, storageConnector.getOpenList(), null);
        } catch (IndexOutOfBoundsException e) {
            return getFeedbackCal(MESSAGE_INDEX_PROBLEM, storageConnector.getOpenList(), null);
        }
    }

    private Feedback deleteTask(StorageConnector storageConnector, ArrayList<Task> tasks) throws IOException {
        int idOfTaskToDelete = tasks.get(numToDelete).getTaskIndex();
        deletedTask = storageConnector.deleteTask(idOfTaskToDelete);
        String feedbackMessage = String.format(MESSAGE_DELETE_FEEDBACK, deletedTask.toString());
        Feedback feedback = new Feedback(feedbackMessage, storageConnector.getOpenList(), null);
        feedback.setCalFlag();
        return feedback;
    }

    public Feedback undo() throws NothingToUndoException {
        try {
            raiseExceptionIfNoDeletedTask();
            storageConnector.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
                                           deletedTask.getEndDate(), deletedTask.getLocation(), 
                                           deletedTask.getRemindDate(), deletedTask.getPriority());
            String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK, deletedTask.toString());
            return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), deletedTask);
        } catch (IOException e) {
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        }
    }

    private void raiseExceptionIfNoDeletedTask() throws NothingToUndoException {
        if (deletedTask == null){
            throw new NothingToUndoException();
        }
    }

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
