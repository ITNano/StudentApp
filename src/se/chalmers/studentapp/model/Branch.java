package se.chalmers.studentapp.model;

public class Branch {

	private String name;
	
	protected Branch(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	public String toString(){
		return getName();
	}
	
}
