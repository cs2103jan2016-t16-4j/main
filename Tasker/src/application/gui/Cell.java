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

		// Color Randomizer
		int color = number % 3;

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
		taskDurationLabel.setPrefSize(350, 100);
		taskLocationLabel.setPrefSize(200, 100);

		// Set the word wrap of the individual tasks details
		taskNumberLabel.setWrapText(true);
		taskDescriptionLabel.setWrapText(true);
		taskDurationLabel.setWrapText(true);
		taskLocationLabel.setWrapText(true);

		// Set the properties of the individual tasks details
		switch (color) {
		case 0:
			taskNumberLabel.styleProperty().set("-fx-background-color: indianred; -fx-text-fill: white");
			break;
		case 1:
			taskNumberLabel.styleProperty().set("-fx-background-color: blue; -fx-text-fill: white");
			break;
		case 2:
			taskNumberLabel.styleProperty().set("-fx-background-color: green; -fx-text-fill: white");
			break;
		}

		// Add the details to the box for a full task
		taskBox.getChildren().addAll(taskNumberLabel, taskDescriptionLabel, taskDurationLabel, taskLocationLabel);
		taskBox.styleProperty().set("-fx-border-color: black;");
	}

	public HBox getHBox() {
		return taskBox;
	}

}
