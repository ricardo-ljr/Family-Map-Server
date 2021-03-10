package DAO;

/**
 * This class is responsible for displaying any exceptions particular to the database
 */
public class DataAccessException extends Exception {

    /**
     * Initializes constructor for class
     *
     * @param message Message that will be displayed
     */
    public DataAccessException(String message) {

        super(message);
    }

    DataAccessException()
    {
        super();
    }

}
