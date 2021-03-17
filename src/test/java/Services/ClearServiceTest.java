package Services;

import DAO.DataAccessException;
import Model.User;
import Request.RegisterRequest;
import Result.ClearResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClearServiceTest {

    private ClearService clearService;
    private RegisterService registerService;

    @BeforeEach
    void setup() throws DataAccessException {
        registerService = new RegisterService();
    }

    @AfterEach
    void tearDown() throws DataAccessException {
        clearService = new ClearService();
        clearService.clearResult();
    }

    @Test
    void clearResult() {
        clearService = new ClearService();
        ClearResult res = clearService.clearResult();
        clearService = new ClearService();
        ClearResult res2 = clearService.clearResult();

        assertEquals(res.getMessage(), res2.getMessage());
    }

    @Test
    void clearResult2() throws DataAccessException {

        User user = new User("12345", "person_1",
                "Ricardo@email.com", "Ricardo", "Leite","m", "12345");

        RegisterRequest request = new RegisterRequest();
        request.setUserName(user.getUsername());
        request.setPassword(user.getPassword());
        request.setEmail(user.getEmail());
        request.setFirstName(user.getFirstName());
        request.setLastName(user.getLastName());
        request.setGender(user.getGender());

        RegisterResult res = registerService.register(request);
        clearService = new ClearService();
        ClearResult res2 = clearService.clearResult();
        assertEquals("clear succeeded", res2.getMessage());

    }
}