package application.storage;

import java.util.ArrayList;

public class DatabaseManager {
	private int taskIndex = 0;
	private ArrayList<Task> closeList = new ArrayList<Task>();
	private ArrayList<Task> openList = new ArrayList<Task>();
	
	public ArrayList<Task> getCloseList() {
		return closeList;
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
	
	public void updateCloseList(ArrayList<Task> closeList) {
		this.closeList = closeList;
	}
	
	public void updateOpenList(ArrayList<Task> openList) {
		this.openList = openList;
	}

	public int updateTaskIndex() {
		taskIndex++;
		return taskIndex;
	}
}
