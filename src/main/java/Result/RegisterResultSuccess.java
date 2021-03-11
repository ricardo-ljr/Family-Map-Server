package Result;

/**
 * This class handle the register request result, sends a message and data
 */
public class RegisterResultSuccess extends ResultBool {

    private String message;

    private String authToken;

    private String personID;

    private String username;

    /**
     * Initializes empty constructor
     */
    public RegisterResultSuccess() {}

    public RegisterResultSuccess(String message) {
        this.message = message;
    }

    /**
     * Sends the result back after registering a new user
     *
     *
     * @param authToken Authorization token for the user
     * @param personID Unique person identifier for user
     * @param username Username user used to register
     */
    public RegisterResultSuccess(String authToken,  String username, String personID) {
        this.authToken = authToken;
        this.personID = personID;
        this.username = username;
        success = true;
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
