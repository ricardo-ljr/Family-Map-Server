package DAO;

import Model.Event;
import java.sql.*;
import java.util.ArrayList;

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
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public void addEvent(Event newEvent) throws DataAccessException{
        String sql = "INSERT INTO Events (eventID, associatedUsername, latitude, longitude, " +
                "country, city, eventType, year, personID) VALUES(?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newEvent.getEventID());
            stmt.setString(2, newEvent.getAssociatedUsername());
            stmt.setDouble(3, newEvent.getLatitude());
            stmt.setDouble(4, newEvent.getLongitude());
            stmt.setString(5, newEvent.getCountry());
            stmt.setString(6, newEvent.getCity());
            stmt.setString(7, newEvent.getEventType());
            stmt.setInt(8, newEvent.getYear());
            stmt.setString(9, newEvent.getPersonID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataAccessException("Error encountered when adding new event");
        }
    }

    /**
     * Finds a new event in the database
     *
     * @param eventID Finding the event through its unique identifier
     * @return Null for now, but it will return the event
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public Event findEvent(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventID"),
                        rs.getString("associatedUsername"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year"),
                        rs.getString("personID"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    public ArrayList<Event> findAllEvents(String userName) throws DataAccessException {
        Event event;
        ArrayList<Event> events = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userName);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventID"),
                        rs.getString("associatedUsername"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year"),
                        rs.getString("personID"));
                events.add(event);
            }
            return events;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when finding all events for a person");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        /**
         * Clears all data from an event in the database
         *
         * @throws SQLException An exception that provides information on a database access error or other errors
         */
        public void clearEvent () throws DataAccessException {
            try (Statement stmt = connection.createStatement()) {
                String sql = "DELETE FROM Events;";
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                throw new DataAccessException("Error encountered when clearing tables for events");
            }
        }
}

