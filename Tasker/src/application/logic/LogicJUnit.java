// @@author A0125417L
package application.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

import application.storage.Task;

/**
 * 
 * JUnit Test starting from Logic
 *
 */

public class LogicJUnit {
	// Constants
	private static final int EMPTY = 0;

	// Initialization
	private Logic logic = new Logic();

	@Before
	@Test
	public void beforeTestLoad() throws IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		assertTrue(tasklist.size() >= EMPTY);
	}

	@Test
	public void testAddDelete() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		assertEquals("Added Task: \"play dota 30 Apr 2016 1:00 PM to 2:00 PM, at home\"", fb.getMessage());
		assertEquals("play dota", tasklist.get(0).getTaskDescription());
		assertEquals("home", tasklist.get(0).getLocation());
		assertEquals("Sat Apr 30 13:00:00 SGT 2016", tasklist.get(0).getStartDate().getTime().toString());
		assertEquals("Sat Apr 30 14:00:00 SGT 2016", tasklist.get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", tasklist);
		assertEquals("Deleted Task: \"play dota 30 Apr 2016 1:00 PM to 2:00 PM, at home\"", fb.getMessage());
	}

	@Test
	public void testDeleteFail() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("delete 0", tasklist);
		assertEquals("Please enter a valid number.", fb.getMessage());
		assertTrue(tasklist.size() == 1);
		logic.executeCommand("delete 1", tasklist);
	}

	@Test
	public void testSearch() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("search dota", tasklist);
		assertEquals(fb.getMessage(), "Here are the results of your search!");
		assertTrue(fb.getTasks().size() == 1);
		assertEquals("play dota", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Sat Apr 30 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Sat Apr 30 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("search by 30/6/2016", tasklist);
		assertTrue(fb.getTasks().size() == 2);
		fb = logic.executeCommand("search on 30/6/2016", tasklist);
		assertEquals(fb.getMessage(), "Search Not Found");
		fb = logic.executeCommand("delete 1", tasklist);
		fb = logic.executeCommand("delete 1", tasklist);
	}

	@Test
	public void testSearchBy() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("search by 30/6/2016", tasklist);
		assertTrue(fb.getTasks().size() == 2);
		assertEquals("play dota", fb.getTasks().get(1).getTaskDescription());
		assertEquals("home", fb.getTasks().get(1).getLocation());
		assertEquals("Sat Apr 30 13:00:00 SGT 2016", fb.getTasks().get(1).getStartDate().getTime().toString());
		assertEquals("Sat Apr 30 14:00:00 SGT 2016", fb.getTasks().get(1).getEndDate().getTime().toString());
		assertEquals("play dot", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Fri Apr 01 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Fri Apr 01 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", tasklist);
		fb = logic.executeCommand("delete 1", tasklist);
	}

	@Test
	public void testSearchOn() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("search dota", tasklist);
		fb = logic.executeCommand("search on 30/6/2016", tasklist);
		assertEquals(fb.getMessage(), "Search Not Found");
		fb = logic.executeCommand("delete 1", tasklist);
		fb = logic.executeCommand("delete 1", tasklist);
	}

	@Test
	public void testUpdate() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("update 1 play dota", tasklist);
		assertEquals("Updated Task: From: \"play dot 1 Apr 2016 1:00 PM to 2:00 PM, at home\""
				+ "\nTo: \"play dota 1 Apr 2016 1:00 PM to 2:00 PM, at home\"", fb.getMessage());
		assertEquals("play dota", fb.getTasks().get(0).getTaskDescription());
		assertEquals("home", fb.getTasks().get(0).getLocation());
		assertEquals("Fri Apr 01 13:00:00 SGT 2016", fb.getTasks().get(0).getStartDate().getTime().toString());
		assertEquals("Fri Apr 01 14:00:00 SGT 2016", fb.getTasks().get(0).getEndDate().getTime().toString());
		fb = logic.executeCommand("delete 1", tasklist);
	}

	@Test
	public void testDone() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dot from 1 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("done 1", tasklist);
		assertTrue(fb.getTasks().size() == 0);
	}

}