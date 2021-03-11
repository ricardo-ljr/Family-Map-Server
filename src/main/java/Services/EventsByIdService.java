package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.EventDao;
import Model.Event;
import Result.ErrorMessageResult;
import Result.EventByIdResult;
import Result.ResultBool;

/**
 * This class is responsible for returning data for an event by its id
 */
public class EventsByIdService extends Service{

    /**
     * Initializes empty constructor
     */
    public EventsByIdService() {}

    /**
     * This method will query and return a specific event by its id and user's auth token,
     * as it's required
     *
     * @param eventID Unique identifier for the event
     * @param authToken Authorization Token required to retrieve a single event object
     * @return This will return the body of a single event if successful
     */
    public ResultBool getEventById(String eventID, String authToken) {

        EventDao eventDAO = new EventDao(connection);
        try {

            Event event = eventDAO.findEvent(eventID);

            AuthTokenDao authTokenDAO = new AuthTokenDao(connection);
            if (!authTokenDAO.getUsernameForAuthtoken(authToken).equals(event.getAssociatedUsername()))
                return new ErrorMessageResult("Error you are not authorized to do this");

            return new EventByIdResult(
                    event.getAssociatedUsername(),
                    event.getEventID(),
                    event.getPersonID(),
                    event.getLatitude(),
                    event.getLongitude(),
                    event.getCountry(),
                    event.getCity(),
                    event.getEventType(),
                    event.getYear());

        } catch ( DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error while getting event by its id");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }
}

