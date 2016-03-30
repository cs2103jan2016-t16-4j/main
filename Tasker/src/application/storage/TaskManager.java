package application.storage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager {
    private static final int EMPTY = 1;
//	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
	
	public ArrayList<Task> add(ArrayList<Task> openList,
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
		openList.add(newTask);
//		System.out.println("Added : "+newTask.toString());
		return openList;
	}
	
	public ArrayList<ArrayList<Task>> close(ArrayList<Task> closedList, ArrayList<Task> openList, int taskIndex) {
		ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
		boolean isSuccessClose = false;
	
		int index = -1;
		for (int i = 0; i < openList.size(); i++) {				
			if (openList.get(i).getTaskIndex() == taskIndex) {
				index = i;
				isSuccessClose = true;
				break;
				}
		}
		if (isSuccessClose) {
			closedList.add(openList.get(index));
			openList.remove(index);
		}
		lists.add(closedList);
		lists.add(openList);
		return lists;
	}
	
	public ArrayList<ArrayList<Task>> unclose(ArrayList<Task> closedList, ArrayList<Task> openList, int taskIndex){
		ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
		boolean isSuccessUnclose = false;		
		
		int index = -1;
		for (int i = 0; i < closedList.size(); i++) {				
			if (closedList.get(i).getTaskIndex() == taskIndex) {
				index = i;
				isSuccessUnclose = true;
				break;
				}
		}
		if (isSuccessUnclose) {
			openList.add(closedList.get(closedList.size()-1));
			closedList.remove(closedList.size()-1);
		}		
		
		lists.add(closedList);
		lists.add(openList);
		return lists;
	}	
	
	public ArrayList<Task> delete(ArrayList<Task> openList, int taskIndex) {
		for (int i = 0; i < openList.size(); i++) {
			if (openList.get(i).getTaskIndex() == taskIndex) {
				openList.remove(i);
				break;
			}
		}
		return openList;
	}
	
	public ArrayList<Task> searchName(ArrayList<Task> openList, String searchTask) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getTaskDescription().toLowerCase()
					.contains(searchTask.toLowerCase())) {
				searchList.add(obj);
//				System.out.println("Found a entry..");
			}
		}

		return searchList;
	}
	
	public ArrayList<Task> searchDateBy(ArrayList<Task> openList, Calendar searchDate) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i<openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getEndTime().get(Calendar.YEAR)!=EMPTY) {
				if (obj.getEndDate().compareTo(searchDate)<=0){
					searchList.add(obj);
				}
			}
		}
		return searchList;
	}
	
	public ArrayList<Task> searchDateOn(ArrayList<Task> openList, Calendar searchDate) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
//			System.out.println("Start date : "+FORMAT_DATE.format((obj.getStartDate().getTime())));
//			System.out.println("End date : "+FORMAT_DATE.format((obj.getEndDate().getTime())));
//			System.out.println("Search date : "+FORMAT_DATE.format((searchDate.getTime())));
			if (obj.getStartDate().get(Calendar.YEAR) != EMPTY) {
				if (obj.getStartDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
						&& obj.getStartDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
						&& obj.getStartDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
					searchList.add(obj);
					continue;
				}

			} if (obj.getEndDate().get(Calendar.YEAR) != EMPTY) {
				if (obj.getEndDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
						&& obj.getEndDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
						&& obj.getEndDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
					searchList.add(obj);
					continue;
				}
			}
		}
		return searchList;
	}
	
	public ArrayList<Task> searchPriority(ArrayList<Task> openList, String searchPriority) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getPriority().toLowerCase()
					.contains(searchPriority.toLowerCase())) {
				searchList.add(obj);
//				System.out.println("Found a entry..");			
				}
			}
		return searchList;
	}	
	
	public ArrayList<Task> sortDate(ArrayList<Task> openList) {
		Collections.sort(openList, (o1, o2) -> o1.getEndTime().compareTo(o2.getEndTime()));
		return openList;
	}
	
	public ArrayList<Task> sortName(ArrayList<Task> openList) {
		Collections.sort(openList, (o1, o2) -> o1.getTaskDescription().compareTo(o2.getTaskDescription()));
		return openList;
	}
	
	public ArrayList<Task> sortPriority(ArrayList<Task> openList) {
		Collections.sort(openList, new ComparatorPriority());
		return openList;
	}
	
	public ArrayList<Task> update(ArrayList<Task> openList,
			String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {
		int index = 0;	
		
		// find task
		for (int i = 0; i < openList.size(); i++) {
			if (openList.get(i).getTaskIndex() == taskIndex) {
				index = i;
				break;
			}
		}
		
		// update task
		if (!taskDescription.equalsIgnoreCase("")) {
			openList.get(index).setTaskDescription(taskDescription);
		}
		if (startDate.get(Calendar.YEAR)!=EMPTY) {
			openList.get(index).setStartDate(startDate);
		}
		if (!(startDate.get(Calendar.MILLISECOND)==EMPTY && startDate.get(Calendar.HOUR_OF_DAY)==0 && startDate.get(Calendar.MINUTE)==0 && startDate.get(Calendar.SECOND)==0)) {
			System.out.println("Index : "+taskIndex);
			openList.get(index).setStartTime(startDate);
		}
		if (endDate.get(Calendar.YEAR)!=EMPTY) {
			openList.get(index).setEndDate(endDate);
			openList.get(index).setStartDate(startDate);
		}
		if (!(endDate.get(Calendar.MILLISECOND)==EMPTY && endDate.get(Calendar.HOUR_OF_DAY)==0 && endDate.get(Calendar.MINUTE)==0 && endDate.get(Calendar.SECOND)==0)) {
			openList.get(index).setEndTime(endDate);
			openList.get(index).setStartTime(startDate);
		}
		if (!location.equalsIgnoreCase("")) {
			openList.get(index).setLocation(location);
		}
		if (remindDate.get(Calendar.YEAR)!=EMPTY) {
			openList.get(index).setRemindDate(remindDate);
		}
		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
			openList.get(index).setRemindDate(remindDate);
		}
		if (!priority.equalsIgnoreCase("")) {
			openList.get(index).setPriority(priority);
		}
		
		// return updated list
		return openList;

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
