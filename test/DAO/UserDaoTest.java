package DAO;

import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private Database db;
    private User user;
    UserDao uDao;

    @BeforeEach
    void setUp() throws Exception {

        db = new Database();

        user = new User("12345", "person_1",
                "Ricardo@email.com", "Ricardo", "Leite","m", "person_1");

        Connection connection = db.openConnection();

        db.clearTables();

        uDao = new UserDao(connection);
    }

    @AfterEach
    void tearDown() throws Exception {

        db.closeConnection(true);
    }

    @Test
    void registerUser() throws DataAccessException {

        uDao.registerUser(user);

        User compareTest = uDao.findUser(user.getUserName());

        assertNotNull(compareTest);

        assertEquals(user, compareTest);
    }

    @Test
    void registerUserFail() throws DataAccessException{
        uDao.registerUser(user);

        assertThrows(DataAccessException.class, ()->uDao.registerUser(user));
    }

    @Test
    void findUser() throws DataAccessException{
        uDao.registerUser(user);

        User compareTest = uDao.findUser(user.getUserName());

        assertNotNull(compareTest);

        assertEquals(user, compareTest);
    }

    @Test
    void findUserFail() throws DataAccessException {
        User compareTest = uDao.findUser(user.getUserName());
        assertNull(compareTest);
    }

    @Test
    void clearUser() throws DataAccessException{
        uDao.registerUser(user);

        User find = uDao.findUser(user.getUserName());
        uDao.clearUser();

        User clear = uDao.findUser(user.getUserName());

        assertNotNull(find);
        assertNull(clear);
    }
}