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
 * @author Shawn
 *
 */

public class LogicJUnit {
	private Logic logic = new Logic();

	@Before
	@Test
	public void beforeTestLoad() throws IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		assertTrue(tasklist.size() >= 0);
	}

	@Test
	public void testAddDelete() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		assertEquals("Added Task: \"play dota, from 30-04-2016 1:00 PM to 30-04-2016 2:00 PM, at home\"",
				fb.getMessage());
		fb = logic.executeCommand("delete 1", tasklist);
		assertEquals("Deleted Task: \"play dota, from 30-04-2016 1:00 PM to 30-04-2016 2:00 PM, at home\"",
				fb.getMessage());
	}

	@Test
	public void testDeleteFail() throws NoDescriptionException, IOException {
		ArrayList<Task> tasklist = new ArrayList<Task>();
		tasklist = logic.loadDataFile();
		Feedback fb = logic.executeCommand("play dota from 30 april 1pm to 2pm at home", tasklist);
		fb = logic.executeCommand("delete 0", tasklist);
		assertEquals("Please enter a valid number.", fb.getMessage());
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
		fb = logic.executeCommand("search by 30/6/2016", tasklist);
		assertTrue(fb.getTasks().size() == 2);
		System.out.println(fb.getTasks().size());
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
		assertEquals(fb.getMessage(),
				"Updated Task: From: \"play dot, from 01-04-2016 1:00 PM to 01-04-2016 2:00 PM, at home\"\nTo: \"play dota, from 01-04-2016 1:00 PM to 01-04-2016 2:00 PM, at home\"");
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
// @@author A0125417L