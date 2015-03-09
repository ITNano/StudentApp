package se.chalmers.studentapp.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import se.chalmers.studentapp.model.ConcreteCourse;
import se.chalmers.studentapp.model.ConcreteCourse.State;
import se.chalmers.studentapp.model.IModel;
import se.chalmers.studentapp.model.ModelFactory;
import se.chalmers.studentapp.model.NoUserException;
import se.chalmers.studentapp.model.User;
import se.chalmers.studentapp.util.ViewUtil;

public class FrontController implements CourseChangeListener{

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
		IModel model = ModelFactory.getCurrentModel();
		if(model.isLoggedIn()){
			User user = model.getCurrentUser();
			if(user != null){
				studentName.setText(user.getName());
				studentProgramme.setText(user.getBranch()+" ("+user.getProgramme()+")");
			}

			try {
				List<ConcreteCourse> courses = model.getCourses();
				Collections.sort(courses, new Comparator<ConcreteCourse>(){
					@Override
					public int compare(ConcreteCourse c1, ConcreteCourse c2) {
						return -c1.compareTo(c2);
					}
				});
				for(ConcreteCourse c : courses){
					addCourse(c, true);
				}
			} catch (NoUserException e) {
				//User not logged in, switch to login
				ViewUtil.gotoLogin(studentName);
			}
			studentName.requestFocus();
		}else{
			ViewUtil.gotoLogin(studentName);
		}
	}
	
	private void addCourse(ConcreteCourse concreteCourse, boolean ignoreSort){
		if(concreteCourse != null){
			State state = concreteCourse.getState();
			System.out.println("Adding course with state: "+state.name());
			VBox box = (state==State.COMPLETED||state==State.REGISTERED?activeCoursePanel:requiredCoursePanel);
			
			final CourseChangeListener listener = this;
			Node courseNode = ViewUtil.getNodeFromFXML("course", new ViewUtil.Function(){
				public <T> void perform(T controller){
					if(controller instanceof CourseController){
						((CourseController)controller).setCourse(concreteCourse);
						((CourseController)controller).setChangeListener(listener);
					}
				}
			});
			
			int index = 0;
			if(!ignoreSort){
				Node statusNode;
				State tmpState;
				for(Node node : box.getChildren()){
					statusNode = node.lookup("#status");
					if(statusNode != null && statusNode instanceof Label){
						tmpState = CourseController.getState(((Label)statusNode).getText());
						if(tmpState != null && tmpState.getNum()>=concreteCourse.getState().getNum()){
							break;
						}
					}
					index++;
				}
			}else{
				index = box.getChildren().size();
			}
			
			if(index == 1){
				courseNode.getStyleClass().add("first");
				if(box.getChildren().size()>1){
					box.getChildren().get(1).getStyleClass().remove("first");
				}
			}
			
			if(index>=box.getChildren().size()){
				box.getChildren().add(courseNode);
			}else{
				box.getChildren().add(index, courseNode);
			}
		}
	}
	
	public void removeCourse(Node course){
		if(activeCoursePanel.getChildren().size()>2 && activeCoursePanel.getChildren().get(0).equals(course)){
			activeCoursePanel.getChildren().get(1).getStyleClass().add("first");
		}
		activeCoursePanel.getChildren().remove(course);
		if(requiredCoursePanel.getChildren().size()>2 && requiredCoursePanel.getChildren().get(0).equals(course)){
			requiredCoursePanel.getChildren().get(1).getStyleClass().add("first");
		}
		requiredCoursePanel.getChildren().remove(course);
	}
	
	public void resetCourses(){
		activeCoursePanel.getChildren().clear();
		requiredCoursePanel.getChildren().clear();
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

	@Override
	public void courseChanged(CourseController controller) {
		System.out.println("COURSE CHANGED: "+controller.getConcreteCourse().getCourse().getCode());
		removeCourse(controller.getRoot());
		addCourse(controller.getConcreteCourse(), false);
	}
	
}
