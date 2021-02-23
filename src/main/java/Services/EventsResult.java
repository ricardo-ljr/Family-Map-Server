package Services;

/**
 * This class is responsible for returning all events for all
 * family members of the current user. The current user is determined from
 * the provided auth token
 */
public class EventsResult {
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
    public EventsResult getAllEvents(String authtoken) {
        return null;
    }
}
