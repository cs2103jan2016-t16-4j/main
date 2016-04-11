//@@author A0125522R

package application.storage;

import java.util.Calendar;
/**
 * EventTask is a subclass of Task class. 
 * This class is used when the user creates a task with a event duration. 
 * It has an two extra parameter, startDate and endDate, to denote the task duration.
 */
public class EventTask extends Task implements Cloneable {
	private Calendar startDate;
	private Calendar endDate;

	/**
	 * Creates a EventTask object with "empty" variables initalised.
	 */
	public EventTask() {
		setTaskDescription(EMPTY_STRING);
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		setLocation(EMPTY_STRING);
		setRemindDate(Calendar.getInstance());
		setPriority(EMPTY_STRING);
		setTaskIndex(EMPTY_TASK);
	}
	
	/**
	 * Creates a EventTask object with specified variables.
	 */
	public EventTask(String taskDescription, Calendar startDate, Calendar endDate, String location, Calendar remindDate,
			String priority, int taskIndex) {
		setTaskDescription(taskDescription);
		this.startDate = startDate;
		this.endDate = endDate;
		setLocation(location);
		setRemindDate(remindDate);
		setPriority(priority);
		setTaskIndex(taskIndex);
	}

	/**
	 * Set the start date (year, month, date).
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
	}
	
	/**
	 * Set the start time (hour, minute, seconds, milliseconds).
	 */
	public void setStartTime(Calendar startTime) {
		this.startDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
		this.startDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
		this.startDate.set(Calendar.SECOND, startTime.get(Calendar.SECOND));
		this.startDate.set(Calendar.MILLISECOND, startTime.get(Calendar.MILLISECOND));
	}

	/**
	 * Set the end date (year, month, date).
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));
	}

	/**
	 * Set the end time (hour, minute, seconds, milliseconds).
	 */
	public void setEndTime(Calendar endTime) {
		this.endDate.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
		this.endDate.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
		this.endDate.set(Calendar.SECOND, endTime.get(Calendar.SECOND));
		this.endDate.set(Calendar.MILLISECOND, endTime.get(Calendar.MILLISECOND));
	}

	/**
	 * Returns the start date.
	 */
	public Calendar getStartDate() {
		return startDate;
	}

	/**
	 * Returns the start time.
	 */
	public Calendar getStartTime() {
		return startDate;
	}
	
	/**
	 * Returns the end date.
	 */
	public Calendar getEndDate() {
		return endDate;
	}

	/**
	 * Returns the end time.
	 */
	public Calendar getEndTime() {
		return endDate;
	}

	/**
	 * Returns the event duration in String.
	 */
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
			} // if start date and end date is same, display same date + both time
			if (startingDate.equalsIgnoreCase(endingDate)) {
				if (startingTime.equals(EMPTY_TIME_STRING)) {
					durationMessage += endingTime;
				} else if (!endingTime.equals(EMPTY_TIME_STRING) && !startingTime.equals(EMPTY_TIME_STRING)) {
					durationMessage += KEYWORD_TO + endingTime;
				}
				return durationMessage;
			}
		}
		// display start date and end date, if both exist
		if ((!startingDate.equals(EMPTY_DATE_STRING)) && (!endingDate.equals(EMPTY_DATE_STRING))) {
			durationMessage += KEYWORD_TO + endingDate;
			if (!endingTime.equals(EMPTY_TIME_STRING)) {
				durationMessage += KEYWORD_SPACE + endingTime;
			} // display only end date, if exist
		} else if (startingDate.equals(EMPTY_DATE_STRING) && (!endingDate.equals(EMPTY_DATE_STRING))) {
			durationMessage += KEYWORD_BY + endingDate;
			if (!endingTime.equals(EMPTY_TIME_STRING)) {
				durationMessage += KEYWORD_SPACE + endingTime;
			}
		}
		return durationMessage;
	}
	
	/**
	 * Returns the task details in String.
	 */
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

	/**
	 * Clones a EventTask.
	 */
	protected Object clone() throws CloneNotSupportedException {
		EventTask newTask = (EventTask) super.clone();
		newTask.startDate = (Calendar) startDate.clone();
		newTask.endDate = (Calendar) endDate.clone();
		newTask.remindDate = (Calendar) remindDate.clone();
		return newTask;
	}

	// @@author A0110422E
	/**
	 * Return checked priority parameter 
	 */		
	public String getPriority() {
		Calendar currentTime = Calendar.getInstance();
		Calendar startTime = getStartDate();
		String tempPriority = LOW;
		if (startTime.before(currentTime)) {
			setPriority(HIGH);
		}
		if (priority.equalsIgnoreCase(EMPTY_STRING)) {
			// End time is in less than two hours
			if (timeDifference(currentTime, startTime) < TWO_HOUR) {
				tempPriority = HIGH;
			} else {
				// End time is between two hours and one day
				if (timeDifference(currentTime, startTime) < ONE_DAY) {
					tempPriority = MEDIUM;
				} 			
			}
		} else {
			tempPriority = priority;
		}
		return tempPriority;
	}
	/**
	 * Return the time difference between two Calendar objects, in miliseconds
	 */	
	private long timeDifference(Calendar currentTime, Calendar startTime) {
		return startTime.getTimeInMillis() - currentTime.getTimeInMillis();
	}
}
