// @@author A0125417L
package application.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.storage.EventTask;
import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/*
 * Creates a date object item that holds all tasks on the same date
 */

public class DateObject extends HBox {
	// Constants
	private static final int NOT_OVERDUE_VARIABLE = 0;
	private static final String DATE_OBJECT_FXML_URL = "DateObject.fxml";
	private static final String UNDATED_TEXT = "UNDATED";
	public static final String EMPTY = "";
	public static final int OFFSET = 1;

	// Error Messages
	private static final String FXML_LOAD_FAIL_MSG = "Failed to load date object fxml file";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// FXML Variables
	@FXML
	public Label dateLabel;
	@FXML
	public ListView<Task> listViewItem;
	@FXML
	public HBox dateObject;

	public DateObject(String date, ArrayList<Task> taskList, ArrayList<Task> wholeList) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(DATE_OBJECT_FXML_URL));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(date);
			setCellFactoryDateItems(wholeList);
			updateListView(taskList);
		} catch (IOException exception) {
			logger.severe(FXML_LOAD_FAIL_MSG);
			throw new RuntimeException(exception);
		}

	}

	/*
	 *  Setup cell factory
	 */
	private void setCellFactoryDateItems(ArrayList<Task> wholeList) {
		this.listViewItem.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			public ListCell<Task> call(ListView<Task> param) {
				ListCell<Task> cell = new ListCell<Task>() {
					@Override
					public void updateItem(Task item, boolean empty) {
						super.updateItem(item, empty);
						
						if (item != null) {
							int overdueCheck = checkIfOverdue(item);
							CalendarItem calItem = new CalendarItem(item.getTaskDescription(), item.durationToString(),
									item.getLocation(), item.getPriority(), overdueCheck,
									wholeList.indexOf(item) + OFFSET);
							setGraphic(calItem);
						} else {
							assert (item == null);
							setGraphic(null);
						}
					}
				};
				return cell;
			}
		});
	}

	/*
	 * Check if the items are overdue
	 */
	private int checkIfOverdue(Task item) {
		Calendar cal = Calendar.getInstance();
		int overdueCheck = NOT_OVERDUE_VARIABLE;
		if (!(item instanceof EventTask)) {
			overdueCheck = checkNonEventTaskOverdue(item, cal, overdueCheck);
		} else {
			overdueCheck = checkEventTaskOverdue(item, cal, overdueCheck);
		}
		return overdueCheck;
	}

	/*
	 * Check if non event task is overdue
	 */
	private int checkNonEventTaskOverdue(Task item, Calendar cal, int overdueCheck) {
		if (item.getEndDate() != null) {
			overdueCheck = item.getEndDate().getTime().compareTo(cal.getTime());
		}
		return overdueCheck;
	}

	/*
	 * Check if event task is overdue
	 */
	private int checkEventTaskOverdue(Task item, Calendar cal, int overdueCheck) {
		assert (item instanceof EventTask);
		if (item.getStartDate() != null) {
			overdueCheck = item.getStartDate().getTime().compareTo(cal.getTime());
		}
		return overdueCheck;
	}

	/*
	 * Update List View
	 */
	private void updateListView(ArrayList<Task> taskList) {
		ObservableList<Task> list = makeDisplayList(taskList);
		this.listViewItem.setItems(list);
	}

	/*
	 * Adds items to be displayed
	 */
	private ObservableList<Task> makeDisplayList(ArrayList<Task> taskList) {
		ObservableList<Task> displayList = FXCollections.observableArrayList();
		for (Task task : taskList) {
			displayList.add(task);
		}
		return displayList;
	}

	/*
	 * Set date labels
	 */
	private void setLabels(String date) {
		if (date != null) {
			this.dateLabel.setText(date.toUpperCase());
		} else {
			assert (date == null);
			this.dateLabel.setText(UNDATED_TEXT);
		}
	}

	/*
	 * Returns HBox
	 */
	public HBox getHbox() {
		return this.dateObject;
	}

}