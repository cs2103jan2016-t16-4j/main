package application.parser;

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class DeleteByName implements Command {
    private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_DELETE = "Deleted Task: %1$s";
    private static final String MESSAGE_WHICH_DELETE = "Which task would you like to delete?";
    private static final String MESSAGE_DELETE_ERROR = "We encountered some "
            + "problem while deleting this task. We apologise for the inconvenience.";
    
    
    String taskToDelete;
    
    DeleteByName(String taskToDelete){
        this.taskToDelete = taskToDelete;
    }
     
    public Feedback execute(Storage storage){
        try{
            ArrayList<Task> taskList = storage.searchByTask(taskToDelete);
            Feedback feedback = takeAction(taskList, storage);
            return feedback;
        }catch(IOException e){
            return new Feedback(MESSAGE_DELETE_ERROR, storage.getAllTasks()); 
        }
    }
    
    public Feedback takeAction(ArrayList<Task> taskList, Storage storage) throws IOException{
        assert(taskList.size() > 0);
        if (taskList.size() ==  1){
            String feedbackFromStorage = storage.deleteTaskFromSearch(FIRST_INDEX);
            String feedbackMessage = String.format(FEEDBACK_DELETE, feedbackFromStorage);
            return new Feedback(feedbackMessage, storage.getAllTasks());
        } else {
            return new Feedback(MESSAGE_WHICH_DELETE, taskList);
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
