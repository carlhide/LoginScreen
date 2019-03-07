package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController {
	
	public MainController() {
		
	}
	
	@FXML
	private Label usr_lbl;
	
	@FXML
	public void initialize() {
		usr_lbl.setText(Context.username);
	}
	

}
