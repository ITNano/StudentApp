package se.chalmers.studentapp.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import se.chalmers.studentapp.model.Branch;
import se.chalmers.studentapp.model.IModel;
import se.chalmers.studentapp.model.ModelFactory;
import se.chalmers.studentapp.model.Programme;
import se.chalmers.studentapp.util.ViewUtil;

public class RegisterController {
	
	private static int studentID = -1;

	@FXML
	private TextField name;
	@FXML
	private ComboBox<Programme> programme;
	@FXML
	private ComboBox<Branch> branch;
	@FXML
	private Button registerButton;
	
	public void initialize(){
		List<Programme> programmes = ModelFactory.getCurrentModel().getProgrammes();
		programme.getItems().addAll(programmes);
		programme.getSelectionModel().select(0);
	}
	
	public void register(){
		IModel model = ModelFactory.getCurrentModel();
		studentID = model.registerUser(name.getText(), branch.getValue(), programme.getValue());
		ViewUtil.switchPage("register_success", name);
	}
	
	public void nameChanged(){
		registerButton.setDisable(name.getText().length()==0);
	}
	
	public void programmeChanged(){
		branch.getItems().clear();
		branch.getItems().addAll(programme.getValue().getBranches());
		branch.getSelectionModel().select(0);
	}
	
	public void gotoLogin(){
		ViewUtil.gotoLogin(name);
	}
	
	public static int getStudentID(){
		return studentID;
	}
}
