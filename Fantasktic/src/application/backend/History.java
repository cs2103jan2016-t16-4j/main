package application.backend;
//@@author A0132632R

import java.util.Stack;

/**
 * This class maintains a stack of all previous Undoable Commands since the
 * start of the program. This is a singleton class. The Undo command uses this
 * class to undo the previous command. This is the class that allows multiple
 * undos.
 * 
 * @author Pratyush
 *
 */
public class History {

    private Stack<UndoableCommand> executedCommands;
    private static History instance;

    private History() {
        this.executedCommands = new Stack<UndoableCommand>();
    }

    public static History getInstance() {
        if (instance == null) {
            instance = new History();
        }
        return instance;
    }

    /**
     * This method should be used to add a command object to the history stack.
     * If the command is an Undoable command, it is added, otherwise discarded.
     * 
     * @param cmd The Command object you wish to add.
     */

    public void add(Command cmd) {
        if (cmd instanceof UndoableCommand) {
            UndoableCommand cmd_undoable = (UndoableCommand) cmd;
            executedCommands.add(cmd_undoable);
        }
    }

    /**
     * This method should be used to undo the previous command.
     * 
     * @return A feedback object related to the undoing of that particular
     *         command.
     */
    public Feedback undo() {
        try {
            UndoableCommand cmd = executedCommands.pop();
            Feedback feedback = cmd.undo();
            return feedback;
        } catch (NothingToUndoException e) {
            return undo();
        }
    }

}
