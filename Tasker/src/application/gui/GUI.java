package application.gui;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
    private static String LOGGER_NAME = "logfile";
	private String title = "Tasker";
	private String logoURL = "application/gui/files/robot.jpg";
	
    private static Logger logger = Logger.getLogger(LOGGER_NAME);
	
	// Java Controls
	private TextField txtField = new TextField();

	// Alignment
	private VBox root = new VBox();

	@Override
	public void start(Stage primaryStage) {
		try {
			logger.info("Initialising GUI");
			customiseGUIMenuBar(primaryStage);
			createStartStage();
			show(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Failed to load GUI");
		}
	}

	// First screen that users see
	private void createStartStage() {
		addTextFieldToRoot();
		addTaskListToRoot();
	}
	
	public void addTextFieldToRoot() {
		root.getChildren().add(txtField);
	}
	
	public void addTaskListToRoot(){
		TaskListView taskList = new TaskListView();
		taskList.createTaskListView(root);
	}

	private void customiseGUIMenuBar(Stage primaryStage) {
		setProgramName(primaryStage);
		setProgramLogo(primaryStage);
	}

	private void setProgramName(Stage primaryStage) {
		primaryStage.setTitle(title);
	}

	private void setProgramLogo(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(logoURL));
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
