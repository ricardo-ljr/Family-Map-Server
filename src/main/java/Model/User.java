package Model;

import java.util.UUID;

/**
 * A user
 */
public class User {

    /**
     * The user's unique ID
     */
    private String userID;

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
     * The user's personID associated with
     */
    private String personID;

    /**
     * Empty constructor for a user
     */
    public User() {}

    /**
     * Creates a user
     *
     * @param userName User's unique username
     * @param password User's password
     * @param email User's email
     * @param firstName User's first name
     * @param lastName User's last name
     * @param gender User's gender
     * @param personID User's unique personID associated with
     */
    public User(String userID,  String userName, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userID = UUID.randomUUID().toString();
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName) && password.equals(user.password) && email.equals(user.email) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && gender.equals(user.gender) && personID.equals(user.personID);
    }

}
