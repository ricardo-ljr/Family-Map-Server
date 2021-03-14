package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.PersonDao;
import Model.Person;
import Result.PersonByIdResultSuccess;
import Result.ResultBool;

/**
 * This class is responsible for returning a person body by its unique ID
 */
public class PersonByIdService extends Service {

    /**
     * Initializes empty constructor
     */
    public PersonByIdService() {}

    /**
     * This method will return the persons object body
     *
     * @param personID Unique identifier for person
     * @param authtoken Auth token for current user
     * @return Single person object specified by its ID
     */
    public ResultBool getPerson(String personID, String authtoken){
        PersonDao personDAO = new PersonDao(connection);
        try {
            Person person = personDAO.findPerson(personID);

            AuthTokenDao authTokenDAO = new AuthTokenDao(connection);
            if (!authTokenDAO.getUsernameForAuthtoken(authtoken).equals(person.getAssociatedUsername()))
                return new ErrorMessageResult("Error you are not authorized");
            return new PersonByIdResultSuccess(person.getAssociatedUsername(),
                    person.getPersonID(),
                    person.getFirstName(),
                    person.getLastName(),
                    person.getGender(),
                    person.getFatherID(),
                    person.getMotherID(),
                    person.getSpouseID());
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error while getting a person by its id");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }
}
