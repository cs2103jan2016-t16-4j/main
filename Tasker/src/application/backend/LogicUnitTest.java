package application.backend;

import application.storage.Storage;
import application.storage.StorageStubForLogic;
import application.storage.Task;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Test;

public class LogicUnitTest {
    private Storage storage = new StorageStubForLogic();
    private StorageConnector storageConnector = new StorageConnector(storage);
    private Logic logic = new Logic(storageConnector);

    @Test
    public void addTest() throws NoDescriptionException{
        ArrayList<Task> tasksOnScreen = storage.getOpenList(); 
        Feedback fb = logic.executeCommand("play tennis from 5 pm to 6 pm tomorrow", tasksOnScreen);
        assertEquals("play tennis", fb.getIndexToScroll().getTaskDescription());
        Feedback fb2 = logic.executeCommand("add football coaching from 2 pm tomorrow", tasksOnScreen);
        assertEquals("football coaching", fb2.getIndexToScroll().getTaskDescription());
        Feedback fb3 = logic.executeCommand("add football coaching from now to then at NUS -d from 2 pm tomorrow", tasksOnScreen);
        assertEquals("football coaching from now to then at NUS", fb3.getIndexToScroll().getTaskDescription());   
    }
    
    
    
    
}
