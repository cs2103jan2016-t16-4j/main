package application.parser;

import application.storage.Storage;

public class Help implements Command {
	
	public String execute(Storage storage) {
		return "HELP MESSAGE";
	}

}
