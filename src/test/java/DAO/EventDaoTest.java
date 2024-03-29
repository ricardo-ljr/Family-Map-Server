package DAO;

import Model.AuthToken;
import Model.Event;
import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EventDaoTest {

    private Database db;
    private Event event;
    private Event event2;
    EventDao eDao;

    @BeforeEach
    void setUp() throws DataAccessException {

        db = new Database();

        event = new Event("12345",
                "ricardol",
                23.5505f,
                46.6333f,
                "Brazil",
                "Sao Paulo",
                "birth",
                1995,
                "12345"
                );


        event2 = new Event("12346",
                "ricardol",
                33.3528f,
                111.7890f,
                "United States of America",
                "Gilbert",
                "marriage",
                2016,
                "12345"
                );



        Connection connection = db.openConnection();

        db.clearTables();

        eDao = new EventDao(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    void addEvent() throws DataAccessException  {
        eDao.addEvent(event);

        Event compareTest = eDao.findEvent(event.getEventID());

        assertNotNull(compareTest);

        assertEquals(event, compareTest);
    }

    @Test
    void addEventFail() throws DataAccessException {
        eDao.addEvent(event);

        DataAccessException exception = assertThrows(DataAccessException.class, ()-> {
            eDao.addEvent(event);
        });

        assertThrows(DataAccessException.class, ()->eDao.addEvent(event));

        String expectedMessage = "Error encountered when adding new event";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void findEvent() throws DataAccessException {
        eDao.addEvent(event);

        Event compareTest = eDao.findEvent(event.getEventID());

        assertNotNull(compareTest);

        assertEquals(event, compareTest);
    }

    @Test
    void findEventFail() throws DataAccessException {
        Event compareTest = eDao.findEvent(event.getEventID());
        assertNull(compareTest);
    }

    @Test
    void findAllEvents() throws DataAccessException {
        Event[] eventsList = null;
        eDao.addEvent(event);
        eDao.addEvent(event2);

        eventsList = eDao.findAllEvents(event.getAssociatedUsername());

        assertNotNull(eventsList);

        assertEquals(eventsList.length, 2);
    }

    @Test
    void findAllEventsFail() throws DataAccessException {
        Event[] eventsList = null;
        eventsList = eDao.findAllEvents(event.getAssociatedUsername());
        assertEquals(eventsList.length, 0);
    }

    @Test
    void eventExists() throws DataAccessException {
        eDao.addEvent(event);

        assertTrue(eDao.eventExists(event.getEventID()));
    }

    @Test
    void eventExistsFail() throws DataAccessException {
        eDao.addEvent(event);
        assertTrue(eDao.eventExists(event.getEventID()));

        eDao.clearEvent();
        assertFalse(eDao.eventExists(event.getEventID()));
    }

    @Test
    void generateBirth() throws DataAccessException {

        eDao.generateBirth("ricardol", "12345", 1995);
        assertEquals(eDao.findAllEvents("ricardol").length, 1);
    }

    @Test
    void generateMarriage() throws DataAccessException {

        eDao.generateMarriage("ricardol", "1234", "123", 1995);
        assertEquals(eDao.findAllEvents("ricardol").length, 2);
    }

    @Test
    void generateDeath() throws DataAccessException {
        eDao.generateDeath("ricardol", "12345", 1995);
        assertEquals(eDao.findAllEvents("ricardol").length, 1);
    }

    @Test
    void clearEvent() throws DataAccessException {
        eDao.addEvent(event);

        Event find = eDao.findEvent(event.getEventID());
        eDao.clearEvent();

        Event clear = eDao.findEvent(event.getEventID());
        assertNotNull(find);
        assertNull(clear);
    }
}