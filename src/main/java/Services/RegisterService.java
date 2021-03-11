package Services;

import DAO.*;
import Model.AuthToken;
import Model.Person;
import Model.User;
import Request.RegisterRequest;
import Result.ErrorMessageResult;
import Result.RegisterResultSuccess;
import Result.ResultBool;
import Result.SuccessMessageResult;

import java.sql.*;
import java.util.UUID;

/**
 * This class is responsible for registering a new user to the database
 */
public class RegisterService extends Service {

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
    public ResultBool register(RegisterRequest request) {
        setUp();

        if (connection == null)
            return new ErrorMessageResult("Error registering a new user");

        PersonDao personDAO = new PersonDao(connection);
        String id = UUID.randomUUID().toString();
        User newUser = new User(request.getUserName(), request.getPassword(), request.getEmail(),
                request.getFirstName(), request.getLastName(), request.getGender(), id);
        try {
            UserDao uDao = new UserDao(connection);
            uDao.registerUser(newUser);

            return new RegisterResultSuccess(new AuthTokenDao(connection).addToken(newUser).getAuthToken(), newUser.getUserName(), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new ErrorMessageResult("Error registering the new user");
        } finally {
            try {
                db.closeConnection(true);

                FillService fill = new FillService();
                fill.fill(newUser.getUserName(), 4);

            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }
        }
    }

}
