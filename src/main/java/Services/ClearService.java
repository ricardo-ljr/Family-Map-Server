package Services;

import DAO.*;
import Result.ClearResult;

import java.sql.*;

/**
 * This class is responsible for deleting data from the database
 */
public class ClearService {

    private Connection connection;
    private Database db;

    /**
     * Initializes an empty constructor for the class
     */
    public ClearService() {
        db = new Database();
    }

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
    public ClearResult clearResult() {

        ClearResult response = new ClearResult();

        try {
            db.openConnection();
            db.clearTables();

            response.setMessage("clear succeeded");
            response.setSuccess(true);

            db.closeConnection(true);

        } catch(DataAccessException e) {
            response.setMessage("Internal server error - ClearService");
            response.setSuccess(false);

            try {
                db.closeConnection(false);
            } catch(DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }
}

