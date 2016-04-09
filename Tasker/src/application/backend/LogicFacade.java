package application.backend;
//@@author A0132632R

import java.io.IOException;
import java.util.ArrayList;

import application.storage.Task;

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

    public ArrayList<Task> getClashes (Task task){
        return logic.getClashes(task);
    }
    
    public int getCompletedTaskCount() {
        return logic.getCompletedTaskCount();
    }

    public int getRemainingTaskCount() {
        return logic.getRemainingTaskCount();
    }

    public int getOverdueTaskCount() {
        return logic.getOverdueTaskCount();
    }

    public Feedback executeCommand(String command,  ArrayList<Task> tasksOnScreen)throws NoDescriptionException {
        return logic.executeCommand(command, tasksOnScreen);
    }
}
