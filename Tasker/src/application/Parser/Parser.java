package application.Parser;
import java.util.Arrays;

class Parser {
	private enum CommandKeyword {
		ADD, SEARCH, DELETE, UPDATE, CLOSED, UNDO, HELP, STORAGE, NOKEYWORD
	}

	private CommandKeyword getCommandKeywordType(String userInput) {
		CommandKeyword keywordType = CommandKeyword.NOKEYWORD;
		String firstWord = getFirstWord(userInput);
		if (checkCommandKeywordExists(firstWord) == true) {
			keywordType = CommandKeyword.valueOf(firstWord);
		}
		return keywordType;
	}

	private String getFirstWord(String str) {
		int firstSpace = str.indexOf(' ');
		String firstWord = str.substring(0, firstSpace);
		return firstWord;
	}

	private String getRestString(String str) {
		int firstSpace = str.indexOf(' ');
		String rest = str.substring(firstSpace);
		return rest;
	}

	private Boolean checkCommandKeywordExists(String str) {
		String[] KeywordList = { "add", "search", "delete", "update", "closed", "undo", "help", "storage" };
		boolean KeywordExists = Arrays.asList(KeywordList).contains(str);
		return KeywordExists;
	}

	private String getPriority(String str) {
		String priority = "";
		if (checkPriorityKeyword(str) != false) {
			String priorityStr = str.substring(str.indexOf("Priority") + 9);
			String priorityDetails = priorityStr.substring(priorityStr.indexOf(". "));
			priority.concat(priorityDetails);
		}
		return priority;
	}

	// This method is used to return the index of the time keyword, when it
	// exists.
	private int locateTimeKeyword(String str) {
		int index = -1;
		if (str.indexOf("by") != -1) {
			index = str.indexOf("by");
		}
		if (str.indexOf("on") != -1) {
			index = str.indexOf("on");
		}
		if (str.indexOf("before") != -1) {
			index = str.indexOf("before");
		}
		return index;
	}

	// This method is used to return the index of the location keyword, when it
	// exists.
	private int locateLocationKeyword(String str) {
		int index = -1;
		if (str.indexOf("at") != -1) {
			index = str.indexOf("at");
		}
		if (str.indexOf("in") != -1) {
			index = str.indexOf("in");
		}
		if (str.indexOf("near") != -1) {
			index = str.indexOf("near");
		}
		return index;
	}

	private String getRemindTime(String str) {
		String remindTime = "";
		if (checkRemindKeyword(str) != false) {
			String remindTimeStr = str.substring(str.indexOf("Remind") + 7);
			String remindTimeDetails = remindTimeStr.substring(remindTimeStr.indexOf(". "));
			remindTime.concat(remindTimeDetails);
		}
		return remindTime;
	}

	// Haven't include the case of keyword "every" yet.
	// This method is used to get time from a string that includes both time and
	// location.
	private String getTime(String str) {
		String timeStr = "";
		if (checkTimeKeyword(str) == false) {
			if (checkLocationKeyword(str) == false) {
				// when there is no keyword for location and time.
			} else {
				// when there is only keyword for location
			}
		} else {
			if (checkLocationKeyword(str) == false) {
				// when there is only keyword for time.
				int indexOfTimeKeyword = locateTimeKeyword(str);
				String tempStr = str.substring(indexOfTimeKeyword);
				timeStr = tempStr.substring(tempStr.indexOf(" ") + 1, tempStr.indexOf(". "));
			} else {
				// when there is both keyword for time and location.
				int indexOfTimeKeyword = locateTimeKeyword(str);
				int indexOfLocationKeyword = locateLocationKeyword(str);
				String tempStr = str.substring(indexOfTimeKeyword);
				timeStr = tempStr.substring(tempStr.indexOf(" ") + 1,
						Math.min(tempStr.indexOf(". "), tempStr.indexOf(indexOfLocationKeyword) - 1));
			}
		}
		return timeStr;
	}

	// This method is used to get location from a string that includes both time
	// and location.
	private String getLocation(String str) {
		String locationStr = "";
		if (checkTimeKeyword(str) == false) {
			if (checkLocationKeyword(str) == false) {
				// when there is no keyword for location and time.
			} else {
				// when there is only keyword for location
				int indexOfLocationKeyword = locateLocationKeyword(str);
				String tempStr = str.substring(indexOfLocationKeyword);
				locationStr = tempStr.substring(tempStr.indexOf(" ") + 1, tempStr.indexOf(". "));
			}
		} else {
			if (checkLocationKeyword(str) == false) {
				// when there is only keyword for time.
			} else {
				// when there is both keyword for time and location.
				int indexOfTimeKeyword = locateTimeKeyword(str);
				int indexOfLocationKeyword = locateLocationKeyword(str);
				String tempStr = str.substring(indexOfLocationKeyword);
				locationStr = tempStr.substring(tempStr.indexOf(" ") + 1,
						Math.min(tempStr.indexOf(". "), tempStr.indexOf(indexOfTimeKeyword) - 1));

			}
		}
		return locationStr;
	}

	private boolean checkPriorityKeyword(String str) {
		boolean priorityKeywordExists = false;
		if (str.indexOf("Priority") != -1) {
			priorityKeywordExists = true;
		}
		return priorityKeywordExists;
	}

	private boolean checkRemindKeyword(String str) {
		boolean remindKeywordExists = false;
		if (str.indexOf("Remind") != -1) {
			remindKeywordExists = true;
		}
		return remindKeywordExists;
	}

	private boolean checkTimeKeyword(String str) {
		boolean timeKeywordExists = false;
		if (str.indexOf("by") != -1 || str.indexOf("on") != -1 || str.indexOf("before") != -1) {
			timeKeywordExists = true;
		}
		return timeKeywordExists;
	}

	private boolean checkLocationKeyword(String str) {
		boolean locationKeywordExists = false;
		if (str.indexOf("at") != -1 || str.indexOf("near") != -1 || str.indexOf("in") != -1) {
			locationKeywordExists = true;
		}
		return locationKeywordExists;
	}

	public Command parseInput(String str) {
		Command commandObject = new Command();
		CommandKeyword cmd = getCommandKeywordType(str);
		switch (cmd) {
		case ADD:
			commandObject.writeCommandtype("add");
			String restString = getRestString(str);

			if (checkPriorityKeyword(restString) == true) {
				String priority = getPriority(restString);
				commandObject.writePriority(priority);
				// update restString, delete priority sentence.
				restString = restString.replace(priority, "");
			}

			if (checkRemindKeyword(restString) == true) {
				String remind = getRemindTime(restString);
				commandObject.writeRemindDate(remind);
				// update restString, delete remind sentence.
				restString = restString.replace(remind, "");
			}
			
			if (checkTimeKeyword(restString) == true) {
				String endTime = getTime(restString);
				commandObject.writeEndDateAndTime(endTime);
				// update restString, delete end time sentence.
				restString = restString.replace(endTime, "");
			}
			
			if (checkLocationKeyword(restString) == true) {
				String location = getLocation(restString);
				commandObject.writeLocation(location);
				// update restString, delete location sentence.
				restString = restString.replace(location, "");
			}
			break;
		case CLOSED:
			break;
		case DELETE:
			break;
		case HELP:
			break;
		case NOKEYWORD:
			break;
		case SEARCH:
			break;
		case STORAGE:
			break;
		case UNDO:
			break;
		case UPDATE:
			break;
		default:
			break;
		}
		return commandObject;
	}
}
