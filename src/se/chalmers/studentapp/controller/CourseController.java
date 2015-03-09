package se.chalmers.studentapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import se.chalmers.studentapp.model.ConcreteCourse;
import se.chalmers.studentapp.model.ConcreteCourse.State;
import se.chalmers.studentapp.model.Course;
import se.chalmers.studentapp.model.IModel;
import se.chalmers.studentapp.model.IModel.Response;
import se.chalmers.studentapp.model.ModelFactory;
import se.chalmers.studentapp.model.NoUserException;
import se.chalmers.studentapp.util.ViewUtil;

public class CourseController {
	
	private static CourseController inst = new CourseController();
	private static StateStamp[] stamps = {inst.new StateStamp("completed", "#5fd359"),
		  								  inst.new StateStamp("registered", "#ffdb00", Color.BLACK),
										  inst.new StateStamp("queued", "#ed5e00"),
										  inst.new StateStamp("mandatory", "#d61b0a"),
										  inst.new StateStamp("optional", "#000000"),};

	private ConcreteCourse course;
	private CourseChangeListener listener;
	
	@FXML
	private Pane courseRoot;
	@FXML
	private Label code;
	@FXML
	private Label name;
	@FXML
	private Label credits;
	@FXML
	private Label stateLabel;
	
	public void setCourse(ConcreteCourse concreteCourse){
		this.course = concreteCourse;
		Course course = concreteCourse.getCourse();
		State state = concreteCourse.getState();
		code.setText(course.getCode());
		name.setText(course.getName());
		credits.setText(course.getCredits()+" HP");
		stateLabel.setText(stamps[state.getNum()].getLabel());
		stateLabel.setStyle(stateLabel.getStyle()+"-fx-background-color:"+stamps[state.getNum()].getColor());
		stateLabel.textFillProperty().set(stamps[state.getNum()].getTextColor());
		
		final ContextMenu contextMenu = getContextMenu(state);
	    courseRoot.setOnMouseClicked((MouseEvent event) -> {
        	if(event.getButton().equals(MouseButton.SECONDARY)){
        		contextMenu.show(courseRoot, event.getScreenX(), event.getScreenY());
        	}else{
        		contextMenu.hide();
        	}
	    });
	}
	
	public void setChangeListener(CourseChangeListener listener){
		this.listener = listener;
	}

	private void register(){
		try {
			IModel model = ModelFactory.getCurrentModel();
			Response response = model.register(course.getCourse().getCode());
			if(response.didSucceed()){
				model.updateCourseStatus(course);
				if(listener != null){
					listener.courseChanged(this);
				}
			}else{
				ViewUtil.showError(courseRoot, response.getMessage());
			}
		} catch (NoUserException e) {
			ViewUtil.gotoLogin(courseRoot);
		}
	}
	
	private void unregister(){
		try {
			IModel model = ModelFactory.getCurrentModel();
			Response response = model.unregister(course.getCourse().getCode());
			if(response.didSucceed()){
				model.updateCourseStatus(course);
				if(listener != null){
					listener.courseChanged(this);
				}
			}else{
				ViewUtil.showError(courseRoot, response.getMessage());
			}
		} catch (NoUserException e) {
			ViewUtil.gotoLogin(courseRoot);
		}
	}
	
	private ContextMenu getContextMenu(State state){
		ContextMenu menu = new ContextMenu();
		switch(state){
			case COMPLETED:		break;
			case REGISTERED:	MenuItem unregister = new MenuItem("Unregister");
								unregister.setOnAction((ActionEvent event) -> {unregister();});
								menu.getItems().add(unregister);
								break;
			case MANDATORY: case NONE:
								MenuItem register = new MenuItem("Register");
								register.setOnAction((ActionEvent event) -> {register();});
								menu.getItems().add(register);
								break;
			case QUEUED:		MenuItem unqueue = new MenuItem("Leave queue");
								unqueue.setOnAction((ActionEvent event) -> {unregister();});
								menu.getItems().add(unqueue);
								break;
			default:			System.out.println("WARNING: UNIMPLEMENTED STATE!");
		}
		
		return menu;
	}
	
	public ConcreteCourse getConcreteCourse(){
		return course;
	}
	
	public Node getRoot(){
		return courseRoot;
	}
	
	public static State getState(String s){
		switch(s){
			case "completed":	return State.COMPLETED;
			case "registered":	return State.REGISTERED;
			case "queued":		return State.QUEUED;
			case "mandatory":	return State.MANDATORY;
			case "optional":	return State.NONE;
			default:			return State.NONE;
		}
	}
	
	
	private class StateStamp{
		private String label;
		private String color;
		private Color textColor;
		
		public StateStamp(String label, String color){
			this(label, color, Color.WHITE);
		}
		public StateStamp(String label, String color, Color textColor){
			this.label = label;
			this.color = color;
			this.textColor = textColor;
		}
		public String getLabel(){
			return label;
		}
		public String getColor(){
			return color;
		}
		public Color getTextColor(){
			return textColor;
		}
	}
}
