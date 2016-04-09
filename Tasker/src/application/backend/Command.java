package application.backend;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;
//@@author A0132632R

public interface Command {

    Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen);
    
}
