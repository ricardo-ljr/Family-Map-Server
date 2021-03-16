package Request;

/**
 * This class handles the request for a user to login
 */
public class LoginRequest {
    /**
     * The user's unique username
     */
    private String username;

    /**
     * The user's password
     */
    private String password;

    /**
     * Empty constructor for class
     */
    public LoginRequest() {}

    /**
     * Constructor that handles the request to login an existing user
     *
     * @param username Username of user requesting login access
     * @param password Password of user requesting login access
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
