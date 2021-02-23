package DAO;

import Model.User;
import java.sql.*;

/**
 * This class is used to access the user's table in the database, providing methods to add a new user,
 * querying a user's information and clearing user's information from the database
 */
public class UserDao {

    private Connection connection;

    /**
     * Initializing empty constructor for class
     */
    public UserDao() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public UserDao(Connection connection) {
        this.connection = connection;
    }


    /**
     * Creates a new user in the database
     *
     * @param newUser User that is going to be added
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void registerUser(User newUser) throws SQLException {}

    /**
     * Finds a new user in the database
     *
     * @param username Finding the user through its username
     * @return Null for now, but it will return the user
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public User findUser(String username) throws SQLException {
        return null;
    }

    /**
     * Clears all data from a user in the database
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void clearUser() throws SQLException {}
}
