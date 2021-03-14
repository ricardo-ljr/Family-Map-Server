package Services;

import DAO.*;
import Model.*;
import Result.FillResult;
import java.sql.*;


/**
 * This class is responsible for filing data to the database
 */
public class FillService {

    private Connection connection;
    private Database db;

    /**
     * Initializes an empty constructor for the class
     */
    public FillService() {
        db = new Database();
    }

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public FillService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Fill methos to fill database with username and generations
     *
     * @param username
     * @param generations
     * @return
     */
    public FillResult fill(String username, int generations){

        FillResult response = new FillResult();


        try {
            db.openConnection();
            UserDao uDao = new UserDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());

            if(uDao.userExists(username)) {


                pDao.clearPersonUsername(username);
                eDao.deleteAllEvents(username);

                response.setMessage("Successfully added persons and events to the database!");

                response.setSuccess(true);
                db.closeConnection(true);
            } else {
                response.setSuccess(false);
                response.setMessage("Error invalid userName or generations parameter - Fill Service");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Fill Service");

            try {
                db.closeConnection(false);
            } catch(DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }
}
