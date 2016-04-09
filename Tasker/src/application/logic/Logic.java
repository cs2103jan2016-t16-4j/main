package application.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import application.logger.LoggerHandler;
import application.storage.Task;

//@@author A0132632R

public class Logic {

	// Constants
	private static final int START_COUNT = 0;
	private static final int COMPARE_OVERDUE_VARIABLE = 0;

	// Initialization
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
	// @@author A0132632R

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

	// @@author A0125417L
	// Sets directory of files
	public void setDirectory(String file) throws IOException {
		storageConnector.setDirectory(file);
	}

	// Load current tasks
	public ArrayList<Task> loadDataFile() throws IOException {
		storageConnector.initialise();
		return storageConnector.getOpenList();
	}

	// if false means user first time starting program
	public boolean checkIfFileExists() throws IOException {
		return storageConnector.directoryExists();
	}

	// Returns number of completed tasks
	public int getCompletedTaskCount() {
		return storageConnector.getClosedList().size();
	}

	// Returns number of remaining tasks
	public int getRemainingTaskCount() {
		int remainingTask = storageConnector.getOpenList().size() - getOverdueTaskCount();
		return remainingTask;
	}

	// Returns number of overdue tasks
	public int getOverdueTaskCount() {
		int overdueCount = START_COUNT;
		ArrayList<Task> taskList = storageConnector.getOpenList();
		Calendar cal = Calendar.getInstance();
		overdueCount = countOverdue(overdueCount, taskList, cal);
		return overdueCount;
	}

	// Counts number of overdue tasks
	private int countOverdue(int overdueCount, ArrayList<Task> taskList, Calendar cal) {
		for (Task task : taskList) {
			if (task.getEndDate() != null) {
				if (task.getEndDate().getTime().compareTo(cal.getTime()) < COMPARE_OVERDUE_VARIABLE) {
					overdueCount++;
				}
			}
		}
		return overdueCount;
	}

}