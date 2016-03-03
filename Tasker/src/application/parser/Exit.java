package application.parser;

/**
 * 
 * @author Shawn
 *
 */

public class Exit implements Command {

	@Override
	public String execute() {
		System.exit(0);
		return null;
	}
}
