package application.parser;

public class CommandOld {
	public enum CommandType {
		ADD, SEARCH, DELETE, UPDATE, CLOSED, UNDO, HELP, STORAGE
	}

	CommandType operatingCommand;

	public String[] commandDetails = new String[7];
	// The 7 elements in the string array are as follows:
	// <task name> <start date and time> <end date/due date and time> <location>
	// <remind date> <priority> <item number>

	public void writeCommandtype(String command) {
		operatingCommand = CommandType.valueOf(command);
	}

	public void writeTaskName(String taskName) {
		commandDetails[0] = taskName;
	}

	public void writeStartDateAndTime(String startDateAndTime) {
		commandDetails[1] = startDateAndTime;
	}

	public void writeEndDateAndTime(String endDateAndTime) {
		commandDetails[2] = endDateAndTime;
	}

	public void writeLocation(String location) {
		commandDetails[3] = location;
	}

	public void writeRemindDate(String remindDate) {
		commandDetails[4] = remindDate;
	}

	public void writePriority(String priority) {
		commandDetails[5] = priority;
	}

	public void writeItemNumber(String itemNumber) {
		commandDetails[6] = itemNumber;
	}

}
