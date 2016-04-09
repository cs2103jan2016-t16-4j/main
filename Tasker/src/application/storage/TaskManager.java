//@@author A0125522R

package application.storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.logger.LoggerHandler;

public class TaskManager {
	private static final int EMPTY_DATE = 1;
    private static final int EMPTY_TIME_PARAMETER_1 = 1;
    private static final int EMPTY_TIME_PARAMETER_2 = 0;
    private static final int INVALID_TASK = -1;
    private static final int INVALID_INDEX = -1;
    private static final String EMPTY_STRING = "";
    private static final String FLOATING_TASK = "FLOATING_TASK";
    private static final String DEADLINE_TASK = "DEADLINE_TASK";
    private static final String EVENT_TASK = "EVENT_TASK";
    private static final int DATE_IS_BY_AND_ON = 0;
	private static Logger logger = LoggerHandler.getLog();
//	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
//@@author A0110422E	
	public ArrayList<Task> add(ArrayList<Task> openList,
			String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {
		
		// floating task
		if (toFloatingTask(startDate, endDate)) {
			FloatingTask floatingTask = new FloatingTask(taskDescription, location, remindDate, priority, taskIndex);
			openList.add(floatingTask);
			logger.log(Level.INFO, "Adding Floating Task");
		}
		// deadline task
		else if (toDeadlineTask(startDate, endDate)) {
			DeadlineTask deadlineTask = new DeadlineTask(taskDescription, endDate, location, remindDate, priority, taskIndex);	
			openList.add(deadlineTask);
			logger.log(Level.INFO, "Adding Deadline Task");
		}
		// event task
		else {
			EventTask eventTask = new EventTask(taskDescription, startDate, endDate, location, remindDate, priority, taskIndex);
			openList.add(eventTask);
			logger.log(Level.INFO, "Adding Event Task");
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
//		for (int i = 0; i < openList.size(); i++) {
//			if (openList.get(i).getTaskIndex() == taskIndex) {
//				openList.remove(i);
//				break;
//			}
//		}
//		return openList;
		int indexOfTask = findIndexOfTaskInList(openList, taskIndex);
		assert (indexOfTask > INVALID_INDEX);
		if (indexOfTask > INVALID_INDEX) {
			openList.remove(indexOfTask);
			logger.log(Level.INFO, "Deleted Task");
		}

		return openList;
	}
	
	public ArrayList<Task> searchName(ArrayList<Task> openList, String searchTask) {
		String[] splitArray = searchTask.split("\\s+");
		ArrayList<Task> searchList = new ArrayList<Task>();
		logger.log(Level.INFO, "Searching tasks with name");
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
		logger.log(Level.INFO, "Searching tasks by date");
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
//			if (obj instanceof DeadlineTask) {
//				if (obj.getEndTime().get(Calendar.YEAR) != EMPTY) {
//					if (((DeadlineTask) obj).getEndDate().compareTo(searchDate) <= 0) {
//						searchList.add(obj);
//					}
//				}
//			} else if (obj instanceof EventTask) {
//				if (((EventTask) obj).getEndTime().get(Calendar.YEAR) != EMPTY) {
//					if (((EventTask) obj).getEndDate().compareTo(searchDate) <= 0) {
//						searchList.add(obj);
//					}
//				}
//			}
			if (obj.getEndDate()!=null) {
				if (!isDateEmpty(obj.getEndDate())) {
					if (obj.getEndDate().compareTo(searchDate)<=DATE_IS_BY_AND_ON){
						searchList.add(obj);
					}
				}
			}
		}
		return searchList;
	}
	
	public ArrayList<Task> searchDateOn(ArrayList<Task> openList, Calendar searchDate) {
		ArrayList<Task> searchList = new ArrayList<Task>();
		logger.log(Level.INFO, "Searching tasks on date");
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getEndDate()!=null) {
				if (!isDateEmpty(obj.getEndDate())) { 
					if (isDatesSame(searchDate, obj.getEndDate())) {
						searchList.add(obj);
						continue;
					}
				}
			}
			if (obj.getStartDate()!=null) {
				if (!isDateEmpty(obj.getStartDate())) {
					if (isDatesSame(searchDate, obj.getStartDate())) {
						searchList.add(obj);
						continue;
					}
				}
			}
			// check if deadline task --> check end date only
//			if (obj instanceof DeadlineTask) {
//				if (((DeadlineTask) obj).getEndDate().get(Calendar.YEAR) != EMPTY) {
//					if (((DeadlineTask) obj).getEndDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
//							&& ((DeadlineTask) obj).getEndDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
//							&& ((DeadlineTask) obj).getEndDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
//						searchList.add(obj);
//						continue;
//					}
//				}
//				// check if event task --> check start/end date 
//			} else if (obj instanceof EventTask) {
//				if (((EventTask) obj).getStartDate().get(Calendar.YEAR) != EMPTY) {
//					if (((EventTask) obj).getStartDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
//							&& ((EventTask) obj).getStartDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
//							&& ((EventTask) obj).getStartDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
//						searchList.add(obj);
//						continue;
//					}
//				}
//				if (((EventTask) obj).getEndDate().get(Calendar.YEAR) != EMPTY) {
//					if (((EventTask) obj).getEndDate().get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
//							&& ((EventTask) obj).getEndDate().get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
//							&& ((EventTask) obj).getEndDate().get(Calendar.DATE) == searchDate.get(Calendar.DATE)) {
//						searchList.add(obj);
//						continue;
//					}
//
//				}
//
//			}
		}
		return searchList;
	}

	private boolean isDatesSame(Calendar searchDate, Calendar obj) {
		return obj.get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
				&& obj.get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
				&& obj.get(Calendar.DATE) == searchDate.get(Calendar.DATE);
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
		
		// find task
		int indexOfTask = findIndexOfTaskInList(openList, taskIndex);
		assert (indexOfTask>INVALID_INDEX);
		logger.log(Level.INFO, "Updating Task");
		// update task
		if (openList.get(indexOfTask) instanceof FloatingTask) {
			// convert : floating task to event task
			if (toEventTask(startDate, endDate)) {
//				openList.set(indexOfTask, updateToEventTask(openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(EVENT_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				logger.log(Level.INFO, "Converting to Event Task");
			}
			// convert : floating task to deadline task	
			else if (toDeadlineTask(startDate, endDate)) {
//				openList.set(indexOfTask, updateToDeadlineTask(openList.get(indexOfTask), taskDescription, endDate, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(DEADLINE_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				logger.log(Level.INFO, "Converting to Deadline Task");
			}
			// no conversion
			else {
//				openList.set(indexOfTask, updateToFloatingTask(openList.get(indexOfTask), taskDescription, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(FLOATING_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
			}
			

		}
		
		else if (openList.get(indexOfTask) instanceof DeadlineTask) {
			// convert : deadline task to event task
			if (toEventTask(startDate, endDate)) {
//				openList.set(indexOfTask, updateToEventTask(openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(EVENT_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				logger.log(Level.INFO, "Converting to Event Task");
			}
//			// convert : deadline task to floating task
			else if (toFloatingTask(startDate, endDate)) {
	//			openList.set(indexOfTask, updateToFloatingTask(openList.get(indexOfTask), taskDescription, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(FLOATING_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				logger.log(Level.INFO, "Converting to Floating Task");

			}
//			// no conversion
			else {
//				openList.set(indexOfTask, updateToDeadlineTask(openList.get(indexOfTask), taskDescription, endDate, location,
//						remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(DEADLINE_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));

			}
		}
		
		else if (openList.get(indexOfTask) instanceof EventTask) {
//			// convert : event task to deadline task
			if (toDeadlineTask(startDate, endDate)) {
//				openList.set(indexOfTask, updateToDeadlineTask(openList.get(indexOfTask), taskDescription, endDate, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(DEADLINE_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				logger.log(Level.INFO, "Converting to Deadline Task");
				}
//			// convert : event task to floating task
			else if (toFloatingTask(startDate, endDate)) {
//				openList.set(indexOfTask, updateToFloatingTask(openList.get(indexOfTask), taskDescription, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(FLOATING_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				logger.log(Level.INFO, "Converting to Floating Task");
			}
//			// no conversion
			else {
//				openList.set(indexOfTask, updateToEventTask(openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
				openList.set(indexOfTask, updateToTaskType(EVENT_TASK, openList.get(indexOfTask), taskDescription, startDate, endDate, location, remindDate, priority, taskIndex));
			}
		}
//		 return updated list
			
		return openList;
	}

	private boolean toFloatingTask(Calendar startDate, Calendar endDate) {
		return startDate==null && endDate==null;
	}

	private boolean toDeadlineTask(Calendar startDate, Calendar endDate) {
		return startDate==null && endDate!=null;
	}

	private boolean toEventTask(Calendar startDate, Calendar endDate) {
		return startDate!=null && endDate!=null && !isDateEmpty(startDate) && !isDateEmpty(endDate);
	}

	private int findIndexOfTaskInList(ArrayList<Task> openList, int taskIndex) {
		for (int i = 0; i < openList.size(); i++) {
			if (openList.get(i).getTaskIndex() == taskIndex) {
				return i;
			}
		}
		return INVALID_TASK;
	}
		
//	private EventTask updateToEventTask(Task originalTask,
//			String taskDescription, Calendar startDate, Calendar endDate,
//			String location, Calendar remindDate, String priority, int taskIndex) {
//		
//		EventTask eventTask;
//		if (originalTask instanceof FloatingTask) {
//			eventTask = new EventTask(originalTask.getTaskDescription(), Calendar.getInstance(), Calendar.getInstance(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//		}
//		else if (originalTask instanceof DeadlineTask) {
//			eventTask = new EventTask(originalTask.getTaskDescription(), Calendar.getInstance(), ((DeadlineTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//		}
//		else {
//			eventTask = new EventTask(originalTask.getTaskDescription(), ((EventTask) originalTask).getStartDate(), ((EventTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//		}
//				
//		// update task
//		if (!taskDescription.equalsIgnoreCase("")) {
//			eventTask.setTaskDescription(taskDescription);
//		}
//		if (startDate.get(Calendar.YEAR)!=EMPTY_DATE) {
//			eventTask.setStartDate(startDate);
//		}
//		if (!(startDate.get(Calendar.MILLISECOND)==EMPTY_DATE && startDate.get(Calendar.HOUR_OF_DAY)==0 && startDate.get(Calendar.MINUTE)==0 && startDate.get(Calendar.SECOND)==0)) {
//			eventTask.setStartTime(startDate);
//		}
//		if (endDate.get(Calendar.YEAR)!=EMPTY_DATE) {
//			eventTask.setEndDate(endDate);
//			eventTask.setEndTime(endDate);
//		}
//		if (!(endDate.get(Calendar.MILLISECOND)==EMPTY_DATE && endDate.get(Calendar.HOUR_OF_DAY)==0 && endDate.get(Calendar.MINUTE)==0 && endDate.get(Calendar.SECOND)==0)) {
//			eventTask.setEndTime(endDate);
//			eventTask.setStartTime(startDate);
//		}
//		if (!location.equalsIgnoreCase("")) {
//			eventTask.setLocation(location);
//		}
//		if (remindDate.get(Calendar.YEAR)!=EMPTY_DATE) {
//			eventTask.setRemindDate(remindDate);
//		}
//		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY_DATE && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
//			eventTask.setRemindDate(remindDate);
//		}
//		if (!priority.equalsIgnoreCase("")) {
//			eventTask.setPriority(priority);
//		}
//		
//		return eventTask;
//	}
//	
//	private FloatingTask updateToFloatingTask(Task originalTask, String taskDescription, String location, Calendar remindDate, String priority,
//			int taskIndex) {
//		
//		FloatingTask floatingTask = new FloatingTask(originalTask.getTaskDescription(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//	
//		// update task
//		if (!taskDescription.equalsIgnoreCase("")) {
//			floatingTask.setTaskDescription(taskDescription);
//		}
//
//		if (!location.equalsIgnoreCase("")) {
//			floatingTask.setLocation(location);
//		}
//		if (remindDate.get(Calendar.YEAR)!=EMPTY_DATE) {
//			floatingTask.setRemindDate(remindDate);
//		}
//		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY_DATE && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
//			floatingTask.setRemindDate(remindDate);
//		}
//		if (!priority.equalsIgnoreCase("")) {
//			floatingTask.setPriority(priority);
//		}
//		
//		return floatingTask;
//	}
//
//	private DeadlineTask updateToDeadlineTask(Task originalTask, String taskDescription, Calendar endDate, String location,
//			Calendar remindDate, String priority, int taskIndex) {
//		
//		DeadlineTask deadlineTask;
//		if (originalTask instanceof EventTask) {
//			deadlineTask = new DeadlineTask(originalTask.getTaskDescription(), ((EventTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//		}
//		else if (originalTask instanceof FloatingTask) {
//			deadlineTask = new DeadlineTask(originalTask.getTaskDescription(), Calendar.getInstance(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//		}
//		else {
//			deadlineTask = new DeadlineTask(originalTask.getTaskDescription(), ((DeadlineTask) originalTask).getEndDate(), originalTask.getLocation(), originalTask.getRemindDate(), originalTask.getPriority(), originalTask.getTaskIndex());
//		}
//		// update task
//		if (!taskDescription.equalsIgnoreCase("")) {
//			deadlineTask.setTaskDescription(taskDescription);
//		}
//
//		if (endDate.get(Calendar.YEAR)!=EMPTY_DATE) {
//			deadlineTask.setEndDate(endDate);
//			deadlineTask.setEndTime(endDate);
//		}
//		if (!(endDate.get(Calendar.MILLISECOND)==EMPTY_DATE && endDate.get(Calendar.HOUR_OF_DAY)==0 && endDate.get(Calendar.MINUTE)==0 && endDate.get(Calendar.SECOND)==0)) {
//			deadlineTask.setEndTime(endDate);
//		}
//		if (!location.equalsIgnoreCase("")) {
//			deadlineTask.setLocation(location);
//		}
//		if (remindDate.get(Calendar.YEAR)!=EMPTY_DATE) {
//			deadlineTask.setRemindDate(remindDate);
//		}
//		if (!(remindDate.get(Calendar.MILLISECOND)==EMPTY_DATE && remindDate.get(Calendar.HOUR_OF_DAY)==0 && remindDate.get(Calendar.MINUTE)==0 && remindDate.get(Calendar.SECOND)==0)) {
//			deadlineTask.setRemindDate(remindDate);
//		}
//		if (!priority.equalsIgnoreCase("")) {
//			deadlineTask.setPriority(priority);
//		}
//		
//		return deadlineTask;
//	}
	
	private Task updateToTaskType(String taskType, Task originalTask,
			String taskDescription, Calendar startDate, Calendar endDate, String location, Calendar remindDate,
			String priority, int taskIndex) {
		
		String originalTaskDescription = originalTask.getTaskDescription();
		Calendar originalStartDate = originalTask.getStartDate();
		Calendar originalEndDate = originalTask.getEndDate();
		Calendar originalRemindDate = originalTask.getRemindDate();
		String originalLocation = originalTask.getLocation();
		String originalPriority = originalTask.getPriority();
		
		switch (taskType) {
		case (FLOATING_TASK):
			FloatingTask floatingTask = new FloatingTask(originalTaskDescription, originalLocation, originalRemindDate,
					originalPriority, taskIndex);
			floatingTask = (FloatingTask) updateTaskParameters(floatingTask, taskDescription, startDate, endDate,
					location, remindDate, priority, taskIndex);
			return floatingTask;
		case (DEADLINE_TASK):
			DeadlineTask deadlineTask;
			if (originalTask instanceof FloatingTask) {
				deadlineTask = new DeadlineTask(originalTaskDescription, Calendar.getInstance(), originalLocation,
						originalRemindDate, originalPriority, taskIndex);
			} else {
				deadlineTask = new DeadlineTask(originalTaskDescription, originalEndDate, originalLocation,
						originalRemindDate, originalPriority, taskIndex);
			}
			deadlineTask = (DeadlineTask) updateTaskParameters(deadlineTask, taskDescription, startDate, endDate,
					location, remindDate, priority, taskIndex);
			return deadlineTask;
		case (EVENT_TASK):
			EventTask eventTask;
			if (originalTask instanceof FloatingTask) {
				eventTask = new EventTask(originalTaskDescription, Calendar.getInstance(), Calendar.getInstance(),
						originalLocation, originalRemindDate, originalPriority, taskIndex);
			} else if (originalTask instanceof DeadlineTask) {
				eventTask = new EventTask(originalTaskDescription, Calendar.getInstance(), originalEndDate,
						originalLocation, originalRemindDate, originalPriority, taskIndex);
			} else {
				eventTask = new EventTask(originalTaskDescription, originalStartDate, originalEndDate, originalLocation,
						originalRemindDate, originalPriority, taskIndex);
			}
			eventTask = (EventTask) updateTaskParameters(eventTask, taskDescription, startDate, endDate, location,
					remindDate, priority, taskIndex);
			return eventTask;
		default:
			return null;
		}
	}
	
	private Task updateTaskParameters(Task obj, String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {
		// update task
		if (!taskDescription.equalsIgnoreCase(EMPTY_STRING)) {
			obj.setTaskDescription(taskDescription);
			logger.log(Level.INFO, "Updated task description");
		}

		if (!location.equalsIgnoreCase(EMPTY_STRING)) {
			obj.setLocation(location);
			logger.log(Level.INFO, "Updated location");
		}
		if (!isDateEmpty(remindDate)) {
			obj.setRemindDate(remindDate);
			logger.log(Level.INFO, "Updated remind date");
		}
		if (!isTimeEmpty(remindDate)) {
			obj.setRemindTime(remindDate);
			logger.log(Level.INFO, "Updated remind time");
		}
		if (!priority.equalsIgnoreCase(EMPTY_STRING)) {
			obj.setPriority(priority);
			logger.log(Level.INFO, "Updated priority");
		}
		
		if (obj instanceof DeadlineTask) {
			if (!isDateEmpty(endDate)) {
				((DeadlineTask) obj).setEndDate(endDate);
				((DeadlineTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, "Updated Deadline Task's end date");
			}
			if (!isTimeEmpty(endDate)) {
				((DeadlineTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, "Updated Deadline Task's end time");
			}
			
			return obj;
		}
		
		if (obj instanceof EventTask) {
			if (!isDateEmpty(startDate)) {
				((EventTask) obj).setStartDate(startDate);
				logger.log(Level.INFO, "Updated Event Task's start date");
			}
			if (!isTimeEmpty(startDate)) {
				((EventTask) obj).setStartTime(startDate);
				logger.log(Level.INFO, "Updated Event Task's start time");
			}
			if (!isDateEmpty(endDate)) {
				((EventTask) obj).setEndDate(endDate);
				((EventTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, "Updated Event Task's end date");
			}
			if (!isTimeEmpty(endDate)) {
				((EventTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, "Updated Event Task's end time");
			}
			
			return obj;
		}
		
		return obj;
	}

	private boolean isDateEmpty(Calendar date) {
		return date.get(Calendar.YEAR)==EMPTY_DATE;
	}

	private boolean isTimeEmpty(Calendar date) {
		return date.get(Calendar.MILLISECOND)==EMPTY_TIME_PARAMETER_1 && date.get(Calendar.HOUR_OF_DAY)==EMPTY_TIME_PARAMETER_2 && date.get(Calendar.MINUTE)==EMPTY_TIME_PARAMETER_2 && date.get(Calendar.SECOND)==EMPTY_TIME_PARAMETER_2;
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
    private static final int NO_PRIORITY_VALUE = 4;
    private static final int LOW_PRIORITY_VALUE = 3;
    private static final int MEDIUM_PRIORITY_VALUE = 2;
    private static final int HIGH_PRIORITY_VALUE = 1;
    private static final String NO_PRIORITY = "";
    private static final String HIGH_PRIORITY = "high";
    private static final String MEDIUM_PRIORITY = "medium";
    private static final String LOW_PRIORITY = "low";
    private static final int TASK_1_PRIORITY = 0;
    private static final int TASK_2_PRIORITY = 0;
    @Override
    public int compare(Task o1, Task o2) {
    	Task[] tasks = {o1,o2};
    	int[] integers = new int[tasks.length];
    	for (int i = 0; i<tasks.length; i++) {
        	if (tasks[i].getPriority().equals(NO_PRIORITY))
        		integers[i] = NO_PRIORITY_VALUE;
        	if (tasks[i].getPriority().equalsIgnoreCase(LOW_PRIORITY))
        		integers[i] = LOW_PRIORITY_VALUE;
        	if (tasks[i].getPriority().equalsIgnoreCase(MEDIUM_PRIORITY))
        		integers[i] = MEDIUM_PRIORITY_VALUE;
        	if (tasks[i].getPriority().equalsIgnoreCase(HIGH_PRIORITY))
        		integers[i] = HIGH_PRIORITY_VALUE;
    	}
//    	if (o1.getPriority().equals(""))
//    		ob1 = 4;
//    	if (o1.getPriority().equalsIgnoreCase("low"))
//    		ob1 = 3;
//    	if (o1.getPriority().equalsIgnoreCase("medium"))
//    		ob1 = 2;
//    	if (o1.getPriority().equalsIgnoreCase("high"))
//    		ob1 = 1;
//    	if (o2.getPriority().equalsIgnoreCase(""))
//    		ob2 = 4;
//    	if (o2.getPriority().equalsIgnoreCase("low"))
//    		ob2 = 3;
//    	if (o2.getPriority().equalsIgnoreCase("medium"))
//    		ob2 = 2;
//    	if (o2.getPriority().equalsIgnoreCase("high"))
//    		ob2 = 1;
    	
    	// nothing = 4, low = 3, medium = 2, high = 1
        return Integer.compare(integers[TASK_1_PRIORITY],integers[TASK_2_PRIORITY]);
    }
}

class ComparatorDate implements Comparator<Task> {
	private static final int LEFT_TASK_DATE_IS_BEFORE = -1;
	private static final int LEFT_TASK_DATE_IS_AFTER = 1;
	private static final int INVALID = 0;
    @Override
    public int compare(Task o1, Task o2) {
    	if (o1 instanceof DeadlineTask && o2 instanceof DeadlineTask) {
    		return ((DeadlineTask) o1).getEndTime().compareTo(((DeadlineTask) o2).getEndTime());
    	} else if (o1 instanceof DeadlineTask && o2 instanceof EventTask) {
    		return ((DeadlineTask) o1).getEndTime().compareTo(((EventTask) o2).getEndTime());
    	} else if (o1 instanceof DeadlineTask && o2 instanceof FloatingTask) {
    		return LEFT_TASK_DATE_IS_BEFORE;
    	} else if (o1 instanceof EventTask && o2 instanceof DeadlineTask) {
    		return ((EventTask) o1).getEndTime().compareTo(((DeadlineTask) o2).getEndTime());
    	} else if (o1 instanceof EventTask && o2 instanceof EventTask) {
    		return ((EventTask) o1).getEndTime().compareTo(((EventTask) o2).getEndTime());
    	} else if (o1 instanceof EventTask && o2 instanceof FloatingTask) {
    		return LEFT_TASK_DATE_IS_BEFORE;
    	} else if (o1 instanceof FloatingTask && o2 instanceof DeadlineTask) {
    		return LEFT_TASK_DATE_IS_AFTER;
    	} else if (o1 instanceof FloatingTask && o2 instanceof EventTask) {
    		return LEFT_TASK_DATE_IS_AFTER;
    	} else {
    		return INVALID;
    	}
    	
    }    
}
