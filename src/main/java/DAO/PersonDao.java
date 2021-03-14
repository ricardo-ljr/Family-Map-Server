package DAO;

import Model.Person;
import Model.User;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

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
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public void addPerson(Person newPerson) throws DataAccessException {
        String sql = "INSERT INTO Persons(personID, associatedUsername, firstName, lastName, gender, fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newPerson.getPersonID());
            stmt.setString(2, newPerson.getAssociatedUsername());
            stmt.setString(3, newPerson.getFirstName());
            stmt.setString(4, newPerson.getLastName());
            stmt.setString(5, newPerson.getGender());
            stmt.setString(6, newPerson.getFatherID());
            stmt.setString(7, newPerson.getMotherID());
            stmt.setString(8, newPerson.getSpouseID());

            stmt.executeUpdate();
        } catch(SQLException e) {
            throw new DataAccessException("Error encountered while inserting person into database");
        }
    }

    /**
     * Finds a new person in the database
     *
     * @param personID Finding the person through its unique identifier
     * @return Return the person
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public Person findPerson(String personID) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"),
                        rs.getString("associatedUsername"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("fatherID"),
                        rs.getString("motherID"),
                        rs.getString("spouseID"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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

    public boolean personExists(String personID) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM Persons WHERE personID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personID);
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
     * Gets all people given username
     *
     * @param username username
     * @return
     * @throws DataAccessException
     */
    public ArrayList<Person> getPersonsForUsername(String username) throws DataAccessException {
        String sql = "SELECT username, FirstName, LastName, Gender, \"mother id\", \"father id\", \"spouse id\", personID " +
                "FROM persons " +
                "WHERE \"username\"=\"" + username + "\"";

        ArrayList<Person> result = new ArrayList<Person>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(new Person(rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while looking up all persons");
        }

        return result;
    }

    public void clearPersonUsername(String username) throws DataAccessException {
        try(PreparedStatement statement = connection.prepareStatement("DELETE FROM persons WHERE username = \""+ username + "\";")){
            statement.execute();
        } catch (SQLException e) {
            throw new DataAccessException("Error when trying to clear person given username");
        }
    }

    /**
     * Clears all data from a person in the database
     *
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public void clearPerson() throws DataAccessException {
        try (Statement stmt = connection.createStatement()){
            String sql = "DELETE FROM Persons";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
