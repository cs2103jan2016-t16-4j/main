package application.parser;

import application.storage.Storage;

public interface Command {

    String execute(Storage storage);
    
   // String undo();
}
