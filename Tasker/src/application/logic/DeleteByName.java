package application.logic;

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public class DeleteByName implements UndoableCommand {
    private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_DELETE = "Deleted Task: %1$s";
    private static final String MESSAGE_NOTHING_TO_DELETE = "There is no task with that description.";
    private static final String MESSAGE_WHICH_DELETE = "Which task would you like to delete?";
    private static final String MESSAGE_DELETE_ERROR = "We encountered some "
            + "problem while deleting this task. We apologise for the inconvenience.";
    private static final String MESSAGE_UNDO_FAILURE = "We encountered a problem while undoing.";
    private static final String MESSAGE_UNDO_FEEDBACK = "Re-added: %1$s";
    
    String taskToDelete;
    StorageConnector storageConnector;
    Task deletedTask;
    
    DeleteByName(String taskToDelete){
        this.taskToDelete = taskToDelete;
    }
     
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        try{
            ArrayList<Task> taskList = storageConnector.searchTaskByName(taskToDelete);
            Feedback feedback = takeAction(taskList, storageConnector);
            return feedback;
        }catch(IOException e){
            return new Feedback(MESSAGE_DELETE_ERROR, storageConnector.getOpenList()); 
        }
    }
    
    public Feedback takeAction(ArrayList<Task> taskList, StorageConnector storageConnector) throws IOException{
        assert(taskList != null);
        this.storageConnector = storageConnector;
        if (taskList.size() ==  0){
            return new Feedback(MESSAGE_NOTHING_TO_DELETE, storageConnector.getOpenList());
        } else if (taskList.size() ==  1){
            deletedTask = storageConnector.deleteTask(taskList.get(FIRST_INDEX).getTaskIndex());
            String feedbackMessage = String.format(FEEDBACK_DELETE, deletedTask.toString());
            return new Feedback(feedbackMessage, storageConnector.getOpenList());
        } else {
            return new Feedback(MESSAGE_WHICH_DELETE, taskList);
        }
    }
    
    public Feedback undo() throws NothingToUndoException {
        try {
            if (deletedTask != null){
                storageConnector.addTaskInList(deletedTask.getTaskDescription(), deletedTask.getStartDate(),
                        deletedTask.getEndDate(),  deletedTask.getLocation(),  deletedTask.getRemindDate(),
                        deletedTask.getPriority());
                String feedbackMessage = String.format(MESSAGE_UNDO_FEEDBACK,deletedTask.toString());
                return new Feedback(feedbackMessage, storageConnector.getOpenList());
            }else{
                throw new NothingToUndoException();
            }
        }catch(IOException e){
            return new Feedback(MESSAGE_UNDO_FAILURE, storageConnector.getOpenList());
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
