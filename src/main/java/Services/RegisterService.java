package Services;

import Request.RegisterRequest;
import Result.RegisterResult;

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

    /**
     * Registers a new user
     *
     * @param request Takes in the request to create a new user
     * @return Null for now, but it will return new user
     */
    public RegisterResult register(RegisterRequest request) {
        return null;
    }
}
