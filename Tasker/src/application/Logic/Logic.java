package application.Logic;

import application.Parser.Parser;
import application.Storage.Storage;

public class Logic {

	private String previousCommand;
	private String feedBack;
	private Parser parser;
	private Storage storage;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

//	private void processCommand(String cmd){
//		Command command = parser.parseCommand(cmd);
//		String[] commandDetails = command.getDetails();
//	    switch (command.getCommand()){
//			case "add":
//			previousCommand = cmd;
//					task = add(command[1]); //THESE ARE ALL MAGIC NUMBERS. WE NEEED TO CHANGE. AND GET FROM THE COMMAND OBJECT.
//					feedBack = Storage.add(task);
//					showfeedback(feedBack);
//			break;
//			case "delete":
//			previousCommand = cmd;
//					showfeedback(feedBack);
//			break;
//			case "search":
//			previousCommand = cmd;
//					showfeedback(feedBack);
//			break;
//			case "update":
//			previousCommand = cmd;
//			showfeedback(feedBack);
//			break;
//			case "exit":
//					showfeedback(feedBack);
//			System.exit(0);
//			break;
//			case "undo":
//			undo();
//			case default:
//			previousCommand = cmd;
//					task = add(command[1]);
//					storage.add(task);
//					showfeedback(feedBack);
//		}
//	}
//
//	private String showFeedback(String feedback) {
//		UI.displayFeedback();
//	}
//
//	private Task add(String message) {
//		Parser.decipherAdd(message);
//	}
//
//	private void undo() {
//		Parser.Storage.undo();
//	}

}
