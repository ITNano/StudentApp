package se.chalmers.studentapp.util;

import java.io.IOException;

import se.chalmers.studentapp.controller.ErrorPopupController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	
	public static void gotoLogin(Node stageChild){
		switchPage("login", stageChild);
	}
	
	public static void showPopup(String name, Node stageChild, Function function){
		try{
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        dialog.initStyle(StageStyle.UTILITY);
	        dialog.initOwner(stageChild.getScene().getWindow());
	        
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(ViewUtil.class.getClassLoader().getResource(name+".fxml"));
	        Parent root = loader.load();
	        
	        if(function != null){
	        	function.perform(loader.getController());
	        }
	        
	        dialog.setScene(new Scene(root));
	        dialog.show();
		}catch(IOException ioe){
			System.out.println("Could not open new message window: "+ioe.getMessage());
		}
	}
	
	public static void showError(Node stageChild, String msg){
		Function func = new Function(){
			@Override
			public <T> void perform(T controller) {
				if(controller instanceof ErrorPopupController){
					((ErrorPopupController)controller).setMessage(msg);
				}
			}
		};
		showPopup("error", stageChild, func);
	}
	
	public interface Function{
		public <T> void perform(T controller);
	}
	
}
