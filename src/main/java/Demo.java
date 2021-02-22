
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {

        try {
            Demo db = new Demo();

            db.loadDatabaseDriver();
            db.openConnection();
            db.createTables();

            db.deleteAllBooks();

            for (int i = 1; i <= 5; i++) {
                Book b = new Book("Tittle " + i, "Author " + i, "Fiction");
                db.insertBook(b);
            }

            List<Book> allBooks = db.getAllBooks();

            for (Book b : allBooks) {
                System.out.println(b);
            }

            for (int i = 0; i < allBooks.size(); i += 2) {
                db.deleteBook(allBooks.get(i));
            }

            allBooks = db.getAllBooks();

            for (Book b : allBooks) {
                System.out.println(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    final String dbFile = "";
    final String connectionURL = "jdbc:sqlite" + dbFile;

    private Connection connection;

    public Demo() { connection = null; }

    /*** Load the Driver ***/

    public void loadDatabaseDriver() throws ClassNotFoundException {
        final String driver = "org.sqlite.JDBC";

        // Dynamically load the driver class
        Class.forName(driver);
    }

    public void openConnection() throws SQLException {

        // Open a database connection
        connection = DriverManager.getConnection(connectionURL);

        // Start a transaction
        connection.setAutoCommit(false);
    }

    public void closeConnection(boolean commit) throws SQLException {
        if (commit) {
            connection.commit();
        } else {
            connection.rollback();
        }

        connection = null;
    }

    public void createTables() throws SQLException {
        PreparedStatement stmt = null;

        try {
            String sql = "create table if not exists book (" +
                    "id integer not null primary key autoincrement," +
                    "title varchar(255) not null, " +
                    "author vrchar(255) not null," +
                    "genre varchar(32) not null," +
                    "constraint ck_genre (genre in ('Unspecified', 'Fiction', 'NonFiction'))" +
                    ")";

            stmt = connection.prepareStatement(sql);
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public List<Book> getAllBooks() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            List<Book> allBooks = new ArrayList<>();

            String sql = "select id, title, author, genre from book";
            stmt = connection.prepareStatement(sql);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt(1);
                String title = rs.getString(2);
                String author = rs.getString(3);
                String genre = rs.getString(4);

                Book book = new Book(id, title, author, genre);

                allBooks.add(book);
            }
            return allBooks;
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }



}
