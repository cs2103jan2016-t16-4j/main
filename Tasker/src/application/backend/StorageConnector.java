package application.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import application.storage.Storage;
import application.storage.Task;

//@@author A0132632R
/**
 * This class serves as a connector between backend and storage. 
 * All requests to storage from the backend package are routed through this class.
 * @author Pratyush
 *
 */

public class StorageConnector {
    Storage storage = new Storage();
    
    public StorageConnector(){
    }
    
    //For testing purposes
    protected StorageConnector(Storage storage){
        this.storage = storage;
    }
    
    public void setDirectory(String filePath) throws IOException{
        storage.setDirectory(filePath);
    }
    
    public void initialise() throws IOException{
        storage.initialise();
    }
    
    public ArrayList<Task> getOpenList(){
        ArrayList<Task> tasks = storage.getOpenList();
        assert(tasks != null);
        return tasks;
    }
    
    public boolean directoryExists() throws IOException{
        return storage.directoryExists();
    }
    
    public Task addTaskInList(String description, Calendar startDateTime
            ,Calendar endDateTime, String location, Calendar remindDate,String priority) throws IOException{
        Task addedTask =  storage.addTaskInList(description, startDateTime, endDateTime, location, remindDate, priority);
        assert(addedTask != null);
        return addedTask;
    }
    
    public Task deleteTask(int index) throws IOException{
        Task deletedTask = storage.deleteTask(index);
        assert(deletedTask != null);
        return deletedTask;
    }
    
    public ArrayList<Task> searchTaskByName(String taskToDelete){
        ArrayList<Task> tasks = storage.searchTaskByName(taskToDelete);
        assert(tasks != null);
        return tasks;
    }
    
    public Task uncloseTask(int index) throws IOException{
        Task task = storage.uncloseTask(index);
        assert(task != null);
        return task;
    }
    
    public Task closeTask(int index) throws IOException{
        Task task = storage.closeTask(index);
        assert(task != null);
        return task;
    }
    
    public ArrayList<Task> searchTaskByDate(Calendar date){
        ArrayList<Task> tasks = storage.searchTaskByDate(date);
        assert(tasks != null);
        return tasks;
    }
    
    public ArrayList<Task> searchTaskByPriority(String priority) {
        ArrayList<Task> tasks = storage.searchTaskByPriority(priority);
        assert(tasks != null);
        return tasks;
    }
    
    public ArrayList<Task> searchTaskOnDate(Calendar date){
        ArrayList<Task> tasks = storage.searchTaskOnDate(date);
        assert(tasks != null);
        return tasks;
    }
    
    public ArrayList<Task> getClosedList(){
        ArrayList<Task> closedTasks = storage.getCloseList();
        assert(closedTasks != null);
        return closedTasks;
    }
    
    public ArrayList<Task> updateTask(int idTaskToDelete, String description, 
            Calendar startDateTime, Calendar endDateTime
            ,String location
            , Calendar remindDate, String priority) throws IOException, CloneNotSupportedException{
        ArrayList<Task> tasks = storage.updateTask(idTaskToDelete, description, 
                startDateTime, endDateTime
                ,location
                , remindDate, priority);
        assert(tasks != null);
        return tasks;
    }
    
}
