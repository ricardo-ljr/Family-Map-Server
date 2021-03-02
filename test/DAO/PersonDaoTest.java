package DAO;

import Model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person person;
    PersonDao pDao;

    @BeforeEach
    void setUp() throws DataAccessException {

        db = new Database();

        person = new Person("12345", "person_1",
                "Ricardo", "Leite", "m",
                "father_1", "mother_1", "spouse_1");

        Connection connection = db.openConnection();

        db.clearTables();

        pDao = new PersonDao(connection);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(true);
    }

    @Test
    void testAddPerson() throws DataAccessException{

        pDao.addPerson(person);

        Person compareTest = pDao.findPerson(person.getPersonID());

        assertNotNull(compareTest);

        assertEquals(person, compareTest);
    }

    @Test
    void testAddPersonFail() throws DataAccessException {

        pDao.addPerson(person);

        DataAccessException exception = assertThrows(DataAccessException.class, ()-> {
            pDao.addPerson(person);
        });

        assertThrows(DataAccessException.class, ()->pDao.addPerson(person));

        String expectedMessage = "Error encountered while inserting person into database";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testFindPerson() throws DataAccessException{
        pDao.addPerson(person);

        Person compareTest = pDao.findPerson(person.getPersonID());

        assertNotNull(compareTest);

        assertEquals(person, compareTest);
    }

    @Test
    void testFindPersonFail() throws DataAccessException {
        Person compareTest = pDao.findPerson(person.getPersonID());
        assertNull(compareTest);
    }

    @Test
    void testClearPerson() throws DataAccessException{
        pDao.addPerson(person);

        Person find = pDao.findPerson(person.getPersonID());
        pDao.clearPerson();

        Person clear = pDao.findPerson(person.getPersonID());
        assertNotNull(find);
        assertNull(clear);
    }
}