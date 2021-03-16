package DAO;

import JSONReader.Deserializer;
import JSONReader.NamesData;
import Model.Person;
import Model.User;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

/**
 * This class is used to access a person's information in the database
 */
public class PersonDao {

    private ArrayList<String> maleNames;
    private ArrayList<String> femaleNames;
    private ArrayList<String> lastNames;

    private Connection connection;
    private int numOfPersons;

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
        numOfPersons = 0;
        try {
            NamesData f = Deserializer.deserializeNameList(new File("json/fnames.json"));
            NamesData m = Deserializer.deserializeNameList(new File("json/mnames.json"));
            NamesData s = Deserializer.deserializeNameList(new File("json/snames.json"));

            maleNames = new ArrayList<String>(Arrays.asList(m.getName()));
            femaleNames = new ArrayList<String>(Arrays.asList(f.getName()));
            lastNames = new ArrayList<String>(Arrays.asList(s.getName()));

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        numOfPersons++;
    }

    public int getNumOfPersons() { return numOfPersons; }

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

    /**
     * This function is here to clear the tree for the user - used in testing fill service
     *
     * @param username
     * @throws DataAccessException
     */
    public void clearPersonUsername(String username) throws DataAccessException {
        String sql = "DELETE FROM PersonTable WHERE userName = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            stmt.executeUpdate();
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

    /**
     * Public function that will take the current user, and generate a family tree
     *
     * @param currentUser
     * @param personID
     * @param numGenerations
     * @param eDao
     */
   public void generateTree(User currentUser, String personID, int numGenerations, EventDao eDao) throws DataAccessException {

       int year = 2021;

       Person userPerson = new Person(personID, currentUser.getUserName(),
               currentUser.getFirstName(), currentUser.getLastName(),
               currentUser.getGender(), null, null, null);

       addPerson(userPerson);
       // User is born
       eDao.generateBirth(currentUser.getUserName(), personID, (year - 26));

       if(numGenerations > 0) {
           generateParents(currentUser.getUserName(), personID, (year - 20), (numGenerations - 1), eDao, currentUser.getLastName());
       }
   }

    /**
     * This function serves the purpose of updating the user's father ID
     *
     * @param userID Current user ID
     * @param fatherID Unique identifier for father
     * @throws DataAccessException
     */
   public void insertFatherID(String userID, String fatherID) throws DataAccessException {
       String sql = "UPDATED Persons SET fatherID = ? WHERE personID = ?;";

       try (PreparedStatement stmt = connection.prepareStatement(sql)) {
           stmt.setString(1, fatherID);
           stmt.setString(2, userID);

           stmt.executeUpdate();
       } catch (SQLException e) {
           e.printStackTrace();
           throw new DataAccessException("Error encountered while updating user's fatherID");
       }
   }

    /**
     * This function server the purpose of updating the user's mother ID
     *
     * @param userID Current user ID
     * @param motherID Unique identifier for father
     * @throws DataAccessException
     */
    public void insertMotherID(String userID, String motherID) throws DataAccessException {
        String sql = "UPDATED Persons SET motherID = ? WHERE personID = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, motherID);
            stmt.setString(2, userID);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating user's motherID");
        }
    }

    /**
     * Public function to generate fake parents for user
     *
     * @param username
     * @param childID
     * @param childBirthYear
     * @param numGenerations
     * @param eDao
     * @param fatherLastName
     */
   public void generateParents(String username, String childID, int childBirthYear, int  numGenerations, EventDao eDao, String fatherLastName) throws DataAccessException {

       String fatherID = UUID.randomUUID().toString(); // Random ID's for parents
       String motherID = UUID.randomUUID().toString();

       String fatherName = maleNames.get(new Random().nextInt(maleNames.size()));
       String motherName = femaleNames.get(new Random().nextInt(femaleNames.size()));
       String motherLastName = lastNames.get(new Random().nextInt(femaleNames.size()));

       Person father = new Person(fatherID,
               username, fatherName,
               fatherLastName,
               "m",
               null,
               null,
               motherID);

       Person mother = new Person(motherID,
               username,
               motherName,
               motherLastName,
               "f",
               null,
               null,
               fatherID);

       insertFatherID(childID, fatherID);
       insertMotherID(childID, motherID);
       addPerson(father);
       addPerson(mother);
       // Generate and insert events for parents


       // Birth for User and attaching to parents
       eDao.generateBirth(username, fatherID, (childBirthYear - 26));
       eDao.generateBirth(username, motherID, (childBirthYear - 26));
       //Marrying parents
       eDao.generateMarriage(username, fatherID, motherID, (childBirthYear - 5));
       // Maybe generate death
       eDao.generateDeath(username, fatherID, (childBirthYear + 10));
       eDao.generateDeath(username, motherID, (childBirthYear + 8));

       if(numGenerations > 0) {
           generateParents(username, fatherID, (childBirthYear - 26), (numGenerations - 1), eDao, fatherLastName);
           generateParents(username, motherID, (childBirthYear - 26), (numGenerations - 1), eDao, motherLastName);
       }
   }
}
