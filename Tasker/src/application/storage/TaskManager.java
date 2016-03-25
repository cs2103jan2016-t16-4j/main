package application.storage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager {
    private static final int EMPTY = 1;
    
	public ArrayList<Task> add(ArrayList<Task> fileList,
			String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {

		Task newTask = new Task();
		newTask.setTaskDescription(taskDescription);
		newTask.setStartDate(startDate);
		newTask.setStartTime(startDate);
		newTask.setEndDate(endDate);
		newTask.setEndTime(endDate);
		newTask.setLocation(location);
		newTask.setRemindDate(remindDate);
		newTask.setRemindTime(remindDate);
		newTask.setPriority(priority);
		newTask.setTaskIndex(taskIndex);
		fileList.add(newTask);
//		System.out.println("Added : "+newTask.toString());
		return fileList;
	}
	
	public ArrayList<ArrayList<Task>> close(ArrayList<Task> closedList, ArrayList<Task> fileList, int taskIndex) {
		ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
		boolean isSuccessClose = false;
	
		int index = -1;
		for (int i = 0; i < fileList.size(); i++) {				
			if (fileList.get(i).getTaskIndex() == taskIndex) {
				index = i;
				isSuccessClose = true;
				break;
				}
		}
		if (isSuccessClose) {
			closedList.add(fileList.get(index));
			fileList.remove(index);
		}
		lists.add(closedList);
		lists.add(fileList);
		return lists;
	}
	
	public ArrayList<ArrayList<Task>> unclose(ArrayList<Task> closedList, ArrayList<Task> fileList){
		ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
		
		fileList.add(closedList.get(closedList.size()-1));
		closedList.remove(closedList.size()-1);
		
		lists.add(closedList);
		lists.add(fileList);
		return lists;
	}	
	
	public ArrayList<Task> delete(ArrayList<Task> fileList, int taskIndex) {
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getTaskIndex() == taskIndex) {
				fileList.remove(i);
				break;
			}
		}
		return fileList;
	}
	
	public ArrayList<Task> searchName(ArrayList<Task> fileList, String searchTask) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < fileList.size(); i++) {
			Task obj = fileList.get(i);
			if (obj.getTaskDescription().toLowerCase()
					.contains(searchTask.toLowerCase())) {
				searchList.add(obj);
//				System.out.println("Found a entry..");
			}
		}

		return searchList;
	}
	
	public ArrayList<Task> searchDate(ArrayList<Task> fileList, Calendar searchDate) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i<fileList.size(); i++) {
			Task obj = fileList.get(i);
			if (obj.getEndTime().get(Calendar.YEAR)!=EMPTY) {
				if (obj.getEndDate().compareTo(searchDate)<=0){
					searchList.add(obj);
				}
			}
		}
		return searchList;
	}
	
	public ArrayList<Task> searchPriority(ArrayList<Task> fileList, String searchPriority) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < fileList.size(); i++) {
			Task obj = fileList.get(i);
			if (obj.getPriority().toLowerCase()
					.contains(searchPriority.toLowerCase())) {
				searchList.add(obj);
//				System.out.println("Found a entry..");			
				}
			}
		return searchList;
	}	
	
	public ArrayList<Task> sortDate(ArrayList<Task> fileList) {
		Collections.sort(fileList, (o1, o2) -> o1.getEndTime().compareTo(o2.getEndTime()));
		return fileList;
	}
	
	public ArrayList<Task> sortName(ArrayList<Task> fileList) {
		Collections.sort(fileList, (o1, o2) -> o1.getTaskDescription().compareTo(o2.getTaskDescription()));
		return fileList;
	}
	
	public ArrayList<Task> sortPriority(ArrayList<Task> fileList) {
		Collections.sort(fileList, new ComparatorPriority());
		return fileList;
	}
	
	public ArrayList<Task> update(ArrayList<Task> fileList,
			String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {
		int index = 0;	
		
		// find task
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getTaskIndex() == taskIndex) {
				index = i;
				break;
			}
		}
		
		// update task
		if (!taskDescription.equalsIgnoreCase("")) {
			fileList.get(index).setTaskDescription(taskDescription);
		}
		if (startDate.get(Calendar.YEAR)!=EMPTY) {
			fileList.get(index).setStartDate(startDate);
		}
		if (startDate.get(Calendar.MILLISECOND)!=EMPTY && startDate.get(Calendar.HOUR_OF_DAY)!=0 && startDate.get(Calendar.MINUTE)!=0 && startDate.get(Calendar.SECOND)!=0) {
			fileList.get(index).setStartTime(startDate);
		}
		if (endDate.get(Calendar.YEAR)!=EMPTY) {
			fileList.get(index).setEndDate(endDate);
		}
		if (endDate.get(Calendar.MILLISECOND)!=EMPTY && endDate.get(Calendar.HOUR_OF_DAY)!=0 && endDate.get(Calendar.MINUTE)!=0 && endDate.get(Calendar.SECOND)!=0) {
			fileList.get(index).setEndTime(endDate);
		}
		if (!location.equalsIgnoreCase("")) {
			fileList.get(index).setLocation(location);
		}
		if (remindDate.get(Calendar.YEAR)!=EMPTY) {
			fileList.get(index).setRemindDate(remindDate);
		}
		if (remindDate.get(Calendar.MILLISECOND)!=EMPTY && remindDate.get(Calendar.HOUR_OF_DAY)!=0 && remindDate.get(Calendar.MINUTE)!=0 && remindDate.get(Calendar.SECOND)!=0) {
			fileList.get(index).setRemindDate(remindDate);
		}
		if (!priority.equalsIgnoreCase("")) {
			fileList.get(index).setPriority(priority);
		}
		
		// return updated list
		return fileList;

	}
}

class ComparatorPriority implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
    	int ob1=0,ob2=0;
    	if (o1.getPriority().equals(""))
    		ob1 = 4;
    	if (o1.getPriority().equalsIgnoreCase("low"))
    		ob1 = 3;
    	if (o1.getPriority().equalsIgnoreCase("medium"))
    		ob1 = 2;
    	if (o1.getPriority().equalsIgnoreCase("high"))
    		ob1 = 1;
    	if (o2.getPriority().equalsIgnoreCase(""))
    		ob2 = 4;
    	if (o2.getPriority().equalsIgnoreCase("low"))
    		ob2 = 3;
    	if (o2.getPriority().equalsIgnoreCase("medium"))
    		ob2 = 2;
    	if (o2.getPriority().equalsIgnoreCase("high"))
    		ob2 = 1;
    	
    	// nothing = 4, low = 3, medium = 2, high = 1
        return Integer.compare(ob1,ob2);
    }
}
