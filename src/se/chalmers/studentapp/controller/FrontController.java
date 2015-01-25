package se.chalmers.studentapp.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import se.chalmers.studentapp.controller.CourseController.State;
import se.chalmers.studentapp.model.Course;
import se.chalmers.studentapp.model.User;
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
	
	private boolean loaded = false;
	
	public void initialize(){
		User user = User.getCurrentUser();
		if(user != null){
			studentName.setText(user.getName());
			studentProgramme.setText(user.getBranch()+" ("+user.getProgramme()+")");
		}
		
		addCourse(new Course("TDA357", "Databases and stuff", 7.5), State.COMPLETED, true);
		addCourse(new Course("MVE051", "Matematisk statistik", 7.5), State.REGISTERED, true);
		addCourse(new Course("BLA098", "Kurs om blablabla", 7.5), State.QUEUED, false);
		addCourse(new Course("KON433", "Kontroversiell kurs", 7.5), State.MANDATORY, false);
		addCourse(new Course("TRI322", "Tripplekvadratisk mojs", 7.5), State.MANDATORY, false);
		studentName.requestFocus();
	}
	
	private void addCourse(Course course, State state, boolean active){
		VBox box = (active?activeCoursePanel:requiredCoursePanel);
		Node courseNode = ViewUtil.getNodeFromFXML("course", new ViewUtil.Function(){
			public <T> void perform(T controller){
				if(controller instanceof CourseController){
					((CourseController)controller).setCourse(course, state);
				}
			}
		});
		
		if(box.getChildren().size()<=1){
			courseNode.getStyleClass().add("first");
		}
		box.getChildren().add(courseNode);
	}
	
	public void courseSearchChanged(){
		//De-focus the search input field when page is loading
		if(!loaded){
			loaded = true;
			activeCoursePanel.requestFocus();
		}else{
			System.out.println("Searching for '"+courseSearchInput.getText()+"'");
		}
	}
	
}
