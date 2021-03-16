package DAO;

import JSONReader.Deserializer;
import JSONReader.Location;
import JSONReader.LocationData;
import Model.Event;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * This class is used to access an event's information in the database and its related user
 */
public class EventDao {

    private Connection connection;
    private LocationData locations;
    private int numOfEvents;

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

        try {
            locations = Deserializer.deserializeLocations(new File("json/location.json"));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Creates a new event in the database
     *
     * @param newEvent Event that is going to be added
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public void addEvent(Event newEvent) throws DataAccessException{
        String sql = "INSERT INTO Events (eventID, associatedUsername, personID, latitude, longitude, " +
                "country, city, eventType, year) VALUES(?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, newEvent.getEventID());
            stmt.setString(2, newEvent.getAssociatedUsername());
            stmt.setString(3, newEvent.getPersonID());
            stmt.setFloat(4, newEvent.getLatitude());
            stmt.setFloat(5, newEvent.getLongitude());
            stmt.setString(6, newEvent.getCountry());
            stmt.setString(7, newEvent.getCity());
            stmt.setString(8, newEvent.getEventType());
            stmt.setInt(9, newEvent.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new DataAccessException("Error encountered when adding new event");
        }
        numOfEvents++;
    }

    /**
     * Function used in fill service to return number of events that are added
     *
     * @return
     */
    public int getNumOfEvents() { return numOfEvents; }

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
                        rs.getString("personID"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year"));
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

    /**
     * Function that locates all the events give a username
     *
     * @param userName
     * @return
     * @throws DataAccessException
     */
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
                        rs.getString("personID"),
                        rs.getFloat("latitude"),
                        rs.getFloat("longitude"),
                        rs.getString("country"),
                        rs.getString("city"),
                        rs.getString("eventType"),
                        rs.getInt("year"));

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
     * Function that returns a boolean to check whether an event given its id exists!
     *
     * @param eventID
     * @return
     * @throws DataAccessException
     */
    public boolean eventExists(String eventID) throws DataAccessException {

        ResultSet rs = null;
        String sql = "SELECT * FROM Events WHERE eventID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();

            if (!rs.next())
                return false;
            else
                return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * This function is in charge of generating a random event and retrieving a random location
     *
     * @param username Current user logged in
     * @param personID Unique person Identifier
     * @param eDao Event Data Access Object
     * @param eventType Type of event, aka marriage, birth, death, baptism, etc...
     * @param year Year event happened
     * @return
     * @throws DataAccessException
     */
    public Event generateRandomEvent(String username, String personID, EventDao eDao, String eventType, int year) throws DataAccessException {
        Location location = locations.getLocations()[new Random().nextInt(977)];

        return new Event (UUID.randomUUID().toString(), username, personID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                eventType,
                year);

    }

    /**
     * This function takes care of generating a birth event for the user
     *
     * @param username User logged in
     * @param personID Unique person Identifier
     * @param childBirthYear Birth year
     * @throws DataAccessException
     */
    public void generateBirth(String username, String personID, int childBirthYear) throws DataAccessException {
        Location location = locations.getLocations()[new Random().nextInt(977)];
        Event birth = new Event(UUID.randomUUID().toString(),
                username, personID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "birth",
                childBirthYear);
        addEvent(birth);
    }

    /**
     * This function generates a marriage for the parents user
     *
     * @param username user logged in
     * @param fatherID Unique identifier for the user's father
     * @param motherID Unique identifier for the user's mother
     * @param childBirthYear Year child was born
     * @throws DataAccessException
     */
    public void generateMarriage(String username, String fatherID, String motherID, int childBirthYear) throws DataAccessException {
        Location location = locations.getLocations()[new Random().nextInt(977)];

        Event fathersMarriage = new Event(UUID.randomUUID().toString(),
                username,
                fatherID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "marriage",
                (childBirthYear - 5));
        Event mothersMarriage = new Event(UUID.randomUUID().toString(),
                username,
                motherID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "marriage",
                (childBirthYear - 5));

        addEvent(fathersMarriage);
        addEvent(mothersMarriage);
    }

    /**
     * This function takes care of generating a death event
     *
     * @param username User logged in
     * @param personID Unique person identifier
     * @param childBirthYear Year child was born
     * @throws DataAccessException
     */
    public void generateDeath(String username, String personID, int childBirthYear) throws DataAccessException {
        Location location = locations.getLocations()[new Random().nextInt(977)];

        Event death = new Event(UUID.randomUUID().toString(),
                username, personID,
                location.getLatitude(),
                location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                "death",
                (childBirthYear + 65));

        addEvent(death);
    }

    /**
     * Function to delete event giver username
     *
     * @param userName
     * @throws DataAccessException
     */
    public void deleteAllEvents(String userName) throws DataAccessException {
        String sql = "DELETE FROM Events WHERE associatedUsername = ?;";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userName);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error while delete events from a specific user in database");
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

