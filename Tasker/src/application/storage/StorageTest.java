import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class StorageTest {

//	@Test
	public void testDateBeforeOrEqual() {
		Storage storage = new Storage();
		// test by year parameter only
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("11/07/1950", "11/07/2016"));
		assertTrue(storage.dateIsBeforeOrEqual("11/07/2015", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("11/07/2018", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("11/07/5999", "11/07/2015"));
		
		// test by month parameter only
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("11/01/2015", "11/07/2015"));
		assertTrue(storage.dateIsBeforeOrEqual("11/07/2015", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("11/09/2015", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("11/12/2015", "11/07/2015"));
		
		// test by day parameter only
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("01/07/2015", "11/07/2015"));
		assertTrue(storage.dateIsBeforeOrEqual("11/07/2015", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("18/07/2015", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("30/07/2015", "11/07/2015"));
		
		// test by month&year parameter only
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("11/09/1985", "11/07/2016"));
		assertTrue(storage.dateIsBeforeOrEqual("11/01/2015", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("13/02/2018", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("11/09/2015", "11/07/2015"));
		
		// test by day&year parameter only
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("15/07/1985", "11/07/2016"));
		assertTrue(storage.dateIsBeforeOrEqual("11/07/2015", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("13/07/2018", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("15/07/2015", "11/07/2015"));
		
		// test by day&month parameter only
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("15/06/2015", "11/07/2015"));
		assertTrue(storage.dateIsBeforeOrEqual("01/07/2015", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("13/09/2015", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("15/07/2015", "11/07/2015"));
		
		// test by day&month&year
		// before/equal
		assertTrue(storage.dateIsBeforeOrEqual("17/08/2014", "11/07/2015"));
		assertTrue(storage.dateIsBeforeOrEqual("01/01/2000", "11/07/2015"));
		assertTrue(storage.dateIsBeforeOrEqual("13/10/2000", "11/07/2015"));
		// exceeds
		assertFalse(storage.dateIsBeforeOrEqual("01/01/2016", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("02/09/2015", "11/07/2015"));
		assertFalse(storage.dateIsBeforeOrEqual("13/10/2018", "11/07/2015"));
		
		//====================================================================

	}

//	@Test
	public void testAdd() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		System.out.println("Added : "+storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high"));
		System.out.println("Added : "+storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", ""));
		System.out.println("Added : "+storage.addTaskInList("Gym time", "", "", "", "", "", "", ""));
		System.out.println("Added : "+storage.addTaskInList("Play game", "", "", "", "", "", "", "low"));
		storage.saveFile();
	}
//	@Test
	public void testLoad() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
	}
	
//	@Test
	public void testDeleteByAll() throws IOException {		
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "2030", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		System.out.println("Deleted task : "+storage.deleteTaskFromAll(0));
		storage.saveFile();
		assertEquals(1,storage.fileList.size());

	}
	
//	@Test
	public void testDeleteBySearch() throws IOException {		
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go game", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Gym time", "", "", "", "", "", "", "");
		storage.addTaskInList("Play game", "", "", "", "", "", "", "low");
		storage.searchByTask("game");
		System.out.println("Deleted task : "+storage.deleteTaskFromSearch(1));
		storage.saveFile();
		assertEquals(3,storage.fileList.size());

	}
	
//	@Test
	public void testClearFile() throws IOException {
		Storage storage = new Storage();
		storage.clearFile();
	}
	
//	@Test
	public void testFileNotExist() throws IOException {		
		Storage storage = new Storage();
		storage.loadFile();
	}
	
//	@Test
	public void testSearchTask() throws IOException {		
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		storage.searchByTask("home");
		storage.saveFile();
	}
	
//	@Test
	public void testSearchDate() throws IOException {		
		Storage storage = new Storage();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		storage.searchByDate("01/01/2017", true);	
	}
	
//	@Test
	public void testDirectoryFileCreated() throws IOException {		
		Storage storage = new Storage();
		assertFalse(storage.checkDirectoryFileCreated());
		assertTrue(storage.checkDirectoryFileCreated());
	}
	
//	@Test
	public void testDirectoryFile() throws IOException {		
		Storage storage = new Storage();
		storage.checkDirectoryFileCreated();
		assertEquals(null,storage.loadDirectoryFile());
		PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(storage.FILE_DIRECTORY, true)));
		fw.println("D:/Eclipse/eclipse/workspace/TaskerData.txt");
		fw.close();
		assertEquals("D:/Eclipse/eclipse/workspace/TaskerData.txt",storage.loadDirectoryFile());
	}

//	@Test	
	public void testSaveDirectory() throws IOException {
		Storage storage = new Storage();
		storage.checkDirectoryFileCreated();
		storage.saveDirectory("D:/Eclipse/eclipse/workspace/");
		storage.loadDirectoryFile();
		storage.saveDirectory("");
		storage.loadDirectoryFile();
			
	}
	
//	@Test
	public void testUpdateTaskFromAll() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		storage.saveFile();
		System.out.println(storage.updateTaskFromAll(1, "Go home and die", "", "", "", "", "", "", ""));
		storage.saveFile();
	}
	
//	@Test
	public void testUpdateTaskFromSearch() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Go abc", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Go cde", "", "", "02/05/2016", "", "", "", "");
		storage.saveFile();
		storage.searchByTask("go");
		System.out.println(storage.updateTaskFromSearch(1, "Go home and die", "", "", "", "", "", "", ""));
		storage.saveFile();
	}
	
//	@Test
	public void testCloseTaskFromAll() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Go abc", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Go cde", "", "", "02/05/2016", "", "", "", "");
		System.out.println("Closed : "+storage.closeTaskFromAll(2));
		storage.saveFile();

		
	}
	
	@Test
	public void testCloseTaskFromSearch() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		storage.addTaskInList("Do homework", "", "", "02/03/2018", "", "home", "02/07/2016", "high");
		storage.addTaskInList("Go home", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Go abc", "", "", "02/05/2016", "", "", "", "");
		storage.addTaskInList("Go cde", "", "", "02/05/2016", "", "", "", "");
		storage.saveFile();
		storage.searchByTask("go");
		System.out.println("Closed : "+storage.closeTaskFromSearch(1));
		storage.saveFile();
		
	}
	
//	@After
	public void deleteFiles() throws IOException {
		Storage storage = new Storage();
		storage.startUpCheck();
		storage.loadFile();
		File f = new File(storage.FILE_DIRECTORY);
		File p = new File(storage.filePath);
		f.delete();
		p.delete();
	}
}

