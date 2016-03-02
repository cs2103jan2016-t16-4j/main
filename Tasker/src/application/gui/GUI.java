package application.gui;

import application.logic.Logic;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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

	@Override
	public void start(Stage primaryStage) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource Folder");

			ObservableList<String> data = FXCollections.observableArrayList();

			ListView<String> listView = new ListView<String>(data);
			listView.setPrefSize(900, 900);
			setProgramName(primaryStage);

			ScrollPane scrollPane = addToScrollPane(listView);
			TextField titleTextField = new TextField();
			titleTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
				public void handle(KeyEvent ke) {
					if (ke.getCode().equals(KeyCode.ENTER)) {
						if (titleTextField.getText().equalsIgnoreCase("exit")) {
							System.exit(0);
						} else {
							try {
								data.add(logic.processCommand(titleTextField.getText()));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							titleTextField.clear();
						}
					}
				}
			});
			VBox cli = addToVBox(scrollPane, titleTextField);
			fileChooser.showOpenDialog(primaryStage);
			setStage(primaryStage, cli);
		} catch (Exception e) {
			e.printStackTrace();
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

	public static void main(String[] args) {
		launch(args);
	}
}
