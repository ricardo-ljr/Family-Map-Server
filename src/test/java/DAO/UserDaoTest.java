package DAO;

import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    private Database db;
    private User user;
    private User user2;
    UserDao uDao;

    @BeforeEach
    void setUp() throws Exception {

        db = new Database();

        user = new User("12345", "person_1",
                "Ricardo@email.com", "Ricardo", "Leite","m", "12345");
        user2 = new User("123456", "person_2",
                "leite@email.com", "Leite", "Ricardo","m", "123456");

        Connection connection = db.openConnection();

        db.clearTables();

        uDao = new UserDao(connection);
    }

    @AfterEach
    void tearDown() throws DataAccessException {

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

        DataAccessException exception = assertThrows(DataAccessException.class, ()-> {
            uDao.registerUser(user);
        });

        assertThrows(DataAccessException.class, ()->uDao.registerUser(user));

        String expectedMessage = "Error encountered while inserting into database";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
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
    void userExists() throws DataAccessException {
        uDao.registerUser(user);
        uDao.registerUser(user2);

        assertTrue(uDao.userExists(user.getUserName()));
        assertTrue(uDao.userExists(user2.getUserName()));
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