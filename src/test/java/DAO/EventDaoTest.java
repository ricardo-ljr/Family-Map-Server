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
                "12345",
                23.5505f,
                46.6333f,
                "Brazil",
                "Sao Paulo",
                "birth",
                1995
                );


        event2 = new Event("12346",
                "ricardol",
                "12345",
                33.3528f,
                111.7890f,
                "United States of America",
                "Gilbert",
                "marriage",
                2016
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
        ArrayList<Event> eventsList = null;
        eDao.addEvent(event);
        eDao.addEvent(event2);

        eventsList = eDao.findAllEvents(event.getAssociatedUsername());

        assertNotNull(eventsList);

        assertEquals(eventsList.size(), 2);
    }

    @Test
    void findAllEventsFail() throws DataAccessException {
        ArrayList<Event> eventsList = null;
        eventsList = eDao.findAllEvents(event.getAssociatedUsername());
        assertEquals(eventsList.size(), 0);
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