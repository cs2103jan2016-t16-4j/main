//@@author A0132632R
package application.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.SelectionMode;
import application.backend.Feedback;
import application.backend.Logic;
import application.storage.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class OpeningPage extends AnchorPane {
	private static final String SPACE = " ";
	private static final String EMPTY_STRING = "";
	private static final String LOCATION_PREFIX = "AT";
	private static final String BY = "By ";
	private static final String BACKSLASH = "\\";
	private static final String DIRECTORY_CHOOSER_TITLE = "Pick Where To Store Tasks";
	private static final String CURRENT_DIRECTORY = "user.dir";
	private static final int TASK_NUM_OFFSET = 1;

	// Messages
	private static final String ADD_HINT_MESSAGE = "To add: [task description] from [start] to [end] at [location]";
	private static final String HELP_HINT_MESSAGE = "To get help: help";
	private static final String DELETE_HINT_MESSAGE = "To delete: delete [task description/number]";
	private static final String SEARCH_HINT_MESSAGE = "To search: search [task decription/priority [level]/[task description] by [date]]";
	private static final String EXIT_HINT_MESSAGE = "To exit: exit";
	private static final String UPDATE_HINT_MESSAGE = "To update: update [task number] [new task desc]";
	private static final String UNDO_HINT_MESSAGE = "To undo: undo";
	private static final String STORAGE_HINT_MESSAGE = "To change storage: storage";
	private static final String DONE_HINT_MESSAGE = "To mark task as complete: done [task number]";
	private static final String MESSAGE_STORAGE_URL_NOT_FOUND = "Storage Location Invalid: Opening Directory Chooser";
	private static final String MESSAGE_HELP_INTRO = "Start typing and we'll help you out!";
	private static final String MESSAGE_FEEDBACK_INTRO = "We'll give you feedback on your commands here.";

	private static final String MESSAGE_ERROR = "There was some problem processing your request. "
			+ "Please check your input format.";

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
	private ListView<Task> displayList;

	public OpeningPage(ArrayList<Task> taskList, Logic logic) {
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
	//@@author A0125417L
	private void initialize() {
		helpLabel.setText(MESSAGE_HELP_INTRO);
		feedbackLabel.setText(MESSAGE_FEEDBACK_INTRO);
		displayList.setPrefSize(1070, 580);
		this.displayList.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			public ListCell<Task> call(ListView<Task> param) {
				ListCell<Task> cell = new ListCell<Task>() {
					@Override
					public void updateItem(Task item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							int taskNumber = this.getIndex() + TASK_NUM_OFFSET;
							String taskDescription = item.getTaskDescription();
							String taskDuration = item.durationToString();
							String taskLocation = getLocationString(item);

							ListItem listViewItem = new ListItem(taskNumber, taskDescription, taskDuration,
									taskLocation);
							setGraphic(listViewItem);
						} else {
							setGraphic(null);
						}
					}
				};

				return cell;
			}
		});
		updateListView(tasksOnScreen);
	}
	//@@author A0132632R

	
	private void updateListView(ArrayList<Task> taskList) {
		ObservableList<Task> list = makeDisplayList(taskList);
		this.displayList.setItems(list);
//		if(){
//		displayList.scrollTo(taskList.get(0));}
	}

	private ObservableList<Task> makeDisplayList(ArrayList<Task> taskList) {
		ObservableList<Task> displayList = FXCollections.observableArrayList();
		for (Task task : taskList) {
			displayList.add(task);
		}
		return displayList;
	}

	private String getLocationString(Task task) {
		String location = task.getLocation();
		if (location.trim().equals(EMPTY_STRING)) {
			return EMPTY_STRING;
		} else {
			return (LOCATION_PREFIX + SPACE + location);
		}
	}
	//@@author A0125417L
	public void initializeInputArea() {
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
						if (feedback.getMessage().equals(MESSAGE_STORAGE_URL_NOT_FOUND)) {
							DirectoryChooser dirChooser = new DirectoryChooser();
							configureDirectoryChooser(dirChooser);
							Stage stage = new Stage();
							directoryPrompt(stage, dirChooser);
						}
						textInputArea.clear();
					} catch (Exception e) {
						feedbackLabel.setText(MESSAGE_ERROR);
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
		textInputArea.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				getHints(oldValue, newValue, helpLabel);
			}
		});

	}

	public void directoryPrompt(Stage primaryStage, DirectoryChooser dirChooser) throws IOException {
		final File selectedDirectory = dirChooser.showDialog(primaryStage);
		if (selectedDirectory != null) {
			logic.setDirectory(selectedDirectory.getPath().toString() + BACKSLASH);
			feedbackLabel.setText("Directory Changed: " + selectedDirectory.getPath().toString() + BACKSLASH);
		} else {
			logic.setDirectory(EMPTY_STRING);
			feedbackLabel.setText("Directory Not Changed!");
		}
	}

	private void configureDirectoryChooser(final DirectoryChooser dirChooser) {
		dirChooser.setTitle(DIRECTORY_CHOOSER_TITLE);
		dirChooser.setInitialDirectory(new File(System.getProperty(CURRENT_DIRECTORY)));
	}

	private void getHints(String oldValue, String newValue, Label helpLabel) {
		String newLetter = EMPTY_STRING;
		String oldWord = EMPTY_STRING;
		String newWord = EMPTY_STRING;

		if (!oldValue.isEmpty() && oldValue != null) {
			oldWord = getFirstWord(oldValue);
		}
		if (!newValue.isEmpty() && newValue != null) {
			newWord = getFirstWord(newValue);
			newLetter = getFirstLetter(newValue);
		}

		if (newWord == null) {
			return;
		}

		if (newWord.equals(oldWord)) {
			return;
		} else {
			switch (newLetter.toLowerCase()) {
			case "a":
				helpLabel.setText(ADD_HINT_MESSAGE);
				break;
			case "h":
				helpLabel.setText(HELP_HINT_MESSAGE);
				if (!newValue.isEmpty() && newValue.length() >= 4) {
					if (!newWord.equalsIgnoreCase("help")) {
						helpLabel.setText(ADD_HINT_MESSAGE);
					}
				}
				break;
			case "d":
				if (!newValue.isEmpty() && newValue.length() > 1) {
					if (getSecondLetter(newValue).equalsIgnoreCase("do")) {
						helpLabel.setText(DONE_HINT_MESSAGE);
						if (!newValue.isEmpty() && newValue.length() >= 4) {
							if (!newWord.equalsIgnoreCase("done")) {
								helpLabel.setText(ADD_HINT_MESSAGE);
							}
						}
					}
				} else {
					helpLabel.setText(DELETE_HINT_MESSAGE);
				}
				if (!newValue.isEmpty() && newValue.length() >= 6) {
					if (!newWord.equalsIgnoreCase("delete")) {
						helpLabel.setText(ADD_HINT_MESSAGE);
					}
				}
				break;
			case "u":
				if (!newValue.isEmpty() && newValue.length() > 1) {
					if (getSecondLetter(newValue).equalsIgnoreCase("un")) {
						helpLabel.setText(UNDO_HINT_MESSAGE);
						if (!newValue.isEmpty() && newValue.length() >= 4) {
							if (!newWord.equalsIgnoreCase("undo")) {
								helpLabel.setText(ADD_HINT_MESSAGE);
							}
						}
					}
				} else {
					helpLabel.setText(UPDATE_HINT_MESSAGE);
				}
				if (!newValue.isEmpty() && newValue.length() >= 6) {
					if (!newWord.equalsIgnoreCase("update")) {
						helpLabel.setText(ADD_HINT_MESSAGE);
					}
				}
				break;
			case "e":
				helpLabel.setText(EXIT_HINT_MESSAGE);
				if (!newValue.isEmpty() && newValue.length() >= 4) {
					if (!newWord.equalsIgnoreCase("exit")) {
						helpLabel.setText(ADD_HINT_MESSAGE);
					}
				}
				break;
			case "s":
				if (!newValue.isEmpty() && newValue.length() > 1) {
					if (getSecondLetter(newValue).equalsIgnoreCase("se")) {
						helpLabel.setText(SEARCH_HINT_MESSAGE);
						if (!newValue.isEmpty() && newValue.length() >= 6) {
							if (!newWord.equalsIgnoreCase("search")) {
								helpLabel.setText(ADD_HINT_MESSAGE);
							}
						}
					}
				} else {
					helpLabel.setText(STORAGE_HINT_MESSAGE);

				}
				if (!newValue.isEmpty() && newValue.length() >= 7) {
					if (!newWord.equalsIgnoreCase("storage")) {
						helpLabel.setText(ADD_HINT_MESSAGE);
					}
				}
				break;
			default:
				helpLabel.setText(ADD_HINT_MESSAGE);
				break;
			}
		}
	}
	//@@author A0132632R
	private String getFirstLetter(String input) {
		String firstLetter = input.substring(0, 1);
		return firstLetter;
	}

	private String getFirstWord(String input) {
		String[] inputArgs = input.trim().split(SPACE);
		String firstWord = inputArgs[0];
		return firstWord;
	}

	private String getSecondLetter(String input) {
		String secondLetter = input.substring(0, 2);
		return secondLetter;
	}

}
