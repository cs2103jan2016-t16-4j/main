package application.parser;

import java.util.Arrays;

import application.storage.Storage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

public class Add implements Command{
    public static final int NOT_FOUND = -1;
    public static final String EMPTY = "";
    private static final String MESSAGE_ADD_FEEDBACK_NO_START_DATE = "Added Task: %1$s, by %2$s, at %3$s.";
    private static final String MESSAGE_ADD_FEEDBACK_WITH_START_DATE = "Added Task: %1$s, from %2$s to %3$s, at %4$s.";    
    private static final String MESSAGE_ADD_ERROR = 
            "We encountered an error while adding the task. Sorry for the inconvenience.";    
    
    
    String[] arguments;
    String description = EMPTY;
    String startDateTime = EMPTY;
    String endDateTime = EMPTY;
    String location = EMPTY;
    String remindDate = EMPTY;
    String priority = EMPTY;
    PrettyTimeParser dateParser; 
    
    Add(String[] arguments){
        this.arguments =arguments;
        dateParser = new PrettyTimeParser();
        interpretArguments(arguments);
        
    }
    
    private void interpretArguments (String[] arguments){
        int indexDateStart = setDateTime(arguments);
        this.description = getString(arguments, 0, indexDateStart - 1);
        setLocation(arguments);
    }
    
    private void setLocation(String[] args){
        int locationIndex = Arrays.asList(arguments).lastIndexOf("at");
        this.location = getString(arguments, locationIndex + 1, arguments.length - 1);
    }
    
    private int setDateTime(String[] arguments){
        int fromDateIndex = Arrays.asList(arguments).lastIndexOf("from");
        if (fromDateIndex == NOT_FOUND){
            int indexDateStart = setEndDateOnly(arguments);
            return indexDateStart;
        }else{
            int toDateIndex = Arrays.asList(arguments).lastIndexOf("to");
            int locationIndex = Arrays.asList(arguments).lastIndexOf("at");
            parseDateTime(arguments, fromDateIndex, toDateIndex, locationIndex);
        }
        return fromDateIndex;
    }
    
    private int setEndDateOnly(String[] args){
        int byDateIndex = Arrays.asList(arguments).lastIndexOf("by");
        int locationIndex = Arrays.asList(arguments).lastIndexOf("at");
        parseDateTime(args, byDateIndex, locationIndex);
        return byDateIndex;
    }
    
    private void parseDateTime (String[] args, int by, int loc){
        String endDateTime = getString(arguments, by + 1, loc - 1); //MAGIC NUMBERS
        this.endDateTime = interpretDate(endDateTime);
    }
    
    private void parseDateTime (String[] arguments, int from, int to, int loc){
        String startDateTime = getString(arguments, from + 1, to - 1); //MAGIC NUMBERS
        this.startDateTime = interpretDate(startDateTime);
        String endDateTime = getString(arguments, to + 1, loc - 1); //MAGIC NUMBERS
        this.endDateTime = interpretDate(endDateTime);
    }
    
    private String interpretDate(String date){
        List<Date> dateTimes = dateParser.parse(date);
        return dateTimes.get(0).toString();
    }
    
    
    private String getString(String[] args, int start, int end){
        String string = "";
        for(int i = start; i <= end; i++){
            string = string + args[i] + " ";
        }
        return string.trim();
    }
   
    public String execute(Storage storage){
        boolean isSuccess = storage.addTaskInList(description, startDateTime, 
                endDateTime,"", location, remindDate, priority);
        if (isSuccess){
            return makeFeedback();
        } else {
            return MESSAGE_ADD_ERROR;
        }
    }
    
    private String makeFeedback(){
        if (startDateTime.equals(EMPTY)){
            return String.format(MESSAGE_ADD_FEEDBACK_NO_START_DATE, description, endDateTime, location);
        } else {
            return String.format(MESSAGE_ADD_FEEDBACK_WITH_START_DATE, description, startDateTime, endDateTime, location);
        }
        
    }
}
