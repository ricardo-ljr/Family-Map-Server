package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.UserDao;
import Model.User;
import Request.LoginRequest;
import Result.ErrorMessageResult;
import Result.LoginResult;
import Result.ResultBool;

import java.sql.*;

/**
 * This class is responsible for logging a new user into the application
 */
public class LoginService extends Service{

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public LoginService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public LoginService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Logs a user in
     *
     * @param request Takes in the request to login a user
     * @return Null for now, but it will return the user requesting login
     */
    public ResultBool login(LoginRequest request) {
        setUp();

        if (connection == null)
            return new ErrorMessageResult("Error getting the database");

        try {

            UserDao uDao = new UserDao(connection);

            User user = uDao.findUser(request.getUserName());

            if(user.getPassword().equals(request.getPassword())) {
                return new LoginResult(new AuthTokenDao(connection).addToken(user).getAuthToken(),
                        user.getUserName(), user.getPersonID());
            }
            else return new ErrorMessageResult("Error you have typed a wrong password");
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return new ErrorMessageResult("Error logging in");
        } finally {
            try {
                db.closeConnection(true);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
            }

        }
    }
}
