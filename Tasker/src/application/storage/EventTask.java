//@@author A0125522R

package application.storage;

import java.util.Calendar;

public class EventTask extends Task implements Cloneable{
	private Calendar startDate;
	private Calendar endDate;
	
	public EventTask() {
		setTaskDescription("");
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		setLocation("");
		setRemindDate(Calendar.getInstance());
		setPriority("");
		setTaskIndex(-1);
	}
	
	public EventTask(String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority, int taskIndex) {
		setTaskDescription(taskDescription);
		this.startDate = startDate;
		this.endDate = endDate;
		setLocation(location);
		setRemindDate(remindDate);
		setPriority(priority);
		setTaskIndex(taskIndex);
	}
	
	public void setStartDate(Calendar startDate) {
		this.startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
	}
	
	public void setStartTime(Calendar startTime) {
	    this.startDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
		this.startDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
	    this.startDate.set(Calendar.SECOND, startTime.get(Calendar.SECOND));
		this.startDate.set(Calendar.MILLISECOND, startTime.get(Calendar.MILLISECOND));
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
	
	public Calendar getStartDate() {
		return startDate;
	}
	
	public Calendar getStartTime() {
		return startDate;
	}

	public Calendar getEndDate() {
		return endDate;
	}

	public Calendar getEndTime() {
		return endDate;
	}
	
	public String durationToString() {
		String message = "";
		String startingDate = dateToString(startDate);
		String startingTime = timeToString(startDate);
		String endingDate = dateToString(endDate);
		String endingTime = timeToString(endDate);
		// display start date, if exist
		if (!startingDate.equals("")) {
			message += startingDate;
			if (!startingTime.equals("")) {
				message += " " + startingTime;
			} // if start date and end date is same, display 1 date + both time only
			if (startingDate.equalsIgnoreCase(endingDate)) {
				if (startingTime.equals("")) {
					message += endingTime;
				}
				else if (!endingTime.equals("") && !startingTime.equals("")) {
					message += " to " + endingTime;
				}
				return message;
			}
		}
		// display start date and end date, if both exist
		if ( (!startingDate.equals("")) && (!endingDate.equals("")) ) {
			message += " to " + endingDate;
			if (!endingTime.equals("")) {
				message += " " + endingTime;
			} // display end date, if exist
		} else if (startingDate.equals("") && (!endingDate.equals(""))) {
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
		message += " " + durationToString();
		
		if (!getLocation().equalsIgnoreCase("")) {
			message += ", at " + getLocation();
		}
		
		message += "\"";
		
		return message;	
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	EventTask newTask = (EventTask) super.clone();
    	newTask.startDate = (Calendar) startDate.clone();
    	newTask.endDate = (Calendar) endDate.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }
}
