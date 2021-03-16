package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDao;
import Result.EventsResult;

import java.sql.Connection;

/**
 * This class is responsible for returning all events for all
 * family members of the current user. The current user is determined from
 * the provided auth token
 */
public class EventsService {

    private Database db;
    private Connection connection;
    private EventDao eDao;

    /**
     * Empty constructor for class
     */
    public EventsService() {
        db = new Database();
    }

    /**
     * This method will take in an auth token from the user and return all events for
     * all family members of the current user
     *
     * @param authtoken User's auth token
     * @return All events for all family members of the current user
     */
    public EventsResult getAllEvents(String authtoken) {

        EventsResult response = new EventsResult();


        try {
            db.openConnection();
            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());

            if(tDao.authTokenExists(authtoken)) {

                String userName = tDao.authenticate(authtoken).getAssociatedUsername(); // get username for associated event
                response.setEvents(eDao.findAllEvents(userName));

                response.setSuccess(true);
                db.closeConnection(true);

            } else {
                response.setSuccess(false);
                response.setMessage("Error encountered when finding all events - Events Service");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Events Service");

            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }

}
