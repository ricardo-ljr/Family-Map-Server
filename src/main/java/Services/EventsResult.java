package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.EventDao;
import Model.Event;
import Result.ErrorMessageResult;
import Result.ResultBool;

import java.util.ArrayList;

/**
 * This class is responsible for returning all events for all
 * family members of the current user. The current user is determined from
 * the provided auth token
 */
public class EventsResult extends Service {
    /**
     * Empty constructor for class
     */
    public EventsResult() {}

    /**
     * This method will take in an auth token from the user and return all events for
     * all family members of the current user
     *
     * @param authtoken User's auth token
     * @return All events for all family members of the current user
     */
    public ResultBool getAllEvents(String authtoken) {
        EventDao eventDAO = new EventDao(connection);

        AuthTokenDao tDao = new AuthTokenDao(connection);
        try {
            String username = tDao.getUsernameForAuthtoken(authtoken);
            ArrayList<Event> events = eventDAO.findAllEvents(username);
            Result.EventsResult result = new Result.EventsResult();
            result.setEvents(events);

            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error while getting events");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }

}
