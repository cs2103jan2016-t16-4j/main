// @@author A0125417L
package application.logic;

import java.util.ArrayList;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.storage.Task;

/*
 * Exits Program
 */

public class Exit implements Command {

	// Logger Messages
	private static final String LOGGER_EXIT_MSG = "Exiting through exit command";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	@Override
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		logger.info(LOGGER_EXIT_MSG);
		System.exit(0);
		return null;
	}
}