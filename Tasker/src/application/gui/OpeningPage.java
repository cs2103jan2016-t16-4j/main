package application.gui;

import java.awt.TextField;
import java.io.IOException;
import java.util.ArrayList;

import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class OpeningPage extends AnchorPane {
    private ArrayList<Task> tasksOnScreen;
    
    @FXML
    public Label feedback;
    @FXML
    private Label help;
    @FXML
    private TextField textInputArea;
    @FXML
    private ListView<HBox> displayList;
    
    public OpeningPage(){
        //tasksOnScreen = tasks;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListView.fxml"));    
        try {
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            addTasksToList();
            
        } catch (IOException exception) {
            System.out.println("Could not load");
            throw new RuntimeException(exception);
        }
    }

    private void addTasksToList(){
        ObservableList<HBox> items = FXCollections.observableArrayList();
        items.add(new ListItem(1, "HELLO", "DATE", ""));
        items.add(new ListItem(1, "HELLO", "DATE", ""));
        items.add(new ListItem(1, "HELLO", "DATE", ""));
        items.add(new ListItem(1, "HELLO", "DATE", ""));
        displayList.setItems(items);
    }
    


    
}
