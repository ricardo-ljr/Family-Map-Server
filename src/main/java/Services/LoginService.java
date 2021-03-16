package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.AuthToken;
import Request.LoginRequest;
import Result.LoginResult;

import java.sql.*;
import java.util.UUID;

/**
 * This class is responsible for logging a new user into the application
 */
public class LoginService {

    private Connection connection;
    private Database db;

    /**
     * Initializes an empty constructor for the class
     */
    public LoginService() {
        db = new Database();
    }

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
    public LoginResult login(LoginRequest request) throws DataAccessException {

        LoginResult response = new LoginResult();


        try {
            db.openConnection();
            UserDao uDao = new UserDao(db.getConnection());
            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());

            String userName = request.getUserName();
            String password = request.getPassword();


            if (uDao.userExists(userName) && password.equals(uDao.findUser(userName).getPassword())) {

                String newAuthID = UUID.randomUUID().toString();

                if(tDao.userExists(userName)) {
                    tDao.updateAuthToken(newAuthID, userName);
                } else {
                    AuthToken newToken = new AuthToken(newAuthID, userName);
                    tDao.addToken(newToken);
                }

                response.setAuthToken(newAuthID);
                response.setUsername(userName);

                response.setSuccess(true);
                db.closeConnection(true);

            } else {
                response.setSuccess(false);
                response.setMessage("Error: Invalid userName or password");
                db.closeConnection(false);
            }
        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error");

            try {
                db.closeConnection(false);
            } catch(DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }
}


