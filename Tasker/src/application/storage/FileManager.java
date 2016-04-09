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
 * FileManager is used to save/load tasks into/into text file.
 * It also holds the current storage location path, and the data files (open and close lists) path.
 */
public class FileManager {

	// Constants
	private static final String FILE_CLOSED_NAME = "TaskerDataHistory.txt";
	private static final String FILE_DATA_NAME = "TaskerData.txt";
	private static final String FILE_DIRECTORY_NAME = "TaskerDirectory.txt";
	private static final int NO_TASK = 0;
	private static final String EMPTY = "";
	private static final String EMPTY_PATH = "";
	
	// Path variables
	private String closedFilePath = "";
	private String dataFilePath = "";
	
	// Logger
	private static Logger logger = LoggerHandler.getLog();

	/**
	 * Clears the given file.
	 */
	public void clear(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			PrintWriter fw;
			try {
				fw = new PrintWriter(filePath);
				fw.print(EMPTY);
				fw.close();
			} catch (FileNotFoundException e) {
				logger.log(Level.SEVERE, "Error clearing file.");
			}
		}
	}

	/**
	 * Return the path of close list.
	 */
	public String getClosedFilePath() {
		return closedFilePath;
	}

	/**
	 * Return the path of data list.
	 */
	public String getDataFilePath() {
		return dataFilePath;
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
				logger.log(Level.SEVERE, "Error initialising directory.");
				return false;
			}
		} else {
			return true;
		}
	}

	/**
	 * Loads the given file of tasks (close,open) into ArrayList<Task>.
	 */
	public ArrayList<Task> loadFile(String filePath) {
		File file = new File(filePath);
		BufferedReader in;
		ArrayList<Task> list = new ArrayList<Task>();
		logger.log(Level.INFO, "Loading tasks into list");
		if (file.exists()) {
			String readText;
			try {
				in = new BufferedReader(new FileReader(filePath));
				if (filePath.equalsIgnoreCase(dataFilePath)) {
					// skip first line first for data file
					readText = in.readLine();
					Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer())
							.registerTypeAdapter(EventTask.class, new TaskSerializer())
							.registerTypeAdapter(DeadlineTask.class, new TaskSerializer())
							.registerTypeAdapter(FloatingTask.class, new TaskSerializer()).create();
					while ((readText = in.readLine()) != null) {
						Task task = gson.fromJson(readText, Task.class);
						list.add(task);
					}
					in.close();
				}
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error finding file.");
			}
		}
		return list;
	}

	/**
	 * Loads the directory file and initialise the close and data file path.
	 */
	public void loadDirectoryFile() {
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(FILE_DIRECTORY_NAME));
			logger.log(Level.INFO, "Loading directory file");
			String readText;

			readText = in.readLine();
			if (readText == null) {
				closedFilePath = FILE_CLOSED_NAME;
				dataFilePath = FILE_DATA_NAME;
			} else {
				closedFilePath = readText + FILE_CLOSED_NAME;
				dataFilePath = readText + FILE_DATA_NAME;
			}
			in.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error loading directory file.");
		}
	}

	/**
	 * Loads the total task index count.
	 */
	public int loadTaskIndex() {
		File f = new File(dataFilePath);
		if (f.exists()) {
			BufferedReader in;
			try {
				in = new BufferedReader(new FileReader(dataFilePath));
				String readText;
				readText = in.readLine();
				in.close();
				return Integer.parseInt(readText);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error initialising data file task index.");
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
		PrintWriter fwz;
		logger.log(Level.INFO, "Saving tasks into file");
		try {
			fwz = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
			Gson gson = new GsonBuilder().registerTypeAdapter(Task.class, new TaskSerializer())
					.registerTypeAdapter(EventTask.class, new TaskSerializer())
					.registerTypeAdapter(DeadlineTask.class, new TaskSerializer())
					.registerTypeAdapter(FloatingTask.class, new TaskSerializer()).create();
			for (int i = 0; i < list.size(); i++) {
				String json = gson.toJson(list.get(i));
				fwz.println(json);
			}
			fwz.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error saving all tasks.");
		}
	}

	/**
	 * Saves a given file.
	 */
	public void saveFile(ArrayList<Task> list, String filePath) {
		File f = new File(filePath);
		logger.log(Level.INFO, "Saving file");
		if (!f.exists()) {
			try {
				f.createNewFile();
				// save all the tasks
				saveAllTasks(list, filePath);
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error saving file");
			}
		}
	}

	/**
	 * Saves the total task index count.
	 */
	public void saveTaskIndex(int index) {
		// #clear datafile first
		clear(dataFilePath);

		PrintWriter fw;
		try {
			fw = new PrintWriter(new BufferedWriter(new FileWriter(dataFilePath, true)));
			fw.println(index);
			fw.close();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error saving task index");
		}
	}

	/**
	 * Set the directory path that the user specified.
	 */
	public boolean setDirectory(String path) {
		if (path.equalsIgnoreCase(EMPTY_PATH)) {
			logger.log(Level.INFO, "No directory choosen.");
			return false;
		} else {
			clear(FILE_DIRECTORY_NAME);
			logger.log(Level.INFO, "New directory choosen");
			PrintWriter fw;
			try {
				fw = new PrintWriter(FILE_DIRECTORY_NAME);
				// get current path
				Path oldClosedFilePath = Paths.get(closedFilePath);
				Path oldDataFilePath = Paths.get(dataFilePath);

				// store new path
				closedFilePath = path + FILE_CLOSED_NAME;
				dataFilePath = path + FILE_DATA_NAME;
				fw.println(path);

				// migration of data files
				Path newClosedFilePath = Paths.get(closedFilePath);
				if (oldClosedFilePath.toFile().exists()) {
					Files.copy(oldClosedFilePath, newClosedFilePath, StandardCopyOption.REPLACE_EXISTING);
					Files.deleteIfExists(oldClosedFilePath);
				}
				Path newDataFilePath = Paths.get(dataFilePath);
				if (oldDataFilePath.toFile().exists()) {
					Files.copy(oldDataFilePath, newDataFilePath, StandardCopyOption.REPLACE_EXISTING);
					Files.deleteIfExists(oldDataFilePath);
				}
				fw.close();
				return true;
			} catch (IOException e) {
				logger.log(Level.SEVERE, "Error setting directory");
				return false;
			}
		}
	}
}
