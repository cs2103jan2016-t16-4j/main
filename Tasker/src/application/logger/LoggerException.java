//@@author A0125417L
package application.logger;

/*
 * Handles exceptions of logger package
 */

public class LoggerException extends Exception {
	
	private static final long serialVersionUID = -3499023272346800696L;

	public LoggerException(String message) {
		super(message);
	}

}
