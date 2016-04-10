package application.backend;
//@@author A0132632R

/**
 * There are certain undoableCommands that, depending on the way they were
 * executed, may not yet have anything to undo. This exception signifies this
 * and enables the History package to skip these command objects.
 * 
 * @author Pratyush
 *
 */
public class NothingToUndoException extends Exception {

    private static final long serialVersionUID = 1L;

    public NothingToUndoException() {
        super();
    }
}
