//@@author A0125522R

package application.storage;

import java.util.Calendar;

public class FloatingTask extends Task implements Cloneable  {

	public FloatingTask() {
		setTaskDescription(EMPTY_STRING);
		setLocation(EMPTY_STRING);
		setRemindDate(Calendar.getInstance());
		setPriority(EMPTY_STRING);
		setTaskIndex(EMPTY_TASK);
	}
	
	public FloatingTask(String taskDescription, String location, Calendar remindDate, String priority, int taskIndex) {
		
		setTaskDescription(taskDescription);
		setLocation(location);
		setRemindDate(remindDate);
		setPriority(priority);
		setTaskIndex(taskIndex);
	}
		
	public Calendar getStartDate() {
		return NO_DATE;
	}

	public Calendar getEndDate() {
		return NO_DATE;
	}
	
	public Calendar getStartTime() {
		return NO_TIME;
	}

	public Calendar getEndTime() {
		return NO_TIME;
	}
	
	public String durationToString() {
		return EMPTY_STRING;
	}
	
	public String toString() {
		String taskDetails = KEYWORD_QUOTE;
		taskDetails += getTaskDescription();
		
		if (!getLocation().equalsIgnoreCase(EMPTY_STRING)) {
			taskDetails += KEYWORD_AT + getLocation();
		}
		
		taskDetails += KEYWORD_QUOTE;
		
		return taskDetails;	
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	FloatingTask newTask = (FloatingTask) super.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }

}
