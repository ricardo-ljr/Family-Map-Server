package Services;

import DAO.*;
import Result.ResultBool;

import java.sql.*;

/**
 * This class is responsible for deleting data from the database
 */
public class ClearService extends Service {

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
    public ResultBool clearResult() {
        setUp();

        try {
            AuthTokenDao tDao= new AuthTokenDao(connection);
            tDao.clearAuthToken();
            db.closeConnection(true);
            connection = db.openConnection();
            EventDao eventDAO = new EventDao(connection);
            eventDAO.clearEvent();
            db.closeConnection(true);
            connection = db.openConnection();
            PersonDao personDAO = new PersonDao(connection);
            personDAO.clearPerson();
            db.closeConnection(true);
            connection = db.openConnection();
            UserDao userDAO = new UserDao(connection);
            userDAO.clearUser();

            return new SuccessMessageResult("clear succeeded");
        } catch (DataAccessException e) {

            e.printStackTrace();
            return new ErrorMessageResult("failed to clear");
        } finally {

            try {
                db.closeConnection(true);
            } catch (DataAccessException e) {
                e.printStackTrace();
            }
        }
    }
}

