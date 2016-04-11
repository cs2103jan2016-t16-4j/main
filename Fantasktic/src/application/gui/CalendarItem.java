//@@author A0125417L
package application.gui;

import java.io.IOException;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

/*
 * Creates a calendar item box for each task
 */

public class CalendarItem extends AnchorPane {

	// Exception Messages
	private static final String FXML_LOAD_FAIL_MSG = "Failed to load calendar item fxml file";

	// Constants
	private static final String LOW = "low";
	private static final String MEDIUM = "medium";
	private static final String HIGH = "high";
	private static final int overdueCheckVariable = 0;
	private static final String CALENDAR_ITEM_FXML_URL = "CalendarItem.fxml";
	private static final String EMPTY = "";

	// Formatting
	private static final String RED = "#EF9A9A";
	private static final String GREEN = "#A5D6A7";
	private static final String BLUE = "#B2EBF2";
	private static final String BACKGROUND_COLOR = "-fx-fill: %1$s;";
	private static final String FONT_STYLE = "-fx-text-fill: %1$s;";
	private static final String DARK_RED = "#B71C1C";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// FXML variables
	@FXML
	private Label taskName;
	@FXML
	private Label date;
	@FXML
	private Label locationLabel;
	@FXML
	private Rectangle rectangle;
	@FXML
	private Label indexLabel;

	public CalendarItem(String name, String date, String location, String priority, int overdueCheck, int index) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(CALENDAR_ITEM_FXML_URL));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(name, date, location, priority, overdueCheck, index);
		} catch (IOException exception) {
			logger.severe(FXML_LOAD_FAIL_MSG);
			throw new RuntimeException(exception);
		}

	}

	/*
	 *  Sets all labels for the calendar item
	 */
	private void setLabels(String name, String date, String location, String priority, int overdueCheck, int index) {
		this.taskName.setText(name.toUpperCase());
		this.date.setText(date.toUpperCase());
		this.indexLabel.setText(EMPTY + index);
		setLocation(location.trim().toUpperCase());
		setPriorityColor(priority);
		overdueCheck(overdueCheck);
	}

	/*
	 *  Checks if task is overdue
	 */
	private void overdueCheck(int overdueCheck) {
		if (overdueCheck < overdueCheckVariable) {
			setOverdueFormat();
		}
	}

	/*
	 *  Formats for overdue tasks
	 */
	private void setOverdueFormat() {
		rectangle.setStyle(String.format(BACKGROUND_COLOR, DARK_RED));
		this.date.setStyle(this.date.getStyle() + String.format(FONT_STYLE, DARK_RED));
	}

	/*
	 *  Sets the color of the rectangle by priority
	 */
	private void setPriorityColor(String priority) {
		switch (priority) {
		case HIGH:
			rectangle.setStyle(String.format(BACKGROUND_COLOR, RED));
			break;
		case MEDIUM:
			rectangle.setStyle(String.format(BACKGROUND_COLOR, BLUE));
			break;
		case LOW:
			rectangle.setStyle(String.format(BACKGROUND_COLOR, GREEN));
			break;
		default:
			rectangle.setStyle(String.format(BACKGROUND_COLOR, GREEN));
			break;
		}

	}

	/*
	 *  Sets the location labels accordingly
	 */
	private void setLocation(String location) {
		if (!location.equals(EMPTY)) {
			this.locationLabel.setText(location);
		} else {
			assert (location.equals(EMPTY));
			this.locationLabel.setText(EMPTY);
		}
	}

}
