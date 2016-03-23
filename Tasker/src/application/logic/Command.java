package application.logic;

import application.storage.Storage;

public interface Command {

    Feedback execute(Storage storage);
    
}
