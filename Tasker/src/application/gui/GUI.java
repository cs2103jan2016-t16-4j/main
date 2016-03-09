package application.gui;

import java.io.File;
import java.io.IOException;

import application.logic.Logic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * 
 * @author Shawn
 *
 */

public class GUI extends Application {
	private String title = "Tasker";
	public static GUI gui = null;
	Logic logic = new Logic();
	private DirectoryChooser dirChooser;
	private Stage primaryStage = new Stage();
	private HBox root = new HBox(); // declare as root
	private VBox leftWing = new VBox();
	private VBox rightWing = new VBox();
	private TextArea txtArea = new TextArea();
	private TextField txtField = new TextField();
	private ObservableList<String> items = FXCollections.observableArrayList();
	private ListView<String> listView = new ListView<String>(items);

	public static void setStartUpTest(GUI gui0) {
		gui = gui0;
	}

	public GUI() {
		setStartUpTest(this);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			setProgramName();
			configureTextField();
			addListViewToRight();
			addLabelCliToLeft();
			addTextFieldToRight();
			addDataToListView();
			addAllChildrenToRoot();
			setScaleForComponents();
			configureDirectoryChooser();
			firstLaunchDirectoryPrompt();
			show(primaryStage);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setProgramName() {
		primaryStage.setTitle(title);
	}

	// Configuration for directory chooser
	private void configureDirectoryChooser() throws IOException {
		DirectoryChooser dirChooser = new DirectoryChooser();
		dirChooser.setTitle("Open Resource Folder");
		dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
	}

	private void configureTextField() {
		txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						items.add(logic.processCommand(txtField.getText()));
						txtField.clear();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void firstLaunchDirectoryPrompt() throws IOException {
		if (!logic.checkIfFileExists()) {
			final File selectedDirectory = dirChooser.showDialog(primaryStage);
			if (selectedDirectory != null) {
				logic.startDirectoryPrompt(selectedDirectory.getPath().toString() + "\\");
			} else {
				logic.startDirectoryPrompt("");
			}
		} else {
			logic.loadDataFile();
		}
	}

	private void addTextFieldToRight() {
		rightWing.getChildren().add(txtField);
	}

	private void show(Stage primaryStage) {
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void addAllChildrenToRoot() {
		root.getChildren().addAll(leftWing, rightWing);
	}

	private void addDataToListView() {
		ObservableList<String> items = FXCollections.observableArrayList();
		listView.setItems(items);
	}

	private void addLabelCliToLeft() {
		rightWing.getChildren().add(txtArea);

	}

	private void addListViewToRight() {
		leftWing.getChildren().add(listView);
	}

	private void setScaleForComponents() {
		listView.setPrefSize(2500, 2500);
		txtArea.setPrefSize(2500, 2500);
		txtField.setPrefSize(2500, 100);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}