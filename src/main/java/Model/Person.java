package Model;

/**
 * A person
 */
public class Person {
    
    private String personID;
    
    private String associatedUsername;

    private String firstName;

    private String lastName;

    private String gender;

    private String fatherID;

    private String motherID;

    private String spouseID;

    /**
     * Creates a person
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
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
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
        Person person = (Person) o;
        return personID.equals(person.personID) &&
                associatedUsername.equals(person.associatedUsername) &&
                firstName.equals(person.firstName) &&
                lastName.equals(person.lastName) &&
                gender.equals(person.gender) &&
                fatherID.equals(person.fatherID) &&
                motherID.equals(person.motherID) &&
                spouseID.equals(person.spouseID);
    }

}
