//@@author A0125522R

package application.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import application.logger.LoggerHandler;

/**
 * FileManager is used to save/load tasks into/into text file. It also holds the
 * current storage location path, and the data files (open and close lists)
 * path.
 */
public class FileManager {

	// Constants
	private static final String EMPTY = "";
	private static final String EMPTY_PATH = "";
	private static final String FILE_CLOSED_NAME = "FantaskticHistory.txt";
	private static final String FILE_DATA_NAME = "FantaskticData.txt";
	private static final String FILE_DIRECTORY_NAME = "FantaskticDirectory.txt";
	private static final String KEYWORD_SLASH = "\\";
	private static final int DIRECTORY_BEGIN_INDEX = 0;
	private static final int NO_TASK = 0;

	// Log messages
	private static final String LOG_INFO_LOADING_DIRECTORY = "Loading directory file.";
	private static final String LOG_INFO_LOADING_TASKS = "Loading tasks into list.";
	private static final String LOG_INFO_NO_DIRECTORY_CHOOSEN = "No directory choosen.";
	private static final String LOG_INFO_NEW_DIRECTORY_CHOOSEN = "New directory choosen.";
	private static final String LOG_INFO_SAVING_TASKS = "Saving tasks into file.";
	private static final String LOG_INFO_SAVE_FILE = "Saving file.";
	private static final String LOG_ERROR_CLEAR_FILE = "Error clearing file.";
	private static final String LOG_ERROR_FIND_FILE = "Error finding file.";
	private static final String LOG_ERROR_INITIALISE_DIRECTORY = "Error initialising directory.";
	private static final String LOG_ERROR_LOADING_DIERCTORY = "Error loading directory file.";
	private static final String LOG_ERROR_LOAD_TASK_INDEX = "Error initialising data file task index.";
	private static final String LOG_ERROR_SAVING_TASKS = "Error saving all tasks.";
	private static final String LOG_ERROR_SAVE_FILE = "Error saving file.";
	private static final String LOG_ERROR_SAVE_TASK_INDEX = "Error saving task index.";
	private static final String LOG_ERROR_SET_DIRECTORY = "Error setting directory.";

	// Path variables
	private String closedFilePath = "";
	private String dataFilePath = "";

	// Logger
	private static Logger logger = LoggerHandler.getLog();

	/**
	 * Clears the given file.
	 */
	public void clear(String filePath) {
		assert (filePath != null);
		File file = new File(filePath);
		if (file.exists()) {
			try {
				PrintWriter fw = new PrintWriter(filePath);
				fw.print(EMPTY);
				fw.close();
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, LOG_ERROR_CLEAR_FILE);
			}
		}
	}

	/**
	 * Return the path of close list.
	 */
	public String getClosedFilePath() {
		assert (closedFilePath != null);
		return closedFilePath;
	}

	/**
	 * Return the path of data list.
	 */
	public String getDataFilePath() {
		assert (dataFilePath != null);
		return dataFilePath;
	}

	/**
	 * Return the storage path in the directory file .
	 */
	public String getDirectoryPath() {
		return loadDirectoryFile();
	}

	/**
	 * Checks whether the directory file exist.
	 */
	public boolean isDirectoryExists() {
		// check whether its user first time opening program
		File directoryFile = new File(FILE_DIRECTORY_NAME);
		if (!directoryFile.exists()) {
			PrintWriter fw;
			try {
				fw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_DIRECTORY_NAME, true)));
				fw.print(EMPTY);
				fw.close();
				return false;
			} catch (IOException e) {
				logger.log(Level.SEVERE, LOG_ERROR_INITIALISE_DIRECTORY);
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * Loads the user tasks file (open and close list) into a list.
	 */
	public ArrayList<Task> loadFile(String filePath) {
		assert (filePath != null);
		File file = new File(filePath);
		ArrayList<Task> list = new ArrayList<Task>();
		logger.log(Level.INFO, LOG_INFO_LOADING_TASKS);
		if (file.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(filePath));
				
				// skip first line first if its data file (open list)
				if (filePath.equalsIgnoreCase(dataFilePath)) {
					in.readLine();
				}
				list = loadTasksIntoFile(in, list);
				in.close();
			} catch (IOException e) {
				logger.log(Level.SEVERE, LOG_ERROR_FIND_FILE);
			}
		}
		return list;
	}

	/**
	 * Loads the given file of tasks (close,open) into ArrayList<Task>.
	 */
	private ArrayList<Task> loadTasksIntoFile(BufferedReader in, ArrayList<Task> list) throws IOException {
		String readText;
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer())
				.registerTypeAdapter(EventTask.class, new TaskSerializer())
				.registerTypeAdapter(DeadlineTask.class, new TaskSerializer())
				.registerTypeAdapter(FloatingTask.class, new TaskSerializer()).create();
		while ((readText = in.readLine()) != null) {
			Task task = gson.fromJson(readText, Task.class);
			list.add(task);
		}
		return list;
	}

	/**
	 * Loads the directory file and initialise the close, data file path and
	 * return storage directory path of data files.
	 */
	public String loadDirectoryFile() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(FILE_DIRECTORY_NAME));
			logger.log(Level.INFO, LOG_INFO_LOADING_DIRECTORY);
			String directoryPath = in.readLine();
			directoryPath = loadFilePaths(directoryPath);
			in.close();
			return directoryPath;
		} catch (IOException e) {
			logger.log(Level.SEVERE, LOG_ERROR_LOADING_DIERCTORY);
			return null;
		}
	}

	/**
	 * Set the file paths up to save open list and close list subsequently and
	 * return the storage directory of data files.
	 */
	private String loadFilePaths(String directoryPath) {
		if (directoryPath == null) {
			closedFilePath = FILE_CLOSED_NAME;
			dataFilePath = FILE_DATA_NAME;
			File directoryFile = new File(FILE_DIRECTORY_NAME);
			String absolutePath = directoryFile.getAbsolutePath();
			directoryPath = absolutePath.substring(DIRECTORY_BEGIN_INDEX, absolutePath.lastIndexOf(File.separator))
					+ KEYWORD_SLASH;
		} else {
			closedFilePath = directoryPath + FILE_CLOSED_NAME;
			dataFilePath = directoryPath + FILE_DATA_NAME;
		}
		return directoryPath;
	}

	/**
	 * Loads the total task index count.
	 */
	public int loadTaskIndex() {
		File f = new File(dataFilePath);
		if (f.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(dataFilePath));
				String taskIndex;
				taskIndex = in.readLine();
				in.close();
				return Integer.parseInt(taskIndex);
			} catch (IOException e) {
				logger.log(Level.SEVERE, LOG_ERROR_LOAD_TASK_INDEX);
				return NO_TASK;
			}
		} else {
			return NO_TASK;
		}
	}

	/**
	 * Saves all tasks into the file path specified.
	 */
	private void saveAllTasks(ArrayList<Task> list, String filePath) {
		logger.log(Level.INFO, LOG_INFO_SAVING_TASKS);
		try {
			saveTasksIntoFile(list, filePath);
		} catch (IOException e) {
			logger.log(Level.SEVERE, LOG_ERROR_SAVING_TASKS);
		}
	}

	/**
	 * Saves all tasks into the file.
	 */
	private void saveTasksIntoFile(ArrayList<Task> list, String filePath) throws IOException {
		PrintWriter fwz = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
		Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer())
				.registerTypeAdapter(EventTask.class, new TaskSerializer())
				.registerTypeAdapter(DeadlineTask.class, new TaskSerializer())
				.registerTypeAdapter(FloatingTask.class, new TaskSerializer()).create();
		for (int i = 0; i < list.size(); i++) {
			String taskJson = gson.toJson(list.get(i));
			fwz.println(taskJson);
		}
		fwz.close();
	}

	/**
	 * Saves a given file.
	 */
	public void saveFile(ArrayList<Task> list, String filePath) {
		assert (filePath != null);
		File f = new File(filePath);
		logger.log(Level.INFO, LOG_INFO_SAVE_FILE);
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				logger.log(Level.SEVERE, LOG_ERROR_SAVE_FILE);
			}
		} else {
			// save all the tasks
			saveAllTasks(list, filePath);
		}
	}

	/**
	 * Saves the total task index count.
	 */
	public void saveTaskIndex(int taskIndex) {
		// #clear datafile first
		clear(dataFilePath);

		try {
			PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(dataFilePath, true)));
			fw.println(taskIndex);
			fw.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, LOG_ERROR_SAVE_TASK_INDEX);
		}
	}

	/**
	 * Set the storage directory at the path that the user specified.
	 */
	public boolean setDirectory(String newPath) {
		assert (newPath != null);
		if (newPath.equalsIgnoreCase(EMPTY_PATH)) {
			logger.log(Level.INFO, LOG_INFO_NO_DIRECTORY_CHOOSEN);
			return false;
		} else {
			clear(FILE_DIRECTORY_NAME);
			logger.log(Level.INFO, LOG_INFO_NEW_DIRECTORY_CHOOSEN);
			try {
				PrintWriter fw = new PrintWriter(FILE_DIRECTORY_NAME);

				// get current path
				Path oldClosedFilePath = Paths.get(closedFilePath);
				Path oldDataFilePath = Paths.get(dataFilePath);

				// store new path
				closedFilePath = newPath + FILE_CLOSED_NAME;
				dataFilePath = newPath + FILE_DATA_NAME;
				fw.println(newPath);

				// migration of data files
				migrateFilesAndDeleteExistingFiles(oldClosedFilePath, oldDataFilePath);
				fw.close();
				return true;
			} catch (IOException e) {
				logger.log(Level.SEVERE, LOG_ERROR_SET_DIRECTORY);
				return false;
			}
		}
	}

	/**
	 * Migrate any existing data files to new directory and delete the existing
	 * data files.
	 */
	private void migrateFilesAndDeleteExistingFiles(Path oldClosedFilePath, Path oldDataFilePath) throws IOException {
		
		Path newClosedFilePath = Paths.get(closedFilePath);		
		if (oldClosedFilePath.toFile().exists()) {
			if (!oldClosedFilePath.equals(newClosedFilePath)) {
				Files.copy(oldClosedFilePath, newClosedFilePath, StandardCopyOption.REPLACE_EXISTING);
				Files.deleteIfExists(oldClosedFilePath);
			}
		}
		
		Path newDataFilePath = Paths.get(dataFilePath);
		if (oldDataFilePath.toFile().exists()) {
			if (!oldDataFilePath.equals(newDataFilePath)) {
				Files.copy(oldDataFilePath, newDataFilePath, StandardCopyOption.REPLACE_EXISTING);
				Files.deleteIfExists(oldDataFilePath);
			}
		}
	}
}
