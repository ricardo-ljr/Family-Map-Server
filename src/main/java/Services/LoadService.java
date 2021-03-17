package Services;

import DAO.*;
import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;

import java.sql.Connection;

/**
 * This class is responsible for handling loading users, persons
 * and events through a post method to the database
 */
public class LoadService {

    private Database db;
    private Connection connection;

    /**
     * Initialize empty constructor
     */
    public LoadService() {

        db = new Database();
    }

    /**
     * This method will take the array of users, persons
     * and events and load it into the database. It will return
     * a message if the request was successful or not
     *
     * @param request Data request that will be loaded into the database
     * @return Null for now, it will return a message if the operation
     * was successful or not
     */
    public LoadResult load(LoadRequest request) {

        LoadResult response = new LoadResult();

        int users = 0;
        int persons = 0;
        int events = 0;

        try {

            db.openConnection();
            db.clearTables();

            for (User user : request.getUsers()) {
                UserDao uDao = new UserDao(db.getConnection());
                uDao.registerUser(user);
                users++;
                db.closeConnection(true);
            }
            for (Person person : request.getPersons()) {
                PersonDao pDao = new PersonDao(db.getConnection());
                pDao.addPerson(person);
                persons++;
                db.closeConnection(true);
            }
            for (Event event : request.getEvents()) {
                EventDao eDao = new EventDao(db.getConnection());
                eDao.addEvent(event);
                events++;
                db.closeConnection(true);
            }

            response.setMessage("Successfully added " + users + " users, " + persons + " persons, and " + events + " events to the database - Load Service");

            response.setSuccess(true);
//            db.closeConnection(true);

        } catch (DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Load Service");

            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                f.printStackTrace();
            }
        }
        return response;
    }
}

