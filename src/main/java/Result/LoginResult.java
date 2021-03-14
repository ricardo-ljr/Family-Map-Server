package Result;

import java.util.Objects;

/**
 * This class handle the register request result, sends a message and data
 */
public class LoginResult extends ResultBool{

    private String authToken;

    private String personID;

    private String username;

    /**
     * Constructor to handle login result for user
     *
     * @param authToken Authorization token assigned to the user
     * @param personID Person unique identifier assigned to
     * @param username Username of user that logged in
     */
    public LoginResult(String authToken, String personID, String username) {
        this.authToken = authToken;
        this.personID = personID;
        this.username = username;
    }

    public LoginResult() { }


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
