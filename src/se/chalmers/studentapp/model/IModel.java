package se.chalmers.studentapp.model;

import java.util.List;

public interface IModel {

	public boolean login(int studentID);
	public boolean isLoggedIn();
	public User getCurrentUser();
	public void logout();
	
	public int registerUser(String name, Branch branch, Programme programme);
	
	public Response register(String courseName) throws NoUserException;
	public Response unregister(String courseName) throws NoUserException;
	
	public List<ConcreteCourse> getCourses() throws NoUserException;
	public List<Programme> getProgrammes();
	public void updateCourseStatus(ConcreteCourse course) throws NoUserException;
	
	
	
	public class Response{
		
		private boolean success;
		private String msg;
		
		protected Response(boolean success, String msg){
			this.success = success;
			this.msg = msg;
		}
		
		public boolean didSucceed(){
			return success;
		}
		
		public String getMessage(){
			return msg;
		}
	}
	
}
