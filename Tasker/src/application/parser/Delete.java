package application.parser;

import application.storage.Storage;

public class Delete implements Command{
    Command delObj;
    
    
    Delete(String[] arguments, Storage storage){
        String taskToDelete = getString(arguments, 0, arguments.length - 1);
        initAppropDelete(taskToDelete, storage);
        
    }
    
    public void initAppropDelete(String taskToDel, Storage storage){
        try{
            int taskNumber = Integer.parseInt(taskToDel);
            delObj = new DeleteByNum(taskNumber, storage);
        }catch(NumberFormatException e){
            delObj = new DeleteByName(taskToDel, storage);
        }
    }

    private String getString(String[] args, int start, int end){
        String string = "";
        for(int i = start; i <= end; i++){
            string = string + args[i] + " ";
        }
        return string.trim();
    }
   
    
    public String execute(){
        String feedback = delObj.execute();
        return feedback;
    }
    
}
