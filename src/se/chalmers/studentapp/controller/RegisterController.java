package se.chalmers.studentapp.controller;

import se.chalmers.studentapp.util.ViewUtil;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class RegisterController {
	
	private static String studentID;

	@FXML
	private TextField name;
	@FXML
	private ComboBox<String> programme;
	
	public void register(){
		System.out.println("Registering...");
		studentID = "1038756";
		ViewUtil.switchPage("register_success", name);
	}
	
	public void gotoLogin(){
		ViewUtil.switchPage("login", name);
	}
	
	public static String getStudentID(){
		return studentID;
	}
}
