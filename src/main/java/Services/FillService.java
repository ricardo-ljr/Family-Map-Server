package Services;

import DAO.*;
import Model.*;
import Result.ResultBool;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

import java.sql.*;
import java.util.UUID;

/**
 * This class is responsible for filing data to the database
 */
public class FillService {

    private Connection connection;
    private int personsAdded;
    private int eventsAdded;
    private Database db;

    /**
     * Initializes an empty constructor for the class
     */
    public FillService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public FillService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Fill methos to fill database with username and generations
     *
     * @param username
     * @param generations
     * @return
     */
    public ResultBool fill(String username, int generations){
        personsAdded = 0;
        eventsAdded = 0;
        PersonDao pDao = new PersonDao(connection);
        UserDao uDao = new UserDao(connection);
        EventDao eDao = new EventDao(connection);

        try {

            User user = uDao.findUser(username);

            pDao.clearPersonUsername(username);
            fillHelper(user.getFirstName(),
                    user.getLastName(),
                    user.getGender(),
                    generations,
                    pDao,
                    eDao,
                    username,
                    user.getPersonID(),
                    null,
                    2050,
                    null);

        } catch (DataAccessException | FileNotFoundException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error while filling the database");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
        return new SuccessMessageResult("Successfully added " + personsAdded + " persons and " + eventsAdded + " events");
    }

    private void fillHelper(String firstName,
                            String lastName,
                            String gender,
                            int generation,
                            PersonDao pDao,
                            EventDao eDao,
                            String username,
                            String id,
                            String spouseID,
                            int childsBirthYear,
                            Event marriage) throws DataAccessException, FileNotFoundException {

        int birthYear = childsBirthYear - 20;

        fillEvents(username, id, childsBirthYear, eDao, marriage);

        Person person;

        FileReader reader = new FileReader((gender.equals("m")?"json/mnames.json":"json/fnames.json"));

        Gson gson = new Gson();

        NamesData namesData = gson.fromJson(reader, NamesData.class);

        if (firstName==null) firstName = namesData.getRandomName();

        reader = new FileReader("json/snames.json");
        namesData = gson.fromJson(reader, NamesData.class);

        if (lastName == null) lastName = namesData.getRandomName();

        if (generation>0){

            String fatherID = UUID.randomUUID().toString();
            String motherID = UUID.randomUUID().toString();
            Event parentsMarriage = generateRandomEvent(username, fatherID, eDao, "marriage", childsBirthYear-3);

            fillHelper(null, null,"m", generation-1, pDao, eDao, username, fatherID, motherID, birthYear, parentsMarriage);
            parentsMarriage.setPersonID(motherID);
            parentsMarriage.setEventID(UUID.randomUUID().toString());

            fillHelper(null, null, "f", generation-1,
                    pDao, eDao, username, motherID, fatherID, birthYear, parentsMarriage);

            person = new Person(username, firstName, lastName, gender, fatherID,
                    motherID, spouseID, id);
        } else {
            person = new Person(username, firstName, lastName, gender, null,
                    null, spouseID, id);
        }
        pDao.addPerson(person);
        personsAdded++;
    }

    private void fillEvents(String username, String personID, int childsBirthYear, EventDao eDao, Event marriage) throws FileNotFoundException, DataAccessException {
        eDao.addEvent(generateRandomEvent(username, personID, eDao, "birth",childsBirthYear - 20));
        eventsAdded++;

        if (marriage != null) {
            eDao.addEvent(marriage);
        } else  {
            eDao.addEvent(generateRandomEvent(username, personID, eDao, "marriage", childsBirthYear-3));
        }
        eventsAdded++;

        eDao.addEvent(generateRandomEvent(username, personID, eDao, "death", childsBirthYear+60));
        eventsAdded++;
    }

    private Event generateRandomEvent(String username, String personID, EventDao eDao, String type, int year) throws FileNotFoundException {
        FileReader reader = new FileReader("json/locations.json");

        Gson gson = new Gson();
        LocationData locationData = gson.fromJson(reader, LocationData.class);
        Location location = locationData.getRandomLocation();
        String id = UUID.randomUUID().toString();
        return new Event(id,
                username,
                personID,
                (float)location.getLatitude(),
                (float)location.getLongitude(),
                location.getCountry(),
                location.getCity(),
                type,
                year);
    }
}
