/**
 * This error occurs when the database read in from a file is
 * improperly formatted according to specifications.
 * @author Kristen McClelland
 * @version 1.0
 */
public class CorruptDatabaseException extends Exception {
    private String message;
    /**
     * Default constructor with no parameters. Message is:
     * The database is corrupt!
     */
    public CorruptDatabaseException() {
        this("The database is corrupt!");
    }
    /**
     * Constructor that takes in a message for the Exception to be
     * returned when the Exception occurs.
     * @param message String message that is passed in to the Exception
     */
    public CorruptDatabaseException(String message) {
        this.message = message;
    }
    /**
     * Returns the message of this Exception
     * @return String message of this Exception
     */
    public String getMessage() {
        return message;
    }
}