package application.parser;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;

import application.storage.Task;

/**
 * This is the class which takes in a userInput and interprets it. If user input
 * is not erroneous, it returns a command object which contains all the
 * information of the user input and also holds information on how to carry out
 * the request of the user.
 * 
 * @author Pratyush, Shawn
 *
 */

public class Parser {
    private static final String LOGGER_NAME = "logfile";
    private static final String MESSAGE_NULL_ERROR = "command cannot be null";
    private static final String KEYWORD_ADD = "add";
    private static final String KEYWORD_SEARCH = "search";
    private static final String KEYWORD_DELETE = "delete";
    private static final String KEYWORD_UPDATE = "update";
    private static final String KEYWORD_DONE = "done";
    private static final String KEYWORD_UNDO = "undo";
    private static final String KEYWORD_HELP = "help";
    private static final String KEYWORD_STORAGE = "storage";
    private static final String KEYWORD_EXIT = "exit";
    
    private static final boolean WITH_KEYWORD = true; //For add function. Since we accept no keyword.

    private static Logger logger = Logger.getLogger(LOGGER_NAME);

    

    
    public Command interpretCommand(String userCommand) /*throws Exception*/ {
        logger.info("Checking for error in user command: " + userCommand);
        checkForError(userCommand);
        logger.info("Splitting command into array of its words");
        String[] inputArgs = userCommand.trim().split("\\s+");
        logger.info("Getting command key word");
        String commandKeyword = inputArgs[0]; //Always going to be zero so inputing magic number for now
        logger.info("Converting to command object: " + commandKeyword);
        Command command = convertToCommand(commandKeyword, inputArgs);
        logger.info("Returning command object");
        return command;
    }
    
    public Command convertToCommand(String keyword, String[] args){
        Command command;

        switch (keyword.toLowerCase()) {
            case KEYWORD_ADD :
                logger.info("Making add command object");
                command = initializeAdd(args, WITH_KEYWORD );
                break;
            
            case KEYWORD_SEARCH :
                logger.info("Making search command object");
                command = initializeSearch(args);
                break;
            
            case KEYWORD_DELETE :
                logger.info("Making delete command object");
                command = initializeDelete(args);
                break;
              
            case KEYWORD_UPDATE :
                logger.info("Making update command object");
                command = initializeUpdate(args);
                break;
               
            case KEYWORD_DONE :
                logger.info("Making done command object");
                command = initializeDone(args);
                break;
            /*    
            case KEYWORD_UNDO :
                command = initializeUndo();
                break;
                
            case KEYWORD_HELP :
                command = initializeHelp();
                break;
            */
//            case KEYWORD_STORAGE :
//                command = initializeStorageLocation();
//                break;
                            
            case KEYWORD_EXIT :
                logger.info("Making exit command object");
                command = initializeExit();
                break;
                
            default :
                logger.info("Making add command object");
                command = initializeAdd(args, !WITH_KEYWORD);
                break;
        }
        logger.info("Returning command object");
        return command;

    }
    
    private Command initializeAdd(String[] args, boolean isWithKeyWord){
        if (isWithKeyWord){
            args = (String[]) ArrayUtils.remove(args, 0);
        }
        Command command = new Add (args);
        return command;
    }
    
    private Command initializeSearch(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Search (args);
        return command;
    }
    
    private Command initializeDelete(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Delete (args);
        return command;
    }
    
    private Command initializeUpdate(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Update ( args);
        return command;
    }
    
    private Command initializeDone(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Done ( args);
        return command;
    }
    /*
    
    private Command initializeUndo(){
        Command command = new Undo ();
        return command;
    }
    
    private Command initializeHelp(){
        Command command = new Help ();
        return command;
    }
    */ 
    
    // Requests the logic to call for new storage location from the GUI then sends the data to Storage
//    private Command initializeStorageLocation(){
//    	String args = logic.promptNewStorage();
//        Command command = new ChangeStorageLocation (args);
//        return command;
//    }
    

    private Command initializeExit(){
        Command command = new Exit ();
        return command;
    }
       
    private void checkForError(String userCommand) /*throws Error */ {
        if (userCommand == null) {
            logger.warning("null user commands");
            /*throw new Error(MESSAGE_NULL_ERROR);*/
        }
    }
    

    
}
