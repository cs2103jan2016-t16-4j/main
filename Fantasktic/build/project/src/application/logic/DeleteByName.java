package application.logic;

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class DeleteByName implements UndoableCommand {
    private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_DELETE = "Deleted Task: %1$s";
    private static final String MESSAGE_WHICH_DELETE = "Which task would you like to delete?";
    private static final String MESSAGE_DELETE_ERROR = "We encountered some "
            + "problem while deleting this task. We apologise for the inconvenience.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_UNDO_FEEDBACK = "Re-added: %1$s";
    
    String taskToDelete;
    Storage storage;
    Task deletedTask;
    
    DeleteByName(String taskToDelete){
        this.taskToDelete = taskToDelete;
    }
     
    public Feedback execute(Storage storage, ArrayList<Task> tasks){
        try{
            ArrayList<Task> taskList = storage.searchTaskByName(taskToDelete);
            Feedback feedback = takeAction(taskList, storage);
            return feedback;
        }catch(IOException e){
            return new Feedback(MESSAGE_DELETE_ERROR, storage.getFileList()); 
        }
    }
    
    public Feedback takeAction(ArrayList<Task> taskList, Storage storage) throws IOException{
        assert(taskList.size() > 0);
        this.storage = storage;
        if (taskList.size() ==  1){
            deletedTask = storage.deleteTask(taskList.get(FIRST_INDEX).getTaskIndex());
            String feedbackMessage = String.format(FEEDBACK_DELETE, deletedTask.toString());
            return new Feedback(feedbackMessage, storage.getFileList());
        } else {
            return new Feedback(MESSAGE_WHICH_DELETE, taskList);
        }
    }
    
    public Feedback undo(){
        try {
            if (deletedTask != null){
                storage.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
                deletedTask.getEndDate(),  deletedTask.getLocation(),  deletedTask.getRemindDate(),
                deletedTask.getPriority());
                String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,deletedTask.toString());
                return new Feedback(feedbackMessage, storage.getFileList());
            }else{
                new NothingToUndoException();
                return new Feedback("", storage.getFileList());
            }
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storage.getFileList());
        }
    }

    /*private String deleteSingleTask(ArrayList<Task> taskList, Storage storage) throws IOException {
        String feedback = String.format(FEEDBACK_DELETE, 
                taskList.get(FIRST_INDEX).getTaskDescription());
        storage.deleteTaskFromSearch(FIRST_INDEX);
        return feedback;
    }
    
    private String listTasks(ArrayList<Task> taskList){
        String listed = "Which task would you like to delete?";
        int i = 1;
        for (Task task: taskList){
            listed = listed + "\n" + i + ") " + task.getTaskDescription();
            i++;
        }
        return listed;
    }
    */
}
