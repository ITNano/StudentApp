package se.chalmers.studentapp.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	private static final String username = "vtda357_025";
	private static final String password = "x7a4b516";
	private static final String serverURL = "db.student.chalmers.se:1521/kingu.ita.chalmers.se";

	private static Connection conn;	
	
	protected static Connection start(){
		try {
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			return DriverManager.getConnection("jdbc:oracle:thin:@"+serverURL, username, password);
		} catch (SQLException e) {
			System.out.println("Could not start DB: "+e.getMessage()+" ["+e.getSQLState()+"]");
			return null;
		}
	}
	
	public static void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Could not close DB: "+e.getMessage());
		}
	}
}
