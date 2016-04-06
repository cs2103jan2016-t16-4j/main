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
	public Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks) {
		System.exit(0);
		return null;
	}
}
