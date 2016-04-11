//@@author A0125522R

package application.storage;

import java.util.Calendar;
/**
 * FloatingTask is a subclass of Task class. 
 * This class is used when the user creates a task with no date specified.
 */
public class FloatingTask extends Task implements Cloneable {

	/**
	 * Creates a FloatingTask object with "empty" variables initalised.
	 */
	public FloatingTask() {
		setTaskDescription(EMPTY_STRING);
		setLocation(EMPTY_STRING);
		setRemindDate(Calendar.getInstance());
		setPriority(EMPTY_STRING);
		setTaskIndex(EMPTY_TASK);
	}

	/**
	 * Creates a FloatingTask object with specified variables.
	 */
	public FloatingTask(String taskDescription, String location, Calendar remindDate, String priority, int taskIndex) {
		setTaskDescription(taskDescription);
		setLocation(location);
		setRemindDate(remindDate);
		setPriority(priority);
		setTaskIndex(taskIndex);
	}

	/**
	 * Returns null as no start date for FloatingTask.
	 */
	public Calendar getStartDate() {
		return NO_DATE;
	}

	/**
	 * Returns null as no end date for FloatingTask.
	 */
	public Calendar getEndDate() {
		return NO_DATE;
	}

	/**
	 * Returns null as no start time for FloatingTask.
	 */
	public Calendar getStartTime() {
		return NO_TIME;
	}

	/**
	 * Returns null as no end time for FloatingTask.
	 */
	public Calendar getEndTime() {
		return NO_TIME;
	}

	/**
	 * Returns empty string as no duration for FloatingTask.
	 */
	public String durationToString() {
		return EMPTY_STRING;
	}

	/**
	 * Returns the task details in String.
	 */
	public String toString() {
		String taskDetails = KEYWORD_QUOTE;
		taskDetails += getTaskDescription();
		if (!getLocation().equalsIgnoreCase(EMPTY_STRING)) {
			taskDetails += KEYWORD_AT + getLocation();
		}
		if (!getPriority().equalsIgnoreCase(EMPTY_STRING)) {
			taskDetails += KEYWORD_FULLSTOP + KEYWORD_PRIORITY + getPriority();
		}
		taskDetails += KEYWORD_QUOTE;
		return taskDetails;
	}

	/**
	 * Clones a FloatingTask.
	 */
	protected Object clone() throws CloneNotSupportedException {
		FloatingTask newTask = (FloatingTask) super.clone();
		newTask.remindDate = (Calendar) remindDate.clone();
		return newTask;
	}

	// @@author A0110422E
	/**
	 * Return checked priority parameter 
	 */		
	public String getPriority() {		
		if (priority.equalsIgnoreCase(EMPTY_STRING)){
			return LOW;
		} else {
			return priority;
		}
	}
}
