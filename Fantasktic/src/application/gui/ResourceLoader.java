//@@author A0125417L
package application.gui;

import java.io.InputStream;
import java.util.logging.Logger;
import javax.annotation.Resource;
import application.logger.LoggerHandler;

/*
 * Fixes problem of unable to get certain images and jar file unable to run due to missing image
 */

public class ResourceLoader {
	
	// Constants
	private static final String LOADING_RESOURCE_LOGGER_MSG = "Loading Resource";
	private static final String SLASH = "/";
	
	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	public static InputStream load(String path) {
		logger.info(LOADING_RESOURCE_LOGGER_MSG);
		InputStream input = ResourceLoader.class.getResourceAsStream(path);
		if (input == null) {
			input = Resource.class.getResourceAsStream(SLASH + path);
		}
		return input;
	}
}