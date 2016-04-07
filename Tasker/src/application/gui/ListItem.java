// @@author A0125417L
package application.gui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ListItem extends HBox {
	public static final String EMPTY = "";
	public static final String BACKGROUND_STYLE = "-fx-background-color: %1$s;";
	public static final String FONT_STYLE = "-fx-text-fill: %1$s;";
	public static final String RED = "#EF9A9A";
	public static final String GREEN = "#A5D6A7";
	public static final String BLUE = "#B2EBF2";
	public static final String DARK_RED = "#B71C1C";

	@FXML
	public Label listNumber;
	@FXML
	private Label taskName;
	@FXML
	private Label date;
	@FXML
	private Label taskLocation;

	public ListItem(int taskNumber, String name, String date, String location, String taskPriority, int overdueCheck) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListItem.fxml"));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(taskNumber, name, date, location, taskPriority, overdueCheck);
		} catch (IOException exception) {
			System.out.println("Could not load");
			throw new RuntimeException(exception);
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

	private void overdueCheck(int overdueCheck) {
		if (overdueCheck < 0) {
			listNumber.setStyle(String.format(BACKGROUND_STYLE, DARK_RED));
			this.date.setStyle(this.date.getStyle() + String.format(FONT_STYLE, DARK_RED));
		}
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
		default:
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