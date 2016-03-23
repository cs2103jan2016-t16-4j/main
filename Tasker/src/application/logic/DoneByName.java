package application.logic;

import application.storage.Storage;
import application.storage.Task;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoneByName implements Command {
     private static final int FIRST_INDEX = 0;
    private static final String FEEDBACK_CLOSE = "CLOSE Task: %1$s";
    private static final String MESSAGE_CLOSE_ERROR = "We encountered some "
            + "problem while closing this task. We apologise for the inconvenience.";
      Logger logger =null;
    
  
    String taskToClose;
    
    DoneByName(String taskToClose){
        this.taskToClose = taskToClose;
           logger=Logger.getLogger("Logfile");  
        FileHandler fileHandler; 
        try {
            fileHandler = new FileHandler("LogFile.log");
            fileHandler.setLevel(Level.INFO); 
            logger.info("initial DoneByName");
        } catch (IOException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
     
    //NEED TO IMPLEMENT THIS
    public String execute(Storage storage){
         try{
            ArrayList<Task> taskList = storage.searchByTask(taskToClose);
            String feedback = takeAction(taskList, storage);
            logger.info("execute DoneByName");
            return feedback;
        }catch(IOException e){
            logger.info("MESSAGE_CLOSE_ERROR");
            return MESSAGE_CLOSE_ERROR;
        }
    }
    
    public String takeAction(ArrayList<Task> taskList, Storage storage) throws IOException{
        assert(taskList!=null);
        assert(storage!=null);
        assert(taskList.size() > 0);
        if (taskList.size() ==  1){
            logger.info("takeAction closeSingleTask");
            return closeSingleTask(taskList, storage);
        } else {
            logger.info("takeAction listTasks");
            return listTasks(taskList);
        }
    }

    private String closeSingleTask(ArrayList<Task> taskList, Storage storage) throws IOException {
        assert(taskList!=null);
        assert(storage!=null);
       
        String feedback = String.format(FEEDBACK_CLOSE, 
                taskList.get(FIRST_INDEX).getTaskDescription());
        try{
            storage.closeTaskInList(FIRST_INDEX);
            logger.info("closeSingleTask");
            return feedback;
        }
        catch(Exception e) 
        {
            logger.info(e.getMessage());
            return feedback;
        }
    }
    
    private String listTasks(ArrayList<Task> taskList){
        assert(taskList!=null);
        String listed = "\nWhich task would you like to close?";
        try
        {
            int i = 1;
            for (Task task: taskList){
                listed = listed + "\n" + i + ") " + task.getTaskDescription();
                i++;
            }
              logger.info("listTasks");
            return listed;
        }
        catch(Exception e)
        {
             logger.info(e.getMessage());
             return listed;
        }
    }
    
    
    
}
