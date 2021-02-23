package Result;

import java.util.Objects;

/**
 * This class handle the register request result, sends a message and data
 */
public class LoginResult {

    private String message;

    private String authToken;

    private String personID;

    private String username;

    /**
     * Constructor to handle login result for user
     *
     * @param message Message to let user know if authenticated correctly or not
     * @param authToken Authorization token assigned to the user
     * @param personID Person unique identifier assigned to
     * @param username Username of user that logged in
     */
    public LoginResult(String message, String authToken, String personID, String username) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginResult that = (LoginResult) o;
        return message.equals(that.message) && authToken.equals(that.authToken) && personID.equals(that.personID) && username.equals(that.username);
    }

}
