package Services;

import java.sql.*;

/**
 * This class is responsible for filing data to the database
 */
public class FillService {

    private Connection connection;

    /**
     * Initializes an empty constructor for the class
     */
    public FillService() {}

    /**
     * Initializing constructor for class with a connection argument
     *
     * @param connection Links connection with database
     */
    public FillService(Connection connection) {
        this.connection = connection;
    }


}
