package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;

/**
 * This class is responsible for handing requests to load data into the database
 */
public class LoadRequest {

    private User[] users;

    private Person[] persons;

    private Event[] events;

    /**
     * Initialize empty constructor
     */
    public LoadRequest() {}

    /**
     * This constructor will handle a request to load the data of users, persons
     * and events into the database
     *
     * @param users List of users
     * @param persons List of persons
     * @param events List of events
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
