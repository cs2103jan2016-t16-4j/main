package application.gui;

import application.logic.Feedback;
import application.logic.Help;
import application.storage.Storage;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelpMessage extends Application {
	//Storage storage;
	//Feedback help = Help.execute(storage) ;
	//String helpMessage = help.getMessage();
	String windowTitle = "Help";
	String helpMessage = "";
	@Override
	public void start(Stage stage) {
		Text text = new Text(10, 40, helpMessage);
		text.setFont(new Font(40));
		Scene scene = new Scene(new Group(text));

		stage.setTitle(windowTitle);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
