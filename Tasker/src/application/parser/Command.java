package application.parser;

import application.storage.Storage;

public interface Command {

    Feedback execute(Storage storage);
    
   // String undo();
}
