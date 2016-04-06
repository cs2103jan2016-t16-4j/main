// @@author A0125417L
package application.gui;

import java.io.IOException;
import java.util.ArrayList;

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

public class DateObject extends HBox {
	public static final String EMPTY = "";

	@FXML
	public Label dateLabel;
	@FXML
	public ListView<Task> listViewItem;
	@FXML
	public HBox dateObject;

	public DateObject(String date, ArrayList<Task> taskList) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DateObject.fxml"));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(date);
			ObservableList<Task> list = FXCollections.observableArrayList();
			this.listViewItem.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
				public ListCell<Task> call(ListView<Task> param) {
					ListCell<Task> cell = new ListCell<Task>() {
						@Override
						public void updateItem(Task item, boolean empty) {
							super.updateItem(item, empty);
							if (item != null) {
								CalendarItem calItem = new CalendarItem(item.getTaskDescription(),
										item.durationToString(), item.getLocation(), item.getPriority());
								setGraphic(calItem);
							} else {
								setGraphic(null);
							}
						}
					};

					return cell;
				}
			});
			updateListView(taskList);
		} catch (IOException exception) {
			System.out.println("Could not load");
			throw new RuntimeException(exception);
		}

	}

	private void updateListView(ArrayList<Task> taskList) {
		ObservableList<Task> list = makeDisplayList(taskList);
		this.listViewItem.setItems(list);
	}

	private ObservableList<Task> makeDisplayList(ArrayList<Task> taskList) {
		ObservableList<Task> displayList = FXCollections.observableArrayList();
		for (Task task : taskList) {
			displayList.add(task);
		}
		return displayList;
	}

	private void setLabels(String date) {
		this.dateLabel.setText(date.toUpperCase());
	}

	public HBox getHbox() {
		return this.dateObject;
	}

}
// @@author A0125417L