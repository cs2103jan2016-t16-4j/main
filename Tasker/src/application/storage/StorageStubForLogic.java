package application.storage;

import java.util.ArrayList;
import java.util.Calendar;

public class StorageStubForLogic extends Storage {
    ArrayList<Task> tasks = new ArrayList<Task>();
    
    public StorageStubForLogic() {
        createListForTest();
    }
    
    public void createListForTest(){
        tasks.clear();
        for (int i = 1; i < 11 ; i++){
            Task task = new EventTask("Task " + i , Calendar.getInstance(), Calendar.getInstance(), "Test House", Calendar.getInstance(), "LOW", i);
            tasks.add(task);
        }
    }
    
    @Override
    public ArrayList<Task> getOpenList(){
        return tasks;
    }
    
    @Override
    public Task addTaskInList(String description, Calendar startDateTime
            ,Calendar endDateTime, String location, Calendar remindDate,String priority){
        Task task = new EventTask(description, startDateTime,endDateTime,location,remindDate, priority, tasks.size() + 1);
        tasks.add(task);
        return task;
    }
    
    public Task deleteTask(int index){
        createListForTest();
        for (Task task: tasks){
            if (task.getTaskIndex() == index){
                return task;
            }
        }
        return new EventTask("NO MATCH FOUND", Calendar.getInstance(), Calendar.getInstance(), "Could not find", Calendar.getInstance(), "", -1);
    }
}
