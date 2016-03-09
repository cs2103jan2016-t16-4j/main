package application.parser;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import application.storage.Storage;

public class Update implements Command{
    public static final int NOT_FOUND = -1;
    public static final int INDEX_TASK_POSITION = 0;
    public static final int ARRAY_INDEXING_OFFSET = 1;
    public static final String EMPTY = "";
    private static final String MESSAGE_UPDATE_ERROR = 
            "We encountered an error while updating the task. Sorry for the inconvenience.";    
    
    
    String[] arguments;
    int taskPosition = -1;
    String description = EMPTY;
    String startDateTime = EMPTY;
    String endDateTime = EMPTY;
    String startTime = EMPTY;
    String endTime = EMPTY;
    String location = EMPTY;
    String remindDate = EMPTY;
    String priority = EMPTY;
    PrettyTimeParser dateParser; 
    
    Update(String[] arguments){
        this.arguments =arguments;
        dateParser = new PrettyTimeParser();
        interpretArguments(arguments);
        
    }
    
    private void interpretArguments (String[] arguments){
        this.taskPosition = interpretPosition(arguments[INDEX_TASK_POSITION]);
        int indexDateStart = setDateTime(arguments);
        this.description = getString(arguments, 1, indexDateStart - 1);
        setLocation(arguments);
    }
    
    private int interpretPosition(String num) throws NumberFormatException{
        return (Integer.parseInt(num) - ARRAY_INDEXING_OFFSET);
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
            setStartAndEndDateTime(arguments, fromDateIndex, toDateIndex, locationIndex);
        }
        return fromDateIndex;
    }
    
    private int setEndDateOnly(String[] args){
        int byDateIndex = Arrays.asList(arguments).lastIndexOf("by");
        int locationIndex = Arrays.asList(arguments).lastIndexOf("at");
        setEndDateAndTime(args, byDateIndex, locationIndex);
        return byDateIndex;
    }
    
    private void setStartAndEndDateTime (String[] arguments, int from, int to, int loc){
        setStartDateAndTIme(arguments, from, to);
        setEndDateAndTime(arguments, to, loc);
    }

    private void setEndDateAndTime(String[] arguments, int to, int loc) {
        String endDateTime = getString(arguments, to + 1, loc - 1); //MAGIC NUMBERS
        Date endDateAndTime = interpretDate(endDateTime);
        this.endDateTime = (new SimpleDateFormat("dd/MM/yyyy").format(endDateAndTime));
        this.endTime = (new SimpleDateFormat("HH:mm").format(endDateAndTime));
    }

    private void setStartDateAndTIme(String[] arguments, int from, int to) {
        String startDateTime = getString(arguments, from + 1, to - 1); //MAGIC NUMBERS
        Date startDateAndTime = interpretDate(startDateTime);
        this.startDateTime = (new SimpleDateFormat("dd/MM/yyyy").format(startDateAndTime));
        this.startTime = (new SimpleDateFormat("HH:mm").format(startDateAndTime));
    }
    
    private Date interpretDate(String date){
        List<Date> dateTimes = dateParser.parse(date);
        return dateTimes.get(0);
    }
    
    
    private String getString(String[] args, int start, int end){
        String string = "";
        for(int i = start; i <= end; i++){
            string = string + args[i] + " ";
        }
        return string.trim();
    }
   
    public Feedback execute(Storage storage){
        
        String feedbackMessage = storage.updateTaskFromSearch(taskPosition, description, 
                startDateTime, startTime, endDateTime
                , endTime,location
                , remindDate, priority);
        if (feedbackMessage != null){
            return new Feedback(feedbackMessage, storage.getAllTasks());
        }else{
            return new Feedback(MESSAGE_UPDATE_ERROR, storage.getAllTasks());
        }
        
    }
    /*
    private String makeFeedback(){
        if (startDateTime.equals(EMPTY)){
            return String.format(MESSAGE_UPDATE_FEEDBACK_NO_START_DATE, description, endDateTime, location);
        } else {
            return String.format(MESSAGE_UPDATE_FEEDBACK_WITH_START_DATE, description, startDateTime, endDateTime, location);
        }
        
    }
    */
}
