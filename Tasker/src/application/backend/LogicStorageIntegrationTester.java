// @@author A0125417L
package application.backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import application.storage.Task;

/**
 * 
 * Integration Test starting from Logic all the way to storage. Delete all data
 * in TaskerData.txt or run the gui and delete all data before testing
 *
 */

public class LogicStorageIntegrationTester {
	// Constants
	private static final int EMPTY = 0;

	// Initialization
	private Logic logic = new Logic();

	public ArrayList<Task> initilaiseLoad() throws IOException {
		return logic.loadDataFile();
	}

	@Before
	@Test
	public void beforeTestLoad() throws IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		assertTrue(tasklist.size() >= EMPTY);
	}

	@Test
	public void testAddDelete() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		assertEquals("Added Task: \"play dota 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\"",
				fb.getMessage());
		assertEquals("play dota", tasklist.get(0).getTaskDescription());
		assertEquals("home", tasklist.get(0).getLocation());
		assertEquals("Sun Dec 18 13:00:00 SGT 2016", tasklist.get(0).getStartDate().getTime().toString());
		assertEquals("Sun Dec 18 14:00:00 SGT 2016", tasklist.get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", fb.getTasks());
		assertEquals("Deleted Task: \"play dota 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\"",
				fb.getMessage());
	}

	@Test
	public void testAddUndo() throws IOException, NoDescriptionException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		assertEquals("Added Task: \"play dota 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\"",
				fb.getMessage());
		fb = logic.executeCommand("delete 1", fb.getTasks());
		assertTrue(fb.getTasks().size() == 0);
		fb = logic.executeCommand("undo", fb.getTasks());
		assertTrue(fb.getTasks().size() == 1);
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testDeleteUndo() throws IOException, NoDescriptionException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		assertEquals("Added Task: \"play dota 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\"",
				fb.getMessage());
		fb = logic.executeCommand("undo", fb.getTasks());
		assertTrue(fb.getTasks().size() == 0);
	}

	@Test
	public void testDeleteFail() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("delete 0", fb.getTasks());
		assertEquals("Please enter a valid number.", fb.getMessage());
		assertTrue(tasklist.size() == 1);
		logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testSearch() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", fb.getTasks());
		fb = logic.executeCommand("search dota", fb.getTasks());
		assertEquals(fb.getMessage(), "Here are the results of your search! Use home command to view all tasks!");
		assertTrue(fb.getTasks().size() == 1);
		assertEquals("play dota", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Sun Dec 18 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Sun Dec 18 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("search by 30/6/2016", fb.getTasks());
		assertTrue(fb.getTasks().size() == 1);
		fb = logic.executeCommand("search on 30/6/2016", fb.getTasks());
		assertEquals(fb.getMessage(), "Search Not Found");
		fb = logic.executeCommand("delete 1", fb.getTasks());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testSearchBy() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", fb.getTasks());
		fb = logic.executeCommand("search by 28/12/2016", fb.getTasks());
		assertTrue(fb.getTasks().size() == 2);
		assertEquals("play dota", fb.getTasks().get(1).getTaskDescription());
		assertEquals("home", fb.getTasks().get(1).getLocation());
		assertEquals("Sun Dec 18 13:00:00 SGT 2016", fb.getTasks().get(1).getStartDate().getTime().toString());
		assertEquals("Sun Dec 18 14:00:00 SGT 2016", fb.getTasks().get(1).getEndDate().getTime().toString());
		assertEquals("play dot", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Fri Apr 01 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Fri Apr 01 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", fb.getTasks());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testSearchOn() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dota from 18 december 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", fb.getTasks());
		fb = logic.executeCommand("search dota", fb.getTasks());
		fb = logic.executeCommand("search on 30/6/2016", fb.getTasks());
		assertEquals(fb.getMessage(), "Search Not Found");
		fb = logic.executeCommand("search on 18 december", fb.getTasks());
		assertTrue(fb.getTasks().size() == 1);
		fb = logic.executeCommand("delete 1", fb.getTasks());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testUpdate() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("update 1 play dota", fb.getTasks());
		assertEquals("Updated Task: From: \"play dot 1 Apr 2016 1:00 PM to 2:00 PM, at home. Priority high\""
				+ "\nTo: \"play dota 1 Apr 2016 1:00 PM to 2:00 PM, at home. Priority high\"", fb.getMessage());
		assertEquals("play dota", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Fri Apr 01 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Fri Apr 01 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testUpdatePriority() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 18 dec 1pm to 2pm at home", tasklist);
		assertEquals("low", fb.getTasks().get(0).getPriority());
		fb = logic.executeCommand("update 1 priority high", fb.getTasks());
		assertEquals("Updated Task: From: \"play dot 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\""
				+ "\nTo: \"play dot 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority high\"", fb.getMessage());
		assertEquals("play dot", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Sun Dec 18 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Sun Dec 18 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		assertEquals("high", fb.getTasks().get(0).getPriority());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}
	
	@Test
	public void testUpdateDate() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 18 dec 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("update 1 from 19 dec 2pm to 3pm", fb.getTasks());
		assertEquals("Updated Task: From: \"play dot 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\""
				+ "\nTo: \"play dot 19 Dec 2016 2:00 PM to 3:00 PM, at home. Priority low\"", fb.getMessage());
		assertEquals("play dot", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Mon Dec 19 14:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Mon Dec 19 15:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}
	
	@Test
	public void testUpdateLocation() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 18 dec 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("update 1 at nus", fb.getTasks());
		assertEquals("Updated Task: From: \"play dot 18 Dec 2016 1:00 PM to 2:00 PM, at home. Priority low\""
				+ "\nTo: \"play dot 18 Dec 2016 1:00 PM to 2:00 PM, at nus. Priority low\"", fb.getMessage());
		assertEquals("play dot", fb.getTasks().get(0).getTaskDescription());
		assertEquals("nus", fb.getTasks().get(0).getLocation());
		assertEquals("Sun Dec 18 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Sun Dec 18 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testUpdateUndo() throws IOException, NoDescriptionException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("update 1 play dota", fb.getTasks());
		assertEquals("Updated Task: From: \"play dot 1 Apr 2016 1:00 PM to 2:00 PM, at home. Priority high\""
				+ "\nTo: \"play dota 1 Apr 2016 1:00 PM to 2:00 PM, at home. Priority high\"", fb.getMessage());
		assertEquals("play dota", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Fri Apr 01 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Fri Apr 01 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("undo", fb.getTasks());
		assertEquals("play dot", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Fri Apr 01 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Fri Apr 01 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}

	@Test
	public void testDone() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("done 1", fb.getTasks());
		assertTrue(fb.getTasks().size() == 0);
	}

	@Test
	public void testDoneUndo() throws IOException, NoDescriptionException {
		ArrayList<Task> tasklist = initilaiseLoad();
		Feedback fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("done 1", fb.getTasks());
		assertTrue(fb.getTasks().size() == 0);
		fb = logic.executeCommand("undo", fb.getTasks());
		assertTrue(fb.getTasks().size() == 1);
		fb = logic.executeCommand("delete 1", fb.getTasks());
	}
}
