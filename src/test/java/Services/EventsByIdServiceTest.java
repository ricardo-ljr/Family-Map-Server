package Services;

import DAO.DataAccessException;
import Model.Event;
import Model.User;
import Request.RegisterRequest;
import Result.EventByIdResult;
import Result.EventsResult;
import Result.FillResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class EventsByIdServiceTest {

    private EventsByIdService eventIDService;
    private EventsService eventAllService;
    private FillService fillService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        fillService = new FillService();
        eventIDService = new EventsByIdService();
        eventAllService = new EventsService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        ClearService clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    void getEventById() throws DataAccessException {

        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345");

        RegisterRequest request = new RegisterRequest();
        request.setUserName(newUser.getUsername());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        RegisterResult response = registerService.register(request);
        String authID = response.getAuthToken();

        EventsResult responseAll = eventAllService.getAllEvents(authID);

        Event[] events = responseAll.getEvents();

        Random num = new Random();

        String randomID = events[num.nextInt(30)].getEventID();

        EventByIdResult responseID = eventIDService.getEventById(randomID, authID);

        assertEquals(responseID.getEventID(), randomID);
    }

    @Test
    void getEventByIdBirth() throws DataAccessException {

        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345");

        RegisterRequest request = new RegisterRequest();
        request.setUserName(newUser.getUsername());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        RegisterResult response = registerService.register(request);
        String authID = response.getAuthToken();

        FillResult res = fillService.fill(newUser.getUsername(), 0); // Generates birth

        EventsResult responseAll = eventAllService.getAllEvents(authID);

        Event[] events = responseAll.getEvents();
        String randomID = events[0].getEventID();

        EventByIdResult responseID = eventIDService.getEventById(randomID, authID);

        assertEquals(responseID.getEventType(), "birth");
    }
}