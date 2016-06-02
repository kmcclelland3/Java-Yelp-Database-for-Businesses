/**
 * Is produced if a database has 2 of the same restaurant.
 * @author Kristen McClelland
 * @version 1.0
 */
public class DuplicateRestaurantException extends RuntimeException {
    private String message;
    /**
     * Default constructor with no parameters. Message is:
     * There is a duplicate restaurant in the database!
     */
    public DuplicateRestaurantException() {
        this("There is a duplicate restaurant in the database!");
    }
    /**
     * Constructor that takes in a message for the Exception to be
     * returned when the Exception occurs
     * @param message String message that is passed in to the Exception
     */
    public DuplicateRestaurantException(String message) {
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