package se.chalmers.studentapp.view;

import se.chalmers.studentapp.util.ViewUtil;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainView extends Application{

	public static void show(){
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
        stage.setTitle("Student Terminal");
        stage.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("icon.png")));
        stage.setScene(new Scene((Parent)ViewUtil.getNodeFromFXML("login")));
        stage.show();
	}
	
}
