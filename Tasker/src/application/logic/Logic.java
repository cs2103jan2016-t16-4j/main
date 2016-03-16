package application.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import application.parser.Command;
import application.parser.Feedback;
import application.parser.Parser;
import application.storage.Storage;
import application.storage.Task;
import application.gui.Ui;
import application.gui.Cli;
import application.gui.GUI;


/**
 * 
 * @author Shawn, Pratyush
 *
 */

public class Logic {

    private static final String MESSAGE_LOAD_ERROR = "There was some problem loading the app. "
            + "Please restart. We regret the inconvenience.";
    private static final String MESSAGE_ERROR = "There was some problem processing your request. "
            + "Please check your input format.";
    private static final String LOGGER_NAME = "logfile";
    
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private Ui ui = new Cli();
	private static Logger logger = Logger.getLogger(LOGGER_NAME);

	public static void main(String[] args) throws IOException{
	    initializeLogger();
	    logger.info("Initialising Logic");
        Logic tasker = new Logic();
	    try{
	        logger.info("Setting Environment");
            ArrayList<Task> tasks = tasker.setEnvironment();
            logger.info("Printing welcome message.");
            tasker.ui.printWelcomeMessage(tasks);
            logger.info("Starting execution loop");
            tasker.executeCommandsUntilExit();
	    }catch(IOException e){
	        logger.info("Printing file input output error.");
	        tasker.ui.showError(MESSAGE_LOAD_ERROR);
        }
	}

    private static void initializeLogger() throws IOException {
        Handler fileHandler = new FileHandler("logfile.txt", true);
	    fileHandler.setFormatter(new SimpleFormatter());
	    logger.setUseParentHandlers(false);
	    logger.addHandler(fileHandler);
    }
	
	private void executeCommandsUntilExit(){
	    while(true){
	        try{
	            logger.info("Getting command from user");
	            String userCommand = ui.getCommand();
	            logger.info("Parsing command: " + userCommand);
	            Command cmd = parser.interpretCommand(userCommand);
	            logger.info("executing above parsed command");
                Feedback feedback = cmd.execute(storage);
                logger.info("displaying feedback");
                feedback.display(ui);
                logger.info("saving tasks to file.");
                storage.saveFile();
	        }catch(Exception e){
	            ui.showError(MESSAGE_ERROR);
	        }
	    }
	}
	
	private ArrayList<Task> setEnvironment() throws IOException{
	    logger.info("Checking if file exists");
	    checkIfFileExists();
	    logger.info("Loading tasks");
        return loadDataFile();
    }
	/*
	public static void main(String[] args) {
		launchGUI();
		Logic tasker = new Logic();
		tasker.setEnvironment();
		tasker.ui.printWelcomeMessage();
		tasker.executeCommandsUntilExit();
	}

	private static void launchGUI() {
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(GUI.class);
			}
		}.start();
	}

	private void executeCommandsUntilExit() {
		while (true) {
			try {
				String userCommand = ui.getCommand();
				Command cmd = parser.interpretCommand(userCommand);
				String feedback = cmd.execute(storage);
				ui.showToUser(feedback);
				storage.saveFile();
			} catch (Exception e) {
				ui.showToUser(MESSAGE_ERROR);
			}
		}
	}

	private void setEnvironment() {
		try {
			checkIfFileExists();
			loadDataFile();
		} catch (IOException e) {
			ui.showToUser(MESSAGE_LOAD_ERROR);
		}
	}

	public Feedback processCommand(String cmd) throws Exception {
		Command command = parser.interpretCommand(cmd);
		Feedback feedback = command.execute(storage);
		storage.saveFile();
		return feedback;
	}
*/
	// Sends directory location back to storage
	public void startDirectoryPrompt(String file) throws IOException {
		storage.saveDirectory(file);
		loadDataFile();
	}

	private ArrayList<Task> loadDataFile() throws IOException {
	    return storage.loadFile();
	}

	// if false means user first time starting program
	public boolean checkIfFileExists() throws IOException {
		return storage.startUpCheck();
	}

}
