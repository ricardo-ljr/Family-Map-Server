package Services;

import DAO.DataAccessException;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        ClearService clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    void register() throws DataAccessException {

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

        assertEquals(response.getUsername(), newUser.getUsername());

    }

    @Test
    void registerFails() throws DataAccessException{
        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "",
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

        assertEquals(response.getMessage(), "Error request missing properties - Register Service");

    }
}