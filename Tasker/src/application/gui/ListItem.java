package application.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class ListItem extends HBox implements Initializable{
    
    @FXML
    public Label listNumber;
    @FXML
    private Label taskName;
    @FXML
    private Label date;
    @FXML
    private Label location;
    
    
    public void setLabels(int taskNumber, String name, String date, String location) {
        listNumber.setText(""+ taskNumber);
        taskName.setText(name);
        this.date.setText(date);
        this.location.setText(location);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(listNumber.getText());
    }

}
