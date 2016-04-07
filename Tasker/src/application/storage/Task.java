//@@author A0125522R

package application.storage;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public abstract class Task implements Cloneable {

	private String taskDescription;
	private String location;
	protected Calendar remindDate;
	private String priority;
	private int taskIndex;
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("d MMM yyyy");
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
    private static final int EMPTY = 1;
    private static final String EMPTY_STRING = "";
    
	public Task() {
		taskDescription = "";
		location = "";
		remindDate = Calendar.getInstance();
		priority = "";
		taskIndex = -1;
	}
	
	public Task(String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority, int taskIndex) {
		this.taskDescription = taskDescription;
		this.location = location;
		this.remindDate = remindDate;
		this.priority = priority;
		this.taskIndex = taskIndex;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRemindDate(Calendar remindDate) {
		this.remindDate.set(remindDate.get(Calendar.YEAR), remindDate.get(Calendar.MONTH), remindDate.get(Calendar.DATE));
	}
	
	public void setRemindTime(Calendar remindTime) {
		this.remindDate.set(Calendar.HOUR_OF_DAY, remindTime.get(Calendar.HOUR_OF_DAY));
		this.remindDate.set(Calendar.MINUTE, remindTime.get(Calendar.MINUTE));
		this.remindDate.set(Calendar.SECOND, remindTime.get(Calendar.SECOND));
		this.remindDate.set(Calendar.MILLISECOND, remindTime.get(Calendar.MILLISECOND));
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setTaskIndex(int taskIndex) {
		this.taskIndex = taskIndex;
	}

	public String getTaskDescription() {
		return taskDescription;
	}
	
	public int getTaskIndex() {
		return taskIndex;
	}

	public String getLocation() {
		return location;
	}

	public Calendar getRemindDate() {
		return remindDate;
	}
	
	public Calendar getRemindTime() {
		return remindDate;
	}

	public String getPriority() {
		return priority;
	}
	
	public String dateToString(Calendar date) {
		if (date.get(Calendar.YEAR)!=EMPTY) {
			return FORMAT_DATE.format(date.getTime());
		} else {
			return EMPTY_STRING;
		}
	}
	
	public String timeToString(Calendar date) {
		if (!((date.get(Calendar.MILLISECOND)==EMPTY) && (date.get(Calendar.HOUR_OF_DAY)==0) && (date.get(Calendar.MINUTE)==0) && (date.get(Calendar.SECOND)==0))) {
			return FORMAT_TIME.format(date.getTime());
		} else {
			return EMPTY_STRING;
		}

	}

//@@author A0110422E
/*	
	private String categoryType;

	public String getCategoryType() {
		return categoryType;
	}
*/

//@@author A0125522R
	public abstract Calendar getStartDate();
	
	public abstract Calendar getEndDate();
	
	public abstract String durationToString();
    
	protected Object clone() throws CloneNotSupportedException {
    	Task newTask = (Task) super.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
    }
	
}
