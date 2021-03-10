package DAO;

import Model.AuthToken;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {

    private Database db;
    private AuthToken authToken;
    AuthTokenDao authTokenDao;

    @BeforeEach
    void setUp() throws DataAccessException {

        db = new Database();

        authToken = new AuthToken("12345", "ricardol");

        Connection connection = db.openConnection();

        db.clearTables();

        authTokenDao = new AuthTokenDao(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    void addToken() throws DataAccessException {
        authTokenDao.addToken(authToken);

        AuthToken compareTest = authTokenDao.findToken(authToken.getAuthToken());

        assertNotNull(compareTest);

        assertEquals(authToken, compareTest);
    }

    @Test
    void addTokenFail() throws DataAccessException {
        authTokenDao.addToken(authToken);

        DataAccessException exception = assertThrows(DataAccessException.class, ()-> {
            authTokenDao.addToken(authToken);
        });

        assertThrows(DataAccessException.class, ()->authTokenDao.addToken(authToken));

        String expectedMessage = "Error when adding new token";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findToken() throws DataAccessException {
        authTokenDao.addToken(authToken);

        AuthToken compareTest = authTokenDao.findToken(authToken.getAuthToken());

        assertNotNull(compareTest);

        assertEquals(authToken, compareTest);
    }

    @Test
    void findTokenFail() throws DataAccessException {
        AuthToken compareTest = authTokenDao.findToken(authToken.getAuthToken());
        assertNull(compareTest);
    }

    @Test
    void clearAuthToken() throws DataAccessException {
        authTokenDao.addToken(authToken);

        AuthToken find = authTokenDao.findToken(authToken.getAuthToken());
        authTokenDao.clearAuthToken();

        AuthToken clear = authTokenDao.findToken(authToken.getAuthToken());
        assertNotNull(find);
        assertNull(clear);
    }
}