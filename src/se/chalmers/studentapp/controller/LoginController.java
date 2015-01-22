package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import se.chalmers.studentapp.model.User;
import se.chalmers.studentapp.util.ViewUtil;

public class LoginController {

	@FXML
	private TextField studentID;
	
	public void login(){
		if(User.login(studentID.getText())){
			ViewUtil.switchPage("front", studentID);
		}
	}
	
	public void gotoRegister(){
		ViewUtil.switchPage("register", studentID);
	}
	
	public void gotoForgotID(){
		ViewUtil.switchPage("forgotID", studentID);
	}
	
}
