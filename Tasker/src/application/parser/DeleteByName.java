package application.parser;

import application.storage.Storage;

public class DeleteByName implements Command {
    
    String taskToDelete;
    
    DeleteByName(String taskToDelete){
        this.taskToDelete = taskToDelete;
    }
     
    //NEED TO IMPLEMENT THIS
    public String execute(Storage storage){
        return "FEEDBACK FOR DELETE BY NAME";
    }
    
    
    
    
}
