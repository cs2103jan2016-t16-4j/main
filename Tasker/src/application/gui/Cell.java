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
		taskNumberLabel.setPadding(new Insets(30, 30, 30, 30));
		taskDescriptionLabel.setPadding(new Insets(30, 30, 30, 30));
		taskDurationLabel.setPadding(new Insets(30, 30, 30, 30));
		taskLocationLabel.setPadding(new Insets(30, 30, 30, 30));
		
		// Set the size of the individual tasks details
		taskNumberLabel.setPrefSize(20, 100);
		taskDescriptionLabel.setPrefSize(500, 100);
		taskDurationLabel.setPrefSize(500, 100);
		taskLocationLabel.setPrefSize(200, 100);
		
		// Set the word wrap of the individual tasks details
		taskNumberLabel.setWrapText(true);
		taskDescriptionLabel.setWrapText(true);
		taskDurationLabel.setWrapText(true);
		taskLocationLabel.setWrapText(true);


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
