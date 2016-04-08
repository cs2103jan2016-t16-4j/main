//@@author A0125522R

package application.storage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
	Storage storageController;
	static final String FILE_CLOSED_NAME = "TaskerDataHistory.txt";
	static final String FILE_DATA_NAME = "TaskerData.txt";
	static final String FILE_DIRECTORY_NAME = "TaskerDirectory.txt";
	static final String CUSTOM_DIRECTORY1 = "E:/Eclipse/workspace/";
	static final String CUSTOM_DIRECTORY2 = "E:/Eclipse/workspace/CS2103_Tasker/";
	static Calendar cal1 = Calendar.getInstance();
	static Calendar cal2 = Calendar.getInstance();
	static Calendar cal3 = Calendar.getInstance();
	static Calendar time1 = Calendar.getInstance();
	static Calendar noDate = Calendar.getInstance();
	static Calendar noTime = Calendar.getInstance();
    private static final int EMPTY = 1;
	
    @Before
	public void before() throws IOException {
		storageController = new Storage();
		if(!storageController.directoryExists())	
			storageController.setDirectory("");
		storageController.initialise();
		cal1.set(2020, Calendar.JUNE, 30);
		cal2.set(2030, Calendar.DECEMBER, 25);
		cal3.set(2040, Calendar.APRIL, 1);
		noDate.set(Calendar.YEAR, EMPTY);
		noTime.set(Calendar.MILLISECOND, EMPTY);
		noTime.set(Calendar.HOUR_OF_DAY, 0);
		noTime.set(Calendar.MINUTE, 0);
		noTime.set(Calendar.SECOND, 0);
		time1.set(Calendar.YEAR, EMPTY);
		time1.set(Calendar.HOUR_OF_DAY, 17);
		time1.set(Calendar.MINUTE, 17);
	}
	
//	@Test
	public void checkDirectory() throws IOException {
		assertFalse(storageController.directoryExists());	
	}
	
//	@Test
	public void checkInitialise() throws IOException {
		storageController.directoryExists();	
		storageController.setDirectory("");
		assertTrue(storageController.initialise());
	}
	
//	@Test
	public void setDirectory() throws IOException {
		storageController.addTaskInList("Go to hell", cal1, noDate, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", noDate, cal1, "Home", cal2, "low");
		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
		storageController.setDirectory(CUSTOM_DIRECTORY1);
		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
		storageController.setDirectory("");
		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
		storageController.setDirectory(CUSTOM_DIRECTORY2);
		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
		storageController.setDirectory("");
		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
	}

//	@Test
	public void addTask() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		assertEquals("FloatingTask",storageController.getOpenList().get(0).getClass().getSimpleName());
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		assertEquals("DeadlineTask",storageController.getOpenList().get(1).getClass().getSimpleName());
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		assertEquals("EventTask",storageController.getOpenList().get(2).getClass().getSimpleName());
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		assertEquals("FloatingTask",storageController.getOpenList().get(3).getClass().getSimpleName());
	}
	
//	@Test
	public void closeTask() throws IOException {
		storageController.addTaskInList("Go to hell", cal1, cal2, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.closeTask(1);
	}
	
//	@Test
	public void deleteTask() throws IOException {
		storageController.addTaskInList("Go to hell", cal1, noDate, "Doom", noDate, "high");
		storageController.deleteTask(1);
	}
	
//	@Test
	public void searchName() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		ArrayList<Task> searchList = storageController.searchTaskByName("home");
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println("Found : "+searchList.get(i).toString());
		}
	}
	
//	@Test
	public void searchByDate() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar searchDate = Calendar.getInstance();
//		cal1.set(2020, Calendar.JUNE, 30);
//		cal2.set(2030, Calendar.DECEMBER, 25);
		searchDate.set(2020, Calendar.JUNE, 30);
		ArrayList<Task> searchList = storageController.searchTaskByDate(searchDate);
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println("Found : "+searchList.get(i).toString());
		}
	}
	
//	@Test
	public void searchPriority() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		ArrayList<Task> searchList = storageController.searchTaskByPriority("low");
		assertTrue(searchList.get(0) == storageController.databaseManager.getOpenList().get(1));
		assertTrue(searchList.get(1) == storageController.databaseManager.getOpenList().get(3));		
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println("Found : "+searchList.get(i).toString());
		}
	}
	
//	@Test
	public void sortName() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		ArrayList<Task> searchList = storageController.sortByName();
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println(i+") " +searchList.get(i).toString());
		}
	}
	
//	@Test
	public void sortDate() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		ArrayList<Task> searchList = storageController.sortByDate();
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println(i+") " +searchList.get(i).toString());
		}
	}
	
//	@Test
	public void sortPriority() throws IOException {
//		storageController.addTaskInList("Go to hell", cal1, noDate, "Doom", noDate, "high");
//		storageController.addTaskInList("Do homework", noDate, cal1, "Home", cal2, "low");
//		storageController.addTaskInList("Finish CS2103", noDate, noDate, "School", noDate, "high");
//		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		ArrayList<Task> searchList = storageController.sortByPriority();
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println(i+") " +searchList.get(i).toString());
		}
	}
	
//	@Test
	public void updateTask() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(3, "Finish CS2103 and CS3230", noDate, newDate, "Home", noDate, "highest");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test
	public void addAndSetDirectoryAndAdd() throws IOException {
//		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
//		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
//		storageController.addTaskInList("Go to hell", cal1, noDate, "Doom", noDate, "high");
//		storageController.addTaskInList("Do homework", noDate, cal1, "Home", cal2, "low");
		
		storageController.setDirectory(CUSTOM_DIRECTORY1);
		System.out.println("Closed File Path is : "+storageController.fileManager.getClosedFilePath());
		System.out.println("Data File Path is : "+storageController.fileManager.getDataFilePath());
		storageController.addTaskInList("Finish CS2103", noDate, noDate, "School", noDate, "high");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
	}

//	@Test
	public void updateTaskWithTime() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(3, "", noDate, time1, "Home", noDate, "highest");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test
	public void searchOnDate() throws IOException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar searchDate = Calendar.getInstance();
		searchDate.set(2030, Calendar.DECEMBER, 25);
//		cal1.set(2020, Calendar.JUNE, 30);
//		cal2.set(2030, Calendar.DECEMBER, 25);
		ArrayList<Task> searchList = storageController.searchTaskOnDate(cal1);
		for (int i = 0; i<searchList.size(); i++) {
			System.out.println("Found : "+searchList.get(i).toString());
		}
	}
	
//	@Test
	public void testString() {
		Task task = new Task();
		Calendar date = Calendar.getInstance();
		date.set(2030, Calendar.DECEMBER, 25);
		task.setStartDate(noDate);
		task.setEndDate(date);
		task.setRemindDate(noDate);
		task.setStartTime(time1);
		task.setEndTime(time1);
		task.setRemindTime(noTime);
		System.out.println("Start Date : "+task.dateToString(task.getStartDate()));
		System.out.println("End Date : "+task.dateToString(task.getEndDate()));
		System.out.println("Remind Date : "+task.dateToString(task.getRemindDate()));
		System.out.println("Start time : "+task.timeToString(task.getStartDate()));
		System.out.println("End time : "+task.timeToString(task.getEndDate()));
		System.out.println("Remind time : "+task.timeToString(task.getRemindDate()));
		System.out.println("Duration : "+task.durationToString());
	}
	
//	@Test 
	public void checkCloseList() throws IOException {
		ArrayList<Task> list = storageController.getCloseList();
		for (int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i).toString());
		}
	}
	
//	@Test	// event task ---> deadline task
	public void updateTaskType1() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(3, "", null, cal1, "School", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test	// event task ---> floating task
	public void updateTaskType2() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(3, "Fucking CS2103", null, null, "School", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test	// event task ---> event task
	public void updateTaskType3() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(3, "", cal2, cal3, "School", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test // floating task ---> event task
	public void updateTaskType4() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(1, "", cal1, cal2, "Hell", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test // floating task ---> deadline task
	public void updateTaskType5() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(1, "", null, cal2, "Heaven", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test // floating task ---> floating task
	public void updateTaskType6() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(1, "", null, null, "LT33", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test // deadline task ---> event task	
	public void updateTaskType7() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(2, "", cal1, cal2, "Hell", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test // deadline task --> floating task
	public void updateTaskType8() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(2, "", null, null, "Hell", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
	
//	@Test // deadline task ---> deadline task
	public void updateTaskType9() throws IOException, CloneNotSupportedException {
		storageController.addTaskInList("Go to hell", null, null, "Doom", noDate, "high");
		storageController.addTaskInList("Do homework", null, cal1, "Home", cal2, "low");
		storageController.addTaskInList("Sign up for homework", cal1, cal2, "Home", noDate, "low");
		storageController.addTaskInList("Finish CS2103", null, null, "School", noDate, "high");
		Calendar newDate = Calendar.getInstance();
		newDate.set(2035, Calendar.JUNE, 30);
		ArrayList<Task> list = new ArrayList<Task>();
		list = storageController.updateTask(2, "", null, cal3, "Hell", noDate, "high");
		System.out.println(list.size());
		System.out.println("Old : "+list.get(0).toString());
		System.out.println("New : "+list.get(1).toString());
	}
//	@Test
	public void testGetPriority() {
		DeadlineTask deadlineTask = new DeadlineTask();
		Calendar endDate = Calendar.getInstance();
		endDate.roll(Calendar.SECOND, -1);
		deadlineTask.setEndDate(endDate);
		assertTrue(deadlineTask.getPriority()=="high");
		
		endDate = Calendar.getInstance();
		endDate.roll(Calendar.MINUTE, 1);
		deadlineTask.setEndDate(endDate);
		assertTrue(deadlineTask.getPriority()=="high");
		
		endDate = Calendar.getInstance();
		endDate.roll(Calendar.HOUR, 1);
		deadlineTask.setEndDate(endDate);
		assertTrue(deadlineTask.getPriority()=="high");
		
		endDate = Calendar.getInstance();
		endDate.roll(Calendar.HOUR, 3);
		deadlineTask.setEndDate(endDate);
		assertTrue(deadlineTask.getPriority()=="medium");
		
		endDate = Calendar.getInstance();
		endDate.roll(Calendar.HOUR, 23);
		deadlineTask.setEndDate(endDate);
		assertTrue(deadlineTask.getPriority()=="medium");
		
		endDate = Calendar.getInstance();
		endDate.roll(Calendar.HOUR, 25);
		deadlineTask.setEndDate(endDate);
		assertTrue(deadlineTask.getPriority()=="low");
		
		deadlineTask.setPriority("low");
		assertTrue(deadlineTask.getPriority()=="low");
		
		deadlineTask.setPriority("medium");
		assertTrue(deadlineTask.getPriority()=="medium");
		
		deadlineTask.setPriority("high");
		assertTrue(deadlineTask.getPriority()=="high");
	}

		
//	@After
	public void after() {
		File a = new File(FILE_DIRECTORY_NAME);
		File b = new File(FILE_CLOSED_NAME);
		File c = new File(FILE_DATA_NAME);
		a.delete();
		b.delete();
		c.delete();
	}

}
