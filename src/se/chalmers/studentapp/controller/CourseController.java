package se.chalmers.studentapp.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import se.chalmers.studentapp.model.Course;

public class CourseController {
	
	private static CourseController inst = new CourseController();
	private static StateStamp[] stamps = {inst.new StateStamp("completed", "#5fd359"),
										  inst.new StateStamp("mandatory", "#d61b0a"),
										  inst.new StateStamp("queued", "#ed5e00"),
										  inst.new StateStamp("registered", "#ffdb00", Color.BLACK)};

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
	
	public void setCourse(Course course, State state){
		code.setText(course.getCode());
		name.setText(course.getName());
		credits.setText(course.getCredits()+" HP");
		stateLabel.setText(stamps[state.getNum()].getLabel());
		stateLabel.setStyle(stateLabel.getStyle()+"-fx-background-color:"+stamps[state.getNum()].getColor());
		stateLabel.textFillProperty().set(stamps[state.getNum()].getTextColor());
		
		final ContextMenu contextMenu = getContextMenu(state);
	    courseRoot.setOnMouseClicked(new EventHandler<MouseEvent>() {
	        @Override
	        public void handle(MouseEvent mouseEvent) {
	            contextMenu.show(courseRoot, mouseEvent.getScreenX(), mouseEvent.getScreenY());
	        }
	    });
	}

	private void register(){
		System.out.println("Registering "+code.getText());
	}
	
	private void unregister(){
		System.out.println("Unregistering "+code.getText());
	}
	
	private void unqueue(){
		System.out.println("Leaving queue for "+code.getText());
	}
	
	private ContextMenu getContextMenu(State state){
		ContextMenu menu = new ContextMenu();
		switch(state){
			case COMPLETED:		break;
			case REGISTERED:	MenuItem unregister = new MenuItem("Unregister");
								unregister.setOnAction((ActionEvent event) -> {unregister();});
								menu.getItems().add(unregister);
								break;
			case MANDATORY:		MenuItem register = new MenuItem("Register");
								register.setOnAction((ActionEvent event) -> {register();});
								menu.getItems().add(register);
								break;
			case QUEUED:		MenuItem unqueue = new MenuItem("Leave queue");
								unqueue.setOnAction((ActionEvent event) -> {unqueue();});
								menu.getItems().add(unqueue);
								break;
			default:			System.out.println("WARNING: UNIMPLEMENTED STATE!");
		}
		
		return menu;
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
	
	public enum State{
		COMPLETED(0),
		MANDATORY(1),
		QUEUED(2),
		REGISTERED(3);
		
		private int num;
		private State(int num){
			this.num = num;
		}
		public int getNum(){
			return num;
		}
	}
}
