package application.gui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import application.logic.Feedback;
import application.logic.Logic;
import application.storage.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class OpeningPage extends AnchorPane {
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final String LOCATION_PREFIX = "AT";
    private static final String MESSAGE_HELP_INTRO = "Start typing and we'll help you out!";
    private static final String MESSAGE_FEEDBACK_INTRO = "We'll give you feedback on your commands here.";
    private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat FORMAT_TIME = new SimpleDateFormat("h:mm a");
    private static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
    private static final String EMPTY_DATE = "0001";
    private static final String DATE_TO_DATE = " to ";
    private static final String DURATION_SAME_DATE_INFO = "%1$s" + SPACE + "%2$s" + DATE_TO_DATE + "%3$s";
    private static final String DURATION_DIFF_DATE_INFO = "%1$s" + SPACE + "%2$s" + DATE_TO_DATE + "%3$s" + SPACE + "%4$s";
    
    
    String text = EMPTY_STRING;
    private static ArrayList<String> commands = new ArrayList<String>();
    private static int pointer = 0;

    
    private ArrayList<Task> tasksOnScreen;
    private Logic logic;
    @FXML
    public Label feedbackLabel;
    @FXML
    private Label helpLabel;
    @FXML
    private TextField textInputArea;
    @FXML
    private ListView<HBox> displayList;
    
    public OpeningPage(ArrayList<Task> taskList, Logic logic){
        tasksOnScreen = taskList;
        this.logic = logic;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ListView.fxml"));    
        loadFromFxml(fxmlLoader);
        initialize();
        initializeInputArea();
    }

    private void loadFromFxml(FXMLLoader fxmlLoader) {
        try {
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            System.out.println("Could not load");
            throw new RuntimeException(exception);
        }
    }

    
    
    
    private void initialize() {
        helpLabel.setText(MESSAGE_HELP_INTRO);
        feedbackLabel.setText(MESSAGE_FEEDBACK_INTRO);
        updateListView(tasksOnScreen);
    }

    private void updateListView(ArrayList<Task> taskList){
        ObservableList<HBox> list = makeDisplayList(taskList);
        this.displayList.setItems(list);
    }
    
    private ObservableList<HBox> makeDisplayList(ArrayList<Task> taskList){
        ObservableList<HBox> displayList = FXCollections.observableArrayList();
        int i = 1;
        for (Task task : taskList){
            ListItem item = getListItem(i, task);
            displayList.add(item);
            i++;
        }
        return displayList;
    }

    private ListItem getListItem(int i, Task task) {
        String description = task.getTaskDescription();
        String location = getLocationString(task);
        String date = getCorrectDateString(task);
        ListItem item = new ListItem(i, description, date, location);
        return item;
    }

    private String getLocationString(Task task){
        String location = task.getLocation();
        if (location.trim().equals(EMPTY_STRING)){
            return EMPTY_STRING;
        }else{
            return (LOCATION_PREFIX + SPACE + location );
        }
    }
    
    private String getCorrectDateString(Task task) {
        String taskDuration = EMPTY_STRING;
        if (!FORMAT_YEAR.format(task.getStartDate().getTime()).equals(EMPTY_DATE)) {
            if (FORMAT_DATE.format(task.getStartDate().getTime())
                    .equals(FORMAT_DATE.format(task.getEndDate().getTime()))) {
                taskDuration = String.format(DURATION_SAME_DATE_INFO,
                        FORMAT_DATE.format(task.getStartDate().getTime()),
                        FORMAT_TIME.format(task.getStartTime().getTime()),
                        FORMAT_TIME.format(task.getEndTime().getTime()));
            } else {
                taskDuration = String.format(DURATION_DIFF_DATE_INFO,
                        FORMAT_DATE.format(task.getStartDate().getTime()),
                        FORMAT_TIME.format(task.getStartTime().getTime()),
                        FORMAT_DATE.format(task.getEndDate().getTime()),
                        FORMAT_TIME.format(task.getEndTime().getTime()));
            }
        } else {
            taskDuration = EMPTY_STRING;
        }
        return taskDuration;
    }

    public void initializeInputArea(){
        textInputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
    
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    try {
                        text = textInputArea.getText();
                        commands.add(text);
                        Feedback feedback = logic.executeCommand(text, tasksOnScreen);
                        System.out.println(feedback.getMessage());
                        tasksOnScreen = feedback.getTasks();
                        updateListView(tasksOnScreen);
                        feedbackLabel.setText(feedback.getMessage());
                        textInputArea.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (ke.getCode().equals(KeyCode.UP)) {
                    // if used commands list is not empty
                    if (!commands.isEmpty()) {
                        if (!commands.contains(textInputArea.getText())) {
                            pointer = commands.size();
                        } else {
                            pointer = commands.indexOf(textInputArea.getText());
                        }
                        // if pointer is not at the front end
                        if (pointer != 0) {
                            textInputArea.setText(commands.get(pointer - 1));
                        }
                    }
                }
                if (ke.getCode().equals(KeyCode.DOWN)) {
                    if (!commands.isEmpty()) {
                        if (!commands.contains(textInputArea.getText())) {
                            pointer = commands.size();
                        } else {
                            pointer = commands.indexOf(textInputArea.getText());
                        }
                        // if pointer is not at the end
                        if (pointer != commands.size() - 1) {
                            textInputArea.setText(commands.get(pointer + 1));
                        }
                    }
                }
            }
        });
    }
}

    
