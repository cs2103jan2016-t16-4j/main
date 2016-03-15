package application.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * 
 * @author Shawn
 *
 */


public class GUI extends Application {
	public static GUI gui = null;
	private GUIHandler guiH;
	
	// Constants
	private String title = "Tasker";
	
	// Alignment
	private VBox root = new VBox();

	@Override
	public void start(Stage primaryStage) {
		try {
			
			show(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void setStartUp(GUI guiParameter) {
		gui = guiParameter;
	}
	
	private void show(Stage primaryStage) {
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
