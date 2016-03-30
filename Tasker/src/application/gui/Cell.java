package application.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

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

	// Constants
	private static final int RANDOM_COLOR_INDEX = 3;
	private static final String BLACK_BORDER_FX = "-fx-border-color: black; ";
	private static final String RED_BG_FX = "-fx-background-color: indianred; ";
	private static final String BLUE_BG_FX = "-fx-background-color: blue; ";
	private static final String GREEN_BG_FX = "-fx-background-color: green; ";
	private static final String WHITE_TEXT_FILL_FX = "-fx-text-fill: white; ";

	public Cell(int number, String description, String duration, String location) {

		// Color Randomizer
		int color = number % RANDOM_COLOR_INDEX;

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
			taskNumberLabel.styleProperty().set(RED_BG_FX + WHITE_TEXT_FILL_FX);
			break;
		case 1:
			taskNumberLabel.styleProperty().set(BLUE_BG_FX + WHITE_TEXT_FILL_FX);
			break;
		case 2:
			taskNumberLabel.styleProperty().set(GREEN_BG_FX + WHITE_TEXT_FILL_FX);
			break;
		}

		// Add the details to the box for a full task
		taskBox.getChildren().addAll(taskNumberLabel, taskDescriptionLabel, taskDurationLabel, taskLocationLabel);
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		taskBox.setEffect(dropShadow);
		taskBox.styleProperty().set(BLACK_BORDER_FX);
	}

	public HBox getHBox() {
		return taskBox;
	}

}
