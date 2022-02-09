package no.ntnu.appdevapi.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A Class to manage all interactions with the database.
 */
public class DBManager {
    private Connection connection;
    private boolean hasData = false;
    private boolean connected = false;

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
     */
    private void getConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/testdb", "postgres", "123");
        DatabaseMetaData meta = connection.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());

        initialize();
        connected = true;
    }

    /**
     * Checks if the database has been created, and creates it if not.
     */
    private void initialize() {
        if (!hasData) {
            hasData = true;

            try (Statement statement = connection.createStatement()) {
                statement.executeQuery("SELECT * FROM category;");
                System.out.println("Connected to existing database");

            } catch (SQLException e) {
                if (e.getMessage().contains("no such table")) {

                    // TODO: create DB tables.

                    System.out.println("A new database has been created.");
                }
            }
        }
    }

    /**
     * Queries the DB for all users and returns it as a list.
     * @return {@code List<User>} of all users in the DB, or an empty list on error.
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM user;");
                while (rs.next()) {
                    User user = new User(
                            rs.getString("firstname"),
                            rs.getString("lastname"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getInt("permissionid"));
                    user.setId(rs.getInt("id"));
                    user.setCreatedAt(LocalDateTime.parse(rs.getString("createdat")));
                    user.setUpdatedAt(LocalDateTime.parse(rs.getString("updatedat")));
                    user.setEnabled(rs.getBoolean("enabled"));
                    users.add(user);
                }
            } catch (SQLException e) {
                return users;
            }
        }
        return users;
    }

    /**
     * Queries the DB for a user by their user ID.
     * @param userID the ID of the user.
     * @return {@code User} from the search result, or {@code null} if no match was found.
     */
    public User getUserById(int userID) {
        User user = null;
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE id = " + userID + ";");
                user = new User(
                        rs.getString("firstname"),
                        rs.getString("lastname"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("permissionid"));
                user.setId(rs.getInt("id"));
                user.setCreatedAt(LocalDateTime.parse(rs.getString("createdat")));
                user.setUpdatedAt(LocalDateTime.parse(rs.getString("updatedat")));
                user.setEnabled(rs.getBoolean("enabled"));
            } catch (SQLException e) {
                return user;
            }
        }
        return user;
    }

    /**
     * Sends a request to add a user to the DB.
     * @param user the user to be added.
     * @return {@code boolean} true if successful; false if not.
     */
    public boolean addUser(User user) {
        boolean success = false;
        if (connected) {
            try (PreparedStatement prep = connection.prepareStatement("INSERT INTO user(ID,firstname,lastname,email,password,permissionid,createdat,updatedat,enabled) values(?,?,?,?,?,?,?,?,?)")) {
                prep.setInt(1,user.getId());
                prep.setString(2, user.getFirstName());
                prep.setString(3, user.getLastName());
                prep.setString(4, user.getEmail());
                prep.setString(5, user.getPassword());
                prep.setInt(6, user.getPermissionID());
                prep.setString(7, "" + user.getCreatedAt());
                prep.setString(8, "" + user.getUpdatedAt());
                prep.setString(9, "" + user.isEnabled());
                prep.execute();
                success = true;
            } catch (SQLException e) {
                success = false;
            }
        }
        return success;
    }

    /**
     * Sends a request to remove a user from the DB.
     * @param userID ID of the user to be removed.
     * @return {@code boolean} true if successful; false if not.
     */
    public boolean deleteUser(int userID) {
        boolean success = false;
        if (connected) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("DELETE FROM user WHERE ID =" + userID + ";");
                success = true;
            } catch (SQLException e) {
                success = false;
            }
        }
        return success;
    }

}
