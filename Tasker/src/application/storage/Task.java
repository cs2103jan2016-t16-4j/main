package application.storage;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//package application.storage;

public class Task {

	private String taskDescription;
	private Calendar startDate;
	private Calendar endDate;
	private String location;
	private Calendar remindDate;
	private String priority;
	private int taskIndex;	// to be considered
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
    private static final int EMPTY = 999;
    
	public Task() {
		taskDescription = "";
		startDate = Calendar.getInstance();
		endDate = Calendar.getInstance();
		location = "";
		remindDate = Calendar.getInstance();
		priority = "";
		taskIndex = -1;
	}
	
	public Task(String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority, int taskIndex) {
		this.taskDescription = taskDescription;
		this.startDate = startDate;
		this.endDate = endDate;
		this.location = location;
		this.remindDate = remindDate;
		this.priority = priority;
		this.taskIndex = taskIndex;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setStartDate(Calendar startDate) {
		this.startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DATE));
	}
	
	public void setStartTime(Calendar startTime) {
		this.startDate.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY));
		this.startDate.set(Calendar.MINUTE, startTime.get(Calendar.MINUTE));
	}

	public void setEndDate(Calendar endDate) {
		this.endDate.set(endDate.get(Calendar.YEAR), endDate.get(Calendar.MONTH), endDate.get(Calendar.DATE));
	}

	public void setEndTime(Calendar endTime) {
		this.endDate.set(Calendar.HOUR_OF_DAY, endTime.get(Calendar.HOUR_OF_DAY));
		this.endDate.set(Calendar.MINUTE, endTime.get(Calendar.MINUTE));
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
	
	public String toString() {
		String message = "\"";
		message += taskDescription;
		if (startDate.get(Calendar.YEAR)!=EMPTY) {
			message += ", from " + FORMAT_DATE.format(startDate.getTime());
			if (startDate.get(Calendar.MILLISECOND)!=EMPTY){
				message += " " + FORMAT_TIME.format(startDate.getTime());
			}
		}
		
		if (startDate.get(Calendar.YEAR)!=EMPTY && endDate.get(Calendar.YEAR)!=EMPTY) {
			message += " to " + FORMAT_DATE.format(endDate.getTime());
			if (endDate.get(Calendar.MILLISECOND)!=EMPTY){
				message += " " + FORMAT_TIME.format(endDate.getTime());
			}
		} else if (startDate.get(Calendar.YEAR)==EMPTY && endDate.get(Calendar.YEAR)!=EMPTY) {
			message += ", by " + FORMAT_DATE.format(endDate.getTime());
			if (startDate.get(Calendar.MILLISECOND)!=EMPTY){
				message += " " + FORMAT_TIME.format(endDate.getTime());
			}
		}
		
		if (!location.equalsIgnoreCase("")) {
			message += ", at " + location;
		}
		
		message += "\"";
		
		return message;	
	}
	
}
