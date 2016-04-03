package application.gui;

import java.io.IOException;

import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class DateObject extends HBox{
	
    @FXML
    public Label dateLabel;
    @FXML
    public ListView<CalendarItem> listViewItem;
    @FXML
    public HBox dateObject;
    
    
    
    public DateObject(String date,String name, String location){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DateObject.fxml"));    
        try {
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
            this.setLabels(date);
            ObservableList<CalendarItem> list = FXCollections.observableArrayList();
            listViewItem = new ListView<CalendarItem>(list);
            CalendarItem cI = new CalendarItem(name,date,location);
            list.add(cI);
        } catch (IOException exception) {
            System.out.println("Could not load");
            throw new RuntimeException(exception);
        }
 
    }

    private void setLabels(String date) {
        this.dateLabel.setText(date.toUpperCase());
    }
    
    public HBox getHbox(){
    	return this.dateObject;
    }

}
