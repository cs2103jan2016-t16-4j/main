package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GUI extends Application {
	private String title = "Tasker";

	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			ObservableList<String> data = FXCollections.observableArrayList();

		    ListView<String> listView = new ListView<String>(data);
		    listView.setPrefSize(900, 900);
		    data.addAll("A", "B", "C", "D", "E");

			setProgramName(primaryStage);
			ScrollPane scrollPane = new ScrollPane();
			scrollPane.setContent(listView);
			TextField titleTextField;
			titleTextField = new TextField();
			VBox cli = new VBox();
			cli.getChildren().addAll(scrollPane,titleTextField);
			Scene scene = new Scene(cli, 800, 800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setProgramName(Stage primaryStage) {
		primaryStage.setTitle(title);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
