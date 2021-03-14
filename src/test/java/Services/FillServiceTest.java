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
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345");

        RegisterRequest request = new RegisterRequest();

        request.setUserName(newUser.getUserName());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        FillResult response1 = fillService.fill(newUser.getUserName(), 0);

        assertEquals(response1.getMessage(), "Successfully added persons and events to the database! ");

    }
}