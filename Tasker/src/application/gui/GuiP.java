package application.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GuiP extends Application{
    private static final String WINDOW_TITLE = "Tasker"; 
    
    
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(WINDOW_TITLE);
        HBox item = (HBox) FXMLLoader.load(GuiP.class.getResource("ListItem.fxml"));
        System.out.println("HEE");
        //hbox.setLabels(1, "Hello", "", "");
        //AnchorPane page = (AnchorPane) FXMLLoader.load(GuiP.class.getResource("ListView.fxml"));
        
        
        
        //Scene scene = new Scene(item);
        //primaryStage.setScene(scene);
        //primaryStage.setTitle(WINDOW_TITLE);
        //primaryStage.show();
    }

}
