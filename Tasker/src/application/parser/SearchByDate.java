package application.parser;

import application.storage.Storage;

/**
 * 
 * @author Shawn
 *
 */

public class SearchByDate implements Command {
	private Storage storage = new Storage();

	SearchByDate(String[] args) {
		storage.searchByDate(args[0], true);
	}

	@Override
	public String execute(Storage storage) {
		// TODO Auto-generated method stub
		return null;
	}

}
