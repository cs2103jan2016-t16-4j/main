package application.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import application.storage.Storage;
import application.storage.Task;

//@@author A0132632R


public class StorageConnector {
    Storage storage = new Storage();
    
    public void setDirectory(String filePath) throws IOException{
        storage.setDirectory(filePath);
    }
    
    public void initialise() throws IOException{
        storage.initialise();
    }
    
    public ArrayList<Task> getOpenList(){
        return storage.getOpenList();
    }
    
    public boolean directoryExists() throws IOException{
        return storage.directoryExists();
    }
    
    public Task addTaskInList(String description, Calendar startDateTime
            ,Calendar endDateTime, String location, Calendar remindDate,String priority) throws IOException{
        return storage.addTaskInList(description, startDateTime, endDateTime, location, remindDate, priority);
    }
    
    public Task deleteTask(int index) throws IOException{
        return storage.deleteTask(index);
    }
    
    public ArrayList<Task> searchTaskByName(String taskToDelete){
        return storage.searchTaskByName(taskToDelete);
    }
    
    public Task uncloseTask(int index) throws IOException{
        return storage.uncloseTask(index);
    }
    
    public Task closeTask(int index) throws IOException{
        return storage.closeTask(index);
    }
    
    public ArrayList<Task> searchTaskByDate(Calendar date){
        return storage.searchTaskByDate(date);
    }
    
    public ArrayList<Task> searchTaskByPriority(String priority) {
        return storage.searchTaskByPriority(priority);
    }
    
    public ArrayList<Task> searchTaskOnDate(Calendar date){
        return storage.searchTaskOnDate(date);
    }
    
    public ArrayList<Task> getClosedList(){
        return storage.getCloseList();
    }
    
    public ArrayList<Task> updateTask(int idTaskToDelete, String description, 
            Calendar startDateTime, Calendar endDateTime
            ,String location
            , Calendar remindDate, String priority) throws IOException, CloneNotSupportedException{
        return storage.updateTask(idTaskToDelete, description, 
                startDateTime, endDateTime
                ,location
                , remindDate, priority);
    }
    
}
