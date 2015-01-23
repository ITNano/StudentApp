package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import se.chalmers.studentapp.model.Course;

public class CourseController {

	@FXML
	private Label code;
	@FXML
	private Label name;
	@FXML
	private Label credits;
	
	public void setCourse(Course course){
		code.setText(course.getCode());
		name.setText(course.getName());
		credits.setText(course.getCredits()+" HP");
	}
	
}
