package application.backend;

//@@author A0132632R

/**
 * This is an exception to signify that the user has not entered any description
 * for a task s/he wants to add.
 * 
 * @author Pratyush
 *
 */
public class NoDescriptionException extends Exception {

    private static final long serialVersionUID = 1L;

    public NoDescriptionException() {
        super();
    }
}
