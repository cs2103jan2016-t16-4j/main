package application.logic;

import java.io.IOException;

import application.parser.Command;
import application.parser.Parser;
import application.storage.Storage;

/**
 * 
 * @author Shawn
 *
 */

public class Logic {

	private String previousCommand;
	private String feedBack;
	private Parser parser = new Parser();
	private Storage storage = new Storage();
	// private GUI gui = new GUI();

	//
	public String processCommand(String cmd) throws Exception {
		Command command = parser.interpretCommand(cmd);
		String feedback = command.execute(storage);
		storage.saveFile();
		return feedback;
	}

	// Sends directory location back to storage
	public void startDirectoryPrompt(String file) throws IOException {
		storage.loadFile();
	}

	// if false means user first time starting program
	public boolean checkIfFileExists() throws IOException {
		return storage.checkDirectoryFileCreated();
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
