package DAO;

/**
 * This class is responsible for displaying any exceptions particular to the database
 */
public class DatabaseException extends Exception {

    /**
     * Initializes constructor for class
     *
     * @param e Message that will be displayed
     */
    public DatabaseException(String e) {
        super(e);
    }

}
