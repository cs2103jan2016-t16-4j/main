package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public interface Command {

    Feedback execute(Storage storage, ArrayList<Task> tasksOnScreen);
    
}
