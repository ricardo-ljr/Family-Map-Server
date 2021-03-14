package DAO;

import Model.AuthToken;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {

    private Database db;
    private User newUser;
    private AuthToken newToken;
    private AuthTokenDao tDao;

    @BeforeEach
    void setUp() throws DataAccessException {

        db = new Database();

        newUser = new User("12345", "person_1",
                "Ricardo@email.com", "Ricardo", "Leite","m", "12345");

        newToken = new AuthToken("12345", "ricardol");

        Connection connection = db.openConnection();

        db.clearTables();

        tDao = new AuthTokenDao(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    void addToken() throws DataAccessException {
        tDao.addToken(newToken);
        AuthToken compareTest = tDao.authenticate(newToken.getAuthToken());

        assertNotNull(compareTest);
        assertEquals(newToken, compareTest);
    }

    @Test
    void addTokenFail() throws DataAccessException {
//        AuthToken token = tDao.addToken(newUser);
//        AuthToken falseToken = new AuthToken();
//        assertFalse(tDao.authenticate(falseToken));
    }

//    @Test
//    void findToken() throws DataAccessException {
//        AuthToken token = tDao.addToken(newUser);
//        assertTrue(tDao.authenticate(token));
//    }
//
//    @Test
//    void findTokenFails() throws DataAccessException {
//        AuthToken token = tDao.addToken(newUser);
//        AuthToken falseToken = new AuthToken();
//        assertFalse(tDao.authenticate(falseToken));
//    }
//
//
//    @Test
//    void clearAuthToken() throws DataAccessException {
//        AuthToken token = tDao.addToken(newUser);
//        AuthToken sectoken = tDao.addToken(newUser);
//        User newUser2 = new User("username", "password", "address@email.com",
//                "firstname", "lastname", "f", "id12345");
//        AuthToken token2 = tDao.addToken(newUser2);
//
//        tDao.clearAuthToken();
//        assertFalse(tDao.authenticate(sectoken));
//        assertFalse(tDao.authenticate(token2));
//
//    }
}