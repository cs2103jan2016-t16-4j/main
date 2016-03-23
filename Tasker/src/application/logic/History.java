package application.logic;

import java.util.Stack;

public class History {

    private Stack<Command> executedCommands;
    private Stack<Command> undoneCommands;

    private static History instance;
    
    private History(){
        this.executedCommands = new Stack<Command>();
        this.executedCommands = new Stack<Command>();
    }
    
    public static History getInstance(){
        if (instance == null){
            instance = new History();
        }
        return instance;
    }
    
    public void add(Command cmd){
        executedCommands.push(cmd);
    }
    
    
}
