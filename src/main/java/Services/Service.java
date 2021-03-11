package Services;


import DAO.DataAccessException;
import DAO.Database;

import java.sql.Connection;

public class Service {
    protected Database db;
    protected Connection connection;

    Service() { setUp(); }

    protected void setUp(){
        db = new Database();
        try {
            connection = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
