package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ErrorPopupController {

	@FXML
	private Label messageLabel;
	
	public void setMessage(String msg){
		messageLabel.setText(msg);
	}
	
	public void close(){
		((Stage)messageLabel.getScene().getWindow()).close();
	}
	
}
