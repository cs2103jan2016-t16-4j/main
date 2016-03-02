
public class Task {

	private String taskDescription;
	private String startDate;
	private String endDate;
	private String dueTime;
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

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
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

	public String getEndDate() {
		return endDate;
	}

	public String getDueTime() {
		return dueTime;
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
	
}
