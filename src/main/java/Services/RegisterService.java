package Services;

import DAO.AuthTokenDao;
import DAO.DataAccessException;
import DAO.Database;
import DAO.UserDao;
import Model.AuthToken;
import Model.User;
import Request.RegisterRequest;
import Result.RegisterResult;

import java.sql.*;
import java.util.UUID;

/**
 * This class is responsible for registering a new user to the database
 */
public class RegisterService {

    private Connection connection;
    private Database db;

    /**
     * Initializes an empty constructor for the class
     */
    public RegisterService() {
        db = new Database();
    }

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
    public RegisterResult register(RegisterRequest request) throws DataAccessException {

        RegisterResult response = new RegisterResult();


        try {
            db.openConnection();
            UserDao uDao = new UserDao(db.getConnection());
            AuthTokenDao tDao = new AuthTokenDao(db.getConnection());

            boolean validUsername = !request.getUserName().isEmpty();
            boolean validPassword = !request.getPassword().isEmpty();
            boolean validEmail = !request.getEmail().isEmpty();
            boolean validFirstName = !request.getFirstName().isEmpty();
            boolean validLastName = !request.getLastName().isEmpty();
            boolean validGender = (request.getGender().equals("m") || request.getGender().equals("f"));


            if(validUsername && validPassword && validEmail && validFirstName && validLastName && validGender) {
                if (uDao.userExists(request.getUserName())) {
                    response.setSuccess(false);
                    response.setMessage("Error username already exists - Register Service");
                    db.closeConnection(false);
                } else { // if user does not exist

                    String newPersonID = UUID.randomUUID().toString(); // set PersonID as random id

                    User user = new User(
                            request.getUserName(),
                            request.getPassword(),
                            request.getEmail(),
                            request.getFirstName(),
                            request.getLastName(),
                            request.getGender(),
                            newPersonID);

                    uDao.registerUser(user);
//                    db.getPersonDao().generateRoot(user, newPersonID, 4, db.getEventDao());


                    String newAuthID = UUID.randomUUID().toString(); // random authToken
                    AuthToken authToken = new AuthToken(newAuthID, user.getUserName());
                    tDao.addToken(authToken);


                    response.setAuthToken(newAuthID);
                    response.setUsername(user.getUserName());
                    response.setPersonID(user.getPersonID());

                    response.setSuccess(true);
                    db.closeConnection(true);

                }
            } else {
                response.setSuccess(false);
                response.setMessage("Error request missing properties - Register Service");
                db.closeConnection(false);
            }

        } catch(DataAccessException e) {
            response.setSuccess(false);
            response.setMessage("Internal server error - Register Service");

            try {
                db.closeConnection(false);
            } catch(DataAccessException f) {
                f.printStackTrace();
            }
        }

        return response;
    }
}




