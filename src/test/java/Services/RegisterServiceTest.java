package Services;

import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResultSuccess;
import Result.ResultBool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterServiceTest {

    @Test
    void register() throws DataAccessException {
        Database db = new Database();
        UserDao uDao = new UserDao(db.getConnection());
        uDao.clearUser();
        db.closeConnection(true);
        RegisterService service = new RegisterService();
        RegisterRequest request = new RegisterRequest(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m");
        ResultBool response = service.register(request);
        assertTrue(response.isSuccess());
        if (response.isSuccess()) {
            RegisterResultSuccess realResponse = (RegisterResultSuccess) response;
            User correctUser = new User(
                    "patrick",
                    "spencer",
                    "patrick@spencer.com",
                    "Patrick",
                    "Spencer",
                    "m",
                    realResponse.getPersonID());
            db = new Database();
            uDao = new UserDao(db.getConnection());
            assertEquals(correctUser, uDao.findUser(correctUser.getUserName()));
            db.closeConnection(false);
        }
    }
}