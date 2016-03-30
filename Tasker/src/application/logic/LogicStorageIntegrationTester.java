package application.logic;
//import application.storage.Storage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import application.storage.Storage;
import application.storage.Task;

public class LogicStorageIntegrationTester {
    
        private static final String MESSAGE_ADD_FEEDBACK= "Added Task: %1$s";


        Logic logic = new Logic();
    
        @Test
        public void addTest(){
            try{
                ArrayList<Task> tasksOnScreen = logic.loadDataFile();
                Feedback feedback = logic.executeCommand("play tennis from 5 pm to 6pm tomorrow", tasksOnScreen);
                assertEquals(String.format(MESSAGE_ADD_FEEDBACK, "\"play tennis from 1 apr 2016 5:00 pm to 1 apr 2016 6:00 pm\"").toLowerCase(), feedback.getMessage().toLowerCase().trim());
            }catch(Exception e){
                System.out.println("Not working delete");
        }
    } /*
        
        @Test
        public void updateTest(){
            try{
                storage.initialise();
                System.out.println("IM HEREE");
                
                ArrayList<Task> tasks = storage.getFileList();
                int numb = tasks.size();
                Command cmd = parser.interpretCommand("update 2 tennis");
                Feedback fb = cmd.execute(storage, tasks);
                System.out.println(fb.getMessage());
                Command cmd2 = parser.interpretCommand("delete -1");
                Feedback fb2 = cmd2.execute(storage, tasks);
                assertEquals("Please enter a valid number.",fb2.getMessage());
                
                Command cmd3 = parser.interpretCommand("delete 1");
                System.out.println(tasks.size());
                String expected = "Deleted Task: " + tasks.get(0).toString();
                Feedback fb3 = cmd3.execute(storage, tasks);
                System.out.println(fb3.getMessage());
                assertEquals(expected,fb3.getMessage());
        
            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }*/

}
