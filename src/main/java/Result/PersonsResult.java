package Result;

import Model.Person;

import java.util.ArrayList;

/**
 * This class is responsible on handling the result for all family members of a current user in
 * which the current user is determine from the provided auth token
 */
public class PersonsResult {

    private ArrayList<Person> person;

    /**
     * Initializes an empty constructor
     */
    public PersonsResult() {}

    /**
     * Constructor for PersonsResult
     *
     * @param person Parameter to access family members of person
     */
    public PersonsResult(ArrayList<Person> person) {
        this.person = person;
    }

    public ArrayList<Person> getPerson() {
        return person;
    }

    public void setPerson(ArrayList<Person> person) {
        this.person = person;
    }
}
