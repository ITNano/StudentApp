package se.chalmers.studentapp.model;

public class ModelFactory {

	private static IModel model;
	
	private ModelFactory(){}
	
	public static IModel replaceModel(){
		startModel();
		return model;
	}
	
	public static IModel getCurrentModel(){
		if(model == null){
			startModel();
		}
		
		return model;
	}
	
	private static void startModel(){
		model = new ModelImpl();
	}
	
}
