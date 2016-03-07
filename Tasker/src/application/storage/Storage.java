package application.storage;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Storage{

	public static String FILE_PATH = "";
	public static final String FILE_NAME = "TaskerData.txt";
	static final String FILE_DIRECTORY = "TaskerDirectory.txt";
	public static int taskIndex = 0;
	public static ArrayList<Task> fileList = new ArrayList<Task>();
	public ArrayList<Task> searchList;
	public static ArrayList<Task> closedList = new ArrayList<Task>();
	
	public boolean addTaskInList(String taskDescription, String startDate, String endDate
				, String dueTime, String location
				, String remindDate, String priority) {
		
		Task newTask = new Task();
		newTask.setTaskDescription(taskDescription);
		newTask.setStartDate(startDate);
		newTask.setEndDate(endDate);
		newTask.setDueTime(dueTime);
		newTask.setLocation(location);
		newTask.setRemindDate(remindDate);
		newTask.setPriority(priority);
		newTask.setTaskIndex(taskIndex);
		fileList.add(newTask);
		taskIndex++;
		return true;
	}
	
	public void clearFile() throws IOException {
		File f = new File(FILE_PATH);
		if (f.exists()) {
			PrintWriter fw = new PrintWriter(FILE_PATH);
			fw.print("");
			fw.close();
		}
	}
	
	public boolean closeTaskInList(int index) {
		boolean isSuccesClose = false;
		for (int i = 0 ; i<fileList.size(); i++) {
			if (fileList.get(i).getTaskIndex()==index) {
				closedList.add(fileList.get(i));
				fileList.remove(i);
				//System.out.println("\nClosed : "+index);
				isSuccesClose = true;
				break;
			}
		}

		if (isSuccesClose) {
			return true;
		} else {
			return false;
		}	
	//	showFeedback("closed");
	}
	
	public boolean deleteTaskInList(int index) throws IOException {
		boolean isSuccessDelete = false;
		for (int i = 0 ; i<fileList.size(); i++) {
			if (fileList.get(i).getTaskIndex()==index) {
				fileList.remove(i);
				System.out.println("\nDeleted : "+index);
				isSuccessDelete = true;
				break;
			}
		}

		if (isSuccessDelete) {
			return true;
		} else {
			return false;
		}		

	}
	
	
	// DD/MM/YYYY = 10 characters = 0-9 (index)
	// takes in String as "DD/MM/YYYY" with 2 slashes included
	// returns true = if date is before and equal
	// returns false = if date is not before/equal (i.e exceeds)
	public boolean dateIsBeforeOrEqual(String scannedDate, String dateSearch) {
		if (scannedDate.equals("")) {
			return false;
		} else {
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
	}

	public void loadFile() throws IOException {
		checkDirectoryFileCreated();
		loadDirectoryFile();
		loadDataFile();
	}

	public boolean loadDataFile() throws IOException, FileNotFoundException {
		// load datafile if exist
		File f = new File(FILE_PATH);		
		if (f.exists()) {
			// open file and load total task index
			loadTaskIndex();
			// open file and load all tasks
			loadAllTasks();	
			return true;
		} else {
			System.out.println("No saved data file detected yet..\nStart entering tasks!");
			return false;
		}
	}

	public boolean loadDirectoryFile() throws FileNotFoundException, IOException {
		// check whether user specified a custom directory to save datafile
		String readText;
		BufferedReader in = new BufferedReader(new FileReader(FILE_DIRECTORY));
		// skip first line first
		readText = in.readLine();
		in.close();
		if (readText == null) {
			System.out.println("User has not specified directory to store data file yet. \nThus, data file will reside in the program's folder.");
			FILE_PATH = FILE_NAME;
			return false;
		} else {
			FILE_PATH = readText + FILE_NAME;
			System.out.println("User specified directory : "+FILE_PATH);
			return true;
		}
	}

	// true - if user has launched program b4
	// false - if user first time launching program
	public boolean checkDirectoryFileCreated() throws IOException {
		// check whether its user first time opening program
		File d = new File(FILE_DIRECTORY);
		if (!d.exists()) {
			PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_DIRECTORY, true)));
			fw.print("");		// indicate path name is not specified yet 
			fw.close();
			return false;
		}
		else {
			return true;
		}
	} 


	public void loadTaskIndex() throws IOException {
		String readText;
		BufferedReader in = new BufferedReader(new FileReader(FILE_PATH));
		readText = in.readLine();
		taskIndex = Integer.parseInt(readText);
		System.out.println("Total task index : "+taskIndex);
		in.close();
	}
	
	public void loadAllTasks() throws FileNotFoundException, IOException {
		String readText;
		BufferedReader in = new BufferedReader(new FileReader(FILE_PATH));
		// skip first line first
		readText = in.readLine();
		
		while ((readText = in.readLine()) != null) {
			String tmp[] = readText.split("\b", 8);
			Task task = new Task();
			task.setTaskDescription(tmp[0]);
			System.out.println("\nTask Description : "+task.getTaskDescription());
			task.setStartDate(tmp[1]);
			System.out.println("Start date : "+task.getStartDate());
			task.setEndDate(tmp[2]);
			System.out.println("End date : "+task.getEndDate());
			task.setDueTime(tmp[3]);
			System.out.println("Due Time : "+task.getDueTime());
			task.setLocation(tmp[4]);
			System.out.println("Location : "+task.getLocation());
			task.setRemindDate(tmp[5]);
			System.out.println("Remind date : "+task.getRemindDate());
			task.setPriority(tmp[6]);
			System.out.println("Priority level : "+task.getPriority());
			task.setTaskIndex(Integer.parseInt(tmp[7]));
			System.out.println("Task Index : "+task.getTaskIndex());
			fileList.add(task);
		}
		in.close();
	}

	public void saveFile() throws IOException {
		// #check if file exists
		File f = new File(FILE_PATH);
		if (!f.exists()) {
			f.createNewFile();
		}
		// #clear file first
		clearFile();
		
		// #save total task index 
		saveTaskIndex();
		
		// #save all the tasks
		saveAllTasks();

	}

	public void saveAllTasks() throws IOException {
		for (int i = 0; i<fileList.size(); i++){			
			String taskToStore = "";
			taskToStore += fileList.get(i).getTaskDescription();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getStartDate();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getEndDate();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getDueTime();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getLocation();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getRemindDate();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getPriority();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getTaskIndex();
			PrintWriter fwz = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH, true)));
			fwz.println(taskToStore);
			fwz.close();
		}
	}

	public void saveTaskIndex() throws IOException {
		PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_PATH, true)));
		fw.println(taskIndex);
//		System.out.println("\nSaving : "+taskIndex);
		fw.close();
	}

	public ArrayList<Task> searchByDate(String dateSearch, boolean isSearchBy) {
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
		return showSearchResults(searchList);
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

	public ArrayList<Task> searchByTask(String taskSearch) {
		searchList = new ArrayList<Task>();
		for (int i = 0; i < fileList.size(); i++) {
			Task obj = fileList.get(i);
			if (obj.getTaskDescription().toLowerCase()
					.contains(taskSearch.toLowerCase())) {
				storeSearchResults(obj);
				System.out.println("Found a entry..");
			}
		}

		return showSearchResults(searchList);
	}

	public ArrayList<Task> showSearchResults(ArrayList<Task> results) {
		System.out.println("Search : " + results.size() + " results found.");

		if (results.size() == 0) {
			results = null;
		} else {
			for (int i = 0; i<searchList.size(); i++){
				System.out.println("\"" + searchList.get(i).getTaskDescription() + "\" is found.");
			}
		}

		return results;
	}

	public void storeSearchResults(Task task) {
		searchList.add(task);
	}

	public boolean showFeedback(String type) {
		if (type.equalsIgnoreCase("added")) {
			return true;
		} else if (type.equalsIgnoreCase("deleted")) {
			return true;
		}
		
		return false;
	}
	
	}
