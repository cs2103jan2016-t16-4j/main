package application.backend;
//@@author A0132632R

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;



public interface UndoableCommand extends Command {
    @Override 
    Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks);
    Feedback undo() throws NothingToUndoException;
}
