package application.gui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Shawn
 *
 */

public class TaskListView {
	private ObservableList<HBox> items = FXCollections.observableArrayList();
	private ListView<HBox> listView = new ListView<HBox>(items);

	// Constants
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
	private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
	private static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
	private static final String EMPTY_STRING = "";
	private static final String LOCATION_AT = "@ ";
	private static final String SPACE = " ";
	private static final String EMPTY_DATE = "0001";
	private static final String DATE_TO_DATE = " to ";
	private static final int TASK_NUM_OFFSET = 1;

	private static String LOCATION_INFO = LOCATION_AT + "%1$s";
	private static String DURATION_SAME_DATE_INFO = "%1$s" + SPACE + "%2$s" + DATE_TO_DATE + "%3$s";
	private static String DURATION_DIFF_DATE_INFO = "%1$s" + SPACE + "%2$s" + DATE_TO_DATE + "%3$s" + SPACE + "%4$s";

	public void createTaskListView(Pane parent) {
		BorderPane pane = new BorderPane();
		addDataToListView();
		pane.setCenter(listView);

		parent.getChildren().add(pane);
	}

	private void addDataToListView() {
		ObservableList<HBox> items = FXCollections.observableArrayList();
		listView.setItems(items);
		listView.setPrefSize(2500, 2500);
	}

	public void updateList(ArrayList<Task> tasks) {
		items.clear();
		for (int i = 0; i < tasks.size(); i++) {
			int taskNumber = i + TASK_NUM_OFFSET;
			String taskDescription = tasks.get(i).getTaskDescription();
			String taskDuration = setDurationDetails(tasks, i);
			String taskLocation = setLocationDetails(tasks, i);
			Cell cell = new Cell(taskNumber, taskDescription, taskDuration, taskLocation);
			items.add(cell.getHBox());
		}
		listView.setItems(items);
	}

	private String setDurationDetails(ArrayList<Task> tasks, int i) {
		String taskDuration = EMPTY_STRING;

		if (!FORMAT_YEAR.format(tasks.get(i).getStartDate().getTime()).equals(EMPTY_DATE)) {
			if (FORMAT_DATE.format(tasks.get(i).getStartDate().getTime())
					.equals(FORMAT_DATE.format(tasks.get(i).getEndDate().getTime()))) {
				taskDuration = String.format(DURATION_SAME_DATE_INFO,
						FORMAT_DATE.format(tasks.get(i).getStartDate().getTime()),
						FORMAT_TIME.format(tasks.get(i).getStartTime().getTime()),
						FORMAT_TIME.format(tasks.get(i).getEndTime().getTime()));
			} else {
				taskDuration = String.format(DURATION_DIFF_DATE_INFO,
						FORMAT_DATE.format(tasks.get(i).getStartDate().getTime()),
						FORMAT_TIME.format(tasks.get(i).getStartTime().getTime()),
						FORMAT_DATE.format(tasks.get(i).getEndDate().getTime()),
						FORMAT_TIME.format(tasks.get(i).getEndTime().getTime()));
			}
		} else {
			taskDuration = EMPTY_STRING;
		}
		return taskDuration;
	}

	private String setLocationDetails(ArrayList<Task> tasks, int i) {
		String taskLocation = EMPTY_STRING;
		;
		if (!tasks.get(i).getLocation().equals(EMPTY_STRING)) {
			taskLocation = String.format(LOCATION_INFO, tasks.get(i).getLocation());
		} else {
			taskLocation = EMPTY_STRING;
		}
		return taskLocation;
	}

}
