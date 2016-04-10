package application.backend;
//@@author A0132632R
import application.storage.Storage;
import application.storage.StorageStubForBackend;
import application.storage.Task;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
/**
 * This class is a test driver for unit testing the backend package.
 * @author Pratyush
 *
 */
public class BackendUnitTest {
    private static final int SIZE = 10;
    
    private Storage storage = new StorageStubForBackend(SIZE);
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
        Feedback fb3 = logic.executeCommand("delete " + SIZE, tasksOnScreen);
        assertEquals("Deleted Task: " + tasksOnScreen.get(9).toString(), fb3.getMessage());
        Feedback fb4 = logic.executeCommand("delete " + (SIZE + 1), tasksOnScreen);
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
    
    @Test
    public void searchByNameTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("search Task 2", tasksOnScreen);
        ArrayList<Task> expectedTasks = new ArrayList<Task>();
        expectedTasks.add(tasksOnScreen.get(1));
        assertEquals(expectedTasks, fb.getTasks());
    }
    @Test
    public void searchByPriorityTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("search priority high", tasksOnScreen);
        ArrayList<Task> expectedTasks = new ArrayList<Task>();
        expectedTasks.add(tasksOnScreen.get(SIZE/2)); //size/2 is always made to be priority high by the stub
        assertEquals(expectedTasks, fb.getTasks());
    }
    
    @Test
    public void helpTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("help", tasksOnScreen);
        assertEquals("help", fb.getFlag());
    }
    

    @Test
    public void summaryTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("summary", tasksOnScreen);
        assertEquals("summary", fb.getFlag());
    }
    
    public void homeTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("home", tasksOnScreen);
        assertEquals("cal", fb.getFlag());
    }
    
    @Test
    public void doneTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("done 1", tasksOnScreen);
        assertEquals("Closed Task: " + tasksOnScreen.get(0).toString(), fb.getMessage());
        Feedback fb1 = logic.executeCommand("done 0", tasksOnScreen);
        assertEquals("Please enter a valid number.", fb1.getMessage());
        Feedback fb2 = logic.executeCommand("done 5", tasksOnScreen);
        assertEquals("Closed Task: " + tasksOnScreen.get(4).toString(), fb2.getMessage());
        Feedback fb3 = logic.executeCommand("done " + SIZE, tasksOnScreen);
        assertEquals("Closed Task: " + tasksOnScreen.get(9).toString(), fb3.getMessage());
        Feedback fb4 = logic.executeCommand("done " + (SIZE + 1), tasksOnScreen);
        assertEquals("Please enter a valid number.", fb4.getMessage());
    }
    
    //deleting and then undoing
    @Test
    public void undoTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("delete 2", tasksOnScreen);
        Task deleted = tasksOnScreen.get(1);
        assertEquals("Deleted Task: " + deleted.toString(), fb.getMessage());
        Feedback fb2 = logic.executeCommand("undo", tasksOnScreen);
        assertEquals(deleted.toString(), fb2.getTaskToScrollTo().toString());
    }
    
    @Test
    public void viewChangeTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("view", tasksOnScreen);
        assertEquals("view", fb.getFlag());
    }
    
}
