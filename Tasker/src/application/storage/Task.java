//@@author A0125522R

package application.storage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * Task is the parent class of all Task types.
 * This class is used when the user wants to interacts with a Task.
 */
public abstract class Task implements Cloneable {

	// Task variables
	private String taskDescription;
	private String location;
	protected Calendar remindDate;
	protected String priority;
	private int taskIndex;
	
	// Date & Time String format
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("d MMM yyyy");
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
	
	// Constants
	protected static final int EMPTY_DATE = 1;
    protected static final int EMPTY_TASK = -1;
    protected static final int EMPTY_TIME_PARAMETER_1 = 1;
    protected static final int EMPTY_TIME_PARAMETER_2 = 0;
	protected static final int ONE_DAY = 86400000;
	protected static final int TWO_HOUR = 7200000;
    protected static final String EMPTY_DATE_STRING = "";
    protected static final String EMPTY_STRING = "";
    protected static final String EMPTY_TIME_STRING = "";
    protected static final String KEYWORD_AND = "and";
    protected static final String KEYWORD_AT = ", at ";
    protected static final String KEYWORD_BY = "by ";
    protected static final String KEYWORD_QUOTE = "\"";
    protected static final String KEYWORD_TO = " to ";
    protected static final String KEYWORD_SPACE = " ";
    protected static final String KEYWORD_FULLSTOP = ". ";
    protected static final String KEYWORD_PRIORITY = "Priority ";
    protected static final Calendar NO_DATE = null;
    protected static final Calendar NO_TIME = null;
	protected static final String HIGH = "high";
	protected static final String MEDIUM = "medium";
	protected static final String LOW = "low";
	

	/**
	 * Task default constructor.
	 */
	public Task() {
		taskDescription = EMPTY_STRING;
		location = EMPTY_STRING;
		remindDate = Calendar.getInstance();
		priority = EMPTY_STRING;
		taskIndex = EMPTY_TASK;
	}
	
	/**
	 * Task constructor with specified variables.
	 */
	public Task(String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority, int taskIndex) {
		this.taskDescription = taskDescription;
		this.location = location;
		this.remindDate = remindDate;
		this.priority = priority;
		this.taskIndex = taskIndex;
	}

	/**
	 * Set the task description.
	 */
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	/**
	 * Set the task location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * Set the remind date (year, month, date).
	 */
	public void setRemindDate(Calendar remindDate) {
		this.remindDate.set(remindDate.get(Calendar.YEAR), remindDate.get(Calendar.MONTH), remindDate.get(Calendar.DATE));
	}
	
	/**
	 * Set the remind time (hour, minute, seconds, milliseconds).
	 */
	public void setRemindTime(Calendar remindTime) {
		this.remindDate.set(Calendar.HOUR_OF_DAY, remindTime.get(Calendar.HOUR_OF_DAY));
		this.remindDate.set(Calendar.MINUTE, remindTime.get(Calendar.MINUTE));
		this.remindDate.set(Calendar.SECOND, remindTime.get(Calendar.SECOND));
		this.remindDate.set(Calendar.MILLISECOND, remindTime.get(Calendar.MILLISECOND));
	}

	/**
	 * Set the priority.
	 */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/**
	 * Set the task index.
	 */
	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}

	/**
	 * Get the task description.
	 */
	public String getTaskDescription() {
		return taskDescription;
	}
	
	/**
	 * Get the task index.
	 */
	public int getTaskIndex() {
		return taskIndex;
	}

	/**
	 * Get the location.
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Get the remind date (year, month, date).
	 */
	public Calendar getRemindDate() {
		return remindDate;
	}
	
	/**
	 * Get the remind date (hour, minute, seconds, milliseconds).
	 */
	public Calendar getRemindTime() {
		return remindDate;
	}

	/**
	 * Get the priority.
	 */
	public String getPriority() {
		return priority;
	}
	
	/**
	 * Formats a given date into a String (e.g 7th April 2016)
	 */
	public String dateToString(Calendar date) {
		if (!isDateEmpty(date)) {
			return FORMAT_DATE.format(date.getTime());
		} else {
			return EMPTY_STRING;
		}
	}

	/**
	 * Checks if the date is "empty".
	 */
	public boolean isDateEmpty(Calendar date) {
		return date.get(Calendar.YEAR)==EMPTY_DATE;
	}
	
	/**
	 * Formats a given time into a String (e.g 5:30 PM)
	 */
	public String timeToString(Calendar date) {
		if (!isTimeEmpty(date)) {
			return FORMAT_TIME.format(date.getTime());
		} else {
			return EMPTY_STRING;
		}

	}
	
	/**
	 * Checks if the time is "empty".
	 */
	public boolean isTimeEmpty(Calendar date) {
		return (date.get(Calendar.MILLISECOND) == EMPTY_TIME_PARAMETER_1)
				&& (date.get(Calendar.HOUR_OF_DAY) == EMPTY_TIME_PARAMETER_2)
				&& (date.get(Calendar.MINUTE) == EMPTY_TIME_PARAMETER_2)
				&& (date.get(Calendar.SECOND) == EMPTY_TIME_PARAMETER_2);
	}
	
	// abstract methods for subclasses
	public abstract Calendar getStartDate();
	
	public abstract Calendar getEndDate();
	
	public abstract Calendar getStartTime();
	
	public abstract Calendar getEndTime();
	
	public abstract String durationToString();

//@@author A0110422E
/*	
	private String categoryType;

	public String getCategoryType() {
		return categoryType;
	}
*/

//@@author A0125522R
	/**
	 * Clones a Task object.
	 */
	protected Object clone() throws CloneNotSupportedException {
    	Task newTask = (Task) super.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }
	
}
