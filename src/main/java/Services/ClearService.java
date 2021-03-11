package Services;

import DAO.DataAccessException;
import DAO.Database;
import Result.SuccessMessageResult;

import java.sql.*;

/**
 * This class is responsible for deleting data from the database
 */
public class ClearService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public ClearService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public ClearService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Clears database and returns message if successful
     */
    public SuccessMessageResult clearResult() {
        Database db = new Database();
        try {
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
            return new SuccessMessageResult("Cleared database successfully.");
        } catch (DataAccessException d) {
            try {
                db.closeConnection(false);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
            return new SuccessMessageResult("Internal server error");
        }
    }
}

