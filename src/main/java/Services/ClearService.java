package Services;

import java.sql.*;

/**
 * This class is responsible for deleting data from the database
 */
public class ClearService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public ClearService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public ClearService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Clears database
     */
    public void clearResult() {

    }
}
