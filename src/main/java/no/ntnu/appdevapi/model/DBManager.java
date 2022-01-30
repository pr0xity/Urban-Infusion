package no.ntnu.appdevapi.model;

import java.sql.*;

/**
 * A Class to manage all interactions with the database.
 */
public class DBManager {
    private Connection connection;
    private boolean hasData = false;

    /**
     * Constructor attempts to connect and initialize the database upon instantiation.
     */
    public DBManager() {
        try {
            getConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Attempts to establish a connection with the database.
     * @throws SQLException if no connection could be established.
     * @throws ClassNotFoundException if driver could not be located.
     */
    private void getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:8080/testdb", "postgres", "123");
        DatabaseMetaData meta = connection.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());

        initialize();
    }

    /**
     * Checks if the database has been created, and creates it if not.
     */
    private void initialize() {
        if (!hasData) {
            hasData = true;

            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM category;");
                System.out.println("Connected to existing database");

            } catch (SQLException e) {
                if (e.getMessage().contains("no such table")) {

                    // TODO: create DB

                    System.out.println("A new database has been created.");
                }
            }
        }
    }
}
