package DAO;

import Model.Person;
import Model.User;

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
