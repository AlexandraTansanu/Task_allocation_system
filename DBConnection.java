package SoftwareEngineeringPractice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

/**
 * This class contains boilerplate code and is not programm specific. 
 * It deals with any database connection.
 * 
 * @author Alexandra
 *
 */
public class DBConnection {
	private Connection conn;
	
	public DBConnection(){
		conn = null;
	}
	
	public boolean Connect(String filename) { //  the filename is the full path
		try {
            String url = "jdbc:sqlite:" + filename;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to " + filename + " has been established.");
        } 
        catch (SQLException e) {
            System.out.println("Failed to connect to " + filename);        	
            System.out.println(e.getMessage());
            return false;
        } 
        return true;
	}
	
	/**
	 * This is a method to insert, delete or edit data and 
	 * does not need to return any result set.
	 */
	public boolean RunSQL(String sql){
		  if(conn == null){
	            System.out.println("There is no database loaded. Cannot run SQL.");
	            return false;
		  }
		  
		  try {
			  Statement sqlStatement = conn.createStatement();
			  sqlStatement.execute(sql);
		  }
		  catch(SQLException e){
			  System.out.println("Failed to execute " + sql);
	          System.out.println(e.getMessage());
	          return false;
		  }
		  
		  return true;
	}

	/**
	 * This is a method to query the database and 
	 * does need to return a result set. 
	 */
	public ResultSet RunSQLQuery(String sql){
		  ResultSet result;
		  
		  if(conn == null){
	           System.out.println("There is no database loaded. Cannot run SQL.");
	           return null;
		  }
		  
		  try {
			  Statement sqlStatement = conn.createStatement();
			  result = sqlStatement.executeQuery(sql);
	      }
		  catch(SQLException e){
			  System.out.println("Failed to execute " + sql);
	          System.out.println(e.getMessage());
	          return null;
	      }
		  
		  return result;
	}

}
