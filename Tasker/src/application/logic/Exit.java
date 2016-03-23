package application.logic;

import java.util.ArrayList;

import application.storage.Storage;
import application.storage.Task;

/**
 * 
 * @author Shawn
 *
 */

public class Exit implements Command {

	@Override
	public Feedback execute(Storage storage, ArrayList<Task> tasks) {
		System.exit(0);
		return null;
	}
}
