package application.logic;

import java.io.IOException;
import java.util.ArrayList;

import application.parser.Command;
import application.parser.Feedback;
import application.parser.Parser;
import application.storage.Storage;
import application.storage.Task;
import application.gui.Ui;
import application.gui.Cli;
import application.gui.GUI;
import application.gui.GUIHandler;

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
    
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private Ui ui = new Cli();
	private GUIHandler guiHandler = new GUIHandler();

	public static void main(String[] args){
        Logic tasker = new Logic();
	    try{
            ArrayList<Task> tasks = tasker.setEnvironment();
            tasker.ui.printWelcomeMessage(tasks);
            tasker.executeCommandsUntilExit();
	    }catch(IOException e){
            tasker.ui.showError(MESSAGE_LOAD_ERROR);
        }
	}
	
	private void executeCommandsUntilExit(){
	    while(true){
	        try{
	            String userCommand = ui.getCommand();
	            Command cmd = parser.interpretCommand(userCommand);
	            Feedback feedback = cmd.execute(storage);
	            feedback.display(ui);
	            storage.saveFile();
	        }catch(Exception e){
	            ui.showError(MESSAGE_ERROR);
	        }
	    }
	}
	
	private ArrayList<Task> setEnvironment() throws IOException{
	    checkIfFileExists();
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
