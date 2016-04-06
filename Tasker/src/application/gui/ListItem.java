// @@author A0125417L
package application.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ListItem extends HBox {
	public static final String EMPTY = "";
	public static final String BACKGROUND_STYLE = "-fx-background-color: %1$s;";
	public static final String RED = "#EF9A9A";
	public static final String GREEN = "#A5D6A7";
	public static final String BLUE = "#B2EBF2";

	@FXML
	public Label listNumber;
	@FXML
	private Label taskName;
	@FXML
	private Label date;
	@FXML
	private Label taskLocation;

	public ListItem(int taskNumber, String name, String date, String location, String taskPriority) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListItem.fxml"));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(taskNumber, name, date, location, taskPriority);
		} catch (IOException exception) {
			System.out.println("Could not load");
			throw new RuntimeException(exception);
		}

	}

	private void setLabels(int taskNumber, String name, String date, String location, String taskPriority) {
		listNumber.setText("" + taskNumber);
		setBackground(taskPriority);
		taskName.setText(name.toUpperCase());
		this.date.setText(date.toUpperCase());
		setLocation(location.trim().toUpperCase());
	}

	private void setBackground(String priority) {
		switch (priority) {
		case "high":
			listNumber.setStyle(String.format(BACKGROUND_STYLE, RED));
			break;
		case "medium":
			listNumber.setStyle(String.format(BACKGROUND_STYLE, BLUE));
			break;
		case "low":
			listNumber.setStyle(String.format(BACKGROUND_STYLE, GREEN));
			break;
		}

	}

	private void setLocation(String location) {
		if (!location.equals(EMPTY)) {
			this.taskLocation.setText(location);
		}
	}
}
// @@author A0125417L