package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/MediaPlayerDB";
    private static final String USER = "root"; // Replace with your MySQL username
    private static final String PASSWORD = "T8emp@056$"; // Replace with your MySQL password

    // Get a connection to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!"); // Success message
        } catch (ClassNotFoundException e) {
            System.out.println("Error: MySQL JDBC Driver not found.");
            e.printStackTrace(); // Print the exception details for debugging
        } catch (SQLException e) {
            System.out.println("Error: Unable to connect to the database.");
            e.printStackTrace(); // Print the exception details for debugging
        }
        return connection;
    }
}
