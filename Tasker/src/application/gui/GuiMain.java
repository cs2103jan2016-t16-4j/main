package application.gui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import application.logger.LoggerFormat;
import application.logic.Logic;
import application.storage.Task;
import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class GuiMain extends Application {
    private static final String EMPTY_STRING = "";
    private static final String SPACE = "\\s+";
    private static final String BACKSLASH = "\\";

    private static final String DIRECTORY_CHOOSER_TITLE = "Pick Where To Store Tasks";
    private static final String CURRENT_DIRECTORY = "user.dir";
    private static final String LOGGER_NAME = "logfile";
    private static final String ERROR_LOGGER_INIT = "There was a problem trying to initialise logger.";

    private Logic logic;
    private Logger logger;
    private ArrayList<Task> taskList;
    
    public static void main(String[] args){
        launch(args);
    }
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        setEnvironment();
        initializeSaveDirectory(primaryStage);
        
    }


    private void setEnvironment() {
        this.logic = new Logic();
        this.logger = Logger.getLogger(LOGGER_NAME);
        initializeLogger();
    }

    private void initializeSaveDirectory(Stage primaryStage) throws IOException{
        if (logic.checkIfFileExists()){
            DirectoryChooser dirChooser = new DirectoryChooser();
            configureDirectoryChooser(dirChooser);
            directoryPrompt(primaryStage, dirChooser);
            logic.loadDataFile();
        }else{
            logic.loadDataFile();
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
        try{
            FileHandler fileHandler = new FileHandler("logfile.txt", true);
            LoggerFormat formatter = new LoggerFormat();
            fileHandler.setFormatter(formatter);
            logger.setUseParentHandlers(false);
            logger.addHandler(fileHandler);
        } catch(IOException e){
            System.out.println(ERROR_LOGGER_INIT);
        }
    }

}
