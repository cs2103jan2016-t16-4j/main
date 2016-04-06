//@@author A0125417L
package application.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class CalendarItem extends AnchorPane {
	public static final String EMPTY = "";
	public static final String RED = "#EF9A9A";
	public static final String GREEN = "#A5D6A7";
	public static final String BLUE = "#B2EBF2";
	public static final String BACKGROUND_COLOR = "-fx-fill: %1$s;";

	@FXML
	private Label taskName;
	@FXML
	private Label date;
	@FXML
	private Label locationLabel;
	@FXML
	private Rectangle rectangle;

	public CalendarItem(String name, String date, String location, String priority) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CalendarItem.fxml"));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(name, date, location, priority);

		} catch (IOException exception) {
			System.out.println("Could not load");
			throw new RuntimeException(exception);
		}

	}

	private void setLabels(String name, String date, String location, String priority) {
		this.taskName.setText(name.toUpperCase());
		this.date.setText(date.toUpperCase());
		setLocation(location.trim().toUpperCase());
		setPriorityColor(priority);
	}

	private void setPriorityColor(String priority) {
		switch (priority) {
		case "high":
			rectangle.setStyle(String.format(BACKGROUND_COLOR, RED));
			break;
		case "medium":
			rectangle.setStyle(String.format(BACKGROUND_COLOR, BLUE));
			break;
		case "low":
			rectangle.setStyle(String.format(BACKGROUND_COLOR, GREEN));
			break;
		default:
			rectangle.setStyle(String.format(BACKGROUND_COLOR, GREEN));
			break;
		}

	}

	private void setLocation(String location) {
		if (!location.equals(EMPTY)) {
			this.locationLabel.setText(location);
		} else {
			this.locationLabel.setText(EMPTY);
		}
	}

}
// @@author A0125417L
