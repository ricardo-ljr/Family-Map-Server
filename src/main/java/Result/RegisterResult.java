package Result;

/**
 * This class handle the register request result, sends a message and data
 */
public class RegisterResult {

    private String message;

    private String authToken;

    private String personID;

    private String username;

    /**
     * Initializes empty constructor
     */
    public RegisterResult() {}

    /**
     * Sends the result back after registering a new user
     *
     * @param message Message of success if user successfully registered or not
     * @param authToken Authorization token for the user
     * @param personID Unique person identifier for user
     * @param username Username user used to register
     */
    public RegisterResult(String message, String authToken, String personID, String username) {
        this.message = message;
        this.authToken = authToken;
        this.personID = personID;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
