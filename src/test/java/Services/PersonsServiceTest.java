package Services;

import DAO.DataAccessException;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.FillResult;
import Result.PersonsResult;
import Result.RegisterResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonsServiceTest {

    private PersonByIdService personByIdService;
    private PersonsService personsService;
    private FillService fillService;
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        fillService = new FillService();
        personByIdService = new PersonByIdService();
        personsService = new PersonsService();
        registerService = new RegisterService();
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        ClearService clear = new ClearService();
        clear.clearResult();
    }


    @Test
    void getPersons() throws DataAccessException {

        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345");

        RegisterRequest request = new RegisterRequest();
        request.setUserName(newUser.getUsername());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        RegisterResult response = registerService.register(request);
        String authToken = response.getAuthToken();

        FillResult response1 = fillService.fill(newUser.getUsername(), 4);

        PersonsResult responsePersons = personsService.getPersons(authToken);

        Person[] persons = responsePersons.getPerson();

        assertEquals(persons.length, 31);
    }

    @Test
    void getPersons2() throws DataAccessException { // generating only one person in the tree

        User newUser = new User(
                "patrick",
                "spencer",
                "patrick@spencer.com",
                "Patrick",
                "Spencer",
                "m",
                "12345");

        RegisterRequest request = new RegisterRequest();
        request.setUserName(newUser.getUsername());
        request.setPassword(newUser.getPassword());
        request.setEmail(newUser.getEmail());
        request.setFirstName(newUser.getFirstName());
        request.setLastName(newUser.getLastName());
        request.setGender(newUser.getGender());

        RegisterResult response = registerService.register(request);
        String authToken = response.getAuthToken();

        FillResult response1 = fillService.fill(newUser.getUsername(), 0);

        PersonsResult responsePersons = personsService.getPersons(authToken);

        Person[] persons = responsePersons.getPerson();

        assertEquals(persons.length, 1);
    }
}