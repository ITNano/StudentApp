package se.chalmers.studentapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import se.chalmers.studentapp.model.ConcreteCourse.State;

public class ModelImpl implements IModel{
	
	private Connection conn;
	private User user;
	
	protected ModelImpl(){
		this.conn = Database.start();
		if(this.conn == null){
			System.out.println("Could not initiate model: Closing...");
			System.exit(0);
		}
	}

	@Override
	public boolean login(int studentID) {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT name, branch, program FROM StudentsFollowing WHERE studentID = ?");
			stmt.setInt(1, studentID);
			ResultSet result = stmt.executeQuery();
			if(result.next()){
				this.user = new User(studentID, result.getString(1), result.getString(2), result.getString(3));
				return true;
			}
			result.close();
			stmt.close();
		} catch (SQLException e) {
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e1) {}
			System.out.println("Error for login query: "+e.getMessage());
		}
		
		return false;
	}

	@Override
	public boolean isLoggedIn() {
		return user != null;
	}

	@Override
	public User getCurrentUser() {
		return user;
	}

	@Override
	public void logout() {
		user = null;
	}

	@Override
	public List<Integer> getID(String name) {
		List<Integer> list = new ArrayList<Integer>();
		if(name != null){
			PreparedStatement stmt = null;
			try {
				stmt = conn.prepareStatement("SELECT id FROM students WHERE name = ?");
				stmt.setString(1, name);
				ResultSet result = stmt.executeQuery();
				while(result.next()){
					list.add(result.getInt(1));
				}
			} catch (SQLException e) {
				try {
					if(stmt != null){
						stmt.close();
					}
				} catch (SQLException e1) {}
				System.out.println("Failed to insert student: "+e.getMessage());
			}
		}
		
		return list;
	}

	@Override
	public int registerUser(String name, Branch branch, Programme programme) {
		if(name != null && branch != null && programme != null){
			PreparedStatement stmt = null;
			try {
				//NOTE: This could have been done with a trigger (better solution)
				PreparedStatement stmtFetch = conn.prepareStatement("(SELECT MAX(id) FROM students)");
				ResultSet resultFetch = stmtFetch.executeQuery();
				int id = 1;
				if(resultFetch.next()){
					id = resultFetch.getInt(1)+1;
				}
				resultFetch.close();
				stmtFetch.close();
				
				stmt = conn.prepareStatement("INSERT INTO students VALUES(?, ?, ?, ?)");
				stmt.setInt(1, id);
				stmt.setString(2, name);
				stmt.setString(3, branch.getName());
				stmt.setString(4, programme.getName());
				if(stmt.executeUpdate()>0){
					return id;
				}
			} catch (SQLException e) {
				try {
					if(stmt != null){
						stmt.close();
					}
				} catch (SQLException e1) {}
				System.out.println("Failed to insert student: "+e.getMessage());
			}
		}

		return -1;
	}

	@Override
	public Response register(String courseName) throws NoUserException {
		if(!isLoggedIn()){
			throw new NoUserException();
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("INSERT INTO Registrations VALUES(?, ?, NULL)");
			stmt.setInt(1, user.getID());
			stmt.setString(2, courseName);
			if(stmt.executeUpdate()>0){
				return new Response(true, "The user was registrated to the course");
			}else{
				return new Response(false, "No data was changed.");
			}
		} catch (SQLException e) {
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e1) {}
			System.out.println("Failed to register student to course: "+e.getMessage());
			return new Response(false, "Error: "+e.getMessage());
		}
	}

	@Override
	public Response unregister(String courseName) throws NoUserException {
		if(!isLoggedIn()){
			throw new NoUserException();
		}
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("DELETE FROM Registrations WHERE student = ? AND course = ?");
			stmt.setInt(1, user.getID());
			stmt.setString(2, courseName);
			if(stmt.executeUpdate()>0){
				return new Response(true, "The user was removed from the course");
			}else{
				return new Response(false, "No data was changed.");
			}
		} catch (SQLException e) {
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e1) {}
			System.out.println("Failed to unregister student to course: "+e.getMessage());
			return new Response(false, "Error: "+e.getMessage());
		}
	}

	@Override
	public void updateCourseStatus(ConcreteCourse course) throws NoUserException {
		if(!isLoggedIn()){
			throw new NoUserException();
		}
		
		State newState = State.NONE;
		
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT status FROM Registrations WHERE student = ? AND course = ?");
			stmt.setInt(1, user.getID());
			stmt.setString(2, course.getCourse().getCode());
			ResultSet result = null;
			result = stmt.executeQuery();
			if(result.next()){
				String status = result.getString(1);
				if(status.equals("registered")){
					newState = State.REGISTERED;
				}else if(status.equals("waiting")){
					newState = State.QUEUED;
				}
			}else{
				stmt = conn.prepareStatement("SELECT count(*) FROM PassedCourses WHERE student = ? AND course = ?");
				stmt.setInt(1, user.getID());
				stmt.setString(2, course.getCourse().getCode());
				result = stmt.executeQuery();
				if(result.next() && result.getInt(1)>0){
					newState = State.COMPLETED;
				}else{
					stmt = conn.prepareStatement("SELECT count(*) FROM UnreadMandatory WHERE student = ? AND course = ?");
					stmt.setInt(1, user.getID());
					stmt.setString(2, course.getCourse().getCode());
					result = stmt.executeQuery();
					if(result.next() && result.getInt(1)>0){
						newState = State.MANDATORY;
					}
				}
			}
			
			course.setState(newState);
		} catch (SQLException e) {
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e1) {}
			System.out.println("Failed to update course status: "+e.getMessage());
		}
	}

	@Override
	public List<ConcreteCourse> getCourses() throws NoUserException {
		if(!isLoggedIn()){
			throw new NoUserException();
		}
		
		List<ConcreteCourse> courses = new ArrayList<ConcreteCourse>();
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement("SELECT code, name, credits FROM courses");
			ResultSet result = stmt.executeQuery();
			ConcreteCourse tmp;
			while(result.next()){
				tmp = new ConcreteCourse(new Course(result.getString(1), result.getString(2), result.getDouble(3)), State.NONE);
				updateCourseStatus(tmp);
				courses.add(tmp);
			}
		} catch (SQLException e) {
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e1) {}
			System.out.println("Failed to fetch courses: "+e.getMessage());
		}

		return courses;
	}

	@Override
	public List<Programme> getProgrammes() {
		List<Programme> programmes = new ArrayList<Programme>();
		PreparedStatement stmt = null, innerStmt = null;
		try {
			stmt = conn.prepareStatement("SELECT name FROM programs");
			ResultSet result = stmt.executeQuery();
			Programme tmp;
			while(result.next()){
				tmp = new Programme(result.getString(1));
				innerStmt = conn.prepareStatement("SELECT name FROM branches WHERE program = ?");
				innerStmt.setString(1, tmp.getName());
				ResultSet innerResult = innerStmt.executeQuery();
				while(innerResult.next()){
					tmp.addBranch(new Branch(innerResult.getString(1)));
				}
				programmes.add(tmp);
			}
		} catch (SQLException e) {
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e1) {}
			System.out.println("Failed to fetch courses: "+e.getMessage());
		}

		return programmes;
	}
}
