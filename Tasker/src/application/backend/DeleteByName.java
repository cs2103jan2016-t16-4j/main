package application.backend;
//@@author A0132632R

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Task;

public class DeleteByName implements UndoableCommand {
    private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_DELETE = "Deleted Task: %1$s";
    private static final String MESSAGE_NOTHING_TO_DELETE = "There is no task with that description.";
    private static final String MESSAGE_CANNOT_DELETE_EMPTY = "Please enter some keywords from, or the number of, the task you want to delete.";
    private static final String MESSAGE_WHICH_DELETE = "Which task would you like to delete?";
    private static final String MESSAGE_DELETE_ERROR = "We encountered some "
            + "problem while deleting this task. We apologise for the inconvenience.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_UNDO_FEEDBACK = "Re-added: %1$s";
    
    String taskToDelete;
    StorageConnector storageConnector;
    Task deletedTask;
    
    DeleteByName(String taskToDelete){
        System.out.println("HERE" +taskToDelete);
        this.taskToDelete = taskToDelete;
    }
     
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        try{
            ArrayList<Task> taskList = storageConnector.searchTaskByName(taskToDelete);
            Feedback feedback = takeAction(taskList, storageConnector);
            return feedback;
        }catch(IOException e){
            Feedback feedback = new Feedback(MESSAGE_DELETE_ERROR, storageConnector.getOpenList(), null);
            feedback.setCalFlag();
            return feedback;
        }
    }
    
    public Feedback takeAction(ArrayList<Task> taskList, StorageConnector storageConnector) throws IOException{
        assert(taskList != null);
        this.storageConnector = storageConnector;
        System.out.println(taskList.size());
        if (taskToDelete.trim().equalsIgnoreCase("")){
            return getFeedbackCal(MESSAGE_CANNOT_DELETE_EMPTY, storageConnector.getOpenList(), null);
        }
        return takeActionBasedOnListLength(taskList, storageConnector);
    }

    private Feedback takeActionBasedOnListLength(ArrayList<Task> taskList, StorageConnector storageConnector)
            throws IOException {
        if (taskList.size() ==  0){
            return getFeedbackCal(MESSAGE_NOTHING_TO_DELETE, storageConnector.getOpenList(), null);
            
        } else if (taskList.size() ==  1){
            deletedTask = storageConnector.deleteTask(taskList.get(FIRST_INDEX).getTaskIndex());
            String feedbackMessage = String.format(FEEDBACK_DELETE, deletedTask.toString());
            return getFeedbackCal(feedbackMessage, storageConnector.getOpenList(), null);
        } else {
            return getFeedbackList(MESSAGE_WHICH_DELETE, taskList,null);
        }
    }
    
    public Feedback undo() throws NothingToUndoException {
        try {
            if (deletedTask != null){
                return reAddTask();
            }else{
                throw new NothingToUndoException();
            }
        }catch(IOException e){
            return getFeedbackCal(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList(), null);
        }
    }

    private Feedback reAddTask() throws IOException {
        storageConnector.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
                deletedTask.getEndDate(),  deletedTask.getLocation(),  deletedTask.getRemindDate(),
                deletedTask.getPriority());
        String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,deletedTask.toString());
        return getFeedbackList(feedbackMessage, storageConnector.getOpenList(), deletedTask);
    }

    private Feedback getFeedbackCal(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setCalFlag();
        return fb;
    }
    
    
    private Feedback getFeedbackList(String message, ArrayList<Task> tasks, Task task){
        Feedback fb = new Feedback(message, tasks, task);
        fb.setListFlag();
        return fb;
    }
    
}
