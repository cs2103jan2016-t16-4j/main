package application.parser;

import application.storage.Storage;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByName implements Command {
	private Storage storage = new Storage();

	SearchByName(String[] args) {
		storage.searchByTask(args[0]);
	}

	@Override
	public String execute(Storage storage) {
		// TODO Auto-generated method stub
		return null;
	}

}
