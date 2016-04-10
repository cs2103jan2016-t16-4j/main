package application.backend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;

import application.logger.LoggerHandler;
import application.storage.EventTask;
import application.storage.Task;

//@@author A0132632R
/**
 * This class can be thought of as the decision maker for the backend package.
 * 
 * @author Pratyush
 *
 */
public class Logic {

	// Constants
	private static final int START_COUNT = 0;
	
	// Initialization
	private Parser parser = new Parser();
	private StorageConnector storageConnector = new StorageConnector();
	private static Logger logger = LoggerHandler.getLog();
	private History history = History.getInstance();

	public Logic() {
	}

	// To enable testing
	protected Logic(StorageConnector storageConnector) {
		this.storageConnector = storageConnector;
	}

	/**
	 * This method should be called to parse and execute user entered commands
	 * (which may be in natural language style).
	 * 
	 * @param command
	 *            A string input of the user command
	 * @param tasksOnScreen
	 *            An ArrayList of the current tasks being displayed
	 * @return A feedback object containing feedback which has been constructed
	 *         based on the execution of that particular command
	 * @throws NoDescriptionException
	 *             This exception is thrown if the Parser encounters that the
	 *             user has not entered a description for a task
	 */

	public Feedback executeCommand(String command, ArrayList<Task> tasksOnScreen) throws NoDescriptionException {
		Feedback feedback;
		Command cmd = parser.interpretCommand(command);
		logger.info("executing above parsed command");
		feedback = cmd.execute(storageConnector, tasksOnScreen);
		logger.info("adding command to history");
		history.add(cmd);
		return feedback;
	}

	/**
	 * This method should be called to get all timing clashes with a particular
	 * task.
	 * 
	 * @param task
	 *            The task you want to check clashes for.
	 * @return A list of tasks that clash with this task, along with the task
	 *         itself.
	 */
	public ArrayList<Task> getClashes (Task task){
	    logger.info("getting all tasks to check for clash");
        ArrayList<Task> openTasks = storageConnector.getOpenList();
        logger.info("initiating arraylist");
        ArrayList<Task> tasksClashing = new ArrayList<Task>();
        logger.info("checking if main task is an event");
        if (!(task instanceof EventTask)){
            logger.info("main task not event. returning task without checking for clashes.");
            tasksClashing.add(task);
            return tasksClashing;
        }
        logger.info("task is an event. looping through all lists to check for clash");
        for (Task taskUnderConsideration : openTasks){
            addIfClashing(tasksClashing, task, taskUnderConsideration);
        }
        return tasksClashing;
    }
    
	//adds taskUnderConsideration to the ArrayList if it clashes with 'task'
    private void addIfClashing(ArrayList<Task> tasksClashing, Task task, Task taskUnderConsideration){
        logger.info("checking if Event Task");
        if (taskUnderConsideration instanceof EventTask){
            logger.info("Getting dates from task under consideration");
            Calendar startDate = taskUnderConsideration.getStartDate();
            Calendar endDate = taskUnderConsideration.getEndDate();
            logger.info("Adding to list if clashing");
            if (endDate.compareTo(task.getStartDate()) > 0 && startDate.compareTo(task.getEndDate()) < 0 ){
                tasksClashing.add(taskUnderConsideration);
            }
        }
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
			if (!(task instanceof EventTask)) {
				assert (task.getEndDate() != null);
				if (task.getEndDate() != null) {
					if (task.getEndDate().getTime().compareTo(cal.getTime()) < 0) {
						overdueCount++;
					}
				}
			} else {
				assert (task.getStartDate() != null);
				if (task.getStartDate() != null) {
					if (task.getStartDate().getTime().compareTo(cal.getTime()) < 0) {
						overdueCount++;
					}
				}
				// if (task.getEndDate() != null) {
				// if (task.getEndDate().getTime().compareTo(cal.getTime()) <
				// COMPARE_OVERDUE_VARIABLE) {
				// overdueCount++;
				// }
				// }
			}

		}
		return overdueCount;
	}
}