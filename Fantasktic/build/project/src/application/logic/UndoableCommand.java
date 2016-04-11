package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

public interface UndoableCommand extends Command {
    @Override 
    Feedback execute(Storage storage, ArrayList<Task> tasks);
    Feedback undo();
}
