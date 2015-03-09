package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import se.chalmers.studentapp.model.IModel;
import se.chalmers.studentapp.model.ModelFactory;
import se.chalmers.studentapp.util.ViewUtil;

public class RegisterSuccessController {

	@FXML
	private Label studentID;
	@FXML
	private Button loginButton;
	
	public void initialize(){
		if(RegisterController.getStudentID()>=0){
			studentID.setText(RegisterController.getStudentID()+"");
		}else{
			studentID.setText("Error");
			loginButton.setText("Go back");
		}
	}
	
	public void login(){
		IModel model = ModelFactory.getCurrentModel();
		if(model.login(RegisterController.getStudentID())){
			ViewUtil.switchPage("front", studentID);
		}else{
			ViewUtil.switchPage("register", studentID);
		}
	}
	
}
