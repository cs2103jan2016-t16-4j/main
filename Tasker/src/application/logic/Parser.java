package application.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.LocalDateTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;


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
    private static final String EMPTY = "";
    
    private static final int ARGUMENT_NUMBER = 3;
    private static final int DESC_POS = 0;
    private static final int DATE_POS = 1;
    private static final int LOC_POS = 2;
    
    
    
    private static final boolean WITH_KEYWORD = true; //For add function. Since we accept no keyword.

    private static Logger logger = Logger.getLogger(LOGGER_NAME);

    private PrettyTimeParser dateParser = new PrettyTimeParser();

    
    public Command interpretCommand(String userCommand) throws NoDescriptionException /*throws Exception*/ {
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
    
    public Command convertToCommand(String keyword, String[] args) throws NoDescriptionException{
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
    
    private Command initializeAdd(String[] args, boolean isWithKeyWord) throws NoDescriptionException{
        if (isWithKeyWord){
            args = (String[]) ArrayUtils.remove(args, 0);
        }
        int dateStartIndex = getDateStartIndex(args);
        int locationStartIndex = Arrays.asList(args).lastIndexOf("at");
        String[] segments = getSegments(dateStartIndex, locationStartIndex, args);
        if (segments[DESC_POS].equals(EMPTY)){
            System.out.println("Exception");
            throw new NoDescriptionException();
        }
        Calendar[] dates = parseDates(segments[DATE_POS]);
        Calendar remindDate = convertToCalendar(createEmptyDate());
        Command command = new Add (segments[DESC_POS], dates[0], dates[1], segments[LOC_POS], remindDate);
        return command;
    }
    
   
    
    private Command initializeSearch(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        Command command = new Search (args);
        return command;
    }
    
    private Command initializeDelete(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        try{
            return getAppropDeleteCommand(args);
        }catch(NumberFormatException e){
            return getCommandDeleteByName(args);
        }
    }
    
    
    private Command initializeUpdate(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        int taskToUpdate = Integer.parseInt(args[0]);
        args = (String[]) ArrayUtils.remove(args, 0);
        int dateStartIndex = getDateStartIndex(args);
        int locationStartIndex = Arrays.asList(args).lastIndexOf("at");
        String[] segments = getSegments(dateStartIndex, locationStartIndex, args);
        Calendar[] dates = parseDates(segments[DATE_POS]);
        Calendar remindDate = convertToCalendar(createEmptyDate());
        Command command = new Update(taskToUpdate, segments[DESC_POS], dates[0], dates[1], segments[LOC_POS], remindDate);
        return command;
    }
    
    private Command initializeDone(String[] args){
        args = (String[]) ArrayUtils.remove(args, 0);
        try{
            return getAppropDoneCommand(args);
        }catch(NumberFormatException e){
            return getCommandDoneByName(args);
        }
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
    
    Requests the logic to call for new storage location from the GUI then sends the data to Storage
    private Command initializeStorageLocation(){
    	String args = logic.promptNewStorage();
        Command command = new ChangeStorageLocation (args);
        return command;
    }
    */ 
    

    private Command initializeExit(){
        Command command = new Exit ();
        return command;
    }
       


    private Command getAppropDeleteCommand(String[] args) {
        if (args.length == 1){
            int index = Integer.parseInt(args[0]);
            Command command = new DeleteByNum(index - 1);
            return command;
        }else{
            return getCommandDeleteByName(args);
        }
    }
    
    private Command getCommandDeleteByName(String[] args) {
        String taskToDelete = getString(args, 0, args.length - 1);
        Command command = new DeleteByName(taskToDelete);
        return command;
    }


    private Command getAppropDoneCommand(String[] args) {
        if (args.length == 1){
            int index = Integer.parseInt(args[0]);
            Command command = new DoneByNum(index - 1);
            return command;
        }else{
            return getCommandDoneByName(args);
        }
    }
    
    private Command getCommandDoneByName(String[] args) {
        String taskToDelete = getString(args, 0, args.length - 1);
        Command command = new DoneByName(taskToDelete);
        return command;
    }
    
    
    
    private Calendar[] parseDates(String dateString){
        List<Date> tempDates1 = dateParser.parse(dateString);
        List<Date> tempDates2 = dateParser.parse(dateString);
        Calendar startDate = getStartDate(tempDates1, tempDates2);
        Calendar endDate = getEndDate(tempDates1, tempDates2);
        System.out.println(startDate);
        System.out.println(endDate);
        Calendar[] dates = {startDate, endDate};
        return dates;        
    }
    
    private Calendar getStartDate(List<Date> tempDates1,List<Date> tempDates2){
        LocalDateTime date;
        if (tempDates1.size() == 0){
            date = createEmptyDate();
        } else if (tempDates1.size() == 1){
            date = createEmptyDate();
        }else{
            date = fixDate(tempDates1.get(0), tempDates2.get(0));
        }
        System.out.println(date);
        return convertToCalendar(date);
    }
    
    private Calendar getEndDate(List<Date> tempDates1,List<Date> tempDates2){
        LocalDateTime date;
        if (tempDates1.size() == 0){
            date = createEmptyDate();
        } else if (tempDates1.size() == 1){
            date = fixDate(tempDates1.get(0), tempDates2.get(0));
        }else{
            date = fixDate(tempDates1.get(1), tempDates2.get(1));
        }
        System.out.println(date);
        return convertToCalendar(date);
    }

    private LocalDateTime fixDate(Date date1, Date date2){
        if (date1.equals(date2)){
            return new LocalDateTime(date1);
        }else{
            LocalDateTime date = new LocalDateTime(date1);
            date = date.withMillisOfDay(1);
            return date;
        }
    }
    
    private LocalDateTime createEmptyDate() {
        LocalDateTime date = new LocalDateTime();
        date = date.withYear(1);
        date = date.withMillisOfDay(1);
        return date;
    }
    
    private Calendar convertToCalendar(LocalDateTime date){
        Date temp = date.toDate();
        Calendar cal = Calendar.getInstance();
        cal.setTime(temp);
        return cal;
    }
        
    private String[] getSegments(int dateIndex, int locationIndex, String[] args){
        String description = getDescription(dateIndex, locationIndex, args);;
        String date = getDateString(dateIndex, locationIndex, args);
        String location = getLocationString(dateIndex, locationIndex, args);
        String[] segments = new String[ARGUMENT_NUMBER];
        System.out.println(description);
        segments[DESC_POS] = description.trim();
        segments[DATE_POS] = date.trim();
        segments[LOC_POS] = location.trim();
        return segments;
    }

    private String getDescription(int dateIndex, int locationIndex, String[] args){
        String description;
        if (dateIndex == -1 && locationIndex == -1){
            description = getString(args, 0, args.length - 1);
        }else if ((dateIndex < locationIndex || locationIndex == -1) && dateIndex != -1){
            System.out.println(dateIndex);
            description = getString(args, 0 , dateIndex - 1 );
        }else{
            description = getString(args, 0 , locationIndex - 1 );
        }
        return description;
    }
    
    private String getDateString(int dateIndex, int locationIndex, String[] args) {
        if (dateIndex == -1){
            return EMPTY;
        }else if (dateIndex < locationIndex){
            return getString(args, dateIndex + 1 , locationIndex - 1 );
        }else{
            return getString(args, dateIndex + 1, args.length - 1);
        }
    }
    
    private String getLocationString(int dateIndex, int locationIndex, String[] args) {
        if (locationIndex == -1){
            return EMPTY;
        }else if (locationIndex < dateIndex){
            return getString(args, locationIndex + 1 , dateIndex - 1 );
        }else{
            return getString(args, locationIndex + 1, args.length - 1);
        }
    }
    
    private String getString(String[] args, int start, int end){
        String string = EMPTY;
        for(int i = start; i <= end; i++){
            string = string + args[i] + " ";
        }
        return string.trim();
    }
    
    private int getDateStartIndex(String[] args){
        int fromIndex = Arrays.asList(args).lastIndexOf("from");
        int byIndex = Arrays.asList(args).lastIndexOf("by");
        int dateIndex = fromIndex > byIndex ? fromIndex : byIndex; 
        return dateIndex;
    }
    

    
    private void checkForError(String userCommand) /*throws Error */ {
        if (userCommand == null) {
            logger.warning("null user commands");
            throw new Error(MESSAGE_NULL_ERROR);
        }
    }

}