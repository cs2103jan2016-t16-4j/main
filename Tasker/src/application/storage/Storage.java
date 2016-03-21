package application.storage;
import java.io.IOException;
import java.util.Calendar;
import java.util.ArrayList;

public class Storage {

	private DatabaseManager databaseManager;
	private FileManager fileManager;
	private TaskManager taskManager;

	public Storage() {
		databaseManager = new DatabaseManager();
		fileManager = new FileManager();
		taskManager = new TaskManager();		
	}
	
	public Task addTaskInList(String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority) throws IOException {

		databaseManager.updateFileList(taskManager.add(databaseManager.getFileList(), taskDescription,
				startDate, endDate, location, remindDate, priority,
				databaseManager.updateTaskIndex()));
		
		saveFile();
		return databaseManager.getFileList().get((databaseManager.getFileList().size())-1);
	}
	
	public Task closeTask(int index) throws IOException {
		ArrayList<ArrayList<Task>> lists = taskManager.close(databaseManager.getCloseList(), databaseManager.getFileList(), index);
		databaseManager.updateClosedList(lists.get(0));
		databaseManager.updateFileList(lists.get(1));
		saveFile();
		return databaseManager.getCloseList().get((databaseManager.getCloseList().size())-1);
	}
	
	public Task deleteTask(int index) throws IOException {
		Task deletedTask = new Task();
		for (int i = 0; i<databaseManager.getFileList().size(); i++) {
			if (databaseManager.getFileList().get(i).getTaskIndex()==index) {
				deletedTask = databaseManager.getFileList().get(i);
			}
		}
		
		databaseManager.updateFileList(taskManager.delete(databaseManager.getFileList(), index));
		saveFile();
		return deletedTask;
	}
	
	public boolean directoryExists() throws IOException {
		return fileManager.isDirectoryExists();
	}
	
	public ArrayList<Task> getFileList() {
		return databaseManager.getFileList();
	}
	
	public boolean initialise() throws IOException {
		fileManager.loadDirectoryFile();
		databaseManager.updateClosedList(fileManager.loadFile(fileManager.getClosedFilePath()));
		databaseManager.updateFileList(fileManager.loadFile(fileManager.getDataFilePath()));
		databaseManager.setTaskIndex(fileManager.loadTaskIndex());
		return true;
	}
	
	private void saveFile() throws IOException {
		fileManager.saveTaskIndex(databaseManager.getTaskIndex());
		fileManager.saveFile(databaseManager.getCloseList(), fileManager.getClosedFilePath());
		fileManager.saveFile(databaseManager.getFileList(), fileManager.getDataFilePath());
	}
	
	public ArrayList<Task> searchTaskByDate(Calendar date) {
		return taskManager.searchDate(databaseManager.getFileList(), date);
	}
	
	public ArrayList<Task> searchTaskByName(String taskName) {
		return taskManager.searchName(databaseManager.getFileList(), taskName);
	}
	
	public ArrayList<Task> searchTaskByPriority(String priority) {
		return taskManager.searchPriority(databaseManager.getFileList(), priority);
	}
	
	public void setDirectory(String path) throws IOException {
		fileManager.setDirectory(path);
	}
	
	public ArrayList<Task> sortByDate() {
		databaseManager.updateFileList(taskManager.sortDate(databaseManager.getFileList()));
		return databaseManager.getFileList();
	}
	
	public ArrayList<Task> sortByName() {
		databaseManager.updateFileList(taskManager.sortName(databaseManager.getFileList()));
		return databaseManager.getFileList();
	}
	
	public ArrayList<Task> sortByPriority() {
		databaseManager.updateFileList(taskManager.sortPriority(databaseManager.getFileList()));
		return databaseManager.getFileList();
	}
	
	public Task updateTask(int index, String taskDescription, Calendar startDate,
			Calendar endDate, String location, Calendar remindDate,
			String priority) throws IOException {
		databaseManager.updateFileList(taskManager.update(
				databaseManager.getFileList(), taskDescription, startDate,
				endDate, location, remindDate, priority, index));
		
		Task updatedTask = new Task();
		for (int i = 0; i<databaseManager.getFileList().size(); i++) {
			if (databaseManager.getFileList().get(i).getTaskIndex()==index) {
				updatedTask = databaseManager.getFileList().get(i);
			}
		}
		saveFile();
		return updatedTask;
	}

}
