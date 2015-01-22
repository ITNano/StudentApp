package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import se.chalmers.studentapp.model.User;
import se.chalmers.studentapp.util.ViewUtil;

public class RegisterSuccessController {

	@FXML
	private Label studentID;
	@FXML
	private Button loginButton;
	
	public void initialize(){
		if(RegisterController.getStudentID() != null){
			studentID.setText(RegisterController.getStudentID());
		}else{
			studentID.setText("Error");
			loginButton.setText("Go back");
		}
	}
	
	public void login(){
		if(RegisterController.getStudentID() != null){
			User.login(RegisterController.getStudentID());
			ViewUtil.switchPage("front", studentID);
		}else{
			ViewUtil.switchPage("register", studentID);
		}
	}
	
}
