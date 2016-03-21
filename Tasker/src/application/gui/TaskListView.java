package application.gui;

import java.util.ArrayList;

import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * 
 * @author Shawn
 *
 */

public class TaskListView {
	private ObservableList<String> items = FXCollections.observableArrayList();
	private ListView<String> listView = new ListView<String>(items);

	public void createTaskListView(Pane parent) {
		BorderPane pane = new BorderPane();
		addDataToListView();
		pane.setCenter(listView);

		parent.getChildren().add(pane);
	}

	private void addDataToListView() {
		ObservableList<String> items = FXCollections.observableArrayList();
		listView.setItems(items);
		listView.setPrefSize(2500, 2500);
	}

	public void addToList(String input) {
		items.add(input);
		listView.setItems(items);
	}

	public void updateList(ArrayList<Task> tasks) {
		items.clear();
		String[] taskList = new String[tasks.size()];
		for (int i = 0; i < tasks.size(); i++) {
			taskList[i] = tasks.get(i).getTaskDescription();
		}
		items.addAll(taskList);
		listView.setItems(items);
	}

}
