package DAO;

import Model.Person;
import java.sql.*;

/**
 * This class is used to access a person's information in the database
 */
public class PersonDao {

    private Connection connection;

    /**
     * Initializing empty constructor for class
     */
    public PersonDao() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public PersonDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new person in the database
     *
     * @param newPerson Person that is going to be added
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void addPerson(Person newPerson) throws SQLException {}

    /**
     * Finds a new person in the database
     *
     * @param personID Finding the person through its unique identifier
     * @return Null for now, but it will return the user
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public Person findPerson(String personID) throws SQLException {
        return null;
    }

    /**
     * Clears all data from a person in the database
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void clearPerson() throws SQLException {}
}
