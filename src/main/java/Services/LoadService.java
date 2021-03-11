package Services;

import Request.LoadRequest;
import Result.SuccessMessageResult;

/**
 * This class is responsible for handling loading users, persons
 * and events through a post method to the database
 */
public class LoadService {

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
    public SuccessMessageResult load(LoadRequest request) {
        return null;
    }
}
