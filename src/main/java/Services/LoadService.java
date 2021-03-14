package Services;

import DAO.DataAccessException;
import DAO.EventDao;
import DAO.PersonDao;
import DAO.UserDao;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.ResultBool;

/**
 * This class is responsible for handling loading users, persons
 * and events through a post method to the database
 */
public class LoadService extends Service {

    /**
     * Initialize empty constructor
     */
    public LoadService() {}

    /**
     * This method will take the array of users, persons
     * and events and load it into the database. It will return
     * a message if the request was successful or not
     *
     * @param request Data request that will be loaded into the database
     * @return Null for now, it will return a message if the operation
     * was successful or not
     */
    public ResultBool load(LoadRequest request) {
        try {
            db.clearTables();
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error clearing tables for load.");
        }

        int users = 0;
        int persons = 0;
        int events = 0;

        UserDao uDao = new UserDao(connection);
        PersonDao pDao = new PersonDao(connection);
        EventDao eDao = new EventDao(connection);

        try {
            for (User u : request.getUsers()) {
                uDao.registerUser(u);
                users++;
            }
            for (Person p : request.getPersons()) {
                pDao.addPerson(p);
                persons++;
            }
            for (Event e : request.getEvents()) {
                eDao.addEvent(e);
                events++;
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error inserting user, person, or event.");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }

        return new SuccessMessageResult("Successfully added " + users + " users, " + persons + " persons, and " + events + " events");
    }

}

