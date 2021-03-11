package Services;

import DAO.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResultSuccess;

import java.sql.*;

/**
 * This class is responsible for registering a new user to the database
 */
public class RegisterService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public RegisterService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public RegisterService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Registers a new user
     *
     * @param request Takes in the request to create a new user
     * @return Null for now, but it will return new user
     */
    public RegisterResultSuccess register(RegisterRequest request) {
        Database db = new Database();

        try {
            Connection connection = db.openConnection();
            UserDao userDao = new UserDao(connection);
            User reqUser = userDao.findUser(request.getUserName());

            if (reqUser != null) {
                db.closeConnection(false);
                return new RegisterResultSuccess("Username already taken by another user");
            }

            PersonDao personDAO = new PersonDao(connection);

            try {
                Person newPerson = new Person(request.getUserName(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getGender());
                personDAO.addPerson(newPerson);

                User newUser = new User(request.getUserName(),
                        request.getPassword(),
                        request.getEmail(),
                        request.getFirstName(),
                        request.getLastName(),
                        request.getGender(),
                        newPerson.getPersonID());

                userDao.registerUser(newUser);

                AuthTokenDao authTokenDao = new AuthTokenDao(connection);

                AuthToken newToken = new AuthToken(newUser.getUserName());

                authTokenDao.addToken(newToken);

                db.closeConnection(true);

                return new RegisterResultSuccess(newToken.getAuthToken(), newUser.getUserName(), newUser.getPersonID());

            } catch (DataAccessException e) {
                System.out.println(e.getMessage());
                try {
                    db.closeConnection(false);
                } catch (DataAccessException d) {
                    d.printStackTrace();
                }
                return new RegisterResultSuccess("Error registering the user");
            }
        }catch (DataAccessException e) {
            try {
                db.closeConnection(false);

            } catch (DataAccessException d) {
                d.printStackTrace();
            }
            return new RegisterResultSuccess("Internal server error");
        }
    }
}
