package application.parser;

public interface Command {

    String execute();
    
    String undo();
}
