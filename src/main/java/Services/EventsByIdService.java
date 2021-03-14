package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.EventDao;
import Model.Event;
import Result.EventByIdResult;
import Result.ResultBool;

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
        AuthTokenDao tDao = new AuthTokenDao(connection);
        EventDao eDao = new EventDao(connection);

        try {

            db.openConnection();

            if(tDao.authTokenExists(authToken)) {

                String userName = tDao.getUsernameForAuthtoken(authToken);

                if(eDao.eventExists(eventID)) {

                    Event event = eDao.findEvent(eventID);
                    String eventUsername = event.getAssociatedUsername();

                    if(userName.equals(eventUsername)) {

                        response.setEventID(event.getEventID());
                        response.setAssociatedUsername(event.getAssociatedUsername());
                        response.setPersonID(event.getPersonID());
                        response.setLatitude(event.getLatitude());
                        response.setLongitude(event.getLongitude());
                        response.setCountry(event.getCountry());
                        response.setCity(event.getCity());
                        response.setEventType(event.getEventType());
                        response.setYear(event.getYear());

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

