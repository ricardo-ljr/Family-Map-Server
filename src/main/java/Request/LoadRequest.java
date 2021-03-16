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
