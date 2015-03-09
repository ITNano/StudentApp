package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.studentapp.model.IModel;
import se.chalmers.studentapp.model.ModelFactory;
import se.chalmers.studentapp.util.ViewUtil;

public class LoginController {

	@FXML
	private TextField studentID;
	@FXML
	private Label errorMessage;
	
	public void login(){
		IModel model = ModelFactory.getCurrentModel();
		if(studentID.getText().matches("^\\d+$") && model.login(Integer.parseInt(studentID.getText()))){
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
