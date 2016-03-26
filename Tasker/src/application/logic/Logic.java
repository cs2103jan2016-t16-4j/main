package application.logic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import application.gui.Cli;
import application.gui.GUI;
import application.gui.GUIHandler;
import application.gui.Ui;
import application.logger.LoggerFormat;
import application.storage.Storage;
import application.storage.Task;

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
    private static final String MESSAGE_NO_DESCRIPTION = "You must enter a task description.";
    private static final String LOGGER_NAME = "logfile";
    

	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private Ui ui = new Cli();
	private static Logger logger = Logger.getLogger(LOGGER_NAME);
	//private GUI guiHandler = new GUI();
	private History history = History.getInstance();
	private ArrayList<Task> tasksOnScreen;
	
	public static void main(String[] args) throws IOException{
	    initializeLogger();
	    logger.info("Initialising Logic");
        Logic tasker = new Logic();
	    try{
	        logger.info("Setting Environment");
            tasker.tasksOnScreen = tasker.setEnvironment();
            logger.info("Printing welcome message.");
            tasker.ui.printWelcomeMessage(tasker.tasksOnScreen);
            logger.info("Starting execution loop");
            tasker.executeCommandsUntilExit();
	    }catch(IOException e){
	        logger.info("Printing file input output error.");
	        tasker.ui.showError(MESSAGE_LOAD_ERROR);
        }
	}

    private static void initializeLogger() throws IOException {
        FileHandler fileHandler = new FileHandler("logfile.txt", true);
        LoggerFormat formatter = new LoggerFormat();
	    fileHandler.setFormatter(formatter);
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
                Feedback feedback = cmd.execute(storage, tasksOnScreen);
                logger.info("displaying feedback");
                addCommandToHistoryIfUndoable(cmd);
                tasksOnScreen = feedback.getTasks();
                feedback.display(ui);
                //storage.saveFile();
	        }catch(NoDescriptionException e){
	            ui.showError(MESSAGE_NO_DESCRIPTION);
	        }catch(Exception e){
                ui.showError(MESSAGE_ERROR);
            }
	    }
	}

    private void addCommandToHistoryIfUndoable(Command cmd) {
        if(cmd instanceof UndoableCommand){
            history.add(cmd);
        }
    }
	
	private ArrayList<Task> setEnvironment() throws IOException{
	    logger.info("Checking if file exists");
	    checkIfFileExists();
	    logger.info("Loading tasks");
        return loadDataFile();
    }

	public void startDirectoryPrompt(String file) throws IOException {
		storage.setDirectory(file);
		loadDataFile();
	}

	public ArrayList<Task> loadDataFile() throws IOException {
		storage.initialise();
		return storage.getOpenList();
	}

	// if false means user first time starting program
	public boolean checkIfFileExists() throws IOException {
		return storage.directoryExists();
	}

	// for UI
	public Feedback executeCommand(String command) {
		Feedback feedback = null;
		try {
			Command cmd = parser.interpretCommand(command);
			logger.info("executing above parsed command");
			feedback = cmd.execute(storage, tasksOnScreen);
			logger.info("displaying feedback");
			feedback.display(ui);
			tasksOnScreen = feedback.getTasks();
			logger.info("saving tasks to file.");
			//storage.saveFile();
		} catch (Exception e) {
			ui.showError(MESSAGE_ERROR);
		}
		return feedback;

	}
	
	private static void launchGUI() {
		new Thread() {
			@Override
			public void run() {
				javafx.application.Application.launch(GUI.class);
			}
		}.start();
	}
	
	public String promptNewStorage(){
		return null;
	}


}
