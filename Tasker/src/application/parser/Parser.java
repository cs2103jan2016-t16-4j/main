package application.parser;

import application.storage.Storage;
import application.parser.Command;
import org.apache.commons.lang.ArrayUtils;

/**
 * This is the class which takes in a userInput and interprets it. If user input
 * is not erroneous, it returns a command object which contains all the
 * information of the user input and also holds information on how to carry out
 * the request of the user.
 * 
 * @author Pratyush
 *
 */

public class Parser {
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
    
        
    private Storage storage;

    public Parser(Storage storage, String fileName) {
        this.storage = storage;
    }
    
    public Command interpretCommand(String userCommand) throws Exception {
        checkForError(userCommand);
        String[] inputArgs = userCommand.trim().split("\\s+");
        String commandKeyword = inputArgs[0]; //Always going to be zero so inputing magic number for now
        Command command = convertToCommand(commandKeyword, inputArgs);
        return command;
    }
    
    public Command convertToCommand(String keyword, String[] args){
        Command command;

        switch (keyword.toLowerCase()) {
            case KEYWORD_ADD :
                command = initializeAdd(args, WITH_KEYWORD );
                break;
            
            case KEYWORD_SEARCH :
                command = initializeSearch(args);
                break;
            
            case KEYWORD_DELETE :
                command = initializeDelete(args);
                break;
                
            case KEYWORD_UPDATE :
                command = initializeUpdate(args);
                break;
                
            case KEYWORD_DONE :
                command = initializeDone(args);
                break;
                
            case KEYWORD_UNDO :
                command = initializeUndo();
                break;
                
            case KEYWORD_HELP :
                command = initializeHelp();
                break;
            
            case KEYWORD_STORAGE :
                command = initializeStorageLocation(args);
                break;
                            
            case KEYWORD_EXIT :
                command = initializeExit();
                break;
                
            default :
                command = initializeAdd(args, !WITH_KEYWORD);
                break;
        }
        return command;

    }
    
    private Command initializeAdd(String[] args, boolean isWithKeyWord){
        if (isWithKeyWord){
            args = (String[]) ArrayUtils.remove(args, 0);
        }
        Command command = new Add (storage, args);
        return command;
    }
    
    private Command initializeSearch(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Search (storage, args);
        return command;
    }
    
    private Command initializeDelete(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Delete (storage, args);
        return command;
    }
    
    private Command initializeUpdate(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Update (storage, args);
        return command;
    }
    
    private Command initializeDone(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Done (storage, args);
        return command;
    }
    
    private Command initializeUndo(){
        Command command = new Undo (storage);
        return command;
    }
    
    private Command initializeHelp(){
        Command command = new Help (storage);
        return command;
    }
    
    private Command initializeStorageLocation(String[] args){
        Command command = new ChangeStorageLocation (storage);
        return command;
    }
    

    private Command initializeExit(){
        Command command = new Exit (storage);
        return command;
    }
        
    private void checkForError(String userCommand) throws Error {
        if (userCommand == null) {
            throw new Error(MESSAGE_NULL_ERROR);
        }
    }
    

    
}
