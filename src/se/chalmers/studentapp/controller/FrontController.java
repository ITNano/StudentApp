package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import se.chalmers.studentapp.controller.CourseController.State;
import se.chalmers.studentapp.model.Course;
import se.chalmers.studentapp.util.ViewUtil;

public class FrontController {

	@FXML
	private Label studentName;
	@FXML
	private Label studentProgramme;
	@FXML
	private TextField courseSearchInput;
	
	@FXML
	private VBox activeCoursePanel;
	@FXML
	private VBox requiredCoursePanel;
	
	public void initialize(){
		addCourse(new Course("TDA357", "Databases and stuff", 7.5), State.COMPLETED, true);
		addCourse(new Course("MVE051", "Matematisk statistik", 7.5), State.REGISTERED, true);
		addCourse(new Course("BLA098", "Kurs om blablabla", 7.5), State.QUEUED, false);
		addCourse(new Course("KON433", "Kontroversiell kurs", 7.5), State.MANDATORY, false);
		addCourse(new Course("TRI322", "Tripplekvadratisk mojs", 7.5), State.MANDATORY, false);
	}
	
	private void addCourse(Course course, State state, boolean active){
		VBox box = (active?activeCoursePanel:requiredCoursePanel);
		box.getChildren().add(ViewUtil.getNodeFromFXML("course", new ViewUtil.Function(){
			public <T> void perform(T controller){
				if(controller instanceof CourseController){
					((CourseController)controller).setCourse(course, state);
				}
			}
		}));
	}
	
	public void courseSearchChanged(){
		System.out.println("Searching for '"+courseSearchInput.getText()+"'");
	}
	
}
