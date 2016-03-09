package application.parser;

import application.storage.Storage;

public class Delete implements Command{
    
    public static final int ARRAY_INDEXING_OFFSET = 1;

    Command delObj;
    
    
    Delete(String[] arguments){
        String taskToDelete = getString(arguments, 0, arguments.length - 1);
        initAppropDelete(taskToDelete);
        
    }
    
    public void initAppropDelete(String taskToDel){
        try{
            int taskNumber = Integer.parseInt(taskToDel) - ARRAY_INDEXING_OFFSET ;
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
   
    
    public Feedback execute(Storage storage){
        Feedback feedback = delObj.execute(storage);
        return feedback;
    }
    
}
