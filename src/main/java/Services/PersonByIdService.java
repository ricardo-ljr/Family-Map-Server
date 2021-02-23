package Services;

import Result.PersonsResult;

/**
 * This class is responsible for returning a person body by its unique ID
 */
public class PersonByIdService {

    /**
     * Initializes empty constructor
     */
    public PersonByIdService() {}

    /**
     * This method will return the persons object body
     *
     * @param personID Unique identifier for person
     * @param authToken Auth token for current user
     * @return Single person object specified by its ID
     */
    public PersonsResult getPerson(String personID, String authToken){
        return null;
    }
}
