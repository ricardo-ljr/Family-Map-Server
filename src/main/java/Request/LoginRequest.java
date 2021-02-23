package Request;

/**
 * This class handles the request for a user to login
 */
public class LoginRequest {
    /**
     * The user's unique username
     */
    private String userName;

    /**
     * The user's password
     */
    private String password;

    /**
     * Empty constructor for class
     */
    public LoginRequest() {}

    /**
     * Contrusctor that handles the request to login an existing user
     *
     * @param userName Username of user requesting login access
     * @param password Password of user requesting login access
     */
    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
