package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import se.chalmers.studentapp.util.ViewUtil;

public class ForgotIDController {

	@FXML
	private Hyperlink link;
	
	public void gotoLogin(){
		ViewUtil.gotoLogin(link);
	}
	
}
