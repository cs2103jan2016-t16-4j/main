package application.parser;

import java.io.IOException;
import java.security.MessageDigestSpi;

import javax.xml.ws.handler.MessageContext;

import application.storage.Storage;

public class DeleteByNum implements Command {
    private static final String MESSAGE_DELETE_FAILURE = "We encountered a problem while deleting this task.";
    
    
    Storage storage;
    int numToDelete;
    
    DeleteByNum(int numToDelete, Storage storage) {
        this.numToDelete = numToDelete;
        this.storage = storage;
    }
    
    public String execute(){
        try {
            boolean isSuccess = storage.deleteTaskInList(numToDelete);
            if (isSuccess){
                return "Deleted successfully"; //NEED TO CHANGE THIS SO THAT I SHOW DETAILS WHILE DELETING
            }else {
                return MESSAGE_DELETE_FAILURE;
            }
        } catch (IOException e) {
            return MESSAGE_DELETE_FAILURE;
        }
    }
    

}
