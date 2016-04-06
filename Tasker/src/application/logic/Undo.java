package application.logic;

import java.util.ArrayList;
import java.util.EmptyStackException;

import application.storage.Storage;
import application.storage.Task;

public class Undo implements Command {
    private static final String MESSAGE_NOTHING_TO_UNDO = "There are no commands left to undo!";
    
    
    public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks){
        try{
            History history = History.getInstance();
            return history.undo();
        }catch(EmptyStackException e){
            return new Feedback(MESSAGE_NOTHING_TO_UNDO, storageConnector.getOpenList());
        }
    }
}
