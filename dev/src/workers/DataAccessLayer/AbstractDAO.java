
package workers.DataAccessLayer;

import suppliers.DataAccessLayer.DbController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

public abstract class AbstractDAO {

    protected static final String path = "jdbc:sqlite:dev/src/workers/DataAccessLayer/ADDS_db.db";
    protected static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static void connect() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
                if (connection == null || connection.isClosed()) {
                    connection = DriverManager.getConnection(path);
                    connection.setAutoCommit(false);
                }
            }
            catch (SQLException e) {
                otherconect1();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void otherconect1() {
        try {
            // Use ClassLoader to get the resource as an InputStream
            InputStream inputStream = DbController.class.getClassLoader().getResourceAsStream("workers/DataAccessLayer/ADDS_db.db");

            if (inputStream != null) {
                // Get the desktop path
                String desktopPath = System.getProperty("user.home") + "/Desktop";
                File desktopFolder = new File(desktopPath);
                if (!desktopFolder.exists() || !desktopFolder.isDirectory()) {
                    throw new Exception("Desktop folder not found");
                }

                // Create the file on the desktop
                File desktopFile = new File(desktopPath, "ADDS_db.db");
                // Schedule the file for deletion on JVM exit
                desktopFile.deleteOnExit();

                // Write the InputStream to the file on the desktop
                try (FileOutputStream outputStream = new FileOutputStream(desktopFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                String connectionString = "jdbc:sqlite:" + desktopFile.getAbsolutePath();
                // Load SQLite JDBC driver
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(connectionString);
                if (connection == null) {
                    throw new AssertionError("Database connection failed");
                    // Perform any initial database setup here if needed
                }

            }
        } catch (Exception e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }
    public static void disconnect() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfAreTableEmpty() {
        String[] tables = {"employees", "BranchEmployees", "Branches", "ConstraintsToEmployee", "EmployeeConstraints", "RoleCounts", "Schedules", "Shifts"};
        try {
            connect();
            Statement stmt = connection.createStatement();
            for (String table : tables) {
                String query = "SELECT COUNT(*) FROM " + table;
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        return false;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean checkIfAreCounerZero() {
        String[] tables = {"Counters"};
        try {
            connect();
            Statement stmt = connection.createStatement();
            for (String table : tables) {
                String query = "SELECT COUNT(*) FROM " + table;
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    if (count > 0) {
                        return false;
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
