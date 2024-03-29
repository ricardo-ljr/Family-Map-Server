package Services;

import DAO.*;
import JSONReader.LocationData;
import Model.*;
import Result.FillResult;
import java.sql.*;


/**
 * This class is responsible for filing data to the database
 */
public class FillService {

    private Connection connection;
    private Database db;
    private LocationData locations;

    /**
     * Initializes an empty constructor for the class
     */
    public FillService() {
        db = new Database();
    }

//    /**
//     * Initializing constructor for class with a connection argument
//     *
//     * @param connection Links connection with database
//     */
//    public FillService(Connection connection) {
//        this.connection = connection;
//    }

    /**
     * Fill methods to fill database with username and generations
     *
     * @param username
     * @param generations
     * @return
     */
    public FillResult fill(String username, int generations){

        FillResult response = new FillResult();

        try {
            db.openConnection(); // open connection

            UserDao uDao = new UserDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());

            if(uDao.userExists(username) && generations >= 0) {

                User user = uDao.findUser(username);

                pDao.clearPersonUsername(username); // clear all persons for user
                eDao.deleteAllEvents(username); // clear all events for user

                if(generations == 99) { // default generation lives here
                    pDao.generateTree(
                            user,
                            user.getPersonID(),
                            4,
                            eDao);
                } else { // any other number of generations lives here
                    pDao.generateTree(
                            user,
                            user.getPersonID(),
                            generations,
                            eDao);
                }

                int numOfPersons = pDao.getNumOfPersons();
                int numOfEvents = eDao.getNumOfEvents();

                // Response message
                response.setMessage("Successfully added " + numOfPersons + " persons and " + numOfEvents + " events to the database!");

                response.setSuccess(true);
                db.closeConnection(true);

            } else {

                response.setSuccess(false);
                response.setMessage("Error invalid username or generations - Fill Service");
                db.closeConnection(false);

            }
        } catch(DataAccessException e) {

            response.setSuccess(false);
            response.setMessage("Internal server error - Fill Service");

            try {
                db.closeConnection(false);
            } catch(DataAccessException f) {
                f.printStackTrace();
            } catch (Exception j) {
                j.printStackTrace();
            }
        }

        return response;
    }

    // Generate data to populate the database

}
