package DAO;

import Model.Event;
import java.sql.*;

/**
 * This class is used to access an event's information in the database and its related user
 */
public class EventDao {

    private Connection connection;

    /**
     * Initializing empty constructor for class
     */
    public EventDao() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public EventDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new event in the database
     *
     * @param newEvent Event that is going to be added
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void addEvent(Event newEvent) throws SQLException {}

    /**
     * Finds a new event in the database
     *
     * @param eventID Finding the event through its unique identifier
     * @return Null for now, but it will return the event
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public Event findEvent(String eventID) throws SQLException {
        return null;
    }

    /**
     * Clears all data from an event in the database
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void clearEvent() throws SQLException {}
}
