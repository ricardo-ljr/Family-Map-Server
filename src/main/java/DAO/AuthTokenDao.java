package DAO;

import Model.AuthToken;
import Model.User;

import java.sql.*;

/**
 * This class is used to access the authorization token information in the database
 */
public class AuthTokenDao {

    private Connection connection;

    /**
     * Initializing empty constructor for class
     */
    public AuthTokenDao() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public AuthTokenDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new authToken in the database
     *
     * @param newToken Token that is going to be added
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public AuthToken addToken(User newToken) throws DataAccessException {
        AuthToken token = new AuthToken();
        String sql = "INSERT INTO AuthorizationTokens(authToken,associatedUsername) VALUES(?,?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, token.getAuthToken());
            stmt.setString(2, newToken.getUserName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error when adding new token");
        }
        return token;
    }

    /**
     * Authenticates your token
     * @param auth
     * @return
     * @throws DataAccessException
     */
    public boolean authenticate(AuthToken auth) throws DataAccessException {
        String sql = "SELECT authToken " +
                "FROM AuthorizationTokens " +
                "WHERE authToken=\'" + auth.getAuthToken() + "\'";

        boolean result = false;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while authenticating your token");
        }
        return result;
    }

    /**
     * Find username given auth token
     *
     * @param authtoken Authorization token to give
     * @return
     * @throws DataAccessException Exception throws
     */
    public String getUsernameForAuthtoken(String authtoken) throws DataAccessException {
        String sql = "SELECT username " +
                "FROM authtokens " +
                "WHERE token=\'" + authtoken + "\'";

        String result;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            result = rs.getString(1);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while querying in the database");
        }
        return result;
    }

    /**
     * Clears all data from an authToken in the database
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public void clearAuthToken() throws DataAccessException {
        try (Statement stmt = connection.createStatement()) {
            String sql = "DELETE FROM AuthorizationTokens;";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered when clearing authorization tokens table");
        }
    }
}
