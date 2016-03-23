package application.logic;

import application.storage.Storage;
import application.storage.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Done implements Command{
    Command closeObj;
         Logger logger =null;
    
    Done(String[] arguments){
        assert(arguments!=null);
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
        String taskToClose = getString(arguments, 0, arguments.length - 1);
        initAppropClose(taskToClose);
        
    }
    
    public void initAppropClose(String taskToClose){
        try{
            int taskNumber = Integer.parseInt(taskToClose);
            logger.info("takeAction DoneByNum");
            closeObj = new DoneByNum(taskNumber);
        }catch(NumberFormatException e){
            logger.info("takeAction DoneByName");
            closeObj = new DoneByName(taskToClose);
        }
    }

    private String getString(String[] args, int start, int end){
        assert(start>=0);
        assert(end>=0);
        String string = "";
        for(int i = start; i <= end; i++){
            string = string + args[i] + " ";
        }
        return string.trim();
    }
   
    
    public String execute(Storage storage, ArrayList<Task> tasks){
        assert storage!=null;        
        String feedback = closeObj.execute(storage, tasks);
        return feedback;
    }
    
}
