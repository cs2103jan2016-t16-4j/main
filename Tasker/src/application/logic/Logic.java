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
    
//	private String previousCommand;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	private Ui ui = new Cli();

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
	private Feedback processCommand(String cmd) throws Exception {
		Command command = parser.interpretCommand(cmd);
		Feedback feedback = command.execute(storage);
		storage.saveFile();
		return feedback;
	}
*/
	// Sends directory location back to storage
	private void startDirectoryPrompt(String file) throws IOException {
		storage.saveDirectory(file);
		loadDataFile();
	}

	private ArrayList<Task> loadDataFile() throws IOException {
	    return storage.loadFile();
	}

	// if false means user first time starting program
	private boolean checkIfFileExists() throws IOException {
		return storage.startUpCheck();
	}

	// Gets the GUI to prompt for a new storage location
	// public String promptNewStorage() throws Exception {
	// gui.dirChooser.showDialog(gui.primaryStage);
	// return feedBack;
	// }

	// public void processCommand(String cmd) {
	// Command command = parser.interpretCommand(cmd);
	// switch (command) {
	// case "add":
	// previousCommand = cmd;
	// task = add(command[1]);
	// feedBack = Storage.add(task);
	// showFeedback(feedBack);
	// break;
	//
	// case "delete":
	// previousCommand = cmd;
	// taskNo = delete(command[1]);
	// showFeedback(Storage.delete(task));
	// break;
	//
	// case "search":
	// previousCommand = cmd;
	// message = search(command[1]);
	// showFeedback(Storage.search(message);
	// break;
	//
	// case "update":
	// previousCommand = cmd;
	// message = update(command[1]);
	// showFeedback(Storage.update(message));
	// break;
	//
	// case "exit":
	// showFeedback(UI.exit);
	// System.exit(0);
	// break;
	//
	// case "undo":
	// undo();
	// previousCommand = cmd;
	// showFeedback(feedBack);
	// break;
	//
	// default:
	//
	// }
	// }
	//
	// private String showFeedback(String feedback) {
	// UI.displayFeedback();
	// }
	//
	// private Task add(String message) {
	// return ParserOld.decipherAdd(message);
	// }
	//
	// private int delete(String message) {
	// return ParserOld.decipherDelete(message);
	// }
	//
	// private String search(String message) {
	// return ParserOld.decipherSearch(message);
	// }
	//
	// private String update(String message) {
	// return ParserOld.decipherUpdate(message);
	// }
	//
	// private void undo() {
	// String revCommand = ParserOld.parseUndoCommand(previousCommand);
	// switch (revCommand) {
	// case "add":
	// task = add(command[1]);
	// feedBack = Storage.delete(taskList.size() - 1);
	// showFeedback(feedBack);
	// break;
	//
	// case "delete":
	// feedBack =
	// Storage.add(previousCommand.substring(previousCommand.indexOf(" ")));
	// showFeedback(feedBack);
	// break;
	//
	// case "search":
	// showFeedback(UI.dispalyClear());
	// break;
	//
	// case "update":
	// // maybe abit tricky to do since we only save last command
	// showFeedback(feedBack);
	// break;
	//
	// default:
	// break;
	//
	// }
	// }

}
