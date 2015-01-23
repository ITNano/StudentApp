package se.chalmers.studentapp.util;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewUtil {

	public static Node getNodeFromFXML(String name){
		return getNodeFromFXML(name, null);
	}
	
	public static Node getNodeFromFXML(String name, Function controllerFunc){
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(ViewUtil.class.getClassLoader().getResource(name+".fxml"));
			Node node = loader.load();
			if(controllerFunc != null){
				controllerFunc.perform(loader.getController());
			}
			
			return node;
		} catch (IOException ioe) {
			System.out.println("Could not load "+name+" layout: "+ioe.getMessage());
			return null;
		}
	}
	
	public static void switchPage(String name, Node stageChild){
		Stage stage = (Stage)stageChild.getScene().getWindow();
        stage.setScene(new Scene((Parent)ViewUtil.getNodeFromFXML(name)));
	}
	
	public interface Function{
		public <T> void perform(T controller);
	}
	
}
