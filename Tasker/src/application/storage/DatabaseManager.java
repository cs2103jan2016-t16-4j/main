package application.storage;

import java.util.ArrayList;

public class DatabaseManager {
	private int taskIndex = 0;
	private ArrayList<Task> closedList = new ArrayList<Task>();
	private ArrayList<Task> openList = new ArrayList<Task>();
	
	public ArrayList<Task> getCloseList() {
		return closedList;
	}
	
	public ArrayList<Task> getOpenList() {
		return openList;
	}
	
	public int getTaskIndex() {
		return taskIndex;
	}
	
	public void setTaskIndex(int i) {
		taskIndex = i;
	}
	
	public void updateClosedList(ArrayList<Task> closedList) {
		this.closedList = closedList;
	}
	
	public void updateOpenList(ArrayList<Task> openList) {
		this.openList = openList;
	}

	public int updateTaskIndex() {
		taskIndex++;
		return taskIndex;
	}
}
