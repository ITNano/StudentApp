package se.chalmers.studentapp.model;

public class User {
	
	public static boolean login(String userID){
		System.out.println("Logging in with userID "+userID);
		return userID.length()>0;
	}
	
}
