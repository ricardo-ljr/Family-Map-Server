package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDao;
import Model.Person;
import Result.PersonsResult;
import Result.ResultBool;

import java.util.ArrayList;

/**
 * this class is responsible for returning all family members of the current user
 * in which the current user is determined by the provided auth token
 */
public class PersonsService {
    private Database db;

    /**
     * Initializes empty constructor
     */
    public PersonsService() {
        db = new Database();
    }

    /**
     * This method gets all family members of the current user
     *
     * @param authToken Unique token to identify user
     * @return Returns all family members of the current user
     */
    public PersonsResult getPersons(String authToken) {

        PersonsResult response = new PersonsResult();

        try {
            db.openConnection();
            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());

            if(tDao.authTokenExists(authToken)) {

                String userName = tDao.getUsernameForAuthtoken(authToken);
                response.setPerson(pDao.getPersonsForUsername(userName));

                response.setSuccess(true);
                db.closeConnection(true);
            } else {
                response.setSuccess(false);
                response.setMessage("Error: Invalid auth token");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error");

            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }

}
