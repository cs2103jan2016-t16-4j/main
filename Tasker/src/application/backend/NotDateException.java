package application.backend;
//@@author A0132632R

/**
 * This exception is used to signify that the words following "date keywords"
 * such as "from","to","by", do not represent a date. This is then used to add 
 * these words to the description.
 * @author Pratyush
 *
 */
public class NotDateException extends Exception{

    private static final long serialVersionUID = 1L;

    public NotDateException() {
        super();
    }
    
}
