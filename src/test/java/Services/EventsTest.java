package Services;


import DAO.DataAccessException;
import Model.Event;
import Model.User;
import Request.RegisterRequest;
import Result.EventsResult;
import Result.FillResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventsTest {

    private EventsService eventAllService;
    private FillService fillService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        fillService = new FillService();
        eventAllService = new EventsService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        ClearService clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    public void events() throws DataAccessException {
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
        String authToken = response.getAuthToken();

        FillResult response1 = fillService.fill(newUser.getUsername(), 0);

        EventsResult responseAll = eventAllService.getAllEvents(authToken);

        Event[] events = responseAll.getEvents();

        assertEquals(events.length, 1);
    }

    @Test
    public void eventsFailed() throws DataAccessException {
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
        String authToken = response.getAuthToken();

        FillResult response1 = fillService.fill(newUser.getUsername(), 4);

        EventsResult responseAll = eventAllService.getAllEvents(authToken);

        Event[] events = responseAll.getEvents();

        assertNotEquals(events.length, 1);
    }
}
