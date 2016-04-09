//@@author A0125522R

package application.storage;

import java.util.Calendar;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Storage is implemented with facade pattern which acts as an simple interface to
 * components who wish to utilize its task manipulation function.
 */
public class Storage implements Cloneable {

	// Constants
	private static final int UPDATED_CLOSE_LIST = 0;
	private static final int UPDATED_DATE_LIST = 1;
	
	// Managers
	private DatabaseManager databaseManager;
	private FileManager fileManager;
	private TaskManager taskManager;
	
	public Storage() {
		databaseManager = new DatabaseManager();
		fileManager = new FileManager();
		taskManager = new TaskManager();		
	}
//@@author A0110422E
	public Task addTaskInList(String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority) {
	    
		databaseManager.updateOpenList(taskManager.add(databaseManager.getOpenList(), taskDescription,
				startDate, endDate, location, remindDate, priority,
				databaseManager.updateTaskIndex()));
		saveFile();
		return databaseManager.getOpenList().get((databaseManager.getOpenList().size())-1);
	}
	
	public Task closeTask(int index) {
		ArrayList<ArrayList<Task>> lists = taskManager.close(databaseManager.getCloseList(), databaseManager.getOpenList(), index);
		databaseManager.updateCloseList(lists.get(UPDATED_CLOSE_LIST));
		databaseManager.updateOpenList(lists.get(UPDATED_DATE_LIST));
		saveFile();
		return databaseManager.getCloseList().get((databaseManager.getCloseList().size())-1);
	}
	
	public Task uncloseTask(int index) {
		ArrayList<ArrayList<Task>> lists = taskManager.unclose(databaseManager.getCloseList(), databaseManager.getOpenList(), index);
		databaseManager.updateCloseList(lists.get(UPDATED_CLOSE_LIST));
		databaseManager.updateOpenList(lists.get(UPDATED_DATE_LIST));
		saveFile();
		return databaseManager.getOpenList().get((databaseManager.getOpenList().size())-1);
	}	
//@@author A0125522R	
	/**
	 * Delete a task in the open list with the specified task index and save.
	 */
	public Task deleteTask(int index) {
		Task deletedTask = null;
		for (int i = 0; i<databaseManager.getOpenList().size(); i++) {
			if (databaseManager.getOpenList().get(i).getTaskIndex()==index) {
				deletedTask = databaseManager.getOpenList().get(i);
			}
		}
		
		databaseManager.updateOpenList(taskManager.delete(databaseManager.getOpenList(), index));
		saveFile();
		return deletedTask;
	}
	
	/**
	 * Checks if the directory file exist.
	 */
	public boolean directoryExists() {
		return fileManager.isDirectoryExists();
	}
	
	/**
	 * Gets the updated open list (ongoing tasks).
	 */
	public ArrayList<Task> getOpenList() {
		databaseManager.updateOpenList(taskManager.sortDate(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	/**
	 * Gets the updated close list (tasks done).
	 */
	public ArrayList<Task> getCloseList() {
		databaseManager.updateCloseList(taskManager.sortDate(databaseManager.getCloseList()));
		return databaseManager.getCloseList();
	}
	
	/**
	 * Initialise the Storage by loading the necessary components for task functions.
	 */
	public boolean initialise() {
		fileManager.loadDirectoryFile();
		databaseManager.updateCloseList(fileManager.loadFile(fileManager.getClosedFilePath()));
		databaseManager.updateOpenList(fileManager.loadFile(fileManager.getDataFilePath()));
		databaseManager.setTaskIndex(fileManager.loadTaskIndex());
		return true;
	}
	
	/**
	 * Saves all current data into file.
	 */
	private void saveFile() {
		fileManager.clear(fileManager.getClosedFilePath());
		fileManager.saveTaskIndex(databaseManager.getTaskIndex());
		fileManager.saveFile(databaseManager.getCloseList(), fileManager.getClosedFilePath());
		fileManager.saveFile(databaseManager.getOpenList(), fileManager.getDataFilePath());
	}
	
	/**
	 * Search the tasks by a specified date.
	 */
	public ArrayList<Task> searchTaskByDate(Calendar date) {
		return taskManager.searchDateBy(databaseManager.getOpenList(), date);
	}
	
	/**
	 * Search the tasks on a specified date.
	 */
	public ArrayList<Task> searchTaskOnDate(Calendar date) {
		return taskManager.searchDateOn(databaseManager.getOpenList(), date);
	}
	
	/**
	 * Search the tasks by a specified name.
	 */
	public ArrayList<Task> searchTaskByName(String taskName) {
		return taskManager.searchName(databaseManager.getOpenList(), taskName);
	}
	
	/**
	 * Search the tasks by a specified priority.
	 */
	public ArrayList<Task> searchTaskByPriority(String priority) {
		return taskManager.searchPriority(databaseManager.getOpenList(), priority);
	}
//@@author A0110422E
/*	
	public ArrayList<Task> searchTaskByCategoryType(String categoryType) {
		return taskManager.searchCategoryType(databaseManager.getOpenList(), categoryType);
	}
*/
//@@author A0125522R	
	/**
	 * Sets the directory path to hold the data files (open and close list).
	 */
	public String setDirectory(String path) {
		String originalDirectoryPath = fileManager.getDirectoryPath();
		fileManager.setDirectory(path);
		return originalDirectoryPath;
	}
	
	/**
	 * Sorts the open list (ongoing tasks) by ascending end date.
	 */
	public ArrayList<Task> sortByDate() {
		databaseManager.updateOpenList(taskManager.sortDate(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	/**
	 * Sorts the open list (ongoing tasks) by ascending task name.
	 */
	public ArrayList<Task> sortByName() {
		databaseManager.updateOpenList(taskManager.sortName(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	/**
	 * Sorts the open list (ongoing tasks) by priority (high to low)
	 */
	public ArrayList<Task> sortByPriority() {
		databaseManager.updateOpenList(taskManager.sortPriority(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	/**
	 * Update a task and save the file.
	 */
	public ArrayList<Task> updateTask(int index, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority) {
		ArrayList<Task> list = new ArrayList<Task>();
		
		// add original/old task 
		int taskIndex = -1;
		for (int i = 0; i<databaseManager.getOpenList().size(); i++) {
			if (databaseManager.getOpenList().get(i).getTaskIndex()==index) {
				taskIndex = i;
				list.add(cloneObject(databaseManager.getOpenList().get(i)));
				break;
			}
		}
		
		// update task
		databaseManager.updateOpenList(taskManager.update(
				databaseManager.getOpenList(), taskDescription, startDate,
				endDate, location, remindDate, priority, index));
		list.add(databaseManager.getOpenList().get(taskIndex));
		
		saveFile();
		return list;
	}
	
	/**
	 * Clones a Task object.
	 */
	private Task cloneObject (Task obj) {
		try {
			if (obj instanceof FloatingTask) {
				return (FloatingTask) obj.clone();
			}
			if (obj instanceof DeadlineTask) {
				return (DeadlineTask) obj.clone();
			} else {
				return (EventTask) obj.clone();
			}
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
	
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
