package application.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import application.logger.LoggerHandler;
import application.storage.EventTask;
import application.storage.Task;

//@@author A0132632R

public class Logic {

	private Parser parser = new Parser();
	private StorageConnector storageConnector = new StorageConnector();

	private static Logger logger = LoggerHandler.getLog();

	private History history = History.getInstance();

	private ArrayList<Task> setEnvironment() throws IOException {
		logger.info("Checking if file exists");
		checkIfFileExists();
		logger.info("Loading tasks");
		return loadDataFile();
	}

	// @@author A0125417L
	public void setDirectory(String file) throws IOException {
		storageConnector.setDirectory(file);
	}

	public ArrayList<Task> loadDataFile() throws IOException {
		storageConnector.initialise();
		return storageConnector.getOpenList();
	}

	// if false means user first time starting program
	public boolean checkIfFileExists() throws IOException {
		return storageConnector.directoryExists();
	}

	// for UI

	//@@author A0132632R

	public Feedback executeCommand(String command, ArrayList<Task> tasksOnScreen) throws NoDescriptionException {
		Feedback feedback;
		Command cmd = parser.interpretCommand(command);
		logger.info("executing above parsed command");
		feedback = cmd.execute(storageConnector, tasksOnScreen);
		logger.info("adding command to history");
		history.add(cmd);
		logger.info("saving tasks to file.");
		return feedback;
	}
	
	public ArrayList<Task> getClashes (Task task){
	    ArrayList<Task> openTasks = storageConnector.getOpenList();
	    ArrayList<Task> tasksClashing = new ArrayList<Task>();
	    tasksClashing.add(task);
	    for (Task taskUnderConsideration : openTasks){
	        addIfClashing(tasksClashing, task, taskUnderConsideration);
	    }
	    return tasksClashing;
	}
	
	private void addIfClashing(ArrayList<Task> tasksClashing, Task task, Task taskUnderConsideration){
	    if (taskUnderConsideration instanceof EventTask){
	        Calendar startDate = taskUnderConsideration.getStartDate();
	        Calendar endDate = taskUnderConsideration.getEndDate();
            if (endDate.compareTo(task.getStartDate()) > 0){
                tasksClashing.add(taskUnderConsideration);
            } else if (startDate.compareTo(task.getEndDate()) < 0){
                tasksClashing.add(taskUnderConsideration);
            }
	    }
	}
    // @@author A0125417L

	public int getCompletedTaskCount() {
		return storageConnector.getClosedList().size();
	}

	public int getRemainingTaskCount() {
		int remainingTask = storageConnector.getOpenList().size() - getOverdueTaskCount();
		return remainingTask;
	}

	public int getOverdueTaskCount() {
		int overdueCount = 0;
		ArrayList<Task> taskList = storageConnector.getOpenList();
		Calendar cal = Calendar.getInstance();
		for (Task task : taskList) {
			if (task.getEndDate() != null) {
				if (task.getEndDate().getTime().compareTo(cal.getTime()) < 0) {
					overdueCount++;
				}
			}
		}
		return overdueCount;
	}
	
	

}