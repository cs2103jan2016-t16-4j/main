// @@author A0125417L
package application.gui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import application.logger.LoggerHandler;
import application.backend.LogicFacade;
import application.storage.Task;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Tasker extends Application {

	// Logger Messages
	private static final String CLOSE_CLICK_TRAY_LOGGER_MSG = "Clicked close on system tray icon";
	private static final String SHOW_CLICK_TRAY_LOGGER_MSG = "Clicked show on system tray icon";
	private static final String GUI_SETUP = "Setting up gui";

	// Error Messages
	private static final String LOAD_LOGO_FAIL_MSG = "Failed to load logo";
	private static final String GUI_LOAD_FAIL_MSG = "Failed to load GUI";

	// Initialization
	private static Logger logger = LoggerHandler.getLog();

	// Constants
	private static final String ROBOT_GIF_URL = "robot.gif";
	private static final String APPLICATION_NAME = "Tasker";
	private static final String EMPTY_STRING = "";
	private static final String BACKSLASH = "\\";
	private static final String CSS_URL = "application/gui/application.css";
	private static final String LOGO_URL = "robot.jpg";
	private static final String DIRECTORY_CHOOSER_TITLE = "Pick Where To Store Tasks";
	private static final String CURRENT_DIRECTORY = "user.dir";
	private static final String SYSTEM_TRAY_HINT = "Tasker is still running in the background.";
	private static final String SHOW_MENU_TEXT = "Show";
	private static final String EXIT_MENU_TEXT = "Exit";

	// Variables
	private TrayIcon trayIcon;
	private LogicFacade logicFacade;
	private ArrayList<Task> taskList;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws ExceptionHandler {
		logger.info(GUI_SETUP);
		try {
			setEnvironment();
			initializeSaveDirectory(primaryStage);
			CalendarViewPage page = new CalendarViewPage(taskList, logicFacade);
			Platform.setImplicitExit(false);
			Scene scene = new Scene(page);
			scene.getStylesheets().clear();
			scene.getStylesheets().add(CSS_URL);
			primaryStage.setScene(scene);
			customiseGUIMenuBar(primaryStage);
			primaryStage.setResizable(false);
			primaryStage.show();
			createTrayIcon(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
			logger.severe(GUI_LOAD_FAIL_MSG);
			throw new ExceptionHandler(GUI_LOAD_FAIL_MSG);
		}
	}

	private void customiseGUIMenuBar(Stage primaryStage) {
		setProgramName(primaryStage);
		setProgramLogo(primaryStage);
	}

	// Set program name
	private void setProgramName(Stage primaryStage) {
		primaryStage.setTitle(APPLICATION_NAME);
	}

	// Set program logo
	private void setProgramLogo(Stage primaryStage) {
		primaryStage.getIcons().add(new Image(ResourceLoader.load(LOGO_URL)));
	}

	private void setEnvironment() {
		this.logicFacade = new LogicFacade();
	}

	// Checks if user has used the program at least once if not prompt to save
	// file
	private void initializeSaveDirectory(Stage primaryStage) throws IOException {
		if (!logicFacade.checkIfFileExists()) {
			DirectoryChooser dirChooser = new DirectoryChooser();
			configureDirectoryChooser(dirChooser);
			directoryPrompt(primaryStage, dirChooser);
			taskList = logicFacade.loadDataFile();
		} else {
			taskList = logicFacade.loadDataFile();
		}
	}

	// Does the directory prompt then sends the data to logic accordingly
	public void directoryPrompt(Stage primaryStage, DirectoryChooser dirChooser) throws IOException {
		final File selectedDirectory = dirChooser.showDialog(primaryStage);
		if (selectedDirectory != null) {
			logicFacade.setDirectory(selectedDirectory.getPath().toString() + BACKSLASH);
		} else {
			logicFacade.setDirectory(EMPTY_STRING);
		}
	}

	// Configuration for directory chooser
	private void configureDirectoryChooser(final DirectoryChooser dirChooser) {
		dirChooser.setTitle(DIRECTORY_CHOOSER_TITLE);
		dirChooser.setInitialDirectory(new File(System.getProperty(CURRENT_DIRECTORY)));
	}

	// Create Tray Icon
	public void createTrayIcon(Stage primaryStage) {
		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			setToTray(primaryStage);
			ActionListener showListener = showApplication(primaryStage);
			ActionListener closeListener = closeTray();
			PopupMenu popup = popupMenuConfiguration(closeListener, showListener);
			trayIconConfiguration(showListener, popup);
			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				logger.severe(LOAD_LOGO_FAIL_MSG);
			}

		}
	}

	// Configuration for Tray Icon
	private void trayIconConfiguration(ActionListener showListener, PopupMenu popup) {
		java.awt.Image image = Toolkit.getDefaultToolkit()
				.getImage(getClass().getClassLoader().getResource((ROBOT_GIF_URL)));
		trayIcon = new TrayIcon(image, APPLICATION_NAME, popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(showListener);
	}

	// Show Application Menu
	private ActionListener showApplication(Stage primaryStage) {
		ActionListener showListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						logger.info(SHOW_CLICK_TRAY_LOGGER_MSG);
						primaryStage.show();
						primaryStage.setIconified(false);
					}
				});
			}
		};
		return showListener;
	}

	// Close Menu
	private ActionListener closeTray() {
		ActionListener closeListener = new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				logger.info(CLOSE_CLICK_TRAY_LOGGER_MSG);
				System.exit(0);
			}
		};
		return closeListener;
	}

	// Minimize to tray
	private void setToTray(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent t) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						if (SystemTray.isSupported()) {
							primaryStage.hide();
							showProgramIsMinimizedMsg();
						} else {
							System.exit(0);
						}
					}
				});
			}
		});
	}

	// create a popup menu for right clicking system tray icon
	private PopupMenu popupMenuConfiguration(final ActionListener closeListener, ActionListener showListener) {
		PopupMenu popup = new PopupMenu();

		MenuItem showItem = new MenuItem(SHOW_MENU_TEXT);
		showItem.addActionListener(showListener);
		popup.add(showItem);

		MenuItem closeItem = new MenuItem(EXIT_MENU_TEXT);
		closeItem.addActionListener(closeListener);
		popup.add(closeItem);
		return popup;
	}

	// Message when minimized
	public void showProgramIsMinimizedMsg() {
		trayIcon.displayMessage(APPLICATION_NAME, SYSTEM_TRAY_HINT, TrayIcon.MessageType.INFO);
	}

}