package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

//@@author A0132632R


public interface UndoableCommand extends Command {
    @Override 
    Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks);
    Feedback undo() throws NothingToUndoException;
}
