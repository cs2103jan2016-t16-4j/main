package application.storage;

import java.util.Calendar;

public class DeadlineTask extends Task implements Cloneable {
	private Calendar endDate;
	
	public DeadlineTask() {
		setTaskDescription("");
		endDate = Calendar.getInstance();
		setLocation("");
		setRemindDate(Calendar.getInstance());
		setPriority("");
		setTaskIndex(-1);
	}
	
	public DeadlineTask(String taskDescription, Calendar endDate, String location, Calendar remindDate, String priority,
			int taskIndex) {
		
		setTaskDescription(taskDescription);
		this.endDate = endDate;
		setEndTime(endDate);
		setLocation(location);
		setRemindDate(remindDate);
		setPriority(priority);
		setTaskIndex(taskIndex);
	}
	
	public void setEndDate(Calendar endDate) {
		this.endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));
	}

	public void setEndTime(Calendar endTime) {
		this.endDate.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
		this.endDate.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
		this.endDate.set(Calendar.SECOND, endTime.get(Calendar.SECOND));
		this.endDate.set(Calendar.MILLISECOND, endTime.get(Calendar.MILLISECOND));
	}
	
	public Calendar getEndDate() {
		return endDate;
	}

	public Calendar getEndTime() {
		return endDate;
	}
	
	public String deadlineToString() {
		String message = "";
		String endingDate = dateToString(endDate);
		String endingTime = timeToString(endDate);
		if (!endingDate.equals("")) {
			message += "by " + endingDate;
			if (!endingTime.equals("")) {
				message += " " + endingTime;
			}
		}
		return message;		
	}
	
	public String toString() {
		String message = "\"";
		message += getTaskDescription();
		message += " " + deadlineToString();
		
		if (!getLocation().equalsIgnoreCase("")) {
			message += ", at " + getLocation();
		}
		
		message += "\"";
		
		return message;	
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	DeadlineTask newTask = (DeadlineTask) super.clone();
    	newTask.endDate = (Calendar) endDate.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }
}
