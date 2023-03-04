package projects.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import projects.exception.DbException;

public class DbConnection {
	private static String HOST="localhost";
	private static String PASSWORD="projects";;
	private static int PORT=3306;
	private static String SCHEMA="projects";
	private static String USER="projects";;
		
	public static Connection getConnection() {
		String uri=String.format("jdbc:mysql://%s:%d/%s?user=%s&password=%s", HOST,PORT,SCHEMA,USER,PASSWORD);
		
		try{
			Connection con = DriverManager.getConnection(uri);
			System.out.println("Connection successful");
			return con;
		}
		catch(Exception e){
			System.out.println("Connection failure");
		throw new DbException(e);
		}
		
		
		
	}
	
	 public static Integer getLastInsertId(Connection conn, String table)
		      throws SQLException {
		    String sql = String.format("SELECT LAST_INSERT_ID() FROM %s", table);
		    try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
			        if (rs.next()) {
			        	return rs.getInt(1);
			        }
			        throw new SQLException(
				            "Unable to retrieve the primary key value. No result set!");
				      }
				    }
				  }

}
