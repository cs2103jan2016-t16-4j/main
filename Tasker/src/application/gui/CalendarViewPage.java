//@@author A0125417L
package application.gui;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.controlsfx.control.Notifications;

import application.backend.Feedback;
import application.backend.Logic;
import application.logger.LoggerHandler;
import application.storage.FloatingTask;
import application.storage.Task;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

/*
 * Holds the main page of the gui that runs most of the program
 * 
 */

public class CalendarViewPage extends AnchorPane {

	// Constants
	private static final int FIRST_WORD = 0;
	private static final int ONE_LETTER = 1;
	private static final int TWO_LETTERS = 2;
	private static final String STORAGE_TEXT = "storage";
	private static final String SEARCH_TEXT = "search";
	private static final String SE_TEXT = "se";
	private static final String S_TEXT = "s";
	private static final String EXIT_TEXT = "exit";
	private static final String E_TEXT = "e";
	private static final String UPDATE_TEXT = "update";
	private static final String UNDO_TEXT = "undo";
	private static final String UN_TEXT = "un";
	private static final String U_TEXT = "u";
	private static final String DELETE_TEXT = "delete";
	private static final String DONE_TEXT = "done";
	private static final String DO_TEXT = "do";
	private static final String D_TEXT = "d";
	private static final String H_TEXT = "h";
	private static final String A_TEXT = "a";
	private static final String V_TEXT = "v";
	private static final String HELP_TEXT = "help";
	private static final String LISTVIEW_FXML_URL = "ListView.fxml";
	private static final String LIST_FLAG = "list";
	private static final String CAL_FLAG = "cal";
	private static final String HELP_FLAG = HELP_TEXT;
	private static final String STORAGE_FLAG = STORAGE_TEXT;
	private static final String VIEW_CHANGE_FLAG = "view";
	private static final String SUMMARY_FLAG = "summary";
	private static final String SPACE = " ";
	private static final String EMPTY_STRING = "";
	private static final String LOCATION_PREFIX = "AT";
	private static final String BACKSLASH = "\\";
	private static final String DIRECTORY_CHOOSER_TITLE = "Pick Where To Store Tasks";
	private static final String CURRENT_DIRECTORY = "user.dir";
	private static final int TASK_NUM_OFFSET = 1;
	private static final String OVERDUE_TASKS_TEXT = "Overdue Tasks";
	private static final String REMAINING_TASKS_TEXT = "Remaining Tasks";
	private static final String COMPLETED_TASKS_TEXT = "Completed Tasks";
	private static final String NO_DATES_TEXT = "No Dates Set";
	private static final String NO_START_DATE_TEXT = "No Start Date Set";
	private static final String NO_END_DATE_TEXT = "No End Date Set";
	private static final String REMINDER_TEXT = "Reminder";
	private static final int EMPTY = 0;
	private static final int STARTPOSITION = 0;
	private static final int OFFSET = 1;
	private static final int NEXT = 1;
	private static final int START = 0;
	private static final int previous = 1;
	private static final int BOX_HEIGHT = 580;
	private static final int BOX_WIDTH = 1070;
	private static final int TRANSITION_TIME = 350;
	private static final int overdueCheckVariable = 0;

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// Formats
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd MMM yyyy");

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
	private static final String VIEW_HINT_MESSAGE = "To Toggle Views: view";
	private static final String MESSAGE_STORAGE_URL_NOT_FOUND = "Storage Location Invalid: Opening Directory Chooser";
	private static final String MESSAGE_HELP_INTRO = "Start typing and we'll help you out!";
	private static final String MESSAGE_FEEDBACK_INTRO = "We'll give you feedback on your commands here.";
	private static final String MESSAGE_ERROR = "There was some problem processing your request. "
			+ "Please check your input format.";
	private static String MESSAGE_TASK = "%1$s: %2$s";

	// Error Messages
	private static final String FXML_LOAD_FAILED = "Failed to load ListView FXML file";

	// Variables
	private static int pointer = START;
	private Task taskToFocus;
	private String checkFlag;
	private String text = EMPTY_STRING;
	private static ArrayList<String> commands = new ArrayList<String>();
	private ArrayList<Task> tasksOnScreen;
	private Logic logic;

	// FXML Variables
	@FXML
	public Label feedbackLabel;
	@FXML
	private Label helpLabel;
	@FXML
	private TextField textInputArea;
	@FXML
	private StackPane stackPane;
	@FXML
	private ListView<Task> displayList;
	@FXML
	private ListView<ArrayList<Task>> calendarList;
	@FXML
	private AnchorPane hiddenMenu;
	@FXML
	private Label overdueLabel;
	@FXML
	private Label completedLabel;
	@FXML
	private Label remainingLabel;
	@FXML
	private PieChart pieChart;

	//@@author A0132632R
	public CalendarViewPage(ArrayList<Task> taskList, Logic logic) {
		tasksOnScreen = taskList;
		this.logic = logic;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(LISTVIEW_FXML_URL));
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
			logger.severe(FXML_LOAD_FAILED);
			throw new RuntimeException(exception);
		}
	}

	private void initialize() {
		helpLabel.setText(MESSAGE_HELP_INTRO);
		feedbackLabel.setText(MESSAGE_FEEDBACK_INTRO);
		initialiseCalendarList();
		initialiseDisplayList();
		taskToFocus = null;
		updateViews(tasksOnScreen, taskToFocus);
	}
	//@@author A0125417L

	// Setup cell factory for task list view
	private void initialiseDisplayList() {
		displayList.setPrefSize(BOX_WIDTH, BOX_HEIGHT);
		this.displayList.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
			public ListCell<Task> call(ListView<Task> param) {
				ListCell<Task> cell = new ListCell<Task>() {
					@Override
					public void updateItem(Task item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							Calendar cal = Calendar.getInstance();
							int overdueCheck = checkIfOverdue(item, cal);
							int taskNumber = this.getIndex() + TASK_NUM_OFFSET;
							String taskDescription = item.getTaskDescription();
							String taskDuration = item.durationToString();
							String taskLocation = getLocationString(item);
							String taskPriority = item.getPriority();
							ListItem listViewItem = new ListItem(taskNumber, taskDescription, taskDuration,
									taskLocation, taskPriority, overdueCheck);
							setGraphic(listViewItem);
						} else {
							setGraphic(null);
						}
					}
				};

				return cell;
			}
		});
	}

	// Setup cell factory for calendar view
	private void initialiseCalendarList() {
		calendarList.setPrefSize(BOX_WIDTH, BOX_HEIGHT);
		this.calendarList.setCellFactory(new Callback<ListView<ArrayList<Task>>, ListCell<ArrayList<Task>>>() {
			public ListCell<ArrayList<Task>> call(ListView<ArrayList<Task>> param) {
				ListCell<ArrayList<Task>> cell = new ListCell<ArrayList<Task>>() {
					@Override
					public void updateItem(ArrayList<Task> item, boolean empty) {
						super.updateItem(item, empty);
						if (item != null) {
							String date = setDate(item);
							DateObject listViewItem = new DateObject(date, item, tasksOnScreen);
							setGraphic(listViewItem.getHbox());
						} else {
							setGraphic(null);
						}
					}
				};

				return cell;
			}
		});
	}

	// Set date accordingly
	private String setDate(ArrayList<Task> item) {
		String date = null;
		if (item.get(START).getEndDate() != null) {
			date = FORMAT_DATE.format(item.get(START).getEndDate().getTime());
		}
		return date;
	}

	// Check if task is overdue
	private int checkIfOverdue(Task item, Calendar cal) {
		int overdueCheck = overdueCheckVariable;
		if (item.getEndDate() != null) {
			overdueCheck = item.getEndDate().getTime().compareTo(cal.getTime());
		}
		return overdueCheck;
	}

	// Format for the location section of task list view
	private String getLocationString(Task task) {
		String location = task.getLocation();
		if (location.trim().equals(EMPTY_STRING)) {
			return EMPTY_STRING;
		} else {
			return (LOCATION_PREFIX + SPACE + location);
		}
	}

	// Set the data for the calendar view
	private ArrayList<ArrayList<Task>> getDateArray(ArrayList<Task> taskList) {
		String tempoDate = null;
		ArrayList<ArrayList<Task>> dateArray = new ArrayList<ArrayList<Task>>();
		ArrayList<Task> temporaryList = new ArrayList<Task>();
		for (int i = START; i < taskList.size(); i++) {
			if (!(taskList.get(i) instanceof FloatingTask) && tempoDate != null) {
				if (tempoDate.equals(FORMAT_DATE.format(taskList.get(i).getEndDate().getTime()))) {
					temporaryList.add(taskList.get(i));
				} else {
					dateArray.add(temporaryList);
					temporaryList = new ArrayList<Task>();
					temporaryList.add(taskList.get(i));
				}
			}

			if (!(taskList.get(i) instanceof FloatingTask) && tempoDate == null) {
				temporaryList.add(taskList.get(i));
			}

			if ((taskList.get(i) instanceof FloatingTask) && tempoDate != null) {
				dateArray.add(temporaryList);
				temporaryList = new ArrayList<Task>();
				temporaryList.add(taskList.get(i));
			}

			if ((taskList.get(i) instanceof FloatingTask) && tempoDate == null) {
				temporaryList.add(taskList.get(i));
			}

			if (!(taskList.get(i) instanceof FloatingTask)) {
				tempoDate = FORMAT_DATE.format(taskList.get(i).getEndDate().getTime());
			} else {
				tempoDate = null;
			}
		}
		if (temporaryList.size() != EMPTY) {
			dateArray.add(temporaryList);
		}
		return dateArray;
	}

	// Method that calls other methods to update the data
	private void updateViews(ArrayList<Task> taskList, Task taskToFocus) {
		updateCalendarList(taskList, taskToFocus);
		updateDisplayList(taskList, taskToFocus);
		updateSummary();
	}

	// Update the side panel
	private void updateSummary() {
		completedLabel.setText(String.format(MESSAGE_TASK, COMPLETED_TASKS_TEXT, logic.getCompletedTaskCount()));
		remainingLabel.setText(String.format(MESSAGE_TASK, REMAINING_TASKS_TEXT, logic.getRemainingTaskCount()));
		overdueLabel.setText(String.format(MESSAGE_TASK, OVERDUE_TASKS_TEXT, logic.getOverdueTaskCount()));
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(COMPLETED_TASKS_TEXT, logic.getCompletedTaskCount()),
				new PieChart.Data(REMAINING_TASKS_TEXT, logic.getRemainingTaskCount()),
				new PieChart.Data(OVERDUE_TASKS_TEXT, logic.getOverdueTaskCount()));
		pieChart.setData(pieChartData);
		pieChart.setLabelsVisible(false);
		pieChart.setLegendSide(Side.BOTTOM);
	}

	// Notification behavior for adding or updating tasks
	private void notifyUser(Task taskToFocus) {
		String title = null;
		String text = null;
		if (taskToFocus != null) {
			if (taskToFocus.getEndDate() == null) {
				title = REMINDER_TEXT;
				text = NO_END_DATE_TEXT;
			}
			if (taskToFocus.getStartDate() == null) {
				title = REMINDER_TEXT;
				text = NO_START_DATE_TEXT;
			}
			if (taskToFocus.getStartDate() == null && taskToFocus.getEndDate() == null) {
				title = REMINDER_TEXT;
				text = NO_DATES_TEXT;
			}
			if (title != null && text != null) {
				Notifications.create().title(title).text(text).showInformation();
			}
		}
	}

	// Updates data of the task view
	private void updateDisplayList(ArrayList<Task> taskList, Task taskToFocus) {
		this.displayList.getItems().clear();
		if (taskList.size() != EMPTY) {
			ObservableList<Task> list = makeDisplayList(taskList);
			this.displayList.setItems(list);
			if (taskToFocus != null) {
				this.displayList.scrollTo(taskToFocus);
				this.displayList.getSelectionModel().select(taskToFocus);
			}
		}
	}

	// Updates data of the calendar view
	private void updateCalendarList(ArrayList<Task> taskList, Task taskToFocus) {
		this.calendarList.getItems().clear();
		if (taskList.size() != EMPTY) {
			ArrayList<ArrayList<Task>> dateArray = new ArrayList<ArrayList<Task>>();
			dateArray = getDateArray(taskList);
			ObservableList<ArrayList<Task>> calList = makeCalendarList(dateArray);
			this.calendarList.setItems(calList);
		}
	}

	private ObservableList<ArrayList<Task>> makeCalendarList(ArrayList<ArrayList<Task>> taskList) {
		ObservableList<ArrayList<Task>> calendarList = FXCollections.observableArrayList();
		for (ArrayList<Task> task : taskList) {
			calendarList.add(task);
		}
		return calendarList;

	}

	private ObservableList<Task> makeDisplayList(ArrayList<Task> taskList) {
		ObservableList<Task> displayList = FXCollections.observableArrayList();
		for (Task task : taskList) {
			displayList.add(task);
		}
		return displayList;
	}

	// Sets behavior of the text area
	public void initializeInputArea() {
		textInputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					try {
						text = textInputArea.getText();
						commands.add(text);
						if (text.equals("summary")) {
							TranslateTransition openNav = new TranslateTransition(new Duration(TRANSITION_TIME),
									hiddenMenu);
							openNav.setToX(STARTPOSITION);
							TranslateTransition closeNav = new TranslateTransition(new Duration(TRANSITION_TIME),
									hiddenMenu);
							if (hiddenMenu.getTranslateX() != STARTPOSITION) {
								openNav.play();
							} else {
								closeNav.setToX(+(hiddenMenu.getWidth()));
								closeNav.play();
							}
							hiddenMenu.toFront();
						} else {
							Feedback feedback = logic.executeCommand(text, tasksOnScreen);
							System.out.println(feedback.getMessage());
							tasksOnScreen = feedback.getTasks();
							taskToFocus = feedback.getIndexToScroll();
							notifyUser(taskToFocus);
							updateViews(tasksOnScreen, taskToFocus);
							checkFlag = feedback.getFlag();
							feedbackLabel.setText(feedback.getMessage());
							doFlagCommand(checkFlag, feedback);
							if (checkFlag != HELP_FLAG) {
								textInputArea.clear();
							}
						}
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
						if (pointer != START) {
							textInputArea.setText(commands.get(pointer - previous));
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
						if (pointer != commands.size() - OFFSET) {
							textInputArea.setText(commands.get(pointer + NEXT));
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

	private void doFlagCommand(String checkFlag, Feedback feedback) throws IOException {
		switch (checkFlag) {
		case STORAGE_FLAG:
			promptStorage(feedback);
			break;
		case CAL_FLAG:
			calendarList.toFront();
			break;
		case HELP_FLAG:
			String selected = showHelpDialog();
			switch (selected) {
			case "Add":
				showAddDialog();
				break;
			case "Delete":
				showDeleteDialog();
				break;
			case "Update":
				showUpdateDialog();
				break;
			case "Close":
				showCloseDialog();
				break;
			case "Search":
				showSearchDialog();
				break;
			case "Undo":
				showUndoDialog();
				break;
			case "Storage":
				showStorageDialog();
				break;
			case "Exit":
				showExitDialog();
				break;
			default:
				break;
			}
			break;
		case LIST_FLAG:
			displayList.toFront();
			break;

		case VIEW_CHANGE_FLAG:
			switchViews();
			break;
		case SUMMARY_FLAG:
			break;
		}
	}

	private void promptStorage(Feedback feedback) throws IOException {
		if (feedback.getMessage().equals(MESSAGE_STORAGE_URL_NOT_FOUND)) {
			DirectoryChooser dirChooser = new DirectoryChooser();
			configureDirectoryChooser(dirChooser);
			Stage stage = new Stage();
			directoryPrompt(stage, dirChooser);
		}
	}

	private void switchViews() {
		if (stackPane.getChildren().get(0).equals(displayList)) {
			displayList.toFront();
		} else {
			calendarList.toFront();
		}
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
			case A_TEXT:
				checkA(helpLabel);
				break;
			case H_TEXT:
				checkH(newValue, helpLabel, newWord);
				break;
			case D_TEXT:
				checkD(newValue, helpLabel, newWord);
				break;
			case U_TEXT:
				checkU(newValue, helpLabel, newWord);
				break;
			case E_TEXT:
				checkE(newValue, helpLabel, newWord);
				break;
			case S_TEXT:
				checkS(newValue, helpLabel, newWord);
				break;
			case V_TEXT:
				checkV(helpLabel);
				break;
			default:
				checkA(helpLabel);
				break;
			}
		}
	}

	private void checkV(Label helpLabel) {
		helpLabel.setText(VIEW_HINT_MESSAGE);
	}

	private void checkS(String newValue, Label helpLabel, String newWord) {
		if (!newValue.isEmpty() && newValue.length() > S_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(SE_TEXT)) {
				helpLabel.setText(SEARCH_HINT_MESSAGE);
				if (!newValue.isEmpty() && newValue.length() >= SEARCH_TEXT.length()) {
					if (!newWord.equalsIgnoreCase(SEARCH_TEXT)) {
						checkA(helpLabel);
					}
				}
			}
		} else {
			helpLabel.setText(STORAGE_HINT_MESSAGE);
		}
		if (!newValue.isEmpty() && newValue.length() >= STORAGE_TEXT.length()) {
			if (!newWord.equalsIgnoreCase(STORAGE_TEXT)) {
				checkA(helpLabel);
			}
		}
	}

	private void checkE(String newValue, Label helpLabel, String newWord) {
		helpLabel.setText(EXIT_HINT_MESSAGE);
		if (!newValue.isEmpty() && newValue.length() >= EXIT_TEXT.length()) {
			if (!newWord.equalsIgnoreCase(EXIT_TEXT)) {
				checkA(helpLabel);
			}
		}
	}

	private void checkU(String newValue, Label helpLabel, String newWord) {
		if (!newValue.isEmpty() && newValue.length() > U_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(UN_TEXT)) {
				helpLabel.setText(UNDO_HINT_MESSAGE);
				if (!newValue.isEmpty() && newValue.length() >= UNDO_TEXT.length()) {
					if (!newWord.equalsIgnoreCase(UNDO_TEXT)) {
						checkA(helpLabel);
					}
				}
			}
		} else {
			helpLabel.setText(UPDATE_HINT_MESSAGE);
		}
		if (!newValue.isEmpty() && newValue.length() >= UPDATE_TEXT.length()) {
			if (!newWord.equalsIgnoreCase(UPDATE_TEXT)) {
				checkA(helpLabel);
			}
		}
	}

	private void checkD(String newValue, Label helpLabel, String newWord) {
		if (!newValue.isEmpty() && newValue.length() > D_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(DO_TEXT)) {
				helpLabel.setText(DONE_HINT_MESSAGE);
				if (!newValue.isEmpty() && newValue.length() >= DONE_TEXT.length()) {
					if (!newWord.equalsIgnoreCase(DONE_TEXT)) {
						checkA(helpLabel);
					}
				}
			}
		} else {
			helpLabel.setText(DELETE_HINT_MESSAGE);
		}
		if (!newValue.isEmpty() && newValue.length() >= DELETE_TEXT.length()) {
			if (!newWord.equalsIgnoreCase(DELETE_TEXT)) {
				checkA(helpLabel);
			}
		}
	}

	private void checkA(Label helpLabel) {
		helpLabel.setText(ADD_HINT_MESSAGE);
	}

	private void checkH(String newValue, Label helpLabel, String newWord) {
		helpLabel.setText(HELP_HINT_MESSAGE);
		if (!newValue.isEmpty() && newValue.length() >= HELP_TEXT.length()) {
			if (!newWord.equalsIgnoreCase(HELP_TEXT)) {
				checkA(helpLabel);
			}
		}

	}

	private String getFirstLetter(String input) {
		String firstLetter = input.substring(START, ONE_LETTER);
		return firstLetter;
	}

	private String getFirstWord(String input) {
		String[] inputArgs = input.trim().split(SPACE);
		String firstWord = inputArgs[FIRST_WORD];
		return firstWord;
	}

	private String getTwoLetters(String input) {
		String secondLetter = input.substring(START, TWO_LETTERS);
		return secondLetter;
	}

	private void showExitDialog() {
		textInputArea.setText("Exit");
		feedbackLabel.setText("");
		helpLabel.setText(EXIT_HINT_MESSAGE);
	}

	private void showStorageDialog() {
		textInputArea.setText("Storage");
		feedbackLabel.setText("");
		helpLabel.setText(STORAGE_HINT_MESSAGE);
	}

	private void showUndoDialog() {
		textInputArea.setText("Undo");
		feedbackLabel.setText("");
		helpLabel.setText(UNDO_HINT_MESSAGE);
	}

	private void showSearchDialog() {
		textInputArea.setText("search [task decription/priority [level]/[task description] by [date]]");
		feedbackLabel.setText("");
		helpLabel.setText(SEARCH_HINT_MESSAGE);
	}

	private void showCloseDialog() {
		textInputArea.setText("Done [task number]");
		feedbackLabel.setText("");
		helpLabel.setText(DONE_HINT_MESSAGE);
	}

	private void showUpdateDialog() {
		textInputArea.setText("update [task number] [new task desc]");
		feedbackLabel.setText("");
		helpLabel.setText(UPDATE_HINT_MESSAGE);
	}

	private void showDeleteDialog() {
		textInputArea.setText("delete [task description/number]");
		feedbackLabel.setText("");
		helpLabel.setText(DELETE_HINT_MESSAGE);
	}

	private String showHelpDialog() {
		ChoiceDialog<String> dialog;
		final String[] arrayData = { "Add", "Delete", "Update", "Close", "Search", "Undo", "Storage", "Exit" };
		List<String> dialogData;
		dialogData = Arrays.asList(arrayData);
		dialog = new ChoiceDialog<String>(dialogData.get(0), dialogData);
		String titleTxt = null;
		dialog.setTitle(titleTxt);
		dialog.setHeaderText("What do you want?");
		Optional<String> result = dialog.showAndWait();
		String selected = "cancelled.";

		if (result.isPresent()) {
			selected = result.get();
		}
		return selected;
	}

	private void showAddDialog() {
		// final String COMMAND_ADD = "add:\n--Adds new tasks (keyword add is
		// not required)\n--Adds"
		// + " <Task> due by <date> at <Venue> and sets reminder\n--Note: by
		// <Date>, @ <Venue>, remind <When>, priority <Level> are optional.\n";
		// Alert alert1 = new Alert(AlertType.INFORMATION);
		// alert1.setTitle("Add Commands");
		// alert1.setHeaderText(null);
		// alert1.setContentText(COMMAND_ADD);
		//
		// alert1.showAndWait();
		textInputArea.setText("[desc] from [start date] to [end date] at [location] priority [priority]");
		feedbackLabel.setText("");
	}
}