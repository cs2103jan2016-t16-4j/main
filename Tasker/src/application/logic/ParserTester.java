package application.logic;
//import application.storage.Storage;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import application.storage.Storage;
import application.storage.Task;

public class ParserTester {

        Storage storage = new Storage();
        Parser parser = new Parser();
    
      /*  @Test
    public void deleteTest(){
        try{
            storage.initialise();
            
            ArrayList<Task> tasks = storage.getFileList();
            int numb = tasks.size() + 2;
            Command cmd = parser.interpretCommand("delete " + numb);
            Feedback fb = cmd.execute(storage, tasks);
            assertEquals("Please enter a valid number.",fb.getMessage());
            
            Command cmd2 = parser.interpretCommand("delete 0");
            Feedback fb2 = cmd2.execute(storage, tasks);
            assertEquals("Please enter a valid number.",fb2.getMessage());
            
            Command cmd3 = parser.interpretCommand("delete 1");
            System.out.println(tasks.size());
            String expected = "Deleted Task: " + tasks.get(0).toString();
            Feedback fb3 = cmd3.execute(storage, tasks);
            System.out.println(fb3.getMessage());
            assertEquals(expected,fb3.getMessage());
            
        }catch(Exception e){
            System.out.println("Not working delete");
       }
    } */
        
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
                /*
                Command cmd2 = parser.interpretCommand("delete -1");
                Feedback fb2 = cmd2.execute(storage, tasks);
                assertEquals("Please enter a valid number.",fb2.getMessage());
                
                Command cmd3 = parser.interpretCommand("delete 1");
                System.out.println(tasks.size());
                String expected = "Deleted Task: " + tasks.get(0).toString();
                Feedback fb3 = cmd3.execute(storage, tasks);
                System.out.println(fb3.getMessage());
                assertEquals(expected,fb3.getMessage());
        */
            }catch(Exception e){
                e.printStackTrace(System.out);
            }
        }

}
