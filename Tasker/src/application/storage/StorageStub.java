//@@author A0110422E
package application.storage;

public class StorageStub {

	public void addTaskInList() {
		
	}
	
	public void closeTaskInList() {
		
	}
	
	public void deleteTaskInList() {
		
	}

	public void loadFile() {
		
	}

	public void saveFile() {

	}
	
	// --------------- gerald contribution onwards ----------------- //
	//                        |										 //
	//                        |										 //
	//                        V                   			 		 //
	
	// DD/MM/YYYY = 10 characters = 0-9 (index)
	// takes in String as "DD/MM/YYYY" with 2 slashes included
	// returns true = if date is before and equal against searched-date
	// returns false = if date is not before/equal against searched-date(i.e exceeds)
	//@@author A0125522R
	public boolean dateIsBeforeOrEqual(String scannedDate, String dateSearch) {
		return true;	
	}
	
	// isSearchBy - indicate if its searching <by> date (true), or searching <on> date (false)
	public void searchByDate(String dateSearch, boolean isSearchBy) {
		if (isSearchBy) {
			for (int i = 0; i < 3; i++) {
				if (dateIsBeforeOrEqual("dd/mm/yyyy", dateSearch)) {
					storeSearchResults();
				}
			}
		}
		else {
			for (int i = 0; i < 3; i++) {
				storeSearchResults();
			}
		}					
		showSearchResults();
	}

	// search the task with the index indicate (Task object) and update
	public void findTaskAndUpdate(int index) {
		System.out.println("Finding the task in the search list to update...");
		updateTaskInList();
	}

	// returns Logic a search results list to update using index
	public String getSearchResults() {
		return "A list of search results with index no";
	}
	// updates the task with the necessary details only
	public void updateTaskInList() {
		System.out.println("Updated the task..");
	}

	// search for all possible tasks via taskName
	public void searchByTask(String taskSearch) {
		for (int i = 0; i < 3; i++) {
				storeSearchResults();
		}
		showSearchResults();
	}

	// display total results found + return Logic a search list
	public String showSearchResults() {
		System.out.println("Search : <number> results found.");
		return "A list of search results with index no";
	}

	// store tasks into current search list
	public void storeSearchResults() {
		System.out.println("Storing task into search results...");
	}

	// returns Logic feedback on success/failure on add/update/delete etc
	public String showFeedback() {
		return "Feedback on success/failure from Storage returned to Logic";
	}

}
