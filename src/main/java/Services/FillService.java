package Services;

import Result.MessageResult;

import java.sql.*;

/**
 * This class is responsible for filing data to the database
 */
public class FillService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public FillService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public FillService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Fills database and returns a message of success or failure
     *
     * @param request Takes in the request to fill in database
     * @return null for now, but it will return message of success or failure
     */
    public MessageResult fill(Boolean request) {
        return null;
    }
}
