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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileManager {
	
	private static final String FILE_CLOSED_NAME = "TaskerDataHistory.txt";
	private static final String FILE_DATA_NAME = "TaskerData.txt";
	private static final String FILE_DIRECTORY_NAME = "TaskerDirectory.txt";
	private String closedFilePath = "";
	private String dataFilePath = "";	

	private void clear(String path) throws FileNotFoundException {
		File f = new File(path);
		if (f.exists()) {
			PrintWriter fw = new PrintWriter(path);
			fw.print("");
			fw.close();
		}
	}
	
	public String getClosedFilePath() {
		return closedFilePath;
	}
	
	public String getDataFilePath() {
		return dataFilePath;
	}
	
	public boolean isDirectoryExists() throws IOException {
		// check whether its user first time opening program
		File d = new File(FILE_DIRECTORY_NAME);
		if (!d.exists()) {
			PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(FILE_DIRECTORY_NAME, true)));
			fw.print("");		// indicate path name is not specified yet 
			fw.close();
			return false;
		}
		else {
			return true;
		}
	}
	
	public ArrayList<Task> loadFile(String filePath) throws NumberFormatException, IOException {
		File f = new File(filePath);
		BufferedReader in;
		ArrayList<Task> list = new ArrayList<Task>();
		if (f.exists()) {
		String readText;
		in = new BufferedReader(new FileReader(filePath));
		// skip first line first
		readText = in.readLine();
		Gson gson = new GsonBuilder().create();
		while ((readText = in.readLine()) != null) {
			Task task = gson.fromJson(readText, Task.class);
			list.add(task);
		}
		in.close();
		}
		return list;
	}
	
	public void loadDirectoryFile() throws IOException {
		// check whether user specified a custom directory to save datafile
		BufferedReader in = new BufferedReader(new FileReader(FILE_DIRECTORY_NAME));
		String readText = in.readLine();
		if (readText == null) {
//			System.out.println("User has not specified directory to store data file yet. \nThus, data file will reside in the program's folder.");
			closedFilePath = FILE_CLOSED_NAME;
			dataFilePath = FILE_DATA_NAME;
		} else {
			closedFilePath = readText + FILE_CLOSED_NAME;
			dataFilePath = readText + FILE_DATA_NAME;
		}
		System.out.println("Closed file path : "+closedFilePath);
		System.out.println("Data file path : "+dataFilePath);
		in.close();
	}
	
	public int loadTaskIndex() throws IOException {
		File f = new File(dataFilePath);
		if (f.exists()){
		BufferedReader in = new BufferedReader(new FileReader(dataFilePath));
		String readText = in.readLine();
		in.close();
		return Integer.parseInt(readText);
		} 
		return 0;
	}
	
	private void saveAllTasks(ArrayList<Task> list, String filePath) throws IOException {		
			PrintWriter fwz = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)));
			Gson gson = new Gson();
			for (int i = 0; i<list.size(); i++){			
				String json = gson.toJson(list.get(i));
//				System.out.println(json);
				fwz.println(json);

			}
			fwz.close();
			
	}

	public void saveFile(ArrayList<Task> list, String filePath) throws IOException {
		// #check if file exists
		File f = new File(filePath);
		if (!f.exists()) {
			f.createNewFile();
		}
		
		// #save all the tasks
		saveAllTasks(list, filePath);		
	}
	
	public void saveTaskIndex(int index) throws IOException {
		// #clear datafile first
		clear(dataFilePath);

		PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(dataFilePath, true)));
		fw.println(index);
		fw.close();
	}
	
	public boolean setDirectory(String path) throws IOException {
		clear(FILE_DIRECTORY_NAME);
		
		PrintWriter fw = new PrintWriter(FILE_DIRECTORY_NAME);
		if (path.equalsIgnoreCase("")) {
			closedFilePath = FILE_CLOSED_NAME;
			dataFilePath = FILE_DATA_NAME;
			fw.close();
			return false;
		}
		else {
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
		}
	}

}
