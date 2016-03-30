package application.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

/**
 * 
 * @author Shawn
 *
 */

public class TaskListView {
	private ObservableList<Task> items = FXCollections.observableArrayList();
	private ListView<Task> listView = new ListView<Task>(items);

	// Constants
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
	private static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
	private static final String EMPTY_STRING = "";
	private static final String LOCATION_AT = "At ";
	private static final String BY = "By ";
	private static final String SPACE = " ";
	private static final String EMPTY_DATE = "0001";
	private static final String DATE_TO_DATE = " to ";
	private static final int TASK_NUM_OFFSET = 1;

	private static String LOCATION_INFO = LOCATION_AT + "%1$s";
	private static String DURATION_SAME_DATE_INFO = "%1$s" + SPACE + "%2$s" + DATE_TO_DATE + "%3$s";
	private static String DURATION_DIFF_DATE_INFO = "%1$s" + SPACE + "%2$s" + DATE_TO_DATE + "%3$s" + SPACE + "%4$s";
	private static String DURATION_END_DATE_INFO = BY + "%1$s" + SPACE + "%2$s";

	public void createTaskListView(Pane parent) {
		BorderPane pane = new BorderPane();
		addDataToListView(pane);

		parent.getChildren().add(pane);
	}

	private void addDataToListView(BorderPane pane) {
		listView.setPrefSize(2500, 2500);
		listView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			public ListCell<Task> call(ListView<Task> param) {
				ListCell<Task> cell = new ListCell<Task>() {
					@Override
					public void updateItem(Task item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							int taskNumber = this.getIndex() + TASK_NUM_OFFSET;
							String taskDescription = item.getTaskDescription();
							String taskDuration = setDurationDetails(item);
							String taskLocation = setLocationDetails(item);
							Cell cells = new Cell(taskNumber, taskDescription, taskDuration, taskLocation);

							setGraphic(cells.getHBox());
						} else {
							setGraphic(null);
						}
					}
				};

				return cell;
			}
		});
		pane.setCenter(listView);
	}

	public void updateList(ArrayList<Task> tasks) {

		items.clear();

		for (int i = 0; i < tasks.size(); i++) {
			items.add(tasks.get(i));
		}

		listView.setItems(items);
	}

	private String setDurationDetails(Task item) {
		String taskDuration = EMPTY_STRING;

		if (!FORMAT_YEAR.format(item.getStartDate().getTime()).equals(EMPTY_DATE)) {
			if (FORMAT_DATE.format(item.getStartDate().getTime())
					.equals(FORMAT_DATE.format(item.getEndDate().getTime()))) {
				taskDuration = String.format(DURATION_SAME_DATE_INFO, FORMAT_DATE.format(item.getStartDate().getTime()),
						FORMAT_TIME.format(item.getStartTime().getTime()),
						FORMAT_TIME.format(item.getEndTime().getTime()));
			} else {
				taskDuration = String.format(DURATION_DIFF_DATE_INFO, FORMAT_DATE.format(item.getStartDate().getTime()),
						FORMAT_TIME.format(item.getStartTime().getTime()),
						FORMAT_DATE.format(item.getEndDate().getTime()),
						FORMAT_TIME.format(item.getEndTime().getTime()));
			}
		} else {
			if (!FORMAT_YEAR.format(item.getEndDate().getTime()).equals(EMPTY_DATE)) {
				taskDuration = String.format(DURATION_END_DATE_INFO, FORMAT_DATE.format(item.getEndDate().getTime()),
						FORMAT_TIME.format(item.getEndTime().getTime()));
			} else {
				taskDuration = EMPTY_STRING;
			}
		}
		return taskDuration;
	}

	private String setLocationDetails(Task item) {
		String taskLocation = EMPTY_STRING;

		if (!item.getLocation().equals(EMPTY_STRING)) {
			taskLocation = String.format(LOCATION_INFO, item.getLocation());
		} else {
			taskLocation = EMPTY_STRING;
		}
		return taskLocation;
	}

}
