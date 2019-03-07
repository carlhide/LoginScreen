package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField usr_txt;
	@FXML
	private PasswordField pw_txt;
	
	private DatabaseQueries dbq;
	private Database db;
	private MainApplication ma = new MainApplication();

	@FXML
	public void initialize() throws IOException{
		db = new Database("localhost", "mydb", "root", "password");
		dbq = new DatabaseQueries(db);
	}

	@FXML
	public void login_btn_onClick(ActionEvent e) throws IOException {
		String username = usr_txt.getText();
		String password = pw_txt.getText();	
		Context.username = username;
		System.out.println(username + password);
		try {
			String recievedPassword = db.executeQuery("SELECT Password from users WHERE Username = '" + username + "'");
			System.out.println(recievedPassword);
			if(recievedPassword.equals(password)) {
				System.out.println("Succesful login");
				Stage stage = (Stage) usr_txt.getScene().getWindow();
				Parent root = FXMLLoader.load(getClass().getResource("MainGUI.fxml"));
				stage.setScene(new Scene(root));
				stage.centerOnScreen();
				stage.setTitle("Sapply - Administrator");
			}else {
				System.out.println("Wrong username or password");
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
