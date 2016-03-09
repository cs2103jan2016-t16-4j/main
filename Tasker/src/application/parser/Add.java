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
    String startTime = EMPTY;
    String endDateTime = EMPTY;
    String endTime = EMPTY;
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
            setStartAndEndDateAndTime(arguments, fromDateIndex, toDateIndex, locationIndex);
        }
        return fromDateIndex;
    }
    
    private int setEndDateOnly(String[] args){
        int byDateIndex = Arrays.asList(arguments).lastIndexOf("by");
        int locationIndex = Arrays.asList(arguments).lastIndexOf("at");
        setEndDateAndTime(args, byDateIndex, locationIndex);
        return byDateIndex;
    }
    
    private void setStartAndEndDateAndTime (String[] arguments, int from, int to, int loc){
        setStartDateAndTime(arguments, from, to);
        setEndDateAndTime(arguments, to, loc);
    }

    private void setEndDateAndTime(String[] arguments, int to, int loc) {
        String endDateTime = getString(arguments, to + 1, loc - 1); //MAGIC NUMBERS
        Date endDateAndTime = interpretDate(endDateTime);
        this.endDateTime = (new SimpleDateFormat("dd/MM/yyyy").format(endDateAndTime));
        this.endTime = (new SimpleDateFormat("HH:mm").format(endDateAndTime));
    }

    private void setStartDateAndTime(String[] arguments, int from, int to) {
        String startDateTime = getString(arguments, from + 1, to - 1); //MAGIC NUMBERS
        Date startDateAndTime = interpretDate(startDateTime);
        this.startDateTime = (new SimpleDateFormat("dd/MM/yyyy").format(startDateAndTime));
        this.startTime = (new SimpleDateFormat("HH:mm").format(startDateAndTime));
    }
    
    private Date interpretDate(String date) throws IndexOutOfBoundsException{
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
        String feedbackMessage = storage.addTaskInList(description, startDateTime, startTime
                ,endDateTime, endTime, location, remindDate, priority);
        if (feedbackMessage != null){
            Feedback feedback = new Feedback(feedbackMessage, storage.getAllTasks()) 
            return feedback;
        } else {
            return new Feedback(MESSAGE_ADD_ERROR, storage.getAllTasks());
        }
    }
    /*
    private String makeFeedback(){
        if (startDateTime.equals(EMPTY)){
            return String.format(MESSAGE_ADD_FEEDBACK_NO_START_DATE, description, endDateTime, location);
        } else {
            return String.format(MESSAGE_ADD_FEEDBACK_WITH_START_DATE, description, startDateTime, endDateTime, location);
        }
        
    }*/
}
