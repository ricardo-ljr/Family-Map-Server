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

        newToken = new AuthToken("123456", "ricardol");

        Connection connection = db.openConnection();

        tDao = new AuthTokenDao(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.clearTables();
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
        tDao.addToken(newToken);
        assertThrows(DataAccessException.class, ()-> tDao.addToken(newToken));
    }

    @Test
    void authenticate() throws DataAccessException {
        tDao.addToken(newToken);

        AuthToken compareTest = tDao.authenticate(newToken.getAuthToken());

        assertNotNull(compareTest);
        assertEquals(newToken, compareTest);

    }

    @Test
    void authenticateFail() throws DataAccessException {
        assertNull(tDao.authenticate(newToken.getAuthToken()));
    }

    @Test
    void tokenExists() throws DataAccessException {
        tDao.addToken(newToken);

        assertTrue(tDao.authTokenExists(newToken.getAuthToken()));
    }

    @Test
    void tokenExistsFail() throws DataAccessException {
        tDao.addToken(newToken);

        assertTrue(tDao.authTokenExists(newToken.getAuthToken()));
        tDao.clearAuthToken();
        assertFalse(tDao.authTokenExists(newToken.getAuthToken()));
    }


    @Test
    void clearAuthToken() throws DataAccessException {
        tDao.addToken(newToken);

        tDao.clearAuthToken();
        assertFalse(tDao.authTokenExists(newToken.getAuthToken()));

    }
}