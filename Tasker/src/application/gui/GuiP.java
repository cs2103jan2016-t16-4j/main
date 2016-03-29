package application.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
        modifyListItem(item);
        System.out.println("HEE");
        //hbox.setLabels(1, "Hello", "", "");
        AnchorPane page = (AnchorPane) FXMLLoader.load(GuiP.class.getResource("ListView.fxml"));
        ListView<HBox> list = new ListView<HBox>();
        //list.getItems().add(item);
        page.getChildren().add(list);
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.show();
    }

    private void modifyListItem(HBox item){
        Label taskNumber = (Label) item.lookup("#listNumber");
        taskNumber.setText("1");
        Label taskName = (Label) item.lookup("#taskName");
        taskName.setText("TENNIS IS AWESOME");
        Label date = (Label) item.lookup("#date");
        date.setText("28 MAR TO 30 MAR");
        Label location = (Label) item.lookup("#location");
        location.setText("AT COURTS");
    }
    
}
