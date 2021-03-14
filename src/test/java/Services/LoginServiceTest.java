package Services;

import DAO.DataAccessException;
import Model.User;
import Request.LoginRequest;
import Request.RegisterRequest;
import Result.LoginResult;
import Result.RegisterResult;
import jdk.jfr.MemoryAddress;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginServiceTest {

    private LoginService loginService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        loginService = new LoginService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() {
        ClearService clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    void login() throws DataAccessException {

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

        LoginRequest request1 = new LoginRequest();
        request1.setUserName(newUser.getUserName());
        request1.setPassword(newUser.getPassword());

        RegisterResult response = registerService.register(request);

        LoginResult response1 = loginService.login(request1);

        assertEquals(response.getUsername(), response1.getUsername());

        assertTrue(response1.isSuccess());
    }

    @Test
    public void loginFail() throws DataAccessException {
        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345");

        LoginRequest request = new LoginRequest();
        request.setUserName(newUser.getUserName());
        request.setPassword(newUser.getPassword());

        LoginResult response = loginService.login(request);

        assertFalse(response.isSuccess());
    }
}