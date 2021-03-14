package Services;

import DAO.Database;
import DAO.UserDao;
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
    public LoginResult login(LoginRequest request) {

        LoginResult response = new LoginResult();
        UserDao uDao = new UserDao(connection);

        try {
            db.openConnection();

            String userName = request.getUserName();
            String password = request.getPassword();


            if (uDao.userExists(userName)) {

                String newAuthID = UUID.randomUUID().toString();
                String oldPersonID = db.getUserDao().selectUser(userName).getPersonID();


                if(db.getAuthDao().doesUserNameExist(userName)) {
                    db.getAuthDao().updateAuthToken(newAuthID, userName);
                } else {
                    AuthToken newAT = new AuthToken(newAuthID, userName, oldPersonID);
                    db.getAuthDao().insertAuthToken(newAT);
                }


                response.setAuthToken(newAuthID);
                response.setUserName(userName);
                response.setPersonID(oldPersonID);

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


