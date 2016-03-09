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

public class Storage {

	public static final String FILE_NAME = "TaskerData.txt";
	public static final String FILE_DIRECTORY = "TaskerDirectory.txt";
	public int taskIndex = 0;
	public String filePath = "";
	public ArrayList<Task> closedList = new ArrayList<Task>();
	public ArrayList<Task> fileList = new ArrayList<Task>();
	public ArrayList<Task> searchList;
	
	public String addTaskInList(String taskDescription, String startDate, String startTime, String endDate
				, String endTime, String location
				, String remindDate, String priority) {
		
		Task newTask = new Task();
		newTask.setTaskDescription(taskDescription);
		newTask.setStartDate(startDate);
		newTask.setStartTime(startTime);
		newTask.setEndDate(endDate);
		newTask.setEndTime(endTime);
		newTask.setLocation(location);
		newTask.setRemindDate(remindDate);
		newTask.setPriority(priority);
		newTask.setTaskIndex(taskIndex);
		fileList.add(newTask);
		taskIndex++;
		return showMessage(newTask);
	}
	
	public void clearFile() throws IOException {
		File f = new File(filePath);
		if (f.exists()) {
			PrintWriter fw = new PrintWriter(filePath);
			fw.print("");
			fw.close();
		}
	}
	
	public String closeTaskFromAll(int index) {
			if (fileList.size()>=index) {
				String closeMessage = showMessage(fileList.get(index));
				closedList.add(fileList.get(index));
				fileList.remove(index);
				return closeMessage;
			}
			else {
				return null;
			}
	}
	
	public String closeTaskFromSearch(int index) {
		boolean isSuccessClose = false;
		
		if (fileList.size()>=index) {
			if (searchList.size()>= index) {
				for (int i = 0; i < fileList.size(); i++) {				
					if (searchList.get(index).getTaskIndex() == fileList.get(i).getTaskIndex()) {
						index = i;
						isSuccessClose = true;
						break;
					}
				}
			}	
			if (isSuccessClose) {
				String closeMessage = showMessage(fileList.get(index));
				closedList.add(fileList.get(index));
				fileList.remove(index);
				return closeMessage;
			} else {
				return null;
			}
		}
		else {
			return null;
		}
		
	}
	
	public String deleteTaskFromSearch(int index) throws IOException {
		boolean isSuccessDelete = false;
		
		if (searchList.size()>= index) {
			for (int i = 0; i < fileList.size(); i++) {				
				if (searchList.get(index).getTaskIndex() == fileList.get(i).getTaskIndex()) {
					index = i;
					isSuccessDelete = true;
					break;
				}
			}
				
			if (isSuccessDelete) {
				String message = showMessage(fileList.get(index));
				fileList.remove(index);
				return message;
			} else {
				return null;
			}
			
		} else {
			return null;
		}
		/*
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
*/
	}
	
	public String deleteTaskFromAll(int index) throws IOException {
	
		if (fileList.size()>=index) {
			String message = showMessage(fileList.get(index));
			fileList.remove(index);
			return message;			
		}
		else {
			return null;
		}
		
	}
	
	public String showMessage (Task task) {
		String message = "\"";
		message += task.getTaskDescription();
		
		if (!task.getStartDate().equalsIgnoreCase("")) {
			message += ", from " + task.getStartDate();
			if (!task.getStartTime().equalsIgnoreCase("")){
				message += " " + task.getStartTime();
			}
		}
		
		if (!task.getStartDate().equalsIgnoreCase("") && !task.getEndDate().equalsIgnoreCase("")) {
			message += " to " + task.getEndDate();
			if (!task.getEndTime().equalsIgnoreCase("")){
				message += " " + task.getEndTime();
			}
		} else if (task.getStartDate().equalsIgnoreCase("") && !task.getEndDate().equalsIgnoreCase("")) {
			message += ", by " + task.getEndDate();
			if (!task.getEndTime().equalsIgnoreCase("")){
				message += " " + task.getEndTime();
			}
		}
		
		if (!task.getLocation().equalsIgnoreCase("")) {
			message += ", at " + task.getLocation();
		}
		
		message += "\"";
		
		return message;	
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
//			System.out.println("byYear : " + byYear);
//			System.out.println("scannedYear : " + scannedYear);
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

	public boolean startUpCheck() throws IOException {
		return checkDirectoryFileCreated();
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
	
	public ArrayList<Task> loadFile() throws IOException {
		loadDirectoryFile();
		return loadDataFile();
	}

	public ArrayList<Task> loadDataFile() throws IOException, FileNotFoundException {
		// load datafile if exist
		File f = new File(filePath);		
		if (f.exists()) {
			// open file and load total task index
			loadTaskIndex();
			// open file and load all tasks
			return loadAllTasks();	
		} else {
//			System.out.println("No saved data file detected yet..\nStart entering tasks!");
			return fileList;
		}
	}

	public String loadDirectoryFile() throws FileNotFoundException, IOException {
		// check whether user specified a custom directory to save datafile
		String readText;
		BufferedReader in = new BufferedReader(new FileReader(FILE_DIRECTORY));
		// skip first line first
		readText = in.readLine();
		in.close();
		if (readText == null) {
//			System.out.println("User has not specified directory to store data file yet. \nThus, data file will reside in the program's folder.");
			filePath = FILE_NAME;
		} else {
			filePath = readText;
//			System.out.println("User specified directory : "+filePath);
		}
		
		return readText;
	}

	public void loadTaskIndex() throws IOException {
		String readText;
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		readText = in.readLine();
		taskIndex = Integer.parseInt(readText);
//		System.out.println("Total task index : "+taskIndex);
		in.close();
	}
	
	public ArrayList<Task> loadAllTasks() throws FileNotFoundException, IOException {
		String readText;
		BufferedReader in = new BufferedReader(new FileReader(filePath));
		// skip first line first
		readText = in.readLine();
		
		while ((readText = in.readLine()) != null) {
			String tmp[] = readText.split("\b", 9);
			Task task = new Task();
			task.setTaskDescription(tmp[0]);
			//System.out.println("\nTask Description : "+task.getTaskDescription());
			task.setStartDate(tmp[1]);
			//System.out.println("Start date : "+task.getStartDate());
			task.setStartTime(tmp[2]);
			//System.out.println("Start time : "+task.getStartTime());
			task.setEndDate(tmp[3]);
			//System.out.println("End date : "+task.getEndDate());
			task.setEndTime(tmp[4]);
			//System.out.println("Due Time : "+task.getEndTime());
			task.setLocation(tmp[5]);
			//System.out.println("Location : "+task.getLocation());
			task.setRemindDate(tmp[6]);
			//System.out.println("Remind date : "+task.getRemindDate());
			task.setPriority(tmp[7]);
			//System.out.println("Priority level : "+task.getPriority());
			task.setTaskIndex(Integer.parseInt(tmp[8]));
			//System.out.println("Task Index : "+task.getTaskIndex());
			fileList.add(task);
		}
		in.close();
		searchList = fileList;
		return fileList;
	}

	public void saveFile() throws IOException {
		// #check if file exists
		File f = new File(filePath);
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
			taskToStore += fileList.get(i).getStartTime();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getEndDate();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getEndTime();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getLocation();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getRemindDate();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getPriority();
			taskToStore += "\b";
			taskToStore += fileList.get(i).getTaskIndex();
			PrintWriter fwz = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
			fwz.println(taskToStore);
			fwz.close();
		}
	}

	public void saveTaskIndex() throws IOException {
		PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		fw.println(taskIndex);
//		System.out.println("\nSaving : "+taskIndex);
		fw.close();
	}
	
	public boolean saveDirectory(String path) throws FileNotFoundException {
		PrintWriter fw = new PrintWriter(FILE_DIRECTORY);
		fw.print("");
		
		if (path.equalsIgnoreCase("")) {
			filePath = FILE_NAME;
			fw.close();
			return false;
		}
		else {
			filePath = path + FILE_NAME;
//			System.out.println("To be entered : "+filePath);
			fw.println(filePath);
			fw.close();
			return true;
		}
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

	public String updateTaskFromSearch(int index, String taskDescription,
			String startDate, String startTime, String endDate, String endTime,
			String location, String remindDate, String priority) {
		
		boolean isUpdateSuccess = false;
		
		// find the task in the fileList first
		for (int i = 0; i < fileList.size(); i++) {
			if (searchList.get(index).getTaskIndex() == fileList.get(i).getTaskIndex()) {
				index = i;
				break;
			}
		}
		
		String updateMessage = "\nOld : ";
		updateMessage += showMessage(fileList.get(index));
		
		if (!taskDescription.equalsIgnoreCase("")) {
			fileList.get(index).setTaskDescription(taskDescription);
			isUpdateSuccess = true;
		}
		if (!startDate.equalsIgnoreCase("")) {
			fileList.get(index).setStartDate(startDate);
			isUpdateSuccess = true;
		}
		if (!startTime.equalsIgnoreCase("")) {
			fileList.get(index).setStartTime(startTime);
			isUpdateSuccess = true;
		}
		if (!endDate.equalsIgnoreCase("")) {
			fileList.get(index).setEndDate(endDate);
			isUpdateSuccess = true;
		}
		if (!endTime.equalsIgnoreCase("")) {
			fileList.get(index).setEndTime(endTime);
			isUpdateSuccess = true;
		}
		if (!location.equalsIgnoreCase("")) {
			fileList.get(index).setLocation(location);
			isUpdateSuccess = true;
		}
		if (!remindDate.equalsIgnoreCase("")) {
			fileList.get(index).setRemindDate(remindDate);
			isUpdateSuccess = true;
		}
		if (!priority.equalsIgnoreCase("")) {
			fileList.get(index).setPriority(priority);
			isUpdateSuccess = true;
		}
		
		if (isUpdateSuccess) {
			updateMessage += "\nNew : "+showMessage(fileList.get(index));
			return updateMessage;
		} else {
			return null;
		}
	}
	
	// updates the task with the necessary details only
	public String updateTaskFromAll(int index, String taskDescription,
			String startDate, String startTime, String endDate, String endTime,
			String location, String remindDate, String priority) {
		
		boolean isUpdateSuccess = false;
		
		String updateMessage = "\nOld : ";
		updateMessage += showMessage(fileList.get(index));
		
		if (!taskDescription.equalsIgnoreCase("")) {
			fileList.get(index).setTaskDescription(taskDescription);
			isUpdateSuccess = true;
		}
		if (!startDate.equalsIgnoreCase("")) {
			fileList.get(index).setStartDate(startDate);
			isUpdateSuccess = true;
		}
		if (!startTime.equalsIgnoreCase("")) {
			fileList.get(index).setStartTime(startTime);
			isUpdateSuccess = true;
		}
		if (!endDate.equalsIgnoreCase("")) {
			fileList.get(index).setEndDate(endDate);
			isUpdateSuccess = true;
		}
		if (!endTime.equalsIgnoreCase("")) {
			fileList.get(index).setEndTime(endTime);
			isUpdateSuccess = true;
		}
		if (!location.equalsIgnoreCase("")) {
			fileList.get(index).setLocation(location);
			isUpdateSuccess = true;
		}
		if (!remindDate.equalsIgnoreCase("")) {
			fileList.get(index).setRemindDate(remindDate);
			isUpdateSuccess = true;
		}
		if (!priority.equalsIgnoreCase("")) {
			fileList.get(index).setPriority(priority);
			isUpdateSuccess = true;
		}
		
		if (isUpdateSuccess) {
			updateMessage += "\nNew : "+showMessage(fileList.get(index));
			return updateMessage;
		} else {
			return null;
		}
	}

	public ArrayList<Task> searchByTask(String taskSearch) {
		searchList = new ArrayList<Task>();
		for (int i = 0; i < fileList.size(); i++) {
			Task obj = fileList.get(i);
			if (obj.getTaskDescription().toLowerCase()
					.contains(taskSearch.toLowerCase())) {
				storeSearchResults(obj);
//				System.out.println("Found a entry..");
			}
		}

		return showSearchResults(searchList);
	}

	public ArrayList<Task> showSearchResults(ArrayList<Task> results) {
//		System.out.println("Search : " + results.size() + " results found.");

		if (results.size() == 0) {
			results = null;
		} else {
			for (int i = 0; i<searchList.size(); i++){
//				System.out.println("\"" + searchList.get(i).getTaskDescription() + "\" is found.");
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
	
	public ArrayList<Task> getAllTasks() {
		searchList = fileList;
		return fileList;
	}
	
	}
