package DAO;

import Model.AuthToken;

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
    public void addToken(AuthToken newToken) throws DataAccessException {
        String sql = "INSERT INTO AuthorizationTokens(authToken, associatedUsername) VALUES(?,?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newToken.getAuthToken());
            stmt.setString(2, newToken.getAssociatedUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error when adding new token");
        }
    }

    /**
     * Finds a new token in the database
     *
     * @param authToken Finding the authToken through its unique identifier
     * @return Null for now, but it will return the authToken
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public AuthToken findToken(String authToken) throws DataAccessException {
        AuthToken token;
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthorizationTokens WHERE authToken = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                token = new AuthToken(rs.getString("authToken"), rs.getString("associatedUsername"));
                return token;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered when finding auth token");
        } finally {
            if (rs != null) {
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