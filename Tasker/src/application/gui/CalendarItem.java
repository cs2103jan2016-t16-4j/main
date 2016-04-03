package application.gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CalendarItem extends AnchorPane {
	public static final String EMPTY = "";

	@FXML
	private Label taskName;
	@FXML
	private Label date;
	@FXML
	private Label location;

	public CalendarItem(String name, String date, String location) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CalendarItem.fxml"));
		try {
			fxmlLoader.setRoot(this);
			fxmlLoader.setController(this);
			fxmlLoader.load();
			this.setLabels(name, date, location);
			
		} catch (IOException exception) {
			System.out.println("Could not load");
			throw new RuntimeException(exception);
		}

	}

	private void setLabels(String name, String date, String location) {
		this.taskName.setText(name.toUpperCase());
		this.date.setText(date.toUpperCase());
		setLocation(location.trim().toUpperCase());
	}

	private void setLocation(String location) {
		if (!location.equals(EMPTY)) {
			this.location.setText(location);
		}else{
			this.location.setText(EMPTY);
		}
	}

}
