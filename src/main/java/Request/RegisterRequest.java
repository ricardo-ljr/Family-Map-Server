package Request;

/**
 * This class handle the register request for a user
 */
public class RegisterRequest {
    /**
     * The user's unique username
     */
    private String userName;

    /**
     * The user's password
     */
    private String password;

    /**
     * The user's email
     */
    private String email;

    /**
     * The user's first name
     */
    private String firstName;

    /**
     * The user's last name
     */
    private String lastName;

    /**
     * The user's gender
     */
    private String gender;

    /**
     * Empty constructor for class
     */
    public RegisterRequest() {}

    /**
     * Register a new user
     *
     * @param userName User's unique username
     * @param password User's password
     * @param email User's email
     * @param firstName User's first name
     * @param lastName User's last name
     * @param gender User's gender
     */
    public RegisterRequest(String userName, String password, String email, String firstName, String lastName, String gender) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
