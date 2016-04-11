// @@author A0125417L
package application.gui;

import java.io.IOException;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/*
 * Creates an item for each task
 */

public class ListItem extends HBox {

	// Error Messages
	private static final String FXML_LOAD_FAILED_MSG = "Failed to load list item fxml file";

	// Constants
	private static final String LIST_ITEM_FXML_URL = "ListItem.fxml";
	private static final String EMPTY = "";
	private static final String LOW = "low";
	private static final String MEDIUM = "medium";
	private static final String HIGH = "high";
	private static final int overdueCheckVariable = 0;

	// Formatting
	// @@author A0132632R
	private static final String BACKGROUND_STYLE = "-fx-background-color: %1$s;";
	private static final String FONT_STYLE = "-fx-text-fill: %1$s;";
	private static final String RED = "#EF9A9A";
	private static final String GREEN = "#A5D6A7";
	private static final String BLUE = "#B2EBF2";
	private static final String DARK_RED = "#B71C1C";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// FXML variables
	@FXML
	public Label listNumber;
	@FXML
	private Label taskName;
	@FXML
	private Label date;
	@FXML
	private Label taskLocation;

	public ListItem(int taskNumber, String name, String date, String location, String taskPriority, int overdueCheck) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LIST_ITEM_FXML_URL));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(taskNumber, name, date, location, taskPriority, overdueCheck);
		} catch (IOException exception) {
			logger.severe(FXML_LOAD_FAILED_MSG);
			try {
				throw new ExceptionHandler(FXML_LOAD_FAILED_MSG);
			} catch (ExceptionHandler e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void setLabels(int taskNumber, String name, String date, String location, String taskPriority,
			int overdueCheck) {
		listNumber.setText(EMPTY + taskNumber);
		setBackground(taskPriority);
		taskName.setText(name.toUpperCase());
		this.date.setText(date.toUpperCase());
		setLocation(location.trim().toUpperCase());
		overdueCheck(overdueCheck);
	}

	private void setLocation(String location) {
		if (!location.equals(EMPTY)) {
			this.taskLocation.setText(location);
		}
	}

	// @@author A0125417L

	/*
	 * Checks if the task is overdue
	 */
	private void overdueCheck(int overdueCheck) {
		if (overdueCheck < overdueCheckVariable) {
			setOverdueItems();
		}
	}

	/*
	 * Formats list item accordingly to overdue format
	 */
	private void setOverdueItems() {
		listNumber.setStyle(String.format(BACKGROUND_STYLE, DARK_RED));
		this.date.setStyle(this.date.getStyle() + String.format(FONT_STYLE, DARK_RED));
	}

	/*
	 * Set color of background color of number according to priority
	 */
	private void setBackground(String priority) {
		switch (priority) {
		case HIGH:
			listNumber.setStyle(String.format(BACKGROUND_STYLE, RED));
			break;
		case MEDIUM:
			listNumber.setStyle(String.format(BACKGROUND_STYLE, BLUE));
			break;
		case LOW:
			listNumber.setStyle(String.format(BACKGROUND_STYLE, GREEN));
			break;
		default:
			listNumber.setStyle(String.format(BACKGROUND_STYLE, GREEN));
			break;
		}

	}
}