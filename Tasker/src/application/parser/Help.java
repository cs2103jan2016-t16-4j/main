package application.parser;

import application.storage.Storage;

public class Help implements Command {
	
	public String execute(Storage storage) {
          String msg=String.format("%30s", "add");
          msg=msg+"    --Adds new tasks (keyword add is not required)\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Adds <Task> due by <date> at <Venue> and sets reminder\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Note: by <Date>, @ <Venue>, remind <When>, priority <Level> are optional.\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Note: Use “every” instead of “by” to add recurring tasks.\n";
          
          msg=msg+String.format("%30s", "search");
          msg=msg+"    --Search for tasks\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Search by <Name> or priority <Level> or due <Date>\n";
          
          msg=msg+String.format("%30s", "delete");
          msg=msg+"    --Delete tasks\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Deletes tasks by name or number assigned to the task\n";  
          
          msg=msg+String.format("%30s", "update");
          msg=msg+"    --Updates a task\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Updates task to change description or details.\n";           
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Note: Able to use keywords such as by/at/remind/priority to change the other details also\n"; 
          
          msg=msg+String.format("%30s", "closed");
          msg=msg+"    --Closes/Marks a completed task.\n";
          msg=msg+String.format("%30s", " ");
          msg=msg+"    --Marks <task> as completed.\n"; 
          
          msg=msg+String.format("%30s", "undo");
          msg=msg+"    --Undoes the last input\n";
          
          msg=msg+String.format("%30s", "storage");
          msg=msg+"    --Changes the storage location\n";
          
          msg=msg+String.format("%30s", "help");
          msg=msg+"    --Shows help screen for commands.\n";
          return msg;
	}

}
