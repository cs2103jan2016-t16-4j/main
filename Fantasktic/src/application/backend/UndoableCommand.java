package application.backend;
//@@author A0132632R

import java.util.ArrayList;

import application.storage.Task;

/**
 * This is an interface class for all UndoableCommand objects. This extends the
 * command object class, and command objects implementing this interface have
 * the added feature of being undoable.
 * 
 * @author Pratyush
 *
 */

public interface UndoableCommand extends Command {

    /**
     * This method is implemented by each command object. Logic can simply call
     * this method to execute that particular command object.
     * 
     * @param storageConnector
     *            The StorageConnector object reference should be passed to this
     *            method.
     * @param tasksOnScreen
     *            An ArrayList of Tasks that are currently being displayed
     *            should be passed to this.
     * @return A feedback object containing the specific information of the
     *         particular execution.
     */
    @Override
    Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasks);

    /**
     * This command undoes this particular command object.
     * 
     * @return A feedback object containing the specific information with the
     *         particular undoing of the command.
     * @throws NothingToUndoException
     *             This exception is thrown to signify that this particular
     *             command object does not yet have anything to undo. This
     *             allows the command object to be skipped.
     */
    Feedback undo() throws NothingToUndoException;
}
