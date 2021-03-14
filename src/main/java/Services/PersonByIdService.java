package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.PersonDao;
import Model.Person;
import Result.PersonByIdResult;

/**
 * This class is responsible for returning a person body by its unique ID
 */
public class PersonByIdService {

    private Database db;

    /**
     * Initializes empty constructor
     */
    public PersonByIdService() {
        db = new Database();
    }

    /**
     * This method will return the persons object body
     *
     * @param personID Unique identifier for person
     * @param authtoken Auth token for current user
     * @return Single person object specified by its ID
     */
    public PersonByIdResult getPerson(String personID, String authtoken) throws DataAccessException {

        PersonByIdResult response = new PersonByIdResult();


        try {
            db.openConnection();

            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());
            PersonDao pDao = new PersonDao(db.getConnection());

            if(tDao.authTokenExists(authtoken)) {

                String userName = tDao.getUsernameForAuthtoken(authtoken);

                if(pDao.personExists(personID)) {

                    Person person = pDao.findPerson(personID);
                    String personUsername = person.getAssociatedUsername();

                    if(userName.equals(personUsername)) {

                        response.setAssociatedUsername(person.getAssociatedUsername());
                        response.setPersonID(person.getPersonID());
                        response.setFirstName(person.getFirstName());
                        response.setLastName(person.getLastName());
                        response.setGender(person.getGender());
                        if(person.getFatherID() != null) { response.setFatherID(person.getFatherID()); }
                        if(person.getMotherID() != null) { response.setMotherID(person.getMotherID()); }
                        if(person.getSpouseID() != null) { response.setSpouseID(person.getSpouseID()); }

                        response.setSuccess(true);
                        db.closeConnection(true);
                    } else {
                        response.setSuccess(false);
                        response.setMessage("Error requesting this person - Person by Id Service");
                        db.closeConnection(false);
                    }
                } else {
                    response.setSuccess(false);
                    response.setMessage("Error person ID is not valid - Person by Id Service");
                    db.closeConnection(false);
                }
            } else {
                response.setSuccess(false);
                response.setMessage("Error check your token to see if valid - Person by Id Service");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Person by Id Service");

            try {
                db.closeConnection(false);
            } catch (DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }
}
