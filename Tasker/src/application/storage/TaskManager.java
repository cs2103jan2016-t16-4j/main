//@@author A0125522R

package application.storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager {
    private static final int EMPTY = 1;
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
//@@author A0110422E	
	public ArrayList<Task> add(ArrayList<Task> openList,
			String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {
		
		// floating task
		if (startDate==null && endDate==null) {
			FloatingTask floatingTask = new FloatingTask(taskDescription, location, remindDate, priority, taskIndex);
			openList.add(floatingTask);
		}
		// deadline task
		else if (startDate==null && endDate!=null) {
			DeadlineTask deadlineTask = new DeadlineTask(taskDescription, endDate, location, remindDate, priority, taskIndex);
			openList.add(deadlineTask);
		}
		// event task
		else {
			EventTask eventTask = new EventTask(taskDescription, startDate, endDate, location, remindDate, priority, taskIndex);
			openList.add(eventTask);
		}
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
//@@author A0125522R	
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
		String[] splitArray = searchTask.split("\\s+");
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			for (int k = 0; k<splitArray.length; k++) {
				if (obj.getTaskDescription().toLowerCase().contains(splitArray[k].toLowerCase())) {
					searchList.add(obj);
					break;
				}
			}
		}
		return searchList;
	}
	
	public ArrayList<Task> searchDateBy(ArrayList<Task> openList, Calendar searchDate) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj instanceof DeadlineTask) {
				if (((DeadlineTask) obj).getEndTime().get(Calendar.YEAR) != EMPTY) {
					if (((DeadlineTask) obj).getEndDate().compareTo(searchDate) <= 0) {
						searchList.add(obj);
					}
				}
			} else if (obj instanceof EventTask) {
				if (((EventTask) obj).getEndTime().get(Calendar.YEAR) != EMPTY) {
					if (((EventTask) obj).getEndDate().compareTo(searchDate) <= 0) {
						searchList.add(obj);
					}
				}
			}
		}
		return searchList;
	}
	
	public ArrayList<Task> searchDateOn(ArrayList<Task> openList, Calendar searchDate) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			// check if deadline task --> check end date only
			if (obj instanceof DeadlineTask) {
				if (((DeadlineTask) obj).getEndDate().get(Calendar.YEAR) != EMPTY) {
					if (((DeadlineTask) obj).getEndDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
							&& ((DeadlineTask) obj).getEndDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
							&& ((DeadlineTask) obj).getEndDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
						searchList.add(obj);
						continue;
					}
				}
				// check if event task --> check start/end date 
			} else if (obj instanceof EventTask) {
				if (((EventTask) obj).getStartDate().get(Calendar.YEAR) != EMPTY) {
					if (((EventTask) obj).getStartDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
							&& ((EventTask) obj).getStartDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
							&& ((EventTask) obj).getStartDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
						searchList.add(obj);
						continue;
					}
				}
				if (((EventTask) obj).getEndDate().get(Calendar.YEAR) != EMPTY) {
					if (((EventTask) obj).getEndDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
							&& ((EventTask) obj).getEndDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
							&& ((EventTask) obj).getEndDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
						searchList.add(obj);
						continue;
					}

				}

			}
		}
		return searchList;
	}
//@@author A0110422E	
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
//@@author A0125522R	
	public ArrayList<Task> sortDate(ArrayList<Task> openList) {
		Collections.sort(openList, new ComparatorDate());
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
		if (openList.get(index) instanceof FloatingTask) {
			// convert : floating task to event task
			if (startDate!=null && endDate!=null && startDate.get(Calendar.YEAR)!=EMPTY && endDate.get(Calendar.YEAR)!=EMPTY) {
				openList.set(index, updateToEventTask(openList.get(index), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
			}
			// convert : floating task to deadline task	
			else if (startDate==null && endDate!=null) {
				openList.set(index, updateToDeadlineTask(openList.get(index), taskDescription, endDate, location, remindDate, priority, taskIndex));
			}
			// no conversion
			else {
				openList.set(index, updateToFloatingTask(openList.get(index), taskDescription, location, remindDate, priority, taskIndex));
			}
			

		}
		
		else if (openList.get(index) instanceof DeadlineTask) {
			// convert : deadline task to event task
			if (startDate!=null && endDate!=null && startDate.get(Calendar.YEAR)!=EMPTY && endDate.get(Calendar.YEAR)!=EMPTY) {
				openList.set(index, updateToEventTask(openList.get(index), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
			}
			// convert : deadline task to floating task
			else if (startDate==null && endDate==null) {
				openList.set(index, updateToFloatingTask(openList.get(index), taskDescription, location, remindDate, priority, taskIndex));
			}
			// no conversion
			else {
				openList.set(index, updateToDeadlineTask(openList.get(index), taskDescription, endDate, location,
						remindDate, priority, taskIndex));
			}
		}
		
		else if (openList.get(index) instanceof EventTask) {
			// convert : event task to deadline task
			if (startDate==null && endDate!=null) {
				openList.set(index, updateToDeadlineTask(openList.get(index), taskDescription, endDate, location, remindDate, priority, taskIndex));
				}
			// convert : event task to floating task
			else if (startDate==null && endDate==null) {
				openList.set(index, updateToFloatingTask(openList.get(index), taskDescription, location, remindDate, priority, taskIndex));
			}
			// no conversion
			else {
				openList.set(index, updateToEventTask(openList.get(index), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
			}
		}
		// return updated list
		return openList;
	}
		
	private EventTask updateToEventTask(Task originalTask,
			String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {
		
		EventTask eventTask;
		if (originalTask instanceof FloatingTask) {
			eventTask = new EventTask(originalTask.getTaskDescription(), Calendar.getInstance(), Calendar.getInstance(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
		}
		else if (originalTask instanceof DeadlineTask) {
			eventTask = new EventTask(originalTask.getTaskDescription(), Calendar.getInstance(), ((DeadlineTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
		}
		else {
			eventTask = new EventTask(originalTask.getTaskDescription(), ((EventTask) originalTask).getStartDate(), ((EventTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
		}
				
		// update task
		if (!taskDescription.equalsIgnoreCase("")) {
			eventTask.setTaskDescription(taskDescription);
		}
		if (startDate.get(Calendar.YEAR)!=EMPTY) {
			eventTask.setStartDate(startDate);
		}
		if (!(startDate.get(Calendar.MILLISECOND)==EMPTY && startDate.get(Calendar.HOUR_OF_DAY)==0 && startDate.get(Calendar.MINUTE)==0 && startDate.get(Calendar.SECOND)==0)) {
			eventTask.setStartTime(startDate);
		}
		if (endDate.get(Calendar.YEAR)!=EMPTY) {
			eventTask.setEndDate(endDate);
			eventTask.setEndTime(endDate);
//			eventTask.setStartDate(startDate);
//			eventTask.setStartTime(startDate);
		}
		if (!(endDate.get(Calendar.MILLISECOND)==EMPTY && endDate.get(Calendar.HOUR_OF_DAY)==0 && endDate.get(Calendar.MINUTE)==0 && endDate.get(Calendar.SECOND)==0)) {
			eventTask.setEndTime(endDate);
			eventTask.setStartTime(startDate);
		}
		if (!location.equalsIgnoreCase("")) {
			eventTask.setLocation(location);
		}
		if (remindDate.get(Calendar.YEAR)!=EMPTY) {
			eventTask.setRemindDate(remindDate);
		}
		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
			eventTask.setRemindDate(remindDate);
		}
		if (!priority.equalsIgnoreCase("")) {
			eventTask.setPriority(priority);
		}
		
		return eventTask;
	}
	
	private FloatingTask updateToFloatingTask(Task originalTask, String taskDescription, String location, Calendar remindDate, String priority,
			int taskIndex) {
		
		FloatingTask floatingTask = new FloatingTask(originalTask.getTaskDescription(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
	
		// update task
		if (!taskDescription.equalsIgnoreCase("")) {
			floatingTask.setTaskDescription(taskDescription);
		}

		if (!location.equalsIgnoreCase("")) {
			floatingTask.setLocation(location);
		}
		if (remindDate.get(Calendar.YEAR)!=EMPTY) {
			floatingTask.setRemindDate(remindDate);
		}
		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
			floatingTask.setRemindDate(remindDate);
		}
		if (!priority.equalsIgnoreCase("")) {
			floatingTask.setPriority(priority);
		}
		
		return floatingTask;
	}

	private DeadlineTask updateToDeadlineTask(Task originalTask, String taskDescription, Calendar endDate, String location,
			Calendar remindDate, String priority, int taskIndex) {
		
		DeadlineTask deadlineTask;
		if (originalTask instanceof EventTask) {
			deadlineTask = new DeadlineTask(originalTask.getTaskDescription(), ((EventTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
		}
		else if (originalTask instanceof FloatingTask) {
			deadlineTask = new DeadlineTask(originalTask.getTaskDescription(), Calendar.getInstance(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
		}
		else {
			deadlineTask = new DeadlineTask(originalTask.getTaskDescription(), ((DeadlineTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
		}
		// update task
		if (!taskDescription.equalsIgnoreCase("")) {
			deadlineTask.setTaskDescription(taskDescription);
		}

		if (endDate.get(Calendar.YEAR)!=EMPTY) {
			deadlineTask.setEndDate(endDate);
			deadlineTask.setEndTime(endDate);
		}
		if (!(endDate.get(Calendar.MILLISECOND)==EMPTY && endDate.get(Calendar.HOUR_OF_DAY)==0 && endDate.get(Calendar.MINUTE)==0 && endDate.get(Calendar.SECOND)==0)) {
			deadlineTask.setEndTime(endDate);
		}
		if (!location.equalsIgnoreCase("")) {
			deadlineTask.setLocation(location);
		}
		if (remindDate.get(Calendar.YEAR)!=EMPTY) {
			deadlineTask.setRemindDate(remindDate);
		}
		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
			deadlineTask.setRemindDate(remindDate);
		}
		if (!priority.equalsIgnoreCase("")) {
			deadlineTask.setPriority(priority);
		}
		
		return deadlineTask;
	}

//@@author A0110422E
/*	
	public ArrayList<Task> searchCategoryType(ArrayList<Task> openList, String categoryType) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getCategoryType().toLowerCase()
					.contains(categoryType.toLowerCase())) {
				searchList.add(obj);
//				System.out.println("Found a entry..");			
				}
			}
		return searchList;
	}
*/

//@@author A0125522R
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

class ComparatorDate implements Comparator<Task> {
    @Override
    public int compare(Task o1, Task o2) {
    	if (o1 instanceof DeadlineTask && o2 instanceof DeadlineTask) {
    		return ((DeadlineTask) o1).getEndTime().compareTo(((DeadlineTask) o2).getEndTime());
    	} else if (o1 instanceof DeadlineTask && o2 instanceof EventTask) {
    		return ((DeadlineTask) o1).getEndTime().compareTo(((EventTask) o2).getEndTime());
    	} else if (o1 instanceof DeadlineTask && o2 instanceof FloatingTask) {
    		return -1;
    	} else if (o1 instanceof EventTask && o2 instanceof DeadlineTask) {
    		return ((EventTask) o1).getEndTime().compareTo(((DeadlineTask) o2).getEndTime());
    	} else if (o1 instanceof EventTask && o2 instanceof EventTask) {
    		return ((EventTask) o1).getEndTime().compareTo(((EventTask) o2).getEndTime());
    	} else if (o1 instanceof EventTask && o2 instanceof FloatingTask) {
    		return -1;
    	} else if (o1 instanceof FloatingTask && o2 instanceof DeadlineTask) {
    		return 1;
    	} else if (o1 instanceof FloatingTask && o2 instanceof EventTask) {
    		return 1;
    	} else {
    		return 0;
    	}
    	
    }    
}
