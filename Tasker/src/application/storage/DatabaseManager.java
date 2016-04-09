//@@author A0125522R

package application.storage;

import java.util.ArrayList;

/**
 * DatabaseManager is used to hold the close list (tasks that are done) and open list (current ongoing tasks) for easy access from other components.
 * It also keep track of the total tasks (task index) added in the program.
 */

public class DatabaseManager {
	
	// Lists and task index
	private int taskIndex = 0;
	private ArrayList<Task> closeList = new ArrayList<Task>();
	private ArrayList<Task> openList = new ArrayList<Task>();
	
	/**
	 * Returns the close list (tasks that are done)
	 */
	public ArrayList<Task> getCloseList() {
		return closeList;
	}
	/**
	 * Returns the open list (current ongoing tasks)
	 */
	public ArrayList<Task> getOpenList() {
		return openList;
	}
	
	/**
	 * Returns the current task index count.
	 */
	public int getTaskIndex() {
		return taskIndex;
	}
	
	/**
	 * Set the task index count.
	 */
	public void setTaskIndex(int i) {
		taskIndex = i;
	}
	
	/**
	 * Updates the close list with the new close list.
	 */
	public void updateCloseList(ArrayList<Task> closeList) {
		this.closeList = closeList;
	}
	
	/**
	 * Updates the open list with the new open list.
	 */
	public void updateOpenList(ArrayList<Task> openList) {
		this.openList = openList;
	}

	/**
	 * Increase the task count.
	 */
	public int updateTaskIndex() {
		taskIndex++;
		return taskIndex;
	}
}
