package application.parser;

import application.storage.Storage;


public class Add {
    
    Storage storage;
    String[] arguments;
    String descripton;
    String startDateTime;
    String endDateTime;
    
    
    Add(Storage storage, String[] arguments){
        this.storage = storage;
        this.arguments =arguments;
        interpretArguments(arguments);
    }
    
    private void interpretArguments (String[] arguments){
        
        
    }
    
    
}
