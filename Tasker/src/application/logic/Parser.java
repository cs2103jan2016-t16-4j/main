package application.logic;

//@@author A0132632R

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.LocalDateTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Collections;

/**
 * This is the class which takes in a userInput and interprets it. If user input
 * is not erroneous, it returns a command object which contains all the
 * information of the user input and also holds information on how to carry out
 * the request of the user.
 * 
 * @author Pratyush
 *
 */

//@@author A0132632R

public class Parser {
	private static final String LOGGER_NAME = "logfile";
	private static final String MESSAGE_NULL_ERROR = "command cannot be null";
	private static final String KEYWORD_ADD = "add";
	private static final String KEYWORD_SEARCH = "search";
	private static final String KEYWORD_HOME = "home";
	private static final String KEYWORD_DELETE = "delete";
	private static final String KEYWORD_UPDATE = "update";
	private static final String KEYWORD_DONE = "done";
	private static final String KEYWORD_UNDO = "undo";
	private static final String KEYWORD_HELP = "help";
	private static final String KEYWORD_STORAGE = "storage";
	private static final String KEYWORD_EXIT = "exit";
	private static final String EMPTY = "";

    private static final String[] DATE_MARKERS_START = {"from"};
    private static final String[] DATE_MARKERS_END = {"to", "till"};
    private static final String[] DATE_MARKERS_DEADLINE = {"by"};
	private static final String[] DATE_MARKERS_FULL_DAY_EVENT = {"on"};
	private static final String[] DATE_MARKERS_REMIND = {"remind"};
	private static final String[] LOCATION_MARKERS = {"at", "in"};
	private static final String[] PRIORITY_MARKERS = {"priority"};
    
	private static final String PRIORITY_HIGH = "high";
    private static final String PRIORITY_MEDIUM = "medium";
    private static final String PRIORITY_LOW = "low";
    private static final String[] PRIORITY_LEVELS = {PRIORITY_HIGH,PRIORITY_MEDIUM,PRIORITY_LOW};
    
    private static final int ARGUMENT_NUMBER = 4;
	private static final int DESC_POS = 0;
	private static final int DATE_POS = 1;
	private static final int LOC_POS = 2;
	private static final int PRI_POS = 3;
    private static final int ARRAY_INDEXING_OFFSET = 1;

	private static final boolean WITH_KEYWORD = true; // For add function. Since
														// we accept no keyword.

	private static Logger logger = Logger.getLogger(LOGGER_NAME);

	private PrettyTimeParser dateParser = new PrettyTimeParser();

	public Command interpretCommand(String userCommand)
			throws NoDescriptionException /* throws Exception */ {
		logger.info("Checking for error in user command: " + userCommand);
		checkForError(userCommand);
		logger.info("Splitting command into array of its words");
		String[] inputArgs = userCommand.trim().split("\\s+");
		logger.info("Getting command key word");
		String commandKeyword = inputArgs[0]; // Always going to be zero so
												// inputing magic number for now
		logger.info("Converting to command object: " + commandKeyword);
		Command command = convertToCommand(commandKeyword, inputArgs);
		logger.info("Returning command object");
		return command;
	}

	public Command convertToCommand(String keyword, String[] args) throws NoDescriptionException {
		Command command;

		switch (keyword.toLowerCase()) {
		case KEYWORD_ADD:
			logger.info("Making add command object");
			command = initializeAdd(args, WITH_KEYWORD);
			break;

		case KEYWORD_SEARCH:
			logger.info("Making search command object");
			command = initializeSearch(args);
			break;

		case KEYWORD_HOME:
			command = processHomeInput(args);
			break;

		case KEYWORD_DELETE:
			logger.info("Making delete command object");
			command = initializeDelete(args);
			break;

		case KEYWORD_UPDATE:
			logger.info("Making update command object");
			command = initializeUpdate(args);
			break;

		case KEYWORD_DONE:
			logger.info("Making done command object");
			command = initializeDone(args);
			break;

		case KEYWORD_UNDO:
			command = initializeUndo();
			break;
		/*
		 * case KEYWORD_HELP : command = initializeHelp(); break;
		 */
		case KEYWORD_STORAGE:
			command = initializeStorageLocation(args);
			break;

		case KEYWORD_EXIT:
			logger.info("Making exit command object");
			command = initializeExit();
			break;

		default:
			logger.info("Making add command object");
			command = initializeAdd(args, !WITH_KEYWORD);
			break;
		}
		logger.info("Returning command object");
		return command;

	}

	private Command processHomeInput(String[] args) throws NoDescriptionException {
		Command command;
		if (args.length == 1) {
			logger.info("Making home command object");
			command = initializeHome();
		} else {
			logger.info("Making add command object");
			command = initializeAdd(args, !WITH_KEYWORD);
		}
		return command;
	}

	private Command initializeAdd(String[] args, boolean isWithKeyWord) throws NoDescriptionException {
		args = removeKeyWordIfReq(args, isWithKeyWord);
		int dateStartIndex = getDateStartIndex(args);
		int locationStartIndex = getLastIndex(LOCATION_MARKERS, args);
		int priorityIndex = getLastIndex(PRIORITY_MARKERS, args);
		priorityIndex = fixPriorityIndex(priorityIndex, args);
		String[] segments = getSegments(dateStartIndex, locationStartIndex, priorityIndex, args);
		Calendar[] dates = parseDates(segments);
		if (segments[DESC_POS].equals(EMPTY)) {
			throw new NoDescriptionException();
		}
		Calendar[] datesFixed = fixDatesForAdd(dates);
		Calendar remindDate = convertToCalendar(createEmptyDate());
		Command command = new Add(segments[DESC_POS], datesFixed[0], datesFixed[1], segments[LOC_POS], remindDate, segments[PRI_POS]);
		return command;
	}

	private String[] removeKeyWordIfReq(String[] args, boolean isWithKeyWord) {
		if (isWithKeyWord) {
			return (String[]) ArrayUtils.remove(args, 0);
		}
		return args;
	}

	private Command initializeSearch(String[] args) {
		args = (String[]) ArrayUtils.remove(args, 0);
		Command command = getAppropSearchCommand(args);
		System.out.println("Search initialised");
		return command;
	}

	private Command initializeHome() {
		return new Home();
	}

	private Command initializeDelete(String[] args) {
		args = (String[]) ArrayUtils.remove(args, 0);
		try {
			return getAppropDeleteCommand(args);
		} catch (NumberFormatException e) {
			return getCommandDeleteByName(args);
		}
	}

	private Command initializeUpdate(String[] args) {
		args = (String[]) ArrayUtils.remove(args, 0);
		int taskToUpdate = Integer.parseInt(args[0]);
		args = (String[]) ArrayUtils.remove(args, 0);
		int dateStartIndex = getDateStartIndex(args);
		int locationStartIndex = getLastIndex(LOCATION_MARKERS, args);
		int priorityIndex = getLastIndex(PRIORITY_MARKERS, args);
        priorityIndex = fixPriorityIndex(priorityIndex, args);
        String[] segments = getSegments(dateStartIndex, locationStartIndex, priorityIndex, args);
		Calendar[] dates = parseDates(segments);
		Calendar remindDate = convertToCalendar(createEmptyDate());
		Command command = new Update(taskToUpdate, segments[DESC_POS], dates[0], dates[1], segments[LOC_POS],
				remindDate, segments[PRI_POS]);
		return command;
	}

	private Command initializeDone(String[] args) {
		args = (String[]) ArrayUtils.remove(args, 0);
		try {
			return getAppropDoneCommand(args);
		} catch (NumberFormatException e) {
			return getCommandDoneByName(args);
		}
	}

	private Command initializeUndo() {
		Command command = new Undo();
		return command;
	}

	/*
	 * private Command initializeHelp(){ Command command = new Help (); return
	 * command; }
	 */

	// Requests the logic to call for new storage location from the GUI then
	// sends the data to Storage
	private Command initializeStorageLocation(String[] args) {
		Command command = null;
		if (args.length == 2) {
			command = new ChangeStorageLocation(args[1]);
		} else {
			command = new ChangeStorageLocation("");
		}
		return command;
	}

	private Command initializeExit() {
		Command command = new Exit();
		return command;
	}

	private Command getAppropSearchCommand(String[] args) {
		try {
			return getSearchCommand(args);
		} catch (NotDateException e) {
			return getSearchByName(args);
		}

	}

	private Command getSearchCommand(String[] args) throws NotDateException {
		String[] argsForDate = (String[]) ArrayUtils.remove(args, 0);
		if (args[0].equalsIgnoreCase("by")) {
			return getSearchByDateCommand(argsForDate);
		} else if (args[0].equalsIgnoreCase("on")) {
			return getSearchOnDateCommand(argsForDate);
		} else {
			return getSearchByName(args);
		}
	}

	private Command getSearchByName(String[] args) {
		String taskName = getString(args, 0, args.length - 1);
		Command cmd = new SearchByName(taskName);
		return cmd;
	}

	private Command getSearchOnDateCommand(String[] args) throws NotDateException {
		Calendar date = getDateForSearch(args);
		Command cmd = new SearchOnDate(date);
		return cmd;
	}

	private Command getSearchByDateCommand(String[] args) throws NotDateException {
		Calendar date = getDateForSearch(args);
		Command cmd = new SearchByDate(date);
		return cmd;
	}

	private Calendar getDateForSearch(String[] args) throws NotDateException {
		String dateString = getString(args, 0, args.length - 1);
		List<Date> dates1 = dateParser.parse(dateString);
		List<Date> dates2 = dateParser.parse(dateString);
		if (dates1.size() == 0) {
			throw new NotDateException();
		}
		LocalDateTime date = fixDateForSearch(dates1.get(0), dates2.get(0));
		return convertToCalendar(date);
	}

	private LocalDateTime fixDateForSearch(Date date1, Date date2) {
		if (date1.equals(date2)) {
			return new LocalDateTime(date1);
		} else {
			LocalDateTime date = new LocalDateTime(date1);
			date = date.withHourOfDay(23);
			date = date.withMinuteOfHour(59);
			date = date.withSecondOfMinute(59);
			return date;
		}
	}

	private Command getAppropDeleteCommand(String[] args) {
		if (args.length == 1) {
			int index = Integer.parseInt(args[0]) - ARRAY_INDEXING_OFFSET;
			Command command = new DeleteByNum(index);
			return command;
		} else {
			return getCommandDeleteByName(args);
		}
	}

	private Command getCommandDeleteByName(String[] args) {
		String taskToDelete = getString(args, 0, args.length - 1);
		Command command = new DeleteByName(taskToDelete);
		return command;
	}

	private Command getAppropDoneCommand(String[] args) {
		if (args.length == 1) {
			int index = Integer.parseInt(args[0]) - ARRAY_INDEXING_OFFSET;
			Command command = new DoneByNum(index);
			return command;
		} else {
			return getCommandDoneByName(args);
		}
	}

	private Command getCommandDoneByName(String[] args) {
		String taskToDelete = getString(args, 0, args.length - 1);
		Command command = new DoneByName(taskToDelete);
		return command;
	}

	private Calendar[] parseDates(String[] segments) {
		String dateString = segments[DATE_POS];
		String dateTypeKeyword = dateString.split("\\s+")[0];
		List<Date> tempDates1 = dateParser.parse(dateString);
		List<Date> tempDates2 = dateParser.parse(dateString);
		changeSegmentsIfNeeded(segments, tempDates1.size());
		Calendar startDate = getStartDate(dateTypeKeyword, tempDates1, tempDates2);
		Calendar endDate = getEndDate(dateTypeKeyword, tempDates1, tempDates2);
		System.out.println(startDate);
		System.out.println(endDate);
		Calendar[] dates = { startDate, endDate };
		return dates;
	}

	private void changeSegmentsIfNeeded(String[] segments, int size) {
		if (size == 0) {
			segments[DESC_POS] = segments[DESC_POS].trim() + " " + segments[DATE_POS];
			segments[DESC_POS] = segments[DESC_POS].trim();
		}
	}

	private Calendar getStartDate(String keyword, List<Date> tempDates1, List<Date> tempDates2) {
		LocalDateTime date;
		if (tempDates1.size() == 0) {
			date = null;
		} else if (tempDates1.size() == 1) {
		    if (Arrays.asList(DATE_MARKERS_START).indexOf(keyword) != -1){
		        date = fixDate(tempDates1.get(0), tempDates2.get(0));
		    }else if (Arrays.asList(DATE_MARKERS_END).indexOf(keyword) != -1){
		        date = createEmptyDate();
		    }else if (Arrays.asList(DATE_MARKERS_FULL_DAY_EVENT).indexOf(keyword) != -1){
		        date = fixDate(tempDates1.get(0), tempDates2.get(0));
		        date = date.withMillisOfDay(0);
		    }else{
		        date = null;
		    }
		} else {
			date = fixDate(tempDates1.get(0), tempDates2.get(0));
		}
		//System.out.println(date);
		return convertToCalendar(date);
	}

	
	
	private Calendar getEndDate(String keyword, List<Date> tempDates1, List<Date> tempDates2) {
		LocalDateTime date;
		if (tempDates1.size() == 0) {
			date = null;
		} else if (tempDates1.size() == 1) {
		    if (Arrays.asList(DATE_MARKERS_START).indexOf(keyword) != -1){
                date = createEmptyDate();
            }else if (Arrays.asList(DATE_MARKERS_FULL_DAY_EVENT).indexOf(keyword) != -1){
                date = fixDate(tempDates1.get(0), tempDates2.get(0));
                date = date.withTime(23, 59, 59, 999);
            }else{
                date = fixDate(tempDates1.get(0), tempDates2.get(0));
            }
		} else {
			date = fixDate(tempDates1.get(1), tempDates2.get(1));
		}
		//System.out.println(date);
		return convertToCalendar(date);
	}

	private Calendar[] fixDatesForAdd(Calendar[] dates){
	    Calendar startDate = dates[0];
	    Calendar endDate = dates[1];
        if (startDate != null){
            if (startDate.equals(createEmptyDate())){
               startDate = (Calendar) endDate.clone();
               startDate.add(Calendar.HOUR, -2); 
            } else if (endDate.equals(createEmptyDate())){
                endDate = (Calendar) startDate.clone();
                endDate.add(Calendar.HOUR, -2); 
            }
        }
        Calendar[] fixedDates = {startDate, endDate};
        return fixedDates;
	}
	
	private LocalDateTime fixDate(Date date1, Date date2) {
		if (date1.equals(date2)) {
			return new LocalDateTime(date1);
		} else {
			LocalDateTime date = new LocalDateTime(date1);
			date = date.withMillisOfDay(1);
			System.out.println(date);
			return date;
		}
	}

	private LocalDateTime createEmptyDate() {
		LocalDateTime date = new LocalDateTime();
		date = date.withYear(1);
		date = date.withMillisOfDay(1);
		return date;
	}

	private Calendar convertToCalendar(LocalDateTime date) {
		if ( date == null){
		    return null;
		}
		Date temp = date.toDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(temp);
		return cal;
	}

	private String[] getSegments(int dateIndex, int locationIndex, int priorityIndex, String[] args) {
		String description = getDescription(dateIndex, locationIndex,priorityIndex, args);
		String date = getDateString(dateIndex, locationIndex,priorityIndex, args);
		String location = getLocationString(dateIndex, locationIndex,priorityIndex, args);
		String priority = getPriority(priorityIndex, args);
		String[] segments = new String[ARGUMENT_NUMBER];
		System.out.println(description);
		segments[DESC_POS] = description.trim();
		segments[DATE_POS] = date;
		segments[LOC_POS] = location.trim();
		segments[PRI_POS] = priority.trim();
		return segments;
	}

	private String getPriority(int priorityIndex, String[] args){
	    if (priorityIndex == -1){
	        return EMPTY;
	    } else{
	        return args[priorityIndex + 1];
	    }
	}
	
	private String getDescription(int dateIndex, int locationIndex, int priorityIndex, String[] args) {
		String description;
		int end = args.length;
		if (priorityIndex != -1){
		    end = priorityIndex;
		}
		if (dateIndex == -1 && locationIndex == -1) {
			description = getString(args, 0, end - 1);
		} else if ((dateIndex < locationIndex || locationIndex == -1) && dateIndex != -1) {
			System.out.println(dateIndex);
			description = getString(args, 0, dateIndex - 1);
		} else {
			description = getString(args, 0, locationIndex - 1);
		}
		return description;
	}

	private int fixPriorityIndex(int priorityIndex, String[] args){
	    int priorityLevelPosition = priorityIndex + 1 ;
	    List<String> levels = Arrays.asList(PRIORITY_LEVELS);
	    if (priorityLevelPosition == args.length){
	        return -1;
	    }else if (!levels.contains(args[priorityLevelPosition].toLowerCase())){
	        return -1;
	    }else{
	        return priorityIndex;
	    }
	}
	
	private String getDateString(int dateIndex, int locationIndex, int priorityIndex, String[] args) {
	    int end = args.length;
	    if (priorityIndex != -1){
	        end = priorityIndex;
	    }
	    if (dateIndex == -1) {
			return EMPTY;
		} else if (dateIndex < locationIndex) {
			return getString(args, dateIndex, locationIndex - 1);
		} else {
			return getString(args, dateIndex, end - 1);
		}
	}

	private String getLocationString(int dateIndex, int locationIndex, int priorityIndex, String[] args) {
	    int end = args.length;
        if (priorityIndex != -1){
            end = priorityIndex;
        }
        if (locationIndex == -1) {
			return EMPTY;
		} else if (locationIndex < dateIndex) {
			return getString(args, locationIndex + 1, dateIndex - 1);
		} else {
			return getString(args, locationIndex + 1, end - 1);
		}
	}

	private String getString(String[] args, int start, int end) {
		String string = EMPTY;
		for (int i = start; i <= end; i++) {
			string = string + args[i] + " ";
		}
		return string.trim();
	}

	private int getDateStartIndex(String[] args) {
		ArrayList<Integer> indices = new ArrayList<Integer>();
	    int eventDateIndex = getLastIndex(DATE_MARKERS_START, args);
		if (eventDateIndex == -1){
		    indices.add(getLastIndex(DATE_MARKERS_END, args));
		}else{
		    indices.add(eventDateIndex);
		}
		indices.add(getLastIndex(DATE_MARKERS_DEADLINE, args));
		indices.add(getLastIndex(DATE_MARKERS_FULL_DAY_EVENT, args));
        int dateIndex = (int) Collections.max(indices);
		return dateIndex;
	}
	
	private static int getLastIndex(String[] keywords, String[] commandWords){
	    int[] positions = new int[keywords.length];
	    String[] commandLowerCase = new String[commandWords.length];
	    for (int i = 0; i < commandWords.length; i++ ){
	        commandLowerCase[i] = commandWords[i].toLowerCase();
	    }
	    for (int i = 0; i < keywords.length; i++){
	        positions[i] = Arrays.asList(commandLowerCase).lastIndexOf(keywords[i].toLowerCase());
	    }
	    List<Integer> list = Arrays.asList(ArrayUtils.toObject(positions));
	    return ((int) Collections.max(list));
	}

	private void checkForError(String userCommand) /* throws Error */ {
		if (userCommand == null) {
			logger.warning("null user commands");
			throw new Error(MESSAGE_NULL_ERROR);
		}
	}

}