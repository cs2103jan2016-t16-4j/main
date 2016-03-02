package application.parser;

import application.storage.Storage;

public class DeleteByName implements Command {
    
    String taskToDelete;
    Storage storage;
    
    DeleteByName(String taskToDelete, Storage storage){
        this.taskToDelete = taskToDelete;
        this.storage = storage;
    }
     
    //NEED TO IMPLEMENT THIS
    public String execute(){
        return "FEEDBACK FOR DELETE BY NAME";
    }
    
    
    
    
}
