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
    public void addToken(AuthToken newToken) throws DataAccessException {
//        AuthToken token = new AuthToken();

        String sql = "INSERT INTO AuthorizationTokens(authToken,associatedUsername) VALUES(?,?);";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newToken.getAuthToken());
            stmt.setString(2, newToken.getAssociatedUsername());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error when adding new token");
        }
    }

    /**
     * Authenticates your token
     * @param auth
     * @return
     * @throws DataAccessException
     */
    public AuthToken authenticate(String auth) throws DataAccessException {
        AuthToken token;
        String sql = "SELECT * FROM AuthorizationTokens WHERE authToken = ?;";
        ResultSet rs = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, auth);
            rs = stmt.executeQuery();

            if (rs.next()) {
                token = new AuthToken(rs.getString("authToken"),
                        rs.getString("associatedUsername"));
                return token;
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while authenticating your token");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }

    /**
     * Function to check whether toke exists or not, return a boolean value
     *
     * @param auth
     * @return
     * @throws DataAccessException
     */
    public boolean authTokenExists(String auth) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM AuthorizationTokens WHERE authToken = ?;";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, auth);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
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
     * This function is here to update user's token
     *
     * @param newToken
     * @param username
     * @throws DataAccessException
     */
    public void updateToken(String newToken, String username) throws DataAccessException {
        String sql = "UPDATE AuthorizationTokens SET authToken = ? WHERE associatedUsername = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, newToken);
            stmt.setString(2, username);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while updating Auth Token");
        }
    }

    /**
     * This function is here to simply make sure that the user exists in the Authorization Token table and match its token with its
     * associated username
     *
     * @param username
     * @return
     * @throws DataAccessException
     */
    public boolean userExists(String username) throws DataAccessException {

        ResultSet rs = null;
        String sql = "SELECT * FROM AuthorizationTokens WHERE associatedUsername = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);

            rs = stmt.executeQuery();

            if (!rs.next()) {
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

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
