package application.storage;
import java.util.ArrayList;

public class Storage{

	static final String FILE_DATA = "TaskerData.txt";
	static final int START_LINE = 1;
	static ArrayList<Task> fileList = new ArrayList<Task>();
	private ArrayList<Task> searchList;

	public void addTaskInList(String taskDescription, String startDate, String endDate
				, String endDate, String dueTime, String location
				, String remindDate, String priority) {
			
	}
	
	public void closeTaskInList() {
		
	}
	
	public void deleteTaskInList() {
		
	}
	
	
	// DD/MM/YYYY = 10 characters = 0-9 (index)
	// takes in String as "DD/MM/YYYY" with 2 slashes included
	// returns true = if date is before and equal
	// returns false = if date is not before/equal (i.e exceeds)
	public boolean dateIsBeforeOrEqual(String scannedDate, String dateSearch) {
		int byYear = Integer.parseInt(dateSearch.substring(6));
		int scannedYear = Integer.parseInt(scannedDate.substring(6));
		System.out.println("byYear : " + byYear);
		System.out.println("scannedYear : " + scannedYear);
		int byMonth = Integer.parseInt(dateSearch.substring(3, 5));
		int scannedMonth = Integer.parseInt(scannedDate.substring(3, 5));
		int byDay = Integer.parseInt(dateSearch.substring(0, 2));
		int scannedDay = Integer.parseInt(scannedDate.substring(0, 2));

		// year exceeds
		if (scannedYear > byYear) {
			return false;
		}
		// year doesnt exceed = before/equal
		else {
			// if year is equal, but month exceeds = false
			if (scannedYear == byYear && scannedMonth > byMonth) {
				return false;
			}
			// if year is equal, but month didnt exceed = check day
			else {
				if (scannedYear == byYear && scannedMonth == byMonth
						&& scannedDay > byDay) {
					return false;
				} else {
					return true;
				}
			}
		}
	}

	public void loadFile() {
		// open file
		// load task details into a Task Object
		// store into the ArrayList<Task> fileList
	}

	public void saveFile() {
		// #1 - replace file with fileList
		// #2 - clear/delete file and re-load into file using ArrayList<Task>
		// fileList
	}

	public void searchByDate(String dateSearch, boolean isSearchBy) {
		// initialize new search list
		searchList = new ArrayList<Task>();

		if (isSearchBy) {
			for (int i = 0; i < fileList.size(); i++) {
				Task task = fileList.get(i);
				// find the due date (search by <date> - displays before <date>
				// or on <date>)
				if (isSearchBy) {
					if (dateIsBeforeOrEqual(task.getEndDate(), dateSearch)) {
						// store this task entry into search results
						storeSearchResults(task);
					}
				} else {
					// find the due date (search on <date>)
					if (task.getEndDate().equalsIgnoreCase(dateSearch)) {
						// store this task entry into search results
						storeSearchResults(task);
					}
				}
			}
		}
		showSearchResults(searchList);
	}

	public void findTaskAndUpdate(Task task) {

		// find the task number first
		for (int i = 0; i < fileList.size(); i++) {
			if (fileList.get(i).getTaskIndex() == task.getTaskIndex()) {
				// update the task
				updateTaskInList(task, i);
			}
		}

	}

	// updates the task with the necessary details only
	public void updateTaskInList(Task task, int index) {
		if (task.getTaskDescription() != "") {
			fileList.get(index).setTaskDescription(task.getTaskDescription());
		}
		if (task.getStartDate() != "") {
			fileList.get(index).setStartDate(task.getStartDate());
		}
		if (task.getEndDate() != "") {
			fileList.get(index).setEndDate(task.getEndDate());
		}
		if (task.getDueTime() != "") {
			fileList.get(index).setDueTime(task.getDueTime());
		}
		if (task.getLocation() != "") {
			fileList.get(index).setLocation(task.getLocation());
		}
		if (task.getRemindDate() != "") {
			fileList.get(index).setRemindDate(task.getRemindDate());
		}
		if (task.getPriority() != "") {
			fileList.get(index).setPriority(task.getPriority());
		}
	}

	public void searchByTask(String taskSearch) {
		searchList = new ArrayList<Task>();
		for (int i = 0; i < fileList.size(); i++) {
			Task obj = fileList.get(i);
			if (obj.getTaskDescription().toLowerCase()
					.contains(taskSearch.toLowerCase())) {
				storeSearchResults(obj);
			}
		}

		showSearchResults(searchList);
	}

	public ArrayList<Task> showSearchResults(ArrayList<Task> results) {
		System.out.println("Search : " + results.size() + " results found.");
		if (results.size() == 0) {
			results = null;
		}

		return results;
	}

	public void storeSearchResults(Task task) {
		searchList.add(task);
	}

	public void showFeedback() {

	}
	
	}
