package application.backend;
//@@author A0132632R
import application.storage.Storage;
import application.storage.StorageStubForLogic;
import application.storage.Task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class LogicUnitTest {
    private static final int SIZE = 10;
    
    private Storage storage = new StorageStubForLogic(SIZE);
    private StorageConnector storageConnector = new StorageConnector(storage);
    private Logic logic = new Logic(storageConnector);

    @Test
    public void addTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("play tennis from 5 pm to 6 pm tomorrow", tasksOnScreen);
        assertEquals("play tennis", fb.getTaskToScrollTo().getTaskDescription());
        Feedback fb2 = logic.executeCommand("add football coaching from 2 pm tomorrow", tasksOnScreen);
        assertEquals("football coaching", fb2.getTaskToScrollTo().getTaskDescription());
        Feedback fb3 = logic.executeCommand("add football coaching from now to then at NUS -d from 2 pm tomorrow", tasksOnScreen);
        assertEquals("football coaching from now to then at NUS", fb3.getTaskToScrollTo().getTaskDescription());   
    }
    
    //Testing via equivalence partitioning
    @Test
    public void deleteTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("delete 1", tasksOnScreen);
        assertEquals("Deleted Task: " + tasksOnScreen.get(0).toString(), fb.getMessage());
        Feedback fb1 = logic.executeCommand("delete 0", tasksOnScreen);
        assertEquals("Please enter a valid number.", fb1.getMessage());
        Feedback fb2 = logic.executeCommand("delete 5", tasksOnScreen);
        assertEquals("Deleted Task: " + tasksOnScreen.get(4).toString(), fb2.getMessage());
        Feedback fb3 = logic.executeCommand("delete 10", tasksOnScreen);
        assertEquals("Deleted Task: " + tasksOnScreen.get(9).toString(), fb3.getMessage());
        Feedback fb4 = logic.executeCommand("delete 11", tasksOnScreen);
        assertEquals("Please enter a valid number.", fb4.getMessage());
    }
    
    @Test
    public void updateTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("update 0 hello", tasksOnScreen);
        assertEquals("Please enter a valid task number." , fb.getMessage());
        Feedback fb2 = logic.executeCommand("update 1 play", tasksOnScreen);
        assertEquals("play" , fb2.getTaskToScrollTo().getTaskDescription());
        Feedback fb3 = logic.executeCommand("update 4 tennis", tasksOnScreen);
        assertEquals("tennis" , fb3.getTaskToScrollTo().getTaskDescription());
        Feedback fb4 = logic.executeCommand("update " + SIZE + " football", tasksOnScreen);
        assertEquals("football" , fb4.getTaskToScrollTo().getTaskDescription());
        Feedback fb5 = logic.executeCommand("update " + (SIZE +1) + " football", tasksOnScreen);
        assertEquals("Please enter a valid task number." , fb5.getMessage());
    }
    
    
    
    
}
