package DAO;

import Model.User;
import java.sql.*;

/**
 * This class is used to access the user's table in the database, providing methods to add a new user,
 * querying a user's information and clearing user's information from the database
 */
public class UserDao {

    private Connection connection;

    /**
     * Initializing empty constructor for class
     */
    public UserDao() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public UserDao(Connection connection) {
        this.connection = connection;
    }


    /**
     * Creates a new user in the database
     *
     * @param newUser User that is going to be added
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public void registerUser(User newUser) throws DataAccessException {
        String sql = "INSERT INTO User(username, password, email, firstName, lastName, gender, personID) VALUES(?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newUser.getUserName());
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.getEmail());
            stmt.setString(4, newUser.getFirstName());
            stmt.setString(5, newUser.getLastName());
            stmt.setString(6, newUser.getGender());
            stmt.setString(7, newUser.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while intersting into database");
        }
    }

    /**
     * Finds a new user in the database
     *
     * @param username Finding the user through its username
     * @return Return the user
     * @throws DataAccessException An exception that provides information on a database access error or other errors
     */
    public User findUser(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("userID"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("gender"),
                        rs.getString("personID"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * Clears all data from a user in the database
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void clearUser() throws DataAccessException {
        try (Statement stmt = connection.createStatement()){
            String sql = "DELETE FROM User";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing tables");
        }
    }
}
