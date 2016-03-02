package application.parser;

import application.storage.Storage;

public class Delete implements Command{
    Command delObj;
    
    
    Delete(String[] arguments){
        String taskToDelete = getString(arguments, 0, arguments.length - 1);
        initAppropDelete(taskToDelete);
        
    }
    
    public void initAppropDelete(String taskToDel){
        try{
            int taskNumber = Integer.parseInt(taskToDel);
            delObj = new DeleteByNum(taskNumber);
        }catch(NumberFormatException e){
            delObj = new DeleteByName(taskToDel);
        }
    }

    private String getString(String[] args, int start, int end){
        String string = "";
        for(int i = start; i <= end; i++){
            string = string + args[i] + " ";
        }
        return string.trim();
    }
   
    
    public String execute(Storage storage){
        String feedback = delObj.execute(storage);
        return feedback;
    }
    
}
