package DAO;

import Model.Person;
import Model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person person;
    private Person father;
    private Person mother;
    PersonDao pDao;
    UserDao uDao;
    EventDao eDao;

    @BeforeEach
    void setUp() throws DataAccessException {

        db = new Database();

        person = new Person("12345", "person_1",
                "Ricardo", "Leite", "m",
                "father_1", "mother_1", "spouse_1");

        father = new Person("father_1", "person_1",
                "Ricardo", "Leite", "m",
                "father_2", "mother_2", "spouse_2");

        mother = new Person("mother_1", "person_1",
                "Ricardo", "Leite", "m",
                "father_3", "mother_3", "spouse_2");

        Connection connection = db.openConnection();

        db.clearTables();

        pDao = new PersonDao(connection);
        uDao = new UserDao(connection);
        eDao = new EventDao(connection);
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
    void personExists() throws DataAccessException {
        pDao.addPerson(person);
        assertTrue(pDao.personExists(person.getPersonID()));
    }

    @Test
    void personExistsFail() throws DataAccessException {
        pDao.addPerson(person);
        assertTrue(pDao.personExists(person.getPersonID()));

        pDao.clearPerson();

        assertFalse(pDao.personExists(person.getPersonID()));
    }

    @Test
    void getPersonsByUserName() throws DataAccessException {
        pDao.addPerson(person);

        Person[] compareTest = pDao.getPersonsForUsername(person.getAssociatedUsername());

        assertNotNull(compareTest);

        assertEquals(compareTest.length, 1);
    }

    @Test
    void getPersonsByUserNameFails() throws DataAccessException {
        Person[] compareTest = pDao.getPersonsForUsername(person.getAssociatedUsername());

        assertNotNull(compareTest);

        assertEquals(compareTest.length, 0);
    }


    @Test
    void clearPersonByUsername() throws DataAccessException {
        pDao.addPerson(person);

        Person find = pDao.findPerson(person.getPersonID());
        pDao.clearPersonUsername(person.getAssociatedUsername());

        Person clear = pDao.findPerson(person.getPersonID());
        assertNotNull(find);
        assertNull(clear);
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

    @Test
    void generateTree() throws DataAccessException {
        String personID = UUID.randomUUID().toString();

        User user = new User("12345", "person_1",
                "Ricardo@email.com", "Ricardo", "Leite","m", "12345");

        uDao.registerUser(user);

        pDao.generateTree(user, personID, 1, eDao);

        assertEquals(pDao.getPersonsForUsername(user.getUsername()).length, 3);
    }

    @Test
    void generateTree2() throws DataAccessException {
        String personID2 = UUID.randomUUID().toString();

        User user2 = new User("123456", "person_2",
                "leite@email.com", "Leite", "Ricardo","m", "123456");

        uDao.registerUser(user2);

        pDao.generateTree(user2, personID2, 4, eDao);

        assertEquals(pDao.getPersonsForUsername(user2.getUsername()).length, 31);
    }

    @Test
    void insertFatherID() throws DataAccessException {
        pDao.addPerson(person);

        String childID = person.getPersonID();
        String fatherID = father.getFatherID();

        pDao.insertFatherID(childID, fatherID);
        assertEquals(person.getFatherID(), father.getPersonID());
    }

    @Test
    void insertFatherIDFails() throws DataAccessException {
        pDao.addPerson(person);

        String childID = person.getPersonID();
        String fatherID = UUID.randomUUID().toString();

        pDao.insertFatherID(childID, fatherID);
        assertNotEquals(person.getFatherID(), fatherID);
    }

    @Test
    void insertMotherID() throws DataAccessException {
        pDao.addPerson(person);

        String childID = person.getPersonID();
        String motherID = mother.getPersonID();

        pDao.insertMotherID(childID, motherID);
        assertEquals(person.getMotherID(), mother.getPersonID());
    }

    @Test
    void insertMotherIDFails() throws DataAccessException {
        pDao.addPerson(person);

        String childID = person.getPersonID();
        String motherID = UUID.randomUUID().toString();

        pDao.insertMotherID(childID, motherID);
        assertNotEquals(person.getFatherID(), motherID);
    }
}