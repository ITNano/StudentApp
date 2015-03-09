package se.chalmers.studentapp.model;

public class User {
	
	private int id;
	private String name;
	private String branch;
	private String programme;
	
	protected User(int id, String name, String branch, String programme){
		this.id = id;
		this.name = name;
		this.branch = branch;
		this.programme = programme;
	}
	
	public int getID(){
		return id;
	}
	
	public String getName() {
		return name;
	}

	public String getBranch() {
		return branch;
	}

	public String getProgramme() {
		return programme;
	}
}
