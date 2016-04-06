package application.logic;

import application.storage.Storage;

public class Help implements Command {
	 private static final String COMMAND_ADD="add:\n--Adds new tasks (keyword add is not required)\n--Adds"
                 + " <Task> due by <date> at <Venue> and sets reminder\n--Note: by <Date>, @ <Venue>, remind <When>, priority <Level> are optional.\n"
                 + "--Note: Use ¡°instead of¡± to add recurring tasks.\n";
         private static final String COMMAND_SEARCH="search:\n--Search for tasks\n--Search by <Name> or priority <Level> or due <Date>\n";
         private static final String COMMAND_DELETE="delete:\n --Delete tasks\n--Deletes tasks by name or number assigned to the task\n";
         private static final String COMMAND_UPDATE="update:\n --Updates a task\n--Updates task to change description or details.\n"
                 + "--Note: Able to use keywords such as by/at/remind/priority to change the other details also\n";
         private static final String COMMAND_CLOSED="closed:\n--Closes/Marks a completed task.\n--Marks <task> as completed.\n";
         private static final String COMMAND_UNDO="undo:\n --Undoes the last input\n";
         private static final String COMMAND_STORAGE="storage:\n --Changes the storage location\n";
          private static final String COMMAND_HELP="help:\n--Shows help screen for commands.\n";
	public static Feedback execute(Storage storage) {
          
          String msg="";
          msg+=COMMAND_ADD;
          msg+=COMMAND_SEARCH;
          msg+=COMMAND_DELETE;
          msg+=COMMAND_UPDATE;
          msg+=COMMAND_CLOSED;
          msg+=COMMAND_UNDO;
          msg+=COMMAND_STORAGE;
          msg+=COMMAND_HELP;
         
          return new Feedback(msg, null);
	}

}
