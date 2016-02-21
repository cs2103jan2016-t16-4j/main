package application.GUI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class GUI extends Application {
	private String title = "Tasker";

	@Override
	public void start(Stage primaryStage) {
		try {
			ListView<String> listView = createList();

			setProgramName(primaryStage);
			ScrollPane scrollPane = addToScrollPane(listView);
			TextField titleTextField = new TextField();
			VBox cli = addToVBox(scrollPane, titleTextField);
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

	private ListView<String> createList() {
		ObservableList<String> data = FXCollections.observableArrayList();

		ListView<String> listView = new ListView<String>(data);
		listView.setPrefSize(900, 900);
		data.addAll("A", "B", "C", "D", "E");
		return listView;
	}

	private void setProgramName(Stage primaryStage) {
		primaryStage.setTitle(title);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
