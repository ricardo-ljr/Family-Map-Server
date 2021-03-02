package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
     * @throws DatabaseException An exception to handle errors in the database
     */
    public Connection openConnection() throws DatabaseException {
        try {
            //The Structure for this Connection is driver:language:path
            //The path assumes you start in the root of your project unless given a non-relative path
            final String CONNECTION_URL = "jdbc:sqlite:familymap.sqlite";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * A method that gets a connection to the database
     *
     * @return Connection to database
     * @throws DatabaseException An exception to handle errors in the database
     */
    public Connection getConnection() throws DatabaseException {
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
     * @throws DatabaseException An exception to handle errors in the database
     */
    public void closeConnection(boolean commit) throws DatabaseException {
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
            throw new DatabaseException("Unable to close database connection");
        }
    }

    /**
     * This method clears the tables in the database
     *
     * @throws DatabaseException An exception to handle errors in the database
     */
    public void clearTables() throws DatabaseException
    {

        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM Events" +
                         "DELETE FROM Persons" +
                         "DELETE FROM User" +
                         "DELETE FROM AuthorizationTokens";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DatabaseException("SQL Error encountered while clearing tables");
        }
    }
}

