package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainApplication extends Application {
	
	private Stage primaryStage;
	
	public Stage getStage() {
		return primaryStage;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;

		try {
			//Parent root = FXMLLoader.load(getClass().getResource("../application/LoginGUI.fxml"));

			FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginGUI.fxml"));
			Parent root = loader.load();

			
			primaryStage.setTitle("Login");
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
