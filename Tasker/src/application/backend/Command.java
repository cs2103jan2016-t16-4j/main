package application.backend;
//@@author A0132632R

import java.util.ArrayList;

import application.storage.Task;

/**
 * This is an interface class for all command objects. This is used to implement
 * a command pattern.
 * 
 * @author Pratyush
 *
 */
public interface Command {

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
    Feedback execute(StorageConnector storageConnector, ArrayList<Task> tasksOnScreen);

}
