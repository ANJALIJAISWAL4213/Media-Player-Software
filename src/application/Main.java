package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			// Load the Login/Signup screen first
			Parent loginRoot = FXMLLoader.load(getClass().getResource("/application/loginScreen.fxml"));
			primaryStage.setTitle("MediaPlayerEX");
			primaryStage.getIcons().add(new Image("/Icons/program.png"));
			Scene scene = new Scene(loginRoot, 400, 550);
			scene.getStylesheets().add(getClass().getResource("lite.css").toExternalForm());
			primaryStage.setResizable(true);
			primaryStage.setScene(scene);
			primaryStage.show();

			// After successful login/signup, switch to the main media player screen
			// For example, you can call a method to change the scene after the user logs in
			// Assuming your login controller does something like this:
			// loginController.setOnLoginSuccess(() -> showMediaPlayer(primaryStage));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Method to switch to the media player screen
	public void showMediaPlayer(Stage primaryStage) {
		try {
			Parent mediaRoot = FXMLLoader.load(getClass().getResource("/application/main.fxml"));
			Scene mediaScene = new Scene(mediaRoot, 400, 550);
			mediaScene.getStylesheets().add(getClass().getResource("lite.css").toExternalForm());
			primaryStage.setResizable(true);
			primaryStage.setScene(mediaScene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}