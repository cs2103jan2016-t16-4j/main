// @@author A0125417L
package application.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LoggerHandler {

	private static Logger log;

	private static final String LOGGER_NAME = "logfile";
	private static final String LOGGER_FILE_NAME = "logfile.txt";

	public static Logger getLog() {
		if (log == null) {
			initLog();
		}
		return log;
	}

	private static void initLog() {
		log = Logger.getLogger(LOGGER_NAME);
		FileHandler fileHandler;
		try {
			fileHandler = new FileHandler(LOGGER_FILE_NAME, true);
			LoggerFormat formatter = new LoggerFormat();
			fileHandler.setFormatter(formatter);
			log.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new LoggerException("Failed to initialise Logger");
			} catch (LoggerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
