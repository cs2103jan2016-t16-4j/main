package application.logic;

import application.storage.Storage;
import java.io.IOException;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DoneByNum implements Command {
    private static final String MESSAGE_DONE_FAILURE = "We encountered a problem while closing this task.";
    Logger logger =null;
   
    int numToClose;
    
    DoneByNum(int numToClose) {
        this.numToClose = numToClose;
        logger=Logger.getLogger("Logfile");  
        FileHandler fileHandler; 
        try {
            fileHandler = new FileHandler("LogFile.log");
            fileHandler.setLevel(Level.INFO); 
            logger.info("initial DoneByNum");
        } catch (IOException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(DoneByNum.class.getName()).log(Level.SEVERE, null, ex);
        }
      
    }
    
    public String execute(Storage storage){
        assert storage!=null;
        try {
            boolean isSuccess = storage.closeTaskInList(numToClose);
            if (isSuccess){
                logger.info("Closed successfully");
                return "Closed successfully"; //NEED TO CHANGE THIS SO THAT I SHOW DETAILS WHILE DELETING
            }else {
                logger.info("MESSAGE_DONE_FAILURE");
                return MESSAGE_DONE_FAILURE;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            return MESSAGE_DONE_FAILURE;
        }
    }
    

}