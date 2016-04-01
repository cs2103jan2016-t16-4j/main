package application.storage;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Task implements Cloneable {

	private String taskDescription;
	private Calendar startDate;
	private Calendar endDate;
	private String location;
	private Calendar remindDate;
	private String priority;
	private int taskIndex;
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("d MMM yyyy");
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
    private static final int EMPTY = 1;
    private static final String EMPTY_STRING = "";
    
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
		message += this.taskDescription;
		message += " " + durationToString();
		
		if (!location.equalsIgnoreCase("")) {
			message += ", at " + location;
		}
		
		message += "\"";
		
		return message;	
	}
	
    protected Object clone() throws CloneNotSupportedException {
    	Task newTask = (Task) super.clone();
    	newTask.startDate = (Calendar) startDate.clone();
    	newTask.endDate = (Calendar) endDate.clone();
    	newTask.remindDate = (Calendar) remindDate.clone();
    	return newTask;
//        return super.clone();
    }
	
}
