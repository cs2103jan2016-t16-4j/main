package application.storage;

import java.util.Calendar;

public class FloatingTask extends Task implements Cloneable  {

	public FloatingTask() {
		setTaskDescription("");
		setLocation("");
		setRemindDate(Calendar.getInstance());
		setPriority("");
		setTaskIndex(-1);
	}
	
	public FloatingTask(String taskDescription, String location, Calendar remindDate, String priority, int taskIndex) {
		
		setTaskDescription(taskDescription);
		setLocation(location);
		setRemindDate(remindDate);
		setPriority(priority);
		setTaskIndex(taskIndex);
	}
		
	public Calendar getStartDate() {
		return null;
	}

	public Calendar getEndDate() {
		return null;
	}
	
	public Calendar getStartTime() {
		return null;
	}

	public Calendar getEndTime() {
		return null;
	}
	
	public String durationToString() {
		return "";
	}
	
	public String toString() {
		String message = "\"";
		message += getTaskDescription();
		
		if (!getLocation().equalsIgnoreCase("")) {
			message += " at " + getLocation();
		}
		
		message += "\"";
		
		return message;	
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	FloatingTask newTask = (FloatingTask) super.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }

}
