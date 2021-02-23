package Services;

import Request.LoginRequest;
import Result.LoginResult;

import java.sql.*;

/**
 * This class is responsible for logging a new user into the application
 */
public class LoginService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public LoginService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public LoginService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Logs a user in
     *
     * @param request Takes in the request to login a user
     * @return Null for now, but it will return the user requesting login
     */
    public LoginResult login(LoginRequest request) {
        return null;
    }
}
