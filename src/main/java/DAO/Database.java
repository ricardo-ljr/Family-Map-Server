package DAO;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * This class connects to the Database
 */
public class Database {

    private Connection conn;

    //Whenever we want to make a change to our database we will have to open a connection and use
    //Statements created by that connection to initiate transactions

    /**
     * A method to open a connection with a database
     *
     * @return A connection to the database
     * @throws DataAccessException An exception to handle errors in the database
     */
    public Connection openConnection() throws DataAccessException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:family-map2.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * A method that gets a connection to the database
     *
     * @return Connection to database
     * @throws DataAccessException An exception to handle errors in the database
     */
    public Connection getConnection() throws DataAccessException {
        if(conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    //When we are done manipulating the database it is important to close the connection. This will
    //End the transaction and allow us to either commit our changes to the database or rollback any
    //changes that were made before we encountered a potential error.

    //IMPORTANT: IF YOU FAIL TO CLOSE A CONNECTION AND TRY TO REOPEN THE DATABASE THIS WILL CAUSE THE
    //DATABASE TO LOCK. YOUR CODE MUST ALWAYS INCLUDE A CLOSURE OF THE DATABASE NO MATTER WHAT ERRORS
    //OR PROBLEMS YOU ENCOUNTER

    /**
     * A method to close a connection with a database
     *
     * @param commit Boolean returns whether connection was successful or not
     * @throws DataAccessException An exception to handle errors in the database
     */
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                //This will commit the changes to the database
                conn.commit();
            } else {
                //If we find out something went wrong, pass a false into closeConnection and this
                //will rollback any changes we made during this connection
                conn.rollback();
            }

            conn.close();
            conn = null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection");
        }
    }

    public void createTables() throws DataAccessException {

        try (Statement stmt = conn.createStatement()){

            String sql =
                    "CREATE TABLE IF NOT EXISTS User " +
                    "(" +
                    "    username VARCHAR(255) NOT NULL UNIQUE," +
                    "    password VARCHAR(255) NOT NULL," +
                    "    email VARCHAR(255) NOT NULL," +
                    "    firstName VARCHAR(255) NOT NULL," +
                    "    lastName VARCHAR(255) NOT NULL," +
                    "    gender CHAR(1) CHECK(gender in ('f', 'm')) NOT NULL," +
                    "    personID VARCHAR(255) NOT NULL," +
                    "    FOREIGN KEY (personID) REFERENCES Persons(personID)," +
                    "    PRIMARY KEY (username)" +
                    ");" +

                    "CREATE TABLE IF NOT EXISTS Persons" +
                    "(" +
                    "    personID VARCHAR(255) NOT NULL," +
                    "    associatedUsername VARCHAR(255)," +
                    "    firstName VARCHAR(255)," +
                    "    lastName VARCHAR(255)," +
                    "    gender CHAR(1) CHECK(gender in ('f', 'm')) NOT NULL," +
                    "    fatherID VARCHAR(255)," +
                    "    motherID VARCHAR(255)," +
                    "    spouseID VARCHAR(255)," +
                    "    PRIMARY KEY (personID)" +
                    ");" +

                    "CREATE TABLE IF NOT EXISTS Events " +
                    "(" +
                    "    eventID VARCHAR(255) NOT NULL," +
                    "    associatedUsername VARCHAR(255)," +
                    "    latitude float(15) NOT NULL," +
                    "    longitude float(15) NOT NULL," +
                    "    country VARCHAR(255) NOT NULL," +
                    "    city VARCHAR(255) NOT NULL," +
                    "    eventType VARCHAR(255) NOT NULL," +
                    "    year INT NOT NULL," +
                    "    personID VARCHAR(255) NOT NULL," +
                    "    FOREIGN KEY (personID) REFERENCES Persons(personID)," +
                    "    PRIMARY KEY (eventID)" +
                    ");" +

                    "CREATE TABLE IF NOT EXISTS AuthorizationTokens" +
                    "(" +
                    "    authToken VARCHAR(255) NOT NULL UNIQUE," +
                    "    associatedUsername VARCHAR(255) NOT NULL," +
                    "    PRIMARY KEY (authToken)" +
                    ");";


            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while creating tables");
        }
    }

    /**
     * This method clears the tables in the database
     *
     * @throws DataAccessException An exception to handle errors in the database
     */
    public void clearTables() throws DataAccessException {

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM User;" +
                         "DELETE FROM Persons;" +
                         "DELETE FROM Events;" +
                         "DELETE FROM AuthorizationTokens;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}

