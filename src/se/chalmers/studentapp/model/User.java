package se.chalmers.studentapp.model;

public class User {
	
	private static User currentUser;
	
	private String name;
	private String branch;
	private String programme;
	
	private User(String userID){
		this.name = "Matz Larsson";
		this.branch = "Software Engineer";
		this.programme = "IT";
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

	
	public static boolean login(String userID){
		if(userID.length()>0){
			System.out.println("Logging in with userID "+userID);
			currentUser = new User(userID);
		}
		return userID.length()>0;
	}
	
	public static void logout(){
		currentUser = null;
	}
	
	public static User getCurrentUser(){
		return currentUser;
	}
}
