package application.logic;

import application.parser.*;

public class Logic {

	private String previousCommand;
	private String feedBack;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void processCommand(String cmd) {
		CommandKeyword command = ParserOld.getCommandKeywordType(cmd);
		switch (command) {
		case "add":
			previousCommand = cmd;
			task = add(command[1]);
			feedBack = Storage.add(task);
			showFeedback(feedBack);
			break;

		case "delete":
			previousCommand = cmd;
			taskNo = delete(command[1]);
			showFeedback(Storage.delete(task));
			break;

		case "search":
			previousCommand = cmd;
			message = search(command[1]);
			showFeedback(Storage.search(message);
			break;

		case "update":
			previousCommand = cmd;
			message = update(command[1]);
			showFeedback(Storage.update(message));
			break;

		case "exit":
			showFeedback(UI.exit);
			System.exit(0);
			break;

		case "undo":
			undo();
			previousCommand = cmd;
			showFeedback(feedBack);
			break;

		default:

		}
	}

	private String showFeedback(String feedback) {
		UI.displayFeedback();
	}

	private Task add(String message) {
		return ParserOld.decipherAdd(message);
	}

	private int delete(String message) {
		return ParserOld.decipherDelete(message);
	}

	private String search(String message) {
		return ParserOld.decipherSearch(message);
	}

	private String update(String message) {
		return ParserOld.decipherUpdate(message);
	}

	private void undo() {
		String revCommand = ParserOld.parseUndoCommand(previousCommand);
		switch (revCommand) {
		case "add":
			task = add(command[1]);
			feedBack = Storage.delete(taskList.size() - 1);
			showFeedback(feedBack);
			break;

		case "delete":
			feedBack = Storage.add(previousCommand.substring(previousCommand.indexOf(" ")));
			showFeedback(feedBack);
			break;

		case "search":
			showFeedback(UI.dispalyClear());
			break;

		case "update":
			//maybe abit tricky to do since we only save last command
			showFeedback(feedBack);
			break;

		default:
			break;
			
		}
	}

}
