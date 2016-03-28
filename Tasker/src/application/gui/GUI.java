package application.gui;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import application.logger.LoggerFormat;
import application.logic.Logic;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
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
	private String logoURL = "files/robot.jpg";

	private static Logger logger = Logger.getLogger(LOGGER_NAME);

	// Java Controls
	private TextField txtField = new TextField();
	private Label helpLabel = new Label();
	private Label feedbackLabel = new Label();
	private BorderPane textFieldHolder = new BorderPane();
	private BorderPane helpLabelHolder = new BorderPane();
	private BorderPane feedbackLabelHolder = new BorderPane();

	// Alignment
	private VBox root = new VBox();

	@Override
	public void start(Stage primaryStage) throws ExceptionHandler, IOException {
		Logic logic = new Logic();
		initializeLogger();
		GUIHandler guiH = new GUIHandler(logic);
		TaskListView taskList = new TaskListView();
		DirectoryChooser dirChooser = new DirectoryChooser();
		configureDirectoryChooser(dirChooser);
		try {
			logger.info("Initialising GUI");
			Platform.setImplicitExit(false);
			customiseGUIMenuBar(primaryStage);
			createStartStage(taskList);
			guiH.textFieldSetUp(txtField, taskList, guiH, helpLabel, feedbackLabel, primaryStage, dirChooser);
			guiH.firstLaunchDirectoryPrompt(primaryStage, dirChooser, guiH, taskList);
			show(primaryStage);
			guiH.createTrayIcon(primaryStage);
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
		feedbackLabelHolder.setLeft(feedbackLabel);
		feedbackLabelHolder.setPadding(new Insets(0, 0, 0, 50));
		root.getChildren().add(feedbackLabelHolder);
		textFieldHolder.setCenter(txtField);
		root.getChildren().add(textFieldHolder);
		helpLabelHolder.setLeft(helpLabel);
		helpLabelHolder.setPadding(new Insets(0, 0, 0, 50));
		root.getChildren().add(helpLabelHolder);
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
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream(logoURL)));
	}

	public static void setStartUp(GUI guiParameter) {
		gui = guiParameter;
	}

	private void show(Stage primaryStage) {
		Scene scene = new Scene(root, 1150, 700);
		scene.getStylesheets().add("application/gui/files/stylesheet.css");
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

	private static void initializeLogger() throws IOException {
		FileHandler fileHandler = new FileHandler("logfile.txt", true);
		LoggerFormat formatter = new LoggerFormat();
		fileHandler.setFormatter(formatter);
		logger.setUseParentHandlers(false);
		logger.addHandler(fileHandler);
	}

}
