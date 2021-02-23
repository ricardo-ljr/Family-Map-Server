package Request;

import Model.Event;
import Model.Person;
import Model.User;

import java.util.ArrayList;

/**
 * This class is responsible for handing requests to load data into the database
 */
public class LoadRequest {

    private ArrayList<User> users;

    private ArrayList<Person> persons;

    private ArrayList<Event> events;

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
    public LoadRequest(ArrayList<User> users, ArrayList<Person> persons, ArrayList<Event> events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Person> getPersons() {
        return persons;
    }

    public void setPersons(ArrayList<Person> persons) {
        this.persons = persons;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Event> events) {
        this.events = events;
    }
}
