// package application;

// import javafx.fxml.FXML;
// import javafx.scene.control.Button;
// import javafx.scene.control.TextField;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.Alert;
// import javafx.scene.control.Alert.AlertType;
// import javafx.fxml.FXMLLoader;
// import javafx.scene.Parent;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import java.io.IOException;
// import java.sql.Connection;
// import java.sql.SQLException;
// import java.sql.PreparedStatement;
// import java.sql.ResultSet;

// public class LoginScreenController {

//     @FXML
//     private TextField usernameField;
//     @FXML
//     private PasswordField passwordField;
//     @FXML
//     private Button loginButton;
//     @FXML
//     private Button signupButton;

//     // Login button action
//     @FXML
//     private void handleLogin() {
//         String username = usernameField.getText();
//         String password = passwordField.getText();

//         // Validate user credentials (for example, you can use a database here)
//         if (username.equals("admin") && password.equals("admin")) {
//             showAlert("Login Success", "You have successfully logged in!", AlertType.INFORMATION);
//             // Test database connection
//             testDatabaseConnection();
//             // Switch to the main screen
//             loadMainScreen();
//         } else {
//             showAlert("Login Failed", "Incorrect username or password.", AlertType.ERROR);
//         }
//     }

//     // Signup button action
//     @FXML
//     private void handleSignup() {
//         String username = usernameField.getText();
//         String password = passwordField.getText();

//         if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
//             try {
//                 // Check if username already exists in the database
//                 Connection connection = DBConnection.getConnection();
//                 String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
//                 try (PreparedStatement ps = connection.prepareStatement(checkUserQuery)) {
//                     ps.setString(1, username);
//                     ResultSet rs = ps.executeQuery();
//                     rs.next();
//                     int count = rs.getInt(1);
//                     if (count > 0) {
//                         // Username already exists
//                         showAlert("Signup Failed", "Username is already taken. Please choose another one.",
//                                 AlertType.ERROR);
//                         return;
//                     }
//                 }

//                 // Insert new user into the database
//                 String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
//                 try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
//                     ps.setString(1, username);
//                     ps.setString(2, password);
//                     ps.executeUpdate();
//                     showAlert("Signup Success", "You have successfully signed up!", AlertType.INFORMATION);
//                     // Optionally, you can navigate to the login screen
//                     loadLoginScreen();
//                 }
//             } catch (SQLException e) {
//                 e.printStackTrace();
//                 showAlert("Signup Failed", "An error occurred while signing up. Please try again.", AlertType.ERROR);
//             }
//         } else {
//             showAlert("Signup Failed", "Please enter a valid username and password.", AlertType.ERROR);
//         }
//     }

//     // Show an alert with the given title and message
//     private void showAlert(String title, String message, AlertType alertType) {
//         Alert alert = new Alert(alertType);
//         alert.setTitle(title);
//         alert.setHeaderText(null);
//         alert.setContentText(message);
//         alert.showAndWait();
//     }

//     // Method to load the main screen after login (or signup)
//     private void loadMainScreen() {
//         try {
//             // Load the main screen FXML file
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/main.fxml"));
//             Parent root = loader.load();

//             // Get the current stage (window) and set the new scene
//             Stage stage = (Stage) loginButton.getScene().getWindow();
//             Scene mainScene = new Scene(root);
//             stage.setScene(mainScene);
//             stage.show();
//         } catch (IOException e) {
//             e.printStackTrace();
//             showAlert("Error", "Failed to load main screen.", AlertType.ERROR);
//         }
//     }

//     // Optionally, if you have a separate login screen FXML, you can call this
//     // method
//     private void loadLoginScreen() {
//         try {
//             FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
//             // Create the login screen layout and set it as a new scene
//             Parent loginScreen = loader.load();
//             Stage stage = (Stage) signupButton.getScene().getWindow();
//             stage.setScene(new Scene(loginScreen));
//             stage.show();
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Method to test the database connection
//     private void testDatabaseConnection() {
//         try {
//             // Try to get the connection from DBConnection class
//             Connection connection = DBConnection.getConnection();
//             if (connection != null) {
//                 System.out.println("Database connection successful!"); // Print success message
//             }
//         } catch (Exception e) {
//             // Catch any exception that occurs during the database connection
//             System.out.println("Database connection failed: " + e.getMessage()); // Print error message
//         }
//     }
// }

package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginScreenController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button signupButton;

    // Login button action
    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate user credentials by checking the database
        if (validateLogin(username, password)) {
            showAlert("Login Success", "You have successfully logged in!", AlertType.INFORMATION);
            // Switch to the main screen
            loadMainScreen();
        } else {
            showAlert("Login Failed", "Incorrect username or password.", AlertType.ERROR);
        }
    }

    // Signup button action
    @FXML
    private void handleSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            try {
                // Check if username already exists in the database
                Connection connection = DBConnection.getConnection();
                String checkUserQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
                try (PreparedStatement ps = connection.prepareStatement(checkUserQuery)) {
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);
                    if (count > 0) {
                        // Username already exists
                        showAlert("Signup Failed", "Username is already taken. Please choose another one.",
                                AlertType.ERROR);
                        return;
                    }
                }

                // Insert new user into the database
                String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
                try (PreparedStatement ps = connection.prepareStatement(insertQuery)) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    ps.executeUpdate();
                    showAlert("Signup Success", "You have successfully signed up!", AlertType.INFORMATION);
                    // Navigate to the main screen after signup
                    loadMainScreen();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                showAlert("Signup Failed", "An error occurred while signing up. Please try again.", AlertType.ERROR);
            }
        } else {
            showAlert("Signup Failed", "Please enter a valid username and password.", AlertType.ERROR);
        }
    }

    // Show an alert with the given title and message
    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to load the main screen after login (or signup)
    private void loadMainScreen() {
        try {
            // Load the main screen FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/main.fxml"));
            Parent root = loader.load();

            // Get the current stage (window) and set the new scene
            Stage stage = (Stage) loginButton.getScene().getWindow();
            Scene mainScene = new Scene(root);
            stage.setScene(mainScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load main screen.", AlertType.ERROR);
        }
    }

    // Method to validate login credentials from the database
    private boolean validateLogin(String username, String password) {
        boolean isValid = false;
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        isValid = true; // Login successful if user is found
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    // Optionally, if you have a separate login screen FXML, you can call this
    // method, but we already navigate to the main screen in handleSignup.
    private void loadLoginScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginScreen.fxml"));
            // Create the login screen layout and set it as a new scene
            Parent loginScreen = loader.load();
            Stage stage = (Stage) signupButton.getScene().getWindow();
            stage.setScene(new Scene(loginScreen));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
