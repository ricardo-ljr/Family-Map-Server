package DAO;

import Model.AuthToken;
import Model.Person;

import java.sql.*;

/**
 * This class is used to access the authorization token information in the database
 */
public class AuthTokenDao {

    private Connection connection;

    /**
     * Initializing empty constructor for class
     */
    public AuthTokenDao() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public AuthTokenDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new authToken in the database
     *
     * @param newToken Token that is going to be added
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void addToken(AuthToken newToken) throws SQLException {}

    /**
     * Finds a new token in the database
     *
     * @param authToken Finding the authToken through its unique identifier
     * @return Null for now, but it will return the authToken
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public Person findPerson(String authToken) throws SQLException {
        return null;
    }

    /**
     * Clears all data from an authToken in the database
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void clearAuthToken() throws SQLException {}
}
