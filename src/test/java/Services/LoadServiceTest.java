package Services;

import Model.Event;
import Model.Person;
import Model.User;
import Request.LoadRequest;
import Result.LoadResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LoadServiceTest {

    private LoadService loadService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        loadService = new LoadService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        ClearService clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    void load() {

        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345"); // User

        Person newPerson = new Person("12345",
                "patrick",
                "Patrick", "Spencer", "m",
                "father_1", "mother_1", "spouse_1");

        Event newEvent = new Event("event1",
                "patrick",
                10.0f,
                20.0f,
                "USA",
                "Lehi",
                "birth",
                2000,
                "12345");

        User[] userParam = new User[1];
        Person[] personParam = new Person[1];
        Event[] eventParam = new Event[1];

        userParam[0] = newUser;
        personParam[0] = newPerson;
        eventParam[0] = newEvent;

        LoadRequest loadRequest = new LoadRequest();
        loadRequest.setUsers(userParam);
        loadRequest.setPersons(personParam);
        loadRequest.setEvents(eventParam);

        LoadResult response = new LoadResult();
        response = loadService.load(loadRequest);

        LoadResult compareTest = new LoadResult();
        compareTest.setSuccess(true);

        assertEquals(response.isSuccess(), compareTest.isSuccess());
    }
}