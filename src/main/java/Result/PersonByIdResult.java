package Result;

import java.util.Objects;

/**
 * This class is responsible for handling results associated with a single person by its ID
 */
public class PersonByIdResult extends ResultBool {

    /**
     * The person's unique ID
     */
    private String personID;

    /**
     * The person's associated username
     */
    private String associatedUsername;

    /**
     * The person's first name
     */
    private String firstName;

    /**
     * The person's last name
     */
    private String lastName;

    /**
     * The person's gender
     */
    private String gender;

    /**
     * The person's unique identifier for a father
     */
    private String fatherID;

    /**
     * The person's unique identifier for a mother
     */
    private String motherID;

    /**
     * The person's unique identifier for a spouse
     */
    private String spouseID;

    /**
     * Initializes an empty constructor
     */
    public PersonByIdResult() {}

    /**
     * Constructor for persons by id results
     *
     * @param personID Person's unique identifier
     * @param associatedUsername Person's associated username
     * @param firstName Person's first name
     * @param lastName Person's last name
     * @param gender Person's gender
     * @param fatherID Person's unique identifier for a father
     * @param motherID Person's unique identifier for a mother
     * @param spouseID Person's unique identifier for a spouse
     */
    public PersonByIdResult(String associatedUsername, String personID, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }


    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonByIdResult that = (PersonByIdResult) o;
        return personID.equals(that.personID) && associatedUsername.equals(that.associatedUsername) && firstName.equals(that.firstName) && lastName.equals(that.lastName) && gender.equals(that.gender) && Objects.equals(fatherID, that.fatherID) && Objects.equals(motherID, that.motherID) && Objects.equals(spouseID, that.spouseID);
    }


}
