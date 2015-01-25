package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.studentapp.model.User;
import se.chalmers.studentapp.util.ViewUtil;

public class LoginController {

	@FXML
	private TextField studentID;
	@FXML
	private Label errorMessage;
	
	public void login(){
		if(User.login(studentID.getText())){
			ViewUtil.switchPage("front", studentID);
		}else{
			errorMessage.setStyle("-fx-opacity:1;");
		}
	}
	
	public void gotoRegister(){
		ViewUtil.switchPage("register", studentID);
	}
	
	public void gotoForgotID(){
		ViewUtil.switchPage("forgotID", studentID);
	}
	
}
