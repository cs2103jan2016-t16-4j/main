package application.gui;

import java.io.File;
import java.io.IOException;

import application.logic.Logic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class GUI extends Application {
	private String title = "Tasker";
	private Logic logic = new Logic();
	public DirectoryChooser dirChooser;
	public Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		try {
			DirectoryChooser dirChooser = new DirectoryChooser();
			configureDirectoryChooser(dirChooser);

			ObservableList<String> data = FXCollections.observableArrayList();

			ListView<String> listView = new ListView<String>(data);
			listView.setPrefSize(900, 900);
			setProgramName(primaryStage);

			ScrollPane scrollPane = addToScrollPane(listView);
			TextField titleTextField = new TextField();
			titleTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						try {
							data.add(logic.processCommand(titleTextField.getText()));
							titleTextField.clear();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
			VBox cli = addToVBox(scrollPane, titleTextField);
			firstLaunchDirectoryPrompt(primaryStage, dirChooser);
			setStage(primaryStage, cli);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Checks if save location already exists If exists - use that location If
	 * does not exist then prompt for new location
	 */
	private void firstLaunchDirectoryPrompt(Stage primaryStage, DirectoryChooser dirChooser) throws IOException {
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

	private void setStage(Stage primaryStage, VBox cli) {
		Scene scene = new Scene(cli, 800, 800);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private VBox addToVBox(ScrollPane scrollPane, TextField titleTextField) {
		VBox cli = new VBox();
		cli.getChildren().addAll(scrollPane, titleTextField);
		return cli;
	}

	private ScrollPane addToScrollPane(ListView<String> listView) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(listView);
		return scrollPane;
	}

	private void setProgramName(Stage primaryStage) {
		primaryStage.setTitle(title);
	}

	// Configuration for directory chooser
	private void configureDirectoryChooser(final DirectoryChooser dirChooser) {
		dirChooser.setTitle("Open Resource Folder");
		dirChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
	}

	public static void main(String[] args) {
		launch(args);
	}
}
