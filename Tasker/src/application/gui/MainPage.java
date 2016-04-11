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
import application.logger.LoggerHandler;
import application.backend.Feedback;
import application.backend.NoDescriptionException;
import application.backend.BackendFacade;
import application.storage.EventTask;
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
import javafx.scene.control.SelectionMode;
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

public class MainPage extends AnchorPane {

	// Constants
	private static final String SUMMARY_TEXT = "summary";
	private static final String VIEW_TEXT = "view";
	private static final String SU_TEXT = "su";
	private static final String ST_TEXT = "st";
	private static final String HOME_TEXT = "home";
	private static final String HO_TEXT = "ho";
	private static final String HE_TEXT = "he";
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
	private static final String UP_TEXT = "up";
	private static final String U_TEXT = "u";
	private static final String DELETE_TEXT = "delete";
	private static final String DONE_TEXT = "done";
	private static final String DO_TEXT = "do";
	private static final String DE_TEXT = "de";
	private static final String D_TEXT = "d";
	private static final String H_TEXT = "h";
	private static final String A_TEXT = "a";
	private static final String V_TEXT = "v";
	private static final String HELP_TEXT = "help";
	private static final String MAIN_PAGE_FXML_URL = "MainPage.fxml";
	private static final String LIST_FLAG = "list";
	private static final String CAL_FLAG = "cal";
	private static final String HELP_FLAG = HELP_TEXT;
	private static final String STORAGE_FLAG = STORAGE_TEXT;
	private static final String VIEW_CHANGE_FLAG = VIEW_TEXT;
	private static final String SUMMARY_FLAG = SUMMARY_TEXT;
	private static final String SPACE = " ";
	private static final String EMPTY_STRING = "";
	private static final String LOCATION_PREFIX = "AT";
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
	private static final int STACK_PANE_FIRST_CHILD = 0;
	private static final int CLASH_DETECTION_VARIABLE = 1;

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// Formats
	private static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat("dd MMM yyyy");

	// Messages
	private static final String CLASH_NOTIFICATION_MSG = "Please Note The Highlighted Clashes";
	private static final String CLASH_NOTIFICATION_TITLE = "Clash has been detected";
	private static final String ADD_HINT_MESSAGE = "To add: [task description] from [start] to [end] at [location] priority [priority]";
	private static final String HELP_HINT_MESSAGE = "To get help: help";
	private static final String DELETE_HINT_MESSAGE = "To delete: delete [task description/number]";
	private static final String SEARCH_HINT_MESSAGE = "To search: search [task description/priority [level]/by [date]/on [date]]";
	private static final String EXIT_HINT_MESSAGE = "To exit: exit";
	private static final String HOME_HINT_MESSAGE = "To go back home and view all tasks: home";
	private static final String UPDATE_HINT_MESSAGE = "To update: update [task number] [new task description"
			+ "/priority [level]/from [start] to [end]/at [location]] ";
	private static final String UNDO_HINT_MESSAGE = "To undo: undo";
	private static final String STORAGE_HINT_MESSAGE = "To change storage: storage [url]";
	private static final String SUMMARY_HINT_MESSAGE = "To view an overview of tasks: summary";
	private static final String DONE_HINT_MESSAGE = "To mark task as complete: done [task number]";
	private static final String VIEW_HINT_MESSAGE = "To Toggle Views: view";
	private static final String MESSAGE_HELP_INTRO = "Start typing and we'll help you out!";
	private static final String MESSAGE_FEEDBACK_INTRO = "We'll give you feedback on your commands here.";
	private static final String MESSAGE_ERROR = "There was some problem processing your request. "
			+ "Please check your input format.";
	private static final String ADD_HINT_INPUT = "[desc] from [start date] to [end date] at [location] priority [priority]";
	private static final String DELETE_HINT_INPUT = "delete [task description/number]";
	private static final String UPDATE_HINT_INPUT = "update [task number] [new task desc]";
	private static final String DONE_HINT_INPUT = "Done [task number]";
	private static final String SEARCH_HINT_INPUT = "search [task decription/priority [level]/[task description] by [date]]";
	private static final String UNDO_HINT_INPUT = "Undo";
	private static final String STORAGE_HINT_INPUT = "Storage";
	private static final String EXIT_HINT_INPUT = "Exit";

	// Error Messages
	private static final String FXML_LOAD_FAILED = "Failed to load ListView FXML file";
	private static final String DIRECTORY_NOT_CHANGED_MESSAGE = "Directory Not Changed";

	// Variables
	private static int pointer = START;
	private Task taskToFocus;
	private String checkFlag;
	private String text = EMPTY_STRING;
	private static ArrayList<String> commands = new ArrayList<String>();
	private ArrayList<Task> tasksOnScreen;
	private BackendFacade backendFacade;
	TranslateTransition openPanel;
	TranslateTransition closePanel;

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

	// @@author A0132632R
	public MainPage(ArrayList<Task> taskList, BackendFacade backendFacade) {
		tasksOnScreen = taskList;
		this.backendFacade = backendFacade;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(MAIN_PAGE_FXML_URL));
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
		initializeHiddenPanel();
		updateViews(tasksOnScreen, taskToFocus);
	}

	// @@author A0125417L

	/*
	 * Setup hidden panel
	 */
	private void initializeHiddenPanel() {
		openPanel = new TranslateTransition(new Duration(TRANSITION_TIME), hiddenMenu);
		openPanel.setToX(STARTPOSITION);
		closePanel = new TranslateTransition(new Duration(TRANSITION_TIME), hiddenMenu);
	}

	/*
	 * Setup cell factory for task list view
	 */
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
							assert (item == null);
							setGraphic(null);
						}
					}
				};
				return cell;
			}
		});
	}

	/*
	 * Setup cell factory for calendar view
	 */
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
							assert (item == null);
							setGraphic(null);
						}
					}
				};
				return cell;
			}
		});
	}

	/*
	 * Set date accordingly
	 */
	private String setDate(ArrayList<Task> item) {
		String date = null;
		if (item.get(START).getEndDate() != null) {
			date = FORMAT_DATE.format(item.get(START).getEndDate().getTime());
		}
		return date;
	}

	/*
	 * Check if task is overdue
	 */
	private int checkIfOverdue(Task item, Calendar cal) {
		int overdueCheck = overdueCheckVariable;
		if (!(item instanceof EventTask)) {
			overdueCheck = checkNonEventTaskOverdue(item, cal, overdueCheck);
		} else {
			overdueCheck = checkEventTaskOverdue(item, cal, overdueCheck);
		}
		return overdueCheck;
	}

	/*
	 * Check if non event task is overdue
	 */
	private int checkNonEventTaskOverdue(Task item, Calendar cal, int overdueCheck) {
		if (item.getEndDate() != null) {
			overdueCheck = item.getEndDate().getTime().compareTo(cal.getTime());
		}
		return overdueCheck;
	}

	/*
	 * Check if event task is overdue
	 */
	private int checkEventTaskOverdue(Task item, Calendar cal, int overdueCheck) {
		assert (item instanceof EventTask);
		if (item.getStartDate() != null) {
			overdueCheck = item.getStartDate().getTime().compareTo(cal.getTime());
		}
		return overdueCheck;
	}

	/*
	 * Format for the location section of task list view
	 */
	private String getLocationString(Task task) {
		String location = task.getLocation();
		if (location.trim().equals(EMPTY_STRING)) {
			return EMPTY_STRING;
		} else {
			assert (!location.trim().equals(EMPTY_STRING));
			return (LOCATION_PREFIX + SPACE + location);
		}
	}

	/*
	 * Set the data for the calendar view
	 */
	private ArrayList<ArrayList<Task>> getDateArray(ArrayList<Task> taskList) {
		String tempoDate = null;
		ArrayList<ArrayList<Task>> dateArray = new ArrayList<ArrayList<Task>>();
		ArrayList<Task> temporaryList = new ArrayList<Task>();
		for (int i = START; i < taskList.size(); i++) {
			checkIfNonFloatingTask(taskList, tempoDate, dateArray, temporaryList, i);
			temporaryList = checkIfReachedFloatingTask(taskList, tempoDate, dateArray, temporaryList, i);
			tempoDate = setTempoDate(taskList, i);
		}
		checkTemporaryListBeforeAdd(dateArray, temporaryList);
		return dateArray;
	}

	/*
	 * If non floating task, check if previous task date is the same or not
	 * 
	 * If its the same, add to that task list else add the task list to the list
	 * of task lists, create a new task list and add to the new task list
	 */
	private void checkIfNonFloatingTask(ArrayList<Task> taskList, String tempoDate,
			ArrayList<ArrayList<Task>> dateArray, ArrayList<Task> temporaryList, int i) {
		if (!(taskList.get(i) instanceof FloatingTask) && tempoDate != null) {
			if (tempoDate.equals(FORMAT_DATE.format(taskList.get(i).getEndDate().getTime()))) {
				temporaryList.add(taskList.get(i));
			} else {
				assert (!tempoDate.equals(FORMAT_DATE.format(taskList.get(i).getEndDate().getTime())));
				dateArray.add(temporaryList);
				temporaryList = new ArrayList<Task>();
				temporaryList.add(taskList.get(i));
			}
		}

		if (!(taskList.get(i) instanceof FloatingTask) && tempoDate == null) {
			temporaryList.add(taskList.get(i));
		}
	}

	/*
	 * As floating tasks are at the end of the list, checks if the temporary
	 * date is a non floating task date and current task is a floating task
	 * 
	 * If they are then, create a new temporary list to add floating task else
	 * continue adding floating task to the list if temporary date is of
	 * floating task
	 */
	private ArrayList<Task> checkIfReachedFloatingTask(ArrayList<Task> taskList, String tempoDate,
			ArrayList<ArrayList<Task>> dateArray, ArrayList<Task> temporaryList, int i) {
		if ((taskList.get(i) instanceof FloatingTask) && tempoDate != null) {
			dateArray.add(temporaryList);
			temporaryList = new ArrayList<Task>();
			temporaryList.add(taskList.get(i));
		}

		if ((taskList.get(i) instanceof FloatingTask) && tempoDate == null) {
			temporaryList.add(taskList.get(i));
		}
		return temporaryList;
	}

	/*
	 * Checks if the temporary list is empty before adding to prevent null
	 * pointer later on when showing the calendar view
	 */
	private void checkTemporaryListBeforeAdd(ArrayList<ArrayList<Task>> dateArray, ArrayList<Task> temporaryList) {
		if (temporaryList.size() != EMPTY) {
			dateArray.add(temporaryList);
		}
	}

	/*
	 * Set temporary date for checking if date is the same as previously checked
	 * date
	 */
	private String setTempoDate(ArrayList<Task> taskList, int i) {
		String tempoDate;
		if (!(taskList.get(i) instanceof FloatingTask)) {
			tempoDate = FORMAT_DATE.format(taskList.get(i).getEndDate().getTime());
		} else {
			assert ((taskList.get(i) instanceof FloatingTask));
			tempoDate = null;
		}
		return tempoDate;
	}

	/*
	 * Method that calls other methods to update the data
	 */
	private void updateViews(ArrayList<Task> taskList, Task taskToFocus) {
		ArrayList<Task> clashList = null;
		clashList = getClashList(taskToFocus, clashList);
		updateCalendarList(taskList, taskToFocus);
		updateDisplayList(taskList, clashList, taskToFocus);
		updateSummary();
	}

	private ArrayList<Task> getClashList(Task taskToFocus, ArrayList<Task> clashList) {
		if (taskToFocus != null) {
			clashList = backendFacade.getClashes(taskToFocus);
		}
		return clashList;
	}

	/*
	 * Update the side panel
	 */
	private void updateSummary() {
		setSummaryLabels();
		setPieChart();

	}

	/*
	 * Setup Labels for summary panel
	 */
	private void setSummaryLabels() {
		completedLabel.setText(String.valueOf(backendFacade.getCompletedTaskCount()));
		remainingLabel.setText(String.valueOf(backendFacade.getRemainingTaskCount()));
		overdueLabel.setText(String.valueOf(backendFacade.getOverdueTaskCount()));
	}

	/*
	 * Setup pie chart for summary panel
	 */
	private void setPieChart() {
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
				new PieChart.Data(COMPLETED_TASKS_TEXT, backendFacade.getCompletedTaskCount()),
				new PieChart.Data(REMAINING_TASKS_TEXT, backendFacade.getRemainingTaskCount()),
				new PieChart.Data(OVERDUE_TASKS_TEXT, backendFacade.getOverdueTaskCount()));
		pieChart.setData(pieChartData);
		pieChart.setLabelsVisible(false);
		pieChart.setLegendSide(Side.BOTTOM);
	}

	/*
	 * Notification behavior for adding or updating tasks
	 */
	private void notifyUser(Task taskToFocus) {
		String text = null;
		if (taskToFocus != null) {
			text = noEndDateNotificationSetting(taskToFocus, text);
			text = noStartDateNotificationSetting(taskToFocus, text);
			text = floatingTaskNotificationSetting(taskToFocus, text);
			showNotification(text);
		}
	}

	/*
	 * Show notifications
	 */
	private void showNotification(String text) {
		if (text != null) {
			String title = REMINDER_TEXT;
			Notifications.create().title(title).text(text).showInformation();
		}
	}

	/*
	 * Set floating task notification details
	 */
	private String floatingTaskNotificationSetting(Task taskToFocus, String text) {
		if (taskToFocus.getStartDate() == null && taskToFocus.getEndDate() == null) {
			text = NO_DATES_TEXT;
		}
		return text;
	}

	/*
	 * Set no start date notification details
	 */
	private String noStartDateNotificationSetting(Task taskToFocus, String text) {
		if (taskToFocus.getStartDate() == null) {
			text = NO_START_DATE_TEXT;
		}
		return text;
	}

	/*
	 * Set no end date notification details
	 */
	private String noEndDateNotificationSetting(Task taskToFocus, String text) {
		if (taskToFocus.getEndDate() == null) {
			text = NO_END_DATE_TEXT;
		}
		return text;
	}

	/*
	 * Updates data of the task view
	 */
	private void updateDisplayList(ArrayList<Task> taskList, ArrayList<Task> clashList, Task taskToFocus2) {
		this.displayList.getItems().clear();
		if (taskList.size() != EMPTY) {
			ObservableList<Task> list = makeDisplayList(taskList);
			this.displayList.setItems(list);
			selectAllClashItems(clashList, taskToFocus);
		}
	}

	/*
	 * Selects all clash items in red and/or the task that has been added or
	 * updated blue
	 */
	private void selectAllClashItems(ArrayList<Task> clashList, Task taskToFocus) {
		if (clashList != null) {
			notifyIfClash(clashList);
			this.displayList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			for (Task task : clashList) {
				if (!task.equals(taskToFocus)) {
					this.displayList.getSelectionModel().select(task);
				}
			}
			this.displayList.getSelectionModel().select(taskToFocus);
			this.displayList.scrollTo(taskToFocus);
		}
	}

	/*
	 * Notify users if there is a clash or not
	 */
	private void notifyIfClash(ArrayList<Task> clashList) {
		if (clashList.size() > CLASH_DETECTION_VARIABLE) {
			Notifications.create().title(CLASH_NOTIFICATION_TITLE).text(CLASH_NOTIFICATION_MSG).showInformation();
		}
	}

	/*
	 * Updates data of the calendar view
	 */
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

	/*
	 * Sets behavior of the text area
	 */
	public void initializeInputArea() {
		textInputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent ke) {
				if (ke.getCode().equals(KeyCode.ENTER)) {
					executeCommands();
				}

				if (ke.getCode().equals(KeyCode.UP)) {
					previousRecentlyUsedCommands();
				}

				if (ke.getCode().equals(KeyCode.DOWN)) {
					nextRecentlyUsedCommand();
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

	/*
	 * Execute commands on enter key pressed at text area
	 */
	private void executeCommands() {
		try {
			text = textInputArea.getText();
			commands.add(text);
			Feedback feedback = backendFacade.executeCommand(text, tasksOnScreen);
			tasksOnScreen = feedback.getTasks();
			taskToFocus = feedback.getTaskToScrollTo();
			notifyUser(taskToFocus);
			updateViews(tasksOnScreen, taskToFocus);
			checkFlag = feedback.getFlag();
			feedbackLabel.setText(feedback.getMessage());
			doFlagCommand(checkFlag, feedback);
			if (checkFlag != HELP_FLAG) {
				textInputArea.clear();
			}
			closePanel();
		} catch (Exception e) {
			feedbackLabel.setText(MESSAGE_ERROR);
		}
	}

	/*
	 * Previous recently used commands upon up pressed in the text area
	 */
	private void previousRecentlyUsedCommands() {
		// if used commands list is not empty
		if (!commands.isEmpty()) {
			checkPointerPosition();
			// if pointer is not at the front end
			if (pointer != START) {
				textInputArea.setText(commands.get(pointer - previous));
			}
		}
	}

	/*
	 * Next recently used commands upon down pressed in the text area
	 */
	private void nextRecentlyUsedCommand() {
		if (!commands.isEmpty()) {
			checkPointerPosition();
			// if pointer is not at the end
			if (pointer != commands.size() - OFFSET) {
				textInputArea.setText(commands.get(pointer + NEXT));
			}
		}
	}

	/*
	 * Check pointer position for recently used commands
	 */
	private void checkPointerPosition() {
		if (!commands.contains(textInputArea.getText())) {
			pointer = commands.size();
		} else {
			assert (commands.contains(textInputArea.getText()));
			pointer = commands.indexOf(textInputArea.getText());
		}
	}

	/*
	 * Do flag command
	 */
	private void doFlagCommand(String checkFlag, Feedback feedback) throws IOException {
		switch (checkFlag) {
		case STORAGE_FLAG:
			promptStorage(feedback);
			break;
		case CAL_FLAG:
			calendarList.toFront();
			break;
		case HELP_FLAG:
			helpFunction();
			break;
		case LIST_FLAG:
			displayList.toFront();
			break;
		case VIEW_CHANGE_FLAG:
			switchViews();
			break;
		case SUMMARY_FLAG:
			toggleHiddenPanel();
			break;
		}
	}

	/*
	 * Toggle hidden panel
	 */
	private void toggleHiddenPanel() {
		if (hiddenMenu.getTranslateX() != STARTPOSITION) {
			openPanel.play();
		} else {
			assert (hiddenMenu.getTranslateX() == STARTPOSITION);
			closePanel.setToX(+(hiddenMenu.getWidth()));
			closePanel.play();
		}
	}

	/*
	 * Close hidden panel
	 */
	private void closePanel() {
		if (hiddenMenu.getTranslateX() != STARTPOSITION) {
			openPanel.play();
		}
	}

	/*
	 * Toggle Views
	 */
	private void switchViews() {
		if (stackPane.getChildren().get(STACK_PANE_FIRST_CHILD).equals(displayList)) {
			displayList.toFront();
		} else {
			assert (!stackPane.getChildren().get(STACK_PANE_FIRST_CHILD).equals(displayList));
			calendarList.toFront();
		}
	}

	/*
	 * Prompt for directory change
	 */
	public void directoryPrompt(Stage primaryStage, DirectoryChooser dirChooser) throws IOException {
		final File selectedDirectory = dirChooser.showDialog(primaryStage);
		if (selectedDirectory != null) {
			try {
				Feedback feedback = backendFacade
						.executeCommand(STORAGE_TEXT + SPACE + selectedDirectory.getPath().toString(), tasksOnScreen);
				feedbackLabel.setText(feedback.getMessage());
			} catch (NoDescriptionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			assert (selectedDirectory == null);
			backendFacade.setDirectory(EMPTY_STRING);
			feedbackLabel.setText(DIRECTORY_NOT_CHANGED_MESSAGE);
		}
	}

	/*
	 * Setup for directory change
	 */
	private void promptStorage(Feedback feedback) throws IOException {
		DirectoryChooser dirChooser = new DirectoryChooser();
		configureDirectoryChooser(dirChooser);
		Stage stage = new Stage();
		directoryPrompt(stage, dirChooser);
	}

	/*
	 * Configure directory change dialog
	 */
	private void configureDirectoryChooser(final DirectoryChooser dirChooser) {
		dirChooser.setTitle(DIRECTORY_CHOOSER_TITLE);
		dirChooser.setInitialDirectory(new File(System.getProperty(CURRENT_DIRECTORY)));
	}

	/*
	 * Update hint label
	 */
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
			assert (!newWord.equals(oldWord));
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
				checkV(newValue, helpLabel, newWord);
				break;
			default:
				checkA(helpLabel);
				break;
			}
		}
	}

	/*
	 * Checks for change in v to switch between view and default hints
	 */
	private void checkV(String newValue, Label helpLabel, String newWord) {
		checkString(newValue, helpLabel, newWord, VIEW_HINT_MESSAGE, VIEW_TEXT);
	}

	/*
	 * Checks for change in s to switch between search, storage, summary and
	 * default hints
	 */
	private void checkS(String newValue, Label helpLabel, String newWord) {
		helpLabel.setText(SEARCH_HINT_MESSAGE);
		if (!newValue.isEmpty() && newValue.length() > S_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(SE_TEXT)) {
				checkString(newValue, helpLabel, newWord, SEARCH_HINT_MESSAGE, SEARCH_TEXT);
			} else if (getTwoLetters(newValue).equalsIgnoreCase(ST_TEXT)) {
				checkString(newValue, helpLabel, newWord, STORAGE_HINT_MESSAGE, STORAGE_TEXT);
			} else if (getTwoLetters(newValue).equalsIgnoreCase(SU_TEXT)) {
				checkString(newValue, helpLabel, newWord, SUMMARY_HINT_MESSAGE, SUMMARY_TEXT);
			} else {
				checkA(helpLabel);
			}
		}
	}

	/*
	 * Checks for change in e to switch between exit and default hints
	 */
	private void checkE(String newValue, Label helpLabel, String newWord) {
		checkString(newValue, helpLabel, newWord, EXIT_HINT_MESSAGE, EXIT_TEXT);
	}

	/*
	 * Checks for change in u to switch between update, undo and default hints
	 */
	private void checkU(String newValue, Label helpLabel, String newWord) {
		helpLabel.setText(UPDATE_HINT_MESSAGE);
		if (!newValue.isEmpty() && newValue.length() > U_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(UN_TEXT)) {
				checkString(newValue, helpLabel, newWord, UNDO_HINT_MESSAGE, UNDO_TEXT);
			} else if (getTwoLetters(newValue).equalsIgnoreCase(UP_TEXT)) {
				checkString(newValue, helpLabel, newWord, UPDATE_HINT_MESSAGE, UPDATE_TEXT);
			} else {
				checkA(helpLabel);
			}
		}
	}

	/*
	 * Checks for change in d to switch between done, delete and default hints
	 */
	private void checkD(String newValue, Label helpLabel, String newWord) {
		if (!newValue.isEmpty() && newValue.length() > D_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(DO_TEXT)) {
				checkString(newValue, helpLabel, newWord, DONE_HINT_MESSAGE, DONE_TEXT);
			} else if (getTwoLetters(newValue).equalsIgnoreCase(DE_TEXT)) {
				checkString(newValue, helpLabel, newWord, DELETE_HINT_MESSAGE, DELETE_TEXT);
			} else {
				checkA(helpLabel);
			}
		}
	}

	/*
	 * Default hint
	 */
	private void checkA(Label helpLabel) {
		helpLabel.setText(ADD_HINT_MESSAGE);
	}

	/*
	 * Checks for change in h to switch between 
	 */
	private void checkH(String newValue, Label helpLabel, String newWord) {
		helpLabel.setText(HELP_HINT_MESSAGE);
		if (!newValue.isEmpty() && newValue.length() > H_TEXT.length()) {
			if (getTwoLetters(newValue).equalsIgnoreCase(HE_TEXT)) {
				checkString(newValue, helpLabel, newWord, HELP_HINT_MESSAGE, HELP_TEXT);
			} else if (getTwoLetters(newValue).equalsIgnoreCase(HO_TEXT)) {
				checkString(newValue, helpLabel, newWord, HOME_HINT_MESSAGE, HOME_TEXT);
			} else {
				checkA(helpLabel);
			}
		}
	}

	/*
	 * Checks the string and sets hint message accordingly
	 */
	private void checkString(String newValue, Label helpLabel, String newWord, String message, String command) {
		helpLabel.setText(message);
		if (!newValue.isEmpty() && newValue.length() >= command.length()) {
			defaultText(helpLabel, newWord, command);
		}
	}

	/*
	 * Checks if label is correct else, set default label
	 */
	private void defaultText(Label helpLabel, String newWord, String check) {
		if (!newWord.equalsIgnoreCase(check)) {
			checkA(helpLabel);
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

	// @@author A0078688A

	private void helpFunction() {
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
		case UNDO_HINT_INPUT:
			showUndoDialog();
			break;
		case STORAGE_HINT_INPUT:
			showStorageDialog();
			break;
		case EXIT_HINT_INPUT:
			showExitDialog();
			break;
		default:
			break;
		}
	}

	private void showExitDialog() {
		textInputArea.setText(EXIT_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(EXIT_HINT_MESSAGE);
	}

	private void showStorageDialog() {
		textInputArea.setText(STORAGE_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(STORAGE_HINT_MESSAGE);
	}

	private void showUndoDialog() {
		textInputArea.setText(UNDO_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(UNDO_HINT_MESSAGE);
	}

	private void showSearchDialog() {
		textInputArea.setText(SEARCH_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(SEARCH_HINT_MESSAGE);
	}

	private void showCloseDialog() {
		textInputArea.setText(DONE_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(DONE_HINT_MESSAGE);
	}

	private void showUpdateDialog() {
		textInputArea.setText(UPDATE_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(UPDATE_HINT_MESSAGE);
	}

	private void showDeleteDialog() {
		textInputArea.setText(DELETE_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
		helpLabel.setText(DELETE_HINT_MESSAGE);
	}

	private String showHelpDialog() {
		ChoiceDialog<String> dialog;
		final String[] arrayData = { "Add", "Delete", "Update", "Close", "Search", UNDO_HINT_INPUT, STORAGE_HINT_INPUT,
				EXIT_HINT_INPUT };
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
		textInputArea.setText(ADD_HINT_INPUT);
		feedbackLabel.setText(EMPTY_STRING);
	}
}