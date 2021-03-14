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

        try {
            db.clearTables();
        } catch (DataAccessException e) {
            e.printStackTrace();
            response.setMessage("Error clearing tables for load.");
        }

        int users = 0;
        int persons = 0;
        int events = 0;



        try {
            db.openConnection();
            db.clearTables();
            UserDao uDao = new UserDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());
            EventDao eDao = new EventDao(db.getConnection());

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

            response.setMessage("Successfully added " + users + " users, " + persons + " persons, and " + events + " events");

            response.setSuccess(true);
            db.closeConnection(true);

        } catch (DataAccessException e) {
            e.printStackTrace();
            response.setMessage("Internal server error - Load Service");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }

        return response;
    }

}

