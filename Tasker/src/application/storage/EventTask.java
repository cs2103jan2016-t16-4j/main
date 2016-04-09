//@@author A0125522R

package application.storage;

import java.util.Calendar;

public class EventTask extends Task implements Cloneable{
	private Calendar startDate;
	private Calendar endDate;
	
	public EventTask() {
		setTaskDescription(EMPTY_STRING);
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		setLocation(EMPTY_STRING);
		setRemindDate(Calendar.getInstance());
		setPriority(EMPTY_STRING);
		setTaskIndex(EMPTY_TASK);
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
		String durationMessage = EMPTY_STRING;
		String startingDate = dateToString(startDate);
		String startingTime = timeToString(startDate);
		String endingDate = dateToString(endDate);
		String endingTime = timeToString(endDate);
		// display start date, if exist
		if (!startingDate.equals(EMPTY_DATE_STRING)) {
			durationMessage += startingDate;
			if (!startingTime.equals(EMPTY_TIME_STRING)) {
				durationMessage += KEYWORD_SPACE + startingTime;
			} // if start date and end date is same, display 1 date + both time only
			if (startingDate.equalsIgnoreCase(endingDate)) {
				if (startingTime.equals(EMPTY_TIME_STRING)) {
					durationMessage += endingTime;
				}
				else if (!endingTime.equals(EMPTY_TIME_STRING) && !startingTime.equals(EMPTY_TIME_STRING)) {
					durationMessage += KEYWORD_TO + endingTime;
				}
				return durationMessage;
			}
		}
		// display start date and end date, if both exist
		if ( (!startingDate.equals(EMPTY_DATE_STRING)) && (!endingDate.equals(EMPTY_DATE_STRING)) ) {
			durationMessage += KEYWORD_TO + endingDate;
			if (!endingTime.equals(EMPTY_TIME_STRING)) {
				durationMessage += KEYWORD_SPACE + endingTime;
			} // display end date, if exist
		} else if (startingDate.equals(EMPTY_DATE_STRING) && (!endingDate.equals(EMPTY_DATE_STRING))) {
			durationMessage += KEYWORD_BY + endingDate;
			if (!endingTime.equals(EMPTY_TIME_STRING)) {
				durationMessage += KEYWORD_SPACE + endingTime;
			}
		}
		return durationMessage;		
	}
	
	public String toString() {
		String taskDetails = KEYWORD_QUOTE;
		taskDetails += getTaskDescription();
		taskDetails += KEYWORD_SPACE + durationToString();
		
		if (!getLocation().equalsIgnoreCase(EMPTY_STRING)) {
			taskDetails += KEYWORD_AT + getLocation();
		}
		
		taskDetails += KEYWORD_QUOTE;
		
		return taskDetails;	
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	EventTask newTask = (EventTask) super.clone();
    	newTask.startDate = (Calendar) startDate.clone();
    	newTask.endDate = (Calendar) endDate.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }
    //@@author A0110422E
  	public String getPriority() {
  		Calendar currentTime = Calendar.getInstance();
  		Calendar startTime = startDate;
  		String tempPriority = "low";
  		if (startTime.before(currentTime)) {
  			setPriority("high");
  		}
  		if (priority == "") {
  			// End time is in less than two hours or before current time
  			currentTime.roll(Calendar.HOUR, 2);
  			if (startTime.before(currentTime)) {
  				tempPriority = "high";
  			} else {
  				currentTime.roll(Calendar.HOUR, -2);		
  				// End time is between two hours and one day
  				currentTime.roll(Calendar.DATE, 1);
  				if (startTime.before(currentTime)) {
  					tempPriority = "medium";
  					currentTime.roll(Calendar.DATE, -1);
  				}	
  			}		
  			currentTime.roll(Calendar.DATE, -1);	
  		} else {
  			tempPriority = priority;
  		}
  		return tempPriority;
  	}    
}
