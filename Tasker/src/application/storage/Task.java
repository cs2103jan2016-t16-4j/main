package application.storage;

public class Task {

	private String taskDescription;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
	private String location;
	private String remindDate;
	private String priority;
	private int taskIndex;	// to be considered
	
	public void storeTask(String taskInfo) {
		// call Logic to Parser to decipher String into task
		// gets an array[7] of string or Task objects
		// store string into variables
	}
	
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
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

	public String getStartDate() {
		return startDate;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public String getLocation() {
		return location;
	}

	public String getRemindDate() {
		return remindDate;
	}

	public String getPriority() {
		return priority;
	}
	
	public String showMessage () {
		String message = "\"";
		message += taskDescription;
		
		if (!startDate.equalsIgnoreCase("")) {
			message += ", from " + startDate;
			if (!startTime.equalsIgnoreCase("")){
				message += " " + startTime;
			}
		}
		
		if (!startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase("")) {
			message += " to " + endDate;
			if (!endTime.equalsIgnoreCase("")){
				message += " " + endTime;
			}
		} else if (startDate.equalsIgnoreCase("") && !endDate.equalsIgnoreCase("")) {
			message += ", by " + endDate;
			if (!endTime.equalsIgnoreCase("")){
				message += " " + endTime;
			}
		}
		
		if (!location.equalsIgnoreCase("")) {
			message += ", at " + location;
		}
		
		message += "\"";
		
		return message;	
	}
	
}
