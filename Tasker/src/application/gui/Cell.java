package application.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * 
 * @author Shawn
 *
 */

public class Cell {

	HBox taskBox = new HBox();

	Label taskNumberLabel;
	Label taskDescriptionLabel;
	Label taskDurationLabel;
	Label taskLocationLabel;

	public Cell(int number, String description, String duration, String location) {

		// Set the text of the individual tasks details
		taskNumberLabel = new Label(Integer.toString(number));
		taskDescriptionLabel = new Label(description);
		taskDurationLabel = new Label(duration);
		taskLocationLabel = new Label(location);

		// Set the padding of the individual tasks details
		taskNumberLabel.setPadding(new Insets(10, 50, 50, 50));
		taskDescriptionLabel.setPadding(new Insets(10, 50, 50, 50));
		taskDurationLabel.setPadding(new Insets(10, 50, 50, 50));
		taskLocationLabel.setPadding(new Insets(10, 50, 50, 50));

		// Set the properties of the individual tasks details
		taskNumberLabel.styleProperty().set("-fx-border-color: black;");
		taskDescriptionLabel.styleProperty().set("-fx-border-color: black;");
		taskDurationLabel.styleProperty().set("-fx-border-color: black;");
		taskLocationLabel.styleProperty().set("-fx-border-color: black;");

		// Add the details to the box for a full task
		taskBox.getChildren().addAll(taskNumberLabel, taskDescriptionLabel, taskDurationLabel, taskLocationLabel);
	}

	public HBox getHBox() {
		return taskBox;
	}

}
