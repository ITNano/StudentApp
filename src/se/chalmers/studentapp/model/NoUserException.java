package se.chalmers.studentapp.model;

public class NoUserException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoUserException(){
		this("No user has been logged in");
	}
	
	public NoUserException(String msg){
		super(msg);
	}
	
}
