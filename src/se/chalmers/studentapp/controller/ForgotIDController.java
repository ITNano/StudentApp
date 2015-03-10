package se.chalmers.studentapp.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import javax.swing.Timer;

import se.chalmers.studentapp.model.ModelFactory;
import se.chalmers.studentapp.util.ViewUtil;

public class ForgotIDController {

	@FXML
	private TextField nameField;
	@FXML
	private Button retrieveButton;
	@FXML
	private Label messageLabel;
	
	@FXML
	private Hyperlink link;
	
	public void nameChanged(){
		retrieveButton.setDisable(nameField.getText().length()==0);
	}
	
	public void retrieveID(){
		messageLabel.setStyle("-fx-opacity:1;");
		messageLabel.setText("Loading...");
		
		List<Integer> IDs = ModelFactory.getCurrentModel().getID(nameField.getText());
		if(IDs.isEmpty()){
			messageLabel.setText("Did not find any matches");
		}else if(IDs.size()==1){
			messageLabel.setText("Your ID is "+IDs.get(0));
		}else{
			StringBuilder possibleIDs = new StringBuilder();
			possibleIDs.append(IDs.get(0));
			for(int i = 1; i<IDs.size(); i++){
				possibleIDs.append(", ").append(IDs.get(i));
			}
			messageLabel.setText("Found several possible IDs: ("+possibleIDs.toString()+")");
		}
		Timer t = new Timer(4000, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				Platform.runLater(new Runnable(){
					@Override
					public void run() {
						FadeTransition ft = new FadeTransition(Duration.millis(2500), messageLabel);
						ft.setFromValue(1.0);
						ft.setToValue(0.0);
						ft.play();
					}
				});
			}
		});
		t.setRepeats(false);
		t.start();
	}
	
	public void gotoLogin(){
		ViewUtil.gotoLogin(link);
	}
	
}
