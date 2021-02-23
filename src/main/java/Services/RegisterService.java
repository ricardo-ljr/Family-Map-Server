package Services;

import java.sql.*;

/**
 * This class is responsible for registering a new user to the database
 */
public class RegisterService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public RegisterService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public RegisterService(Connection connection) {
        this.connection = connection;
    }
}
