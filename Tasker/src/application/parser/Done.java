package application.parser;

import application.storage.Storage;

public class Done implements Command{
    private static final int ARRAY_OFFSET = 1;
    
    Command closeObj;
    
    
    Done(String[] arguments){
        String taskToClose = getString(arguments, 0, arguments.length - 1);
        initAppropClose(taskToClose);
        
    }
    
    public void initAppropClose(String taskToClose){
        try{
            int taskNumber = Integer.parseInt(taskToClose) - ARRAY_OFFSET;
            closeObj = new DoneByNum(taskNumber);
        }catch(NumberFormatException e){
            closeObj = new DoneByName(taskToClose);
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
        Feedback feedback = closeObj.execute(storage);
        return feedback;
    }
    
}
