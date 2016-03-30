package application.storage;

import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

public class Storage implements Cloneable {

	private DatabaseManager databaseManager;
	public FileManager fileManager;
	private TaskManager taskManager;

	public Storage() {
		databaseManager = new DatabaseManager();
		fileManager = new FileManager();
		taskManager = new TaskManager();		
	}
	
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
		databaseManager.updateClosedList(lists.get(0));
		databaseManager.updateOpenList(lists.get(1));
		saveFile();
		return databaseManager.getCloseList().get((databaseManager.getCloseList().size())-1);
	}
	
	public Task uncloseTask(int index) throws IOException {
		ArrayList<ArrayList<Task>> lists = taskManager.unclose(databaseManager.getCloseList(), databaseManager.getOpenList(), index);
		databaseManager.updateClosedList(lists.get(0));
		databaseManager.updateOpenList(lists.get(1));
		saveFile();
		return databaseManager.getOpenList().get((databaseManager.getOpenList().size())-1);
	}	
	
	public Task deleteTask(int index) throws IOException {
		Task deletedTask = new Task();
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
	
	public boolean initialise() throws IOException {
		fileManager.loadDirectoryFile();
		databaseManager.updateClosedList(fileManager.loadFile(fileManager.getClosedFilePath()));
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
		int taskindex = -1;
		for (int i = 0; i<databaseManager.getOpenList().size(); i++) {
			if (databaseManager.getOpenList().get(i).getTaskIndex()==index) {
				taskindex = i;
				Task newTask1 = (Task) databaseManager.getOpenList().get(i).clone();
				Calendar newStartDate1 = (Calendar) databaseManager.getOpenList().get(i).getStartDate().clone();
				Calendar newEndDate1 = (Calendar) databaseManager.getOpenList().get(i).getEndDate().clone();
				Calendar newRemindDate1 = (Calendar) databaseManager.getOpenList().get(i).getRemindDate().clone();
				newTask1 = new Task(newTask1.getTaskDescription(), newStartDate1, newEndDate1, newTask1.getLocation(), newRemindDate1, newTask1.getPriority(), newTask1.getTaskIndex());
				list.add(newTask1);
				break;
			}
		}
		
		// update task
		databaseManager.updateOpenList(taskManager.update(
				databaseManager.getOpenList(), taskDescription, startDate,
				endDate, location, remindDate, priority, index));
		Task newTask2 = (Task) databaseManager.getOpenList().get(taskindex).clone();
		Calendar newStartDate2 = (Calendar) databaseManager.getOpenList().get(taskindex).getStartDate().clone();
		Calendar newEndDate2 = (Calendar) databaseManager.getOpenList().get(taskindex).getEndDate().clone();
		Calendar newRemindDate2 = (Calendar) databaseManager.getOpenList().get(taskindex).getRemindDate().clone();
		newTask2 = new Task(newTask2.getTaskDescription(), newStartDate2, newEndDate2, newTask2.getLocation(), newRemindDate2, newTask2.getPriority(), newTask2.getTaskIndex());
		list.add(newTask2);
		
		saveFile();
		return list;
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
