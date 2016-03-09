package application.parser;

import java.io.IOException;

import application.storage.Storage;

public class DoneByNum implements Command {
    private static final String MESSAGE_DONE_FAILURE = "We encountered a problem while closing this task.";
    
    
    int numToClose;
    
    DoneByNum(int numToClose) {
        this.numToClose = numToClose;
    }
    
    public String execute(Storage storage){
        try {
            boolean isSuccess = storage.closeTaskInList(numToClose);
            if (isSuccess){
                return "Closed successfully"; //NEED TO CHANGE THIS SO THAT I SHOW DETAILS WHILE DELETING
            }else {
                return MESSAGE_DONE_FAILURE;
            }
        } catch (Exception e) {
            return MESSAGE_DONE_FAILURE;
        }
    }
    

}