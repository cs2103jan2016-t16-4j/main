package application.logic;

import application.storage.Storage;

public interface UndoableCommand extends Command {
    @Override 
    Feedback execute(Storage storage);
    Feedback undo();
}
