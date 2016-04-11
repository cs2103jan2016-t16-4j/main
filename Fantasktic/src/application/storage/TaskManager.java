//@@author A0125522R

package application.storage;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.logger.LoggerHandler;

/**
 * TaskManager is used when task manipulation functions are needed. It runs and
 * execute all the given task functions, i.e add, close, delete, search, update,
 * etc.
 */
public class TaskManager {

	// Constants
	private static final int DATE_IS_BY_AND_ON = 0;
	private static final int EMPTY_DATE = 1;
	private static final int EMPTY_TIME_PARAMETER_1 = 1;
	private static final int EMPTY_TIME_PARAMETER_2 = 0;
	private static final int INVALID_TASK = -1;
	private static final int INVALID_INDEX = -1;
	private static final int INVALID_TASK_INDEX = 0;
	private static final String DEADLINE_TASK = "DEADLINE_TASK";
	private static final String EMPTY_STRING = "";
	private static final String EVENT_TASK = "EVENT_TASK";
	private static final String FLOATING_TASK = "FLOATING_TASK";
	private static final String SPACE = "\\s+";

	// Log messages
	private static final String LOG_INFO_ADD_FLOATING_TASK = "Adding Floating Task.";
	private static final String LOG_INFO_ADD_DEADLINE_TASK = "Adding Deadline Task.";
	private static final String LOG_INFO_ADD_EVENT_TASK = "Adding Event Task.";
	private static final String LOG_INFO_CONVERT_TO_EVENT_TASK = "Converting to Event Task.";
	private static final String LOG_INFO_CONVERT_TO_DEADLINE_TASK = "Converting to Deadline Task.";
	private static final String LOG_INFO_CONVERT_TO_FLOATING_TASK = "Converting to Floating Task.";
	private static final String LOG_INFO_DELETED_TASK = "Deleted Task.";
	private static final String LOG_INFO_SEARCH_BY_DATE = "Searching tasks by date.";
	private static final String LOG_INFO_SEARCH_NAME = "Searching tasks with name.";
	private static final String LOG_INFO_SEARCH_ON_DATE = "Searching tasks on date.";
	private static final String LOG_INFO_UPDATE_TASK = "Updating Task.";
	private static final String LOG_INFO_UPDATED_DEADLINE_END_DATE = "Updated Deadline Task's end date.";
	private static final String LOG_INFO_UPDATED_DEADLINE_END_TIME = "Updated Deadline Task's end time.";
	private static final String LOG_INFO_UPDATED_EVENT_START_DATE = "Updated Event Task's start date.";
	private static final String LOG_INFO_UPDATED_EVENT_START_TIME = "Updated Event Task's start time.";
	private static final String LOG_INFO_UPDATED_EVENT_END_DATE = "Updated Event Task's end date.";
	private static final String LOG_INFO_UPDATED_EVENT_END_TIME = "Updated Event Task's end time.";
	private static final String LOG_INFO_UPDATED_LOCATION = "Updated location.";
	private static final String LOG_INFO_UPDATED_PRIORITY = "Updated priority";
	private static final String LOG_INFO_UPDATED_REMIND_DATE = "Updated remind date.";
	private static final String LOG_INFO_UPDATED_REMIND_TIME = "Updated remind time.";
	private static final String LOG_INFO_UPDATED_TASK_DESCRIPTION = "Updated task description.";

	// Logger
	private static Logger logger = LoggerHandler.getLog();

	// @@author A0110422E
	/**
	 * Add a new task. The type of task will be determined by input parameters
	 */
	public ArrayList<Task> add(ArrayList<Task> openList, String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {

		// add a floating task
		if (toFloatingTask(startDate, endDate)) {
			FloatingTask floatingTask = new FloatingTask(taskDescription, location, remindDate, priority, taskIndex);
			openList.add(floatingTask);
			logger.log(Level.INFO, LOG_INFO_ADD_FLOATING_TASK);
		}
		// add a deadline task
		else if (toDeadlineTask(startDate, endDate)) {
			DeadlineTask deadlineTask = new DeadlineTask(taskDescription, endDate, location, remindDate, priority,
					taskIndex);
			openList.add(deadlineTask);
			logger.log(Level.INFO, LOG_INFO_ADD_DEADLINE_TASK);
		}
		// add a event task
		else {
			EventTask eventTask = new EventTask(taskDescription, startDate, endDate, location, remindDate, priority,
					taskIndex);
			openList.add(eventTask);
			logger.log(Level.INFO, LOG_INFO_ADD_EVENT_TASK);
		}
		return openList;
	}

	/**
	 * Return a lists of list after transferring a selected task from open list
	 * to closed list
	 */
	public ArrayList<ArrayList<Task>> close(ArrayList<Task> closedList, ArrayList<Task> openList, int taskIndex) {
		ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
		int index = INVALID_INDEX;
		index = findTaskInList(openList, taskIndex, index);
		transferTaskFromListToList(openList, closedList, index);
		compileLists(closedList, openList, lists);
		return lists;
	}

	/**
	 * Return a lists of list after transferring a selected task from closed
	 * list to open list
	 */
	public ArrayList<ArrayList<Task>> unclose(ArrayList<Task> closedList, ArrayList<Task> openList, int taskIndex) {
		ArrayList<ArrayList<Task>> lists = new ArrayList<ArrayList<Task>>();
		int index = INVALID_INDEX;
		index = findTaskInList(closedList, taskIndex, index);
		transferTaskFromListToList(closedList, openList, index);
		compileLists(closedList, openList, lists);
		return lists;
	}

	/**
	 * Shift the selected task from one list to another
	 */
	private void transferTaskFromListToList(ArrayList<Task> closedList, ArrayList<Task> openList, int index) {
		if (isTaskFound(index)) {
			openList.add(closedList.get(index));
			closedList.remove(index);
		}
	}

	/**
	 * Search through closed list, and return the index of the result
	 */
	private int findTaskInList(ArrayList<Task> closedList, int taskIndex, int index) {
		for (int i = 0; i < closedList.size(); i++) {
			if (isTheRightTask(closedList, taskIndex, i)) {
				index = i;
				break;
			}
		}
		return index;
	}

	/**
	 * Check if task is found
	 */
	private boolean isTaskFound(int index) {
		return index != INVALID_INDEX;
	}

	/**
	 * Return True if the task of given index is found
	 */
	private boolean isTheRightTask(ArrayList<Task> openList, int taskIndex, int i) {
		return openList.get(i).getTaskIndex() == taskIndex;
	}

	/**
	 * Append closed list and open list to a list
	 */
	private void compileLists(ArrayList<Task> closedList, ArrayList<Task> openList, ArrayList<ArrayList<Task>> lists) {
		lists.add(closedList);
		lists.add(openList);
	}

	// @@author A0125522R
	/**
	 * Find and delete the task in the open list.
	 */
	public ArrayList<Task> delete(ArrayList<Task> openList, int taskIndex) {
		assert (taskIndex > INVALID_TASK_INDEX);
		int indexOfTask = findIndexOfTaskInList(openList, taskIndex);
		if (indexOfTask > INVALID_INDEX) {
			openList.remove(indexOfTask);
			logger.log(Level.INFO, LOG_INFO_DELETED_TASK);
		}
		return openList;
	}

	/**
	 * Search "name" in the open list and and return search results.
	 */
	public ArrayList<Task> searchName(ArrayList<Task> openList, String searchTask) {
		assert (searchTask != null);
		String[] splitArray = searchTask.split(SPACE);
		ArrayList<Task> searchList = new ArrayList<Task>();
		logger.log(Level.INFO, LOG_INFO_SEARCH_NAME);
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			for (int k = 0; k < splitArray.length; k++) {
				if (obj.getTaskDescription().toLowerCase().contains(splitArray[k].toLowerCase())) {
					searchList.add(obj);
					break;
				}
			}
		}
		return searchList;
	}

	/**
	 * Search the tasks by a specified date in the open list and return search
	 * results.
	 */
	public ArrayList<Task> searchDateBy(ArrayList<Task> openList, Calendar searchDate) {
		assert (searchDate != null);
		ArrayList<Task> searchList = new ArrayList<Task>();
		logger.log(Level.INFO, LOG_INFO_SEARCH_BY_DATE);
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getEndDate() != null) {
				if (!isDateEmpty(obj.getEndDate())) {
					if (obj.getEndDate().compareTo(searchDate) <= DATE_IS_BY_AND_ON) {
						searchList.add(obj);
					}
				}
			}
		}
		return searchList;
	}

	/**
	 * Search the tasks on a specified date in the open list and return search
	 * results.
	 */
	public ArrayList<Task> searchDateOn(ArrayList<Task> openList, Calendar searchDate) {
		assert (searchDate != null);
		ArrayList<Task> searchList = new ArrayList<Task>();
		logger.log(Level.INFO, LOG_INFO_SEARCH_ON_DATE);
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			// check end date first if its deadline task
			if (obj.getEndDate() != null) {
				if (!isDateEmpty(obj.getEndDate())) {
					if (isDatesSame(searchDate, obj.getEndDate())) {
						searchList.add(obj);
						continue;
					}
				}
			}
			// else check start date too if its event task
			if (obj.getStartDate() != null) {
				if (!isDateEmpty(obj.getStartDate())) {
					if (isDatesSame(searchDate, obj.getStartDate())) {
						searchList.add(obj);
						continue;
					}
				}
			}
		}
		return searchList;
	}

	/**
	 * Checks two given date if its same.
	 */
	private boolean isDatesSame(Calendar searchDate, Calendar obj) {
		return obj.get(Calendar.YEAR) == searchDate.get(Calendar.YEAR)
				&& obj.get(Calendar.MONTH) == searchDate.get(Calendar.MONTH)
				&& obj.get(Calendar.DATE) == searchDate.get(Calendar.DATE);
	}

	// @@author A0110422E
	public ArrayList<Task> searchPriority(ArrayList<Task> openList, String searchPriority) {
		assert (searchPriority != null);
		ArrayList<Task> searchList = new ArrayList<Task>();
		for (int i = 0; i < openList.size(); i++) {
			Task obj = openList.get(i);
			if (obj.getPriority().toLowerCase().contains(searchPriority.toLowerCase())) {
				searchList.add(obj);
			}
		}
		return searchList;
	}

	// @@author A0125522R
	/**
	 * Sort the open list in ascending date.
	 */
	public ArrayList<Task> sortDate(ArrayList<Task> openList) {
		Collections.sort(openList, new ComparatorDate());
		return openList;
	}

	/**
	 * Sort the open list in ascending task name.
	 */
	public ArrayList<Task> sortName(ArrayList<Task> openList) {
		Collections.sort(openList, (o1, o2) -> o1.getTaskDescription().compareTo(o2.getTaskDescription()));
		return openList;
	}

	/**
	 * Sort the open list in priority (high to low).
	 */
	public ArrayList<Task> sortPriority(ArrayList<Task> openList) {
		Collections.sort(openList, new ComparatorPriority());
		return openList;
	}
	
	/**
	 * Replaces the specified task with a new task.
	 */
	public ArrayList<Task> replaceTask (ArrayList<Task> openList, int taskIndex, Task task) {
		assert (taskIndex > INVALID_TASK_INDEX);
		for (int i = 0; i < openList.size(); i++) {
			if (openList.get(i).getTaskIndex() == taskIndex) {
				openList.set(i, task);
			}
		}
		return openList;
	}

	/**
	 * Update a specified task with specified variables.
	 */
	public ArrayList<Task> update(ArrayList<Task> openList, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate, String priority, int taskIndex) {

		assert (taskIndex > INVALID_TASK_INDEX);
		// find task
		int indexOfTask = findIndexOfTaskInList(openList, taskIndex);
		logger.log(Level.INFO, LOG_INFO_UPDATE_TASK);

		// update task based on current task type
		if (openList.get(indexOfTask) instanceof FloatingTask) {
			convertFloatingTask(openList, taskDescription, startDate, endDate, location, remindDate, priority,
					taskIndex, indexOfTask);
		} else if (openList.get(indexOfTask) instanceof DeadlineTask) {
			convertDeadlineTask(openList, taskDescription, startDate, endDate, location, remindDate, priority,
					taskIndex, indexOfTask);
		} else if (openList.get(indexOfTask) instanceof EventTask) {
			convertEventTask(openList, taskDescription, startDate, endDate, location, remindDate, priority, taskIndex,
					indexOfTask);
		}
		return openList;
	}

	/**
	 * Convert the FloatingTask if necessary (to DeadlineTask or EventTask).
	 */
	private void convertFloatingTask(ArrayList<Task> openList, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate, String priority, int taskIndex, int indexOfTask) {
		assert (openList != null);

		// convert : floating task to event task
		if (toEventTask(startDate, endDate)) {
			openList.set(indexOfTask, updateToTaskType(EVENT_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
			logger.log(Level.INFO, LOG_INFO_CONVERT_TO_EVENT_TASK);
		}

		// convert : floating task to deadline task
		else if (toDeadlineTask(startDate, endDate)) {
			openList.set(indexOfTask, updateToTaskType(DEADLINE_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
			logger.log(Level.INFO, LOG_INFO_CONVERT_TO_DEADLINE_TASK);
		}

		// no conversion
		else {
			openList.set(indexOfTask, updateToTaskType(FLOATING_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
		}
	}

	/**
	 * Convert the DeadlineTask if necessary (to FloatingTask or EventTask).
	 */
	private void convertDeadlineTask(ArrayList<Task> openList, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate, String priority, int taskIndex, int indexOfTask) {
		assert (openList != null);

		// convert : deadline task to event task
		if (toEventTask(startDate, endDate)) {
			openList.set(indexOfTask, updateToTaskType(EVENT_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
			logger.log(Level.INFO, LOG_INFO_CONVERT_TO_EVENT_TASK);
		}

		// convert : deadline task to floating task
		else if (toFloatingTask(startDate, endDate)) {
			openList.set(indexOfTask, updateToTaskType(FLOATING_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
			logger.log(Level.INFO, LOG_INFO_CONVERT_TO_FLOATING_TASK);
		}

		// no conversion
		else {
			openList.set(indexOfTask, updateToTaskType(DEADLINE_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
		}
	}

	/**
	 * Convert the EventTask if necessary (to FloatingTask or DeadlineTask).
	 */
	private void convertEventTask(ArrayList<Task> openList, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate, String priority, int taskIndex, int indexOfTask) {
		assert (openList != null);

		// convert : event task to deadline task
		if (toDeadlineTask(startDate, endDate)) {
			openList.set(indexOfTask, updateToTaskType(DEADLINE_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
			logger.log(Level.INFO, LOG_INFO_CONVERT_TO_DEADLINE_TASK);
		}

		// convert : event task to floating task
		else if (toFloatingTask(startDate, endDate)) {
			openList.set(indexOfTask, updateToTaskType(FLOATING_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
			logger.log(Level.INFO, LOG_INFO_CONVERT_TO_FLOATING_TASK);
		}

		// no conversion
		else {
			openList.set(indexOfTask, updateToTaskType(EVENT_TASK, openList.get(indexOfTask), taskDescription,
					startDate, endDate, location, remindDate, priority, taskIndex));
		}
	}

	/**
	 * Updates and convert a task to the task type specified.
	 */
	private Task updateToTaskType(String taskType, Task originalTask, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate, String priority, int taskIndex) {
		assert (taskIndex > INVALID_TASK_INDEX);
		String originalTaskDescription = originalTask.getTaskDescription();
		Calendar originalStartDate = originalTask.getStartDate();
		Calendar originalEndDate = originalTask.getEndDate();
		Calendar originalRemindDate = originalTask.getRemindDate();
		String originalLocation = originalTask.getLocation();
		String originalPriority = originalTask.getPriority();

		// determine which task type to update/convert to
		switch (taskType) {
		case (FLOATING_TASK):
			return updatedToFloatingTask(taskDescription, startDate, endDate, location, remindDate, priority, taskIndex,
					originalTaskDescription, originalRemindDate, originalLocation, originalPriority);
		case (DEADLINE_TASK):
			return updatedToDeadlineTask(originalTask, taskDescription, startDate, endDate, location, remindDate,
					priority, taskIndex, originalTaskDescription, originalEndDate, originalRemindDate, originalLocation,
					originalPriority);
		case (EVENT_TASK):
			return updatedToEventTask(originalTask, taskDescription, startDate, endDate, location, remindDate, priority,
					taskIndex, originalTaskDescription, originalStartDate, originalEndDate, originalRemindDate,
					originalLocation, originalPriority);
		default:
			return null;
		}
	}

	/**
	 * Update and returns the task in EventTask type.
	 */
	private Task updatedToEventTask(Task originalTask, String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex, String originalTaskDescription,
			Calendar originalStartDate, Calendar originalEndDate, Calendar originalRemindDate, String originalLocation,
			String originalPriority) {
		assert (originalTask != null);
		EventTask eventTask;

		// get the details from original task type and insert into updated task
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
	}

	/**
	 * Update and returns the task in DeadlineTask type.
	 */
	private Task updatedToDeadlineTask(Task originalTask, String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex, String originalTaskDescription,
			Calendar originalEndDate, Calendar originalRemindDate, String originalLocation, String originalPriority) {
		assert (originalTask != null);
		DeadlineTask deadlineTask;

		// get the details from original task type and insert into updated task
		if (originalTask instanceof FloatingTask) {
			deadlineTask = new DeadlineTask(originalTaskDescription, Calendar.getInstance(), originalLocation,
					originalRemindDate, originalPriority, taskIndex);
		} else {
			deadlineTask = new DeadlineTask(originalTaskDescription, originalEndDate, originalLocation,
					originalRemindDate, originalPriority, taskIndex);
		}
		deadlineTask = (DeadlineTask) updateTaskParameters(deadlineTask, taskDescription, startDate, endDate, location,
				remindDate, priority, taskIndex);
		return deadlineTask;
	}

	/**
	 * Update and returns the task in FloatingTask type.
	 */
	private Task updatedToFloatingTask(String taskDescription, Calendar startDate, Calendar endDate, String location,
			Calendar remindDate, String priority, int taskIndex, String originalTaskDescription,
			Calendar originalRemindDate, String originalLocation, String originalPriority) {

		// get the details from original task type and insert into updated task
		FloatingTask floatingTask = new FloatingTask(originalTaskDescription, originalLocation, originalRemindDate,
				originalPriority, taskIndex);
		floatingTask = (FloatingTask) updateTaskParameters(floatingTask, taskDescription, startDate, endDate, location,
				remindDate, priority, taskIndex);
		return floatingTask;
	}

	/**
	 * Updates a task with the specified variables.
	 */
	private Task updateTaskParameters(Task obj, String taskDescription, Calendar startDate, Calendar endDate,
			String location, Calendar remindDate, String priority, int taskIndex) {

		assert (taskIndex > INVALID_TASK_INDEX);

		// update task parameters
		if (!taskDescription.equalsIgnoreCase(EMPTY_STRING)) {
			obj.setTaskDescription(taskDescription);
			logger.log(Level.INFO, LOG_INFO_UPDATED_TASK_DESCRIPTION);
		}

		if (!location.equalsIgnoreCase(EMPTY_STRING)) {
			obj.setLocation(location);
			logger.log(Level.INFO, LOG_INFO_UPDATED_LOCATION);
		}

		if (!isDateEmpty(remindDate)) {
			obj.setRemindDate(remindDate);
			logger.log(Level.INFO, LOG_INFO_UPDATED_REMIND_DATE);
		}

		if (!isTimeEmpty(remindDate)) {
			obj.setRemindTime(remindDate);
			logger.log(Level.INFO, LOG_INFO_UPDATED_REMIND_TIME);
		}

		if (!priority.equalsIgnoreCase(EMPTY_STRING)) {
			obj.setPriority(priority);
			logger.log(Level.INFO, LOG_INFO_UPDATED_PRIORITY);
		}

		if (obj instanceof DeadlineTask) {
			if (!isDateEmpty(endDate)) {
				((DeadlineTask) obj).setEndDate(endDate);
				((DeadlineTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, LOG_INFO_UPDATED_DEADLINE_END_DATE);
			}
			if (!isTimeEmpty(endDate)) {
				((DeadlineTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, LOG_INFO_UPDATED_DEADLINE_END_TIME);
			}

			return obj;
		}

		if (obj instanceof EventTask) {
			if (!isDateEmpty(startDate)) {
				((EventTask) obj).setStartDate(startDate);
				logger.log(Level.INFO, LOG_INFO_UPDATED_EVENT_START_DATE);
			}
			if (!isTimeEmpty(startDate)) {
				((EventTask) obj).setStartTime(startDate);
				logger.log(Level.INFO, LOG_INFO_UPDATED_EVENT_START_TIME);
			}
			if (!isDateEmpty(endDate)) {
				((EventTask) obj).setEndDate(endDate);
				((EventTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, LOG_INFO_UPDATED_EVENT_END_DATE);
			}
			if (!isTimeEmpty(endDate)) {
				((EventTask) obj).setEndTime(endDate);
				logger.log(Level.INFO, LOG_INFO_UPDATED_EVENT_END_TIME);
			}
			return obj;
		}
		return obj;
	}

	/**
	 * Find the index of the task in the open list.
	 */
	private int findIndexOfTaskInList(ArrayList<Task> openList, int taskIndex) {
		for (int i = 0; i < openList.size(); i++) {
			if (isTheRightTask(openList, taskIndex, i)) {
				return i;
			}
		}
		return INVALID_TASK;
	}

	/**
	 * Checks whether convert task to FloatingTask.
	 */
	private boolean toFloatingTask(Calendar startDate, Calendar endDate) {
		return startDate == null && endDate == null;
	}

	/**
	 * Checks whether convert task to DeadlineTask.
	 */
	private boolean toDeadlineTask(Calendar startDate, Calendar endDate) {
		return startDate == null && endDate != null;
	}

	/**
	 * Checks whether convert task to EventTask.
	 */
	private boolean toEventTask(Calendar startDate, Calendar endDate) {
		return startDate != null && endDate != null && !isDateEmpty(startDate) && !isDateEmpty(endDate);
	}

	/**
	 * Checks whether a date is "empty".
	 */
	private boolean isDateEmpty(Calendar date) {
		return date.get(Calendar.YEAR) == EMPTY_DATE;
	}

	/**
	 * Checks whether a time is "empty".
	 */
	private boolean isTimeEmpty(Calendar date) {
		return date.get(Calendar.MILLISECOND) == EMPTY_TIME_PARAMETER_1
				&& date.get(Calendar.HOUR_OF_DAY) == EMPTY_TIME_PARAMETER_2
				&& date.get(Calendar.MINUTE) == EMPTY_TIME_PARAMETER_2
				&& date.get(Calendar.SECOND) == EMPTY_TIME_PARAMETER_2;
	}

	// @@author A0110422E
	/*
	 * public ArrayList<Task> searchCategoryType(ArrayList<Task> openList,
	 * String categoryType) { ArrayList<Task> searchList = new
	 * ArrayList<Task>(); for (int i = 0; i < openList.size(); i++) { Task obj =
	 * openList.get(i); if (obj.getCategoryType().toLowerCase()
	 * .contains(categoryType.toLowerCase())) { searchList.add(obj); //
	 * System.out.println("Found a entry.."); } } return searchList; }
	 */

	// @@author A0125522R
}

/**
 * Custom comparator for sorting tasks based on priority.
 */
class ComparatorPriority implements Comparator<Task> {

	// Constants
	private static final int NO_PRIORITY_VALUE = 4;
	private static final int LOW_PRIORITY_VALUE = 3;
	private static final int MEDIUM_PRIORITY_VALUE = 2;
	private static final int HIGH_PRIORITY_VALUE = 1;
	private static final String NO_PRIORITY = "";
	private static final String HIGH_PRIORITY = "high";
	private static final String MEDIUM_PRIORITY = "medium";
	private static final String LOW_PRIORITY = "low";
	private static final int TASK_1_PRIORITY = 0;
	private static final int TASK_2_PRIORITY = 1;

	@Override
	public int compare(Task o1, Task o2) {
		Task[] tasks = { o1, o2 };
		int[] integers = new int[tasks.length];
		for (int i = 0; i < tasks.length; i++) {
			if (tasks[i].getPriority().equals(NO_PRIORITY))
				integers[i] = NO_PRIORITY_VALUE;
			if (tasks[i].getPriority().equalsIgnoreCase(LOW_PRIORITY))
				integers[i] = LOW_PRIORITY_VALUE;
			if (tasks[i].getPriority().equalsIgnoreCase(MEDIUM_PRIORITY))
				integers[i] = MEDIUM_PRIORITY_VALUE;
			if (tasks[i].getPriority().equalsIgnoreCase(HIGH_PRIORITY))
				integers[i] = HIGH_PRIORITY_VALUE;
		}
		return Integer.compare(integers[TASK_1_PRIORITY], integers[TASK_2_PRIORITY]);
	}
}

/**
 * Custom comparator for sorting tasks based on date.
 */
class ComparatorDate implements Comparator<Task> {

	// Constants
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
