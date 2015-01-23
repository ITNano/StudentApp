package se.chalmers.studentapp.model;

public class Course {

	private String code;
	private String name;
	private double credits;
	
	public Course(String code, String name, double credits){
		this.code = code;
		this.name = name;
		this.credits = credits;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public double getCredits() {
		return credits;
	}
	
}
