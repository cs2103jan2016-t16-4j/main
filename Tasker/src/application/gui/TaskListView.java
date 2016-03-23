package application.gui;

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
			int taskNumber = i + 1;
			String taskDescription = tasks.get(i).getTaskDescription();
			String taskDuration = "";
			if (tasks.get(i).getStartDate().equals(tasks.get(i).getEndDate())) {
				taskDuration = tasks.get(i).getStartDate() + " " + tasks.get(i).getStartTime() + " to "
						+ tasks.get(i).getEndTime();
			} else {
				taskDuration = tasks.get(i).getStartDate() + " " + tasks.get(i).getStartTime() + " to "
						+ tasks.get(i).getEndDate() + " " + tasks.get(i).getEndTime();
			}
			String taskLocation = "@ " + tasks.get(i).getLocation();
			Cell cell = new Cell(taskNumber, taskDescription, taskDuration, taskLocation);
			items.add(cell.getHBox());
		}
		listView.setItems(items);
	}

}
