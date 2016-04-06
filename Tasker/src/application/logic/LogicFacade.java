package application.logic;

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Task;
//@@author A0132632R

public class LogicFacade {
    private Logic logic = new Logic();
    
    public boolean checkIfFileExists() throws IOException{
        return logic.checkIfFileExists();
    }
    
    public ArrayList<Task> loadDataFile() throws IOException{
        return logic.loadDataFile();
    }
    
    public void setDirectory(String filePath) throws IOException{
        logic.setDirectory(filePath);
    }
    
    public Feedback executeCommand(String command,  ArrayList<Task> tasksOnScreen)throws NoDescriptionException {
        return logic.executeCommand(command, tasksOnScreen);
    }
}
