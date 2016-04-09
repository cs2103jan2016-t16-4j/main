package application.storage;
//@@author A0132632R
import java.util.ArrayList;
import java.util.Calendar;
/**
 * This stub is used for dependency injection needed by the backend package
 * @author Pratyush
 *
 */
public class StorageStubForLogic extends Storage {
    ArrayList<Task> tasks = new ArrayList<Task>();
    private int size;
    public StorageStubForLogic(int size) {
        this.size = size;
        createListForTest(size);
    }
    
    public void createListForTest(int numTasks){
        tasks.clear();
        for (int i = 1; i <= numTasks ; i++){
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
        createListForTest(size);
        Task task = new EventTask(description, startDateTime,endDateTime,location,remindDate, priority, tasks.size() + 1);
        return task;
    }
    @Override
    public Task deleteTask(int index){
        createListForTest(size);
        return getTaskById(index);
    }
    
    @Override
    public ArrayList<Task> updateTask(int idTaskToDelete, String description, 
            Calendar startDateTime, Calendar endDateTime
            ,String location
            , Calendar remindDate, String priority){
        createListForTest(size);
        Task task = getTaskById(idTaskToDelete);
        task.setTaskDescription(description);
        ArrayList<Task> tasksFromUpdate = new ArrayList<Task>();
        tasksFromUpdate.add(task);
        tasksFromUpdate.add(task);
        return tasksFromUpdate;
    }
    
    private Task getTaskById(int id){
        for (Task task: tasks){
            if (task.getTaskIndex() == id){
                return task;
            }
        }
        return new EventTask("NO MATCH FOUND", Calendar.getInstance(), Calendar.getInstance(), "Could not find", Calendar.getInstance(), "", -1);
    }
}
