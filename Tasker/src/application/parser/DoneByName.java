package application.parser;

import application.storage.Storage;

public class DoneByName implements Command {
    
    String taskToClose;
    
    DoneByName(String taskToClose){
        this.taskToClose = taskToClose;
    }
     
    //NEED TO IMPLEMENT THIS
    public String execute(Storage storage){
        return "FEEDBACK FOR DONE BY NAME";
    }
    
    
    
    
}
