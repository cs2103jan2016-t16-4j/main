// @@author A0125417L
package application.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/*
 * Formats the log
 */

public class LoggerFormat extends Formatter {

	// Text Constants
	private static final String HEADER = " Start ";
	private static final String TAIL = "  End  ";
	private static final String PADDING = " ---------------------- ";
	private static final String SEPARATOR = " - ";
	private static final String NEXT_LINE = "\n";

	// Formatting Constants
	private static final String NUMBER_OF_LETTERS = "%-7s";
	private static final String DATE_FORMAT = "dd.MM.yyyy hh:mm:ss";
	private static final DateFormat DF = new SimpleDateFormat(DATE_FORMAT);

	@Override
	public String format(LogRecord log) {
		String date = DF.format(new Date(log.getMillis()));
		String className = log.getSourceClassName();
		String methodName = log.getSourceMethodName();
		String level = String.format(NUMBER_OF_LETTERS, log.getLevel().toString());
		String message = formatMessage(log);
		String logMessage = getMessage(date, className, methodName, level, message);
		return logMessage;
	}

	// Sets each message of the log
	private String getMessage(String date, String className, String methodName, String level, String message) {
		String logMessage = date + SEPARATOR + level + SEPARATOR + className + SEPARATOR + methodName + SEPARATOR
				+ message + NEXT_LINE;
		return logMessage;
	}

	public String getHead(Handler handler) {
		String logHead = PADDING + HEADER + new Date() + PADDING + NEXT_LINE;
		return logHead;
	}

	public String getTail(Handler handler) {
		String logTail = PADDING + TAIL + new Date() + PADDING + NEXT_LINE;
		return logTail;
	}

}