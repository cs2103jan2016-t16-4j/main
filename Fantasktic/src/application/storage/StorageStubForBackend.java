package application.storage;
//@@author A0132632R
import java.util.ArrayList;
import java.util.Calendar;
/**
 * This stub is used for dependency injection needed by the backend package
 * @author Pratyush
 *
 */
public class StorageStubForBackend extends Storage {
    ArrayList<Task> tasks = new ArrayList<Task>();
    private int size;
    public StorageStubForBackend(int size) {
        this.size = size;
        createListForTest(size);
    }
    
    //To create a list for testing. Making logic isolated from actual storage while testing.
    public void createListForTest(int numTasks){
        tasks.clear();
        for (int i = 1; i <= numTasks ; i++){
            Task task = new EventTask("Task " + i , Calendar.getInstance(), Calendar.getInstance(), "Test House", Calendar.getInstance(), "LOW", i);
            task.getStartDate().add(Calendar.HOUR_OF_DAY, 4);
            task.getEndDate().add(Calendar.HOUR_OF_DAY, 4);
            tasks.add(task);
        }
        tasks.get(size/2).setPriority("HIGH");//To test for searchByPriority
    }
    
    @Override
    public ArrayList<Task> getOpenList(){
        createListForTest(size);
        return tasks;
    }
    
    @Override
    public Task addTaskInList(String description, Calendar startDateTime
            ,Calendar endDateTime, String location, Calendar remindDate,String priority){
        createListForTest(size);
        Task task;
        if (startDateTime == null && endDateTime == null){
            task = new FloatingTask(description, location, remindDate, priority, tasks.size() + 1);
        } else if (startDateTime == null){
            task = new DeadlineTask(description,endDateTime, location, remindDate, priority, tasks.size() + 1);
        } else{
            task = new EventTask(description, startDateTime,endDateTime,location,remindDate, priority, tasks.size() + 1);
        }
        return task;
    }
    
    @Override
    public Task deleteTask(int index){
        createListForTest(size);
        return getTaskById(index);
    }
    
    @Override
    public Task closeTask(int index){
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
    
    @Override
    public ArrayList<Task> searchTaskByName(String name){
        createListForTest(size);
        ArrayList<Task> matches = new ArrayList<Task>();
        for (Task task: tasks){
            if (task.getTaskDescription().contains(name)){
                matches.add(task);
            }
        }
        return matches;
    }
    
    @Override
    public void replaceTask(int index, Task task){
        //Just for testing purposes
    }
    
    @Override
    public ArrayList<Task> searchTaskByPriority(String priority){
        createListForTest(size);
        ArrayList<Task> matches = new ArrayList<Task>();
        for (Task task: tasks){
            if (task.getPriority().equalsIgnoreCase(priority)){
                matches.add(task);
            }
        }
        return matches;
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
