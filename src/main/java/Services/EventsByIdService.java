package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDao;
import Model.Event;
import Result.EventByIdResult;
import Result.ResultBool;

import java.sql.Connection;

/**
 * This class is responsible for returning data for an event by its id
 */
public class EventsByIdService {

    private Database db;
    private Connection connection;

    /**
     * Initializes empty constructor
     */
    public EventsByIdService() {
        db = new Database();
    }

    /**
     * This method will query and return a specific event by its id and user's auth token,
     * as it's required
     *
     * @param eventID Unique identifier for the event
     * @param authToken Authorization Token required to retrieve a single event object
     * @return This will return the body of a single event if successful
     */
    public EventByIdResult getEventById(String eventID, String authToken) {

        EventByIdResult response = new EventByIdResult();


        try {

            db.openConnection();

            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());

            if(tDao.authTokenExists(authToken)) {

                String userName = tDao.authenticate(authToken).getAssociatedUsername(); // get associated username for Event

                if(eDao.eventExists(eventID)) { // check if event exists by its ID

                    Event event = eDao.findEvent(eventID); // find event by ID
                    String eventUsername = event.getAssociatedUsername(); // user associated with event

                    if(userName.equals(eventUsername)) { // if logged user is the owner of the new event

                        response.setEventID(event.getEventID());
                        response.setAssociatedUsername(event.getAssociatedUsername());
                        response.setLatitude(event.getLatitude());
                        response.setLongitude(event.getLongitude());
                        response.setCountry(event.getCountry());
                        response.setCity(event.getCity());
                        response.setEventType(event.getEventType());
                        response.setYear(event.getYear());
                        response.setPersonID(event.getPersonID());

                        response.setSuccess(true);
                        db.closeConnection(true);
                    } else {
                        response.setSuccess(false);
                        response.setMessage("Error requesting an by user - Event By Id Service");
                        db.closeConnection(false);
                    }
                } else {
                    response.setSuccess(false);
                    response.setMessage("Error invalid eventID - Event by Id Service");
                    db.closeConnection(false);
                }
            } else {
                response.setSuccess(false);
                response.setMessage("Error - Event by Id Service");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Event by Id Service");

            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }
}

