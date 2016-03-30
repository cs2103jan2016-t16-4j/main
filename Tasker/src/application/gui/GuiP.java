package application.gui;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        ListItem item = new ListItem(1, "HI" , "28 MAR" , "AT YALE-NUS");
        //modifyListItem(item);
        System.out.println("HEE");
        //hbox.setLabels(1, "Hello", "", "");
        OpeningPage page = new OpeningPage();
        //List<HBox> list = new ArrayList();
        //list.add(item);
        //ObservableList obsList = FXCollections.observableArrayList(list);
        //ListView<String> list2 = (ListView<String>) page.lookup("#displayList");
        //list2.setItems(obsList);
        //list.getItems().add(item);
        //page.getChildren().add(list);
        //page.getChildren().add(item);
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
