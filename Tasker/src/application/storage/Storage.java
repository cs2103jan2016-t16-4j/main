//@@author A0125522R

package application.storage;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

public class Storage implements Cloneable {

	private static final int UPDATED_CLOSE_LIST = 0;
	private static final int UPDATED_DATE_LIST = 1;
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
			String priority) throws IOException {
	    
		databaseManager.updateOpenList(taskManager.add(databaseManager.getOpenList(), taskDescription,
				startDate, endDate, location, remindDate, priority,
				databaseManager.updateTaskIndex()));
		
		saveFile();
		return databaseManager.getOpenList().get((databaseManager.getOpenList().size())-1);
	}
	
	public Task closeTask(int index) throws IOException {
		ArrayList<ArrayList<Task>> lists = taskManager.close(databaseManager.getCloseList(), databaseManager.getOpenList(), index);
		databaseManager.updateCloseList(lists.get(UPDATED_CLOSE_LIST));
		databaseManager.updateOpenList(lists.get(UPDATED_DATE_LIST));
		saveFile();
		return databaseManager.getCloseList().get((databaseManager.getCloseList().size())-1);
	}
	
	public Task uncloseTask(int index) throws IOException {
		ArrayList<ArrayList<Task>> lists = taskManager.unclose(databaseManager.getCloseList(), databaseManager.getOpenList(), index);
		databaseManager.updateCloseList(lists.get(UPDATED_CLOSE_LIST));
		databaseManager.updateOpenList(lists.get(UPDATED_DATE_LIST));
		saveFile();
		return databaseManager.getOpenList().get((databaseManager.getOpenList().size())-1);
	}	
//@@author A0125522R	
	public Task deleteTask(int index) throws IOException {
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
	
	public boolean directoryExists() throws IOException {
		return fileManager.isDirectoryExists();
	}
	
	public ArrayList<Task> getOpenList() {
		databaseManager.updateOpenList(taskManager.sortDate(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	public ArrayList<Task> getCloseList() {
		databaseManager.updateCloseList(taskManager.sortDate(databaseManager.getCloseList()));
		return databaseManager.getCloseList();
	}
	
	public boolean initialise() throws IOException {
		fileManager.loadDirectoryFile();
		databaseManager.updateCloseList(fileManager.loadFile(fileManager.getClosedFilePath()));
		databaseManager.updateOpenList(fileManager.loadFile(fileManager.getDataFilePath()));
		databaseManager.setTaskIndex(fileManager.loadTaskIndex());
		return true;
	}
	
	private void saveFile() throws IOException {
		fileManager.clear(fileManager.getClosedFilePath());
		fileManager.saveTaskIndex(databaseManager.getTaskIndex());
		fileManager.saveFile(databaseManager.getCloseList(), fileManager.getClosedFilePath());
		fileManager.saveFile(databaseManager.getOpenList(), fileManager.getDataFilePath());
	}
	
	public ArrayList<Task> searchTaskByDate(Calendar date) {
		return taskManager.searchDateBy(databaseManager.getOpenList(), date);
	}
	
	public ArrayList<Task> searchTaskOnDate(Calendar date) {
		return taskManager.searchDateOn(databaseManager.getOpenList(), date);
	}
	
	public ArrayList<Task> searchTaskByName(String taskName) {
		return taskManager.searchName(databaseManager.getOpenList(), taskName);
	}
	
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
	public void setDirectory(String path) throws IOException {
		fileManager.setDirectory(path);
	}
	
	public ArrayList<Task> sortByDate() {
		databaseManager.updateOpenList(taskManager.sortDate(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	public ArrayList<Task> sortByName() {
		databaseManager.updateOpenList(taskManager.sortName(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	public ArrayList<Task> sortByPriority() {
		databaseManager.updateOpenList(taskManager.sortPriority(databaseManager.getOpenList()));
		return databaseManager.getOpenList();
	}
	
	public ArrayList<Task> updateTask(int index, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority) throws IOException, CloneNotSupportedException {
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
	
	private Task cloneObject (Task obj) throws CloneNotSupportedException {
		if (obj instanceof FloatingTask) {
			return (FloatingTask) obj.clone();
		}
		else if (obj instanceof DeadlineTask) {
			return (DeadlineTask) obj.clone();
		} else {
			return (EventTask) obj.clone();
		}
	}
	
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
