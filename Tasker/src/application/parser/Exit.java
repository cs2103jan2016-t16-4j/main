package application.parser;

import application.storage.Storage;

/**
 * 
 * @author Shawn
 *
 */

public class Exit implements Command {

	@Override
	public String execute(Storage storage) {
		System.exit(0);
		return null;
	}
}
