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
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import application.logger.LoggerFormat;
import application.logic.Logic;
import application.storage.Task;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class GuiMain extends Application {
	private static final String APPLICATION_NAME = "Tasker";
	private static final String EMPTY_STRING = "";
	private static final String SPACE = "\\s+";
	private static final String BACKSLASH = "\\";
	private static final String CSS_URL = "application/gui/files/stylesheet.css";
	private static final String LOGO_URL = "robot.jpg";
	private TrayIcon trayIcon;

	private static final String DIRECTORY_CHOOSER_TITLE = "Pick Where To Store Tasks";
	private static final String CURRENT_DIRECTORY = "user.dir";
	private static final String LOGGER_NAME = "logfile";
	private static final String ERROR_LOGGER_INIT = "There was a problem trying to initialise logger.";

	private static final String SYSTEM_TRAY_HINT = "Tasker is still running in the background.";

	private static final String SHOW_MENU_TEXT = "Show";
	private static final String EXIT_MENU_TEXT = "Exit";

	private Logic logic;
	private Logger logger;
	private ArrayList<Task> taskList;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws ExceptionHandler {
		try {
			setEnvironment();
			initializeSaveDirectory(primaryStage);
			OpeningPage page = new OpeningPage(taskList, logic);
			Platform.setImplicitExit(false);
			Scene scene = new Scene(page);
			scene.getStylesheets().clear();
			scene.getStylesheets().add("application/gui/application.css");
			scene.getStylesheets().add(CSS_URL);
			primaryStage.setScene(scene);
			customiseGUIMenuBar(primaryStage);
			primaryStage.setResizable(false);
			primaryStage.show();
			createTrayIcon(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExceptionHandler("Failed to load GUI");
		}
	}

	private void customiseGUIMenuBar(Stage primaryStage) {
		setProgramName(primaryStage);
		setProgramLogo(primaryStage);
	}

	private void setProgramName(Stage primaryStage) {
		primaryStage.setTitle(APPLICATION_NAME);
	}

	private void setProgramLogo(Stage primaryStage) {

		primaryStage.getIcons().add(new Image(ResourceLoader.load(LOGO_URL)));
	}

	private void setEnvironment() {
		this.logic = new Logic();
		this.logger = Logger.getLogger(LOGGER_NAME);
		initializeLogger();
	}

	private void initializeSaveDirectory(Stage primaryStage) throws IOException {
		if (!logic.checkIfFileExists()) {
			DirectoryChooser dirChooser = new DirectoryChooser();
			configureDirectoryChooser(dirChooser);
			directoryPrompt(primaryStage, dirChooser);
			taskList = logic.loadDataFile();
		} else {
			taskList = logic.loadDataFile();
		}
	}

	public void directoryPrompt(Stage primaryStage, DirectoryChooser dirChooser) throws IOException {
		final File selectedDirectory = dirChooser.showDialog(primaryStage);
		if (selectedDirectory != null) {
			logic.setDirectory(selectedDirectory.getPath().toString() + BACKSLASH);
		} else {
			logic.setDirectory(EMPTY_STRING);
		}
	}

	private void configureDirectoryChooser(final DirectoryChooser dirChooser) {
		dirChooser.setTitle(DIRECTORY_CHOOSER_TITLE);
		dirChooser.setInitialDirectory(new File(System.getProperty(CURRENT_DIRECTORY)));
	}

	private void initializeLogger() {
		try {
			FileHandler fileHandler = new FileHandler("logfile.txt", true);
			LoggerFormat formatter = new LoggerFormat();
			fileHandler.setFormatter(formatter);
			logger.setUseParentHandlers(false);
			logger.addHandler(fileHandler);
		} catch (IOException e) {
			System.out.println(ERROR_LOGGER_INIT);
		}
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
				System.err.println(e);
			}

		}
	}

	// Configuration for Tray Icon
	private void trayIconConfiguration(ActionListener showListener, PopupMenu popup) {
		java.awt.Image image = Toolkit.getDefaultToolkit().getImage("src/application/gui/files/" + LOGO_URL);
		trayIcon = new TrayIcon(image, APPLICATION_NAME, popup);
		trayIcon.setImageAutoSize(false);
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
						logger.info("Clicked show on system tray icon");
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
				logger.info("Clicked close on system tray icon");
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
