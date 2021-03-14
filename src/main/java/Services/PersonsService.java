package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.PersonDao;
import Model.Person;
import Result.PersonsResult;
import Result.ResultBool;

import java.util.ArrayList;

/**
 * this class is responsible for returning all family members of the current user
 * in which the current user is determined by the provided auth token
 */
public class PersonsService extends Service {

    /**
     * Initializes empty constructor
     */
    public PersonsService() {}

    /**
     * This method gets all family members of the current user
     *
     * @param authToken Unique token to identify user
     * @return Returns all family members of the current user
     */
    public ResultBool getPersons(String authToken) {
        PersonDao pDao = new PersonDao(connection);

        AuthTokenDao tDao = new AuthTokenDao(connection);
        try {
            String username = tDao.getUsernameForAuthtoken(authToken);
            ArrayList<Person> persons = pDao.getPersonsForUsername(username);
            PersonsResult result = new PersonsResult();
            result.setPerson(persons);

            return result;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error while getting people");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }

}
