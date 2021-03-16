package Services;

import DAO.DataAccessException;
import Model.User;
import Request.RegisterRequest;
import Result.FillResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FillServiceTest {

    private RegisterService registerService;
    private FillService fillService;

    @BeforeEach
    public void setUp() {
        registerService = new RegisterService();
        fillService = new FillService();
    }

    @AfterEach
    public void tearDown() {
        ClearService clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    void fill() throws DataAccessException {
        User newUser = new User(
                "ricardo",
                "leite",
                "ricardo@leite.com",
                "Ricardo",
                "Leite",
                "m",
                "123456");

        RegisterRequest request = new RegisterRequest();

        request.setUserName(newUser.getUserName());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        RegisterResult response = registerService.register(request); // send a register request

        FillResult response1 = fillService.fill(newUser.getUserName(), 0);

        assertEquals(response1.getMessage(), "Successfully added 1 persons and 1 events to the database!");
    }

    @Test
    void fillFail() throws DataAccessException {

        User newUser = new User(
                "ricardo",
                "leite",
                "ricardo@leite.com",
                "Ricardo",
                "Leite",
                "m",
                "123456");

        RegisterRequest request = new RegisterRequest();

        request.setUserName(newUser.getUserName());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        FillResult response1 = fillService.fill(newUser.getUserName(), 0);

        assertEquals(response1.getMessage(), "Error invalid username or generations - Fill Service");

    }
}