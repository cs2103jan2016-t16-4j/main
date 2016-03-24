package application.gui;

import java.io.File;
import java.util.logging.Logger;

import application.logic.Logic;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Shawn
 *
 */

public class GUI extends Application {
	public static GUI gui = null;
	private GUIHandler guiH;
	private TaskListView taskList;

	// Constants
	private static String LOGGER_NAME = "logfile";
	private String title = "Tasker";
	private String logoURL = "application/gui/files/robot.jpg";

	private static Logger logger = Logger.getLogger(LOGGER_NAME);

	// Java Controls
	private TextField txtField = new TextField();
	private Label helpLabel = new Label();
	private Label feedbackLabel = new Label();

	// Alignment
	private VBox root = new VBox();

	@Override
	public void start(Stage primaryStage) throws ExceptionHandler{
		Logic logic = new Logic();
		GUIHandler guiH = new GUIHandler(logic);
		TaskListView taskList = new TaskListView();
		DirectoryChooser dirChooser = new DirectoryChooser();
		configureDirectoryChooser(dirChooser);
		try {
			logger.info("Initialising GUI");
			customiseGUIMenuBar(primaryStage);
			createStartStage(taskList);
			guiH.textFieldSetUp(txtField, taskList, guiH, helpLabel, feedbackLabel);
			guiH.firstLaunchDirectoryPrompt(primaryStage, dirChooser, guiH, taskList);
			show(primaryStage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.severe("Failed to load GUI");
			throw new ExceptionHandler("Failed to load GUI");
		}
	}

	// First screen that users see
	private void createStartStage(TaskListView taskList) {
		addTextFieldToRoot();
		addTaskListToRoot(taskList);
	}

	public void addTextFieldToRoot() {
		root.getChildren().add(feedbackLabel);
		root.getChildren().add(txtField);
		root.getChildren().add(helpLabel);
	}

	public void addTaskListToRoot(TaskListView taskList) {
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
		Scene scene = new Scene(root, 1400, 900);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public TaskListView getTaskListView() {
		return this.taskList;
	}

	// Configuration for directory chooser
	private void configureDirectoryChooser(final DirectoryChooser dirChooser) {
		dirChooser.setTitle("Open Resource Folder");
		dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
	}

	// Change directory
	// public String changeDirectoryPrompt(Stage primaryStage, DirectoryChooser
	// dirChooser, GUIHandler guiH) {
	// final File selectedDirectory = dirChooser.showDialog(primaryStage);
	// if (selectedDirectory != null) {
	// guiH.startDirectoryPrompt(selectedDirectory.getPath().toString() + "\\");
	// } else {
	// guiH.startDirectoryPrompt("");
	// }
	// }

}
