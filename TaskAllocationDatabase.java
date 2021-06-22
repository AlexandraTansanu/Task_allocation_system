package SoftwareEngineeringPractice;

import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is program specific and need to connect to an 
 * actual database.
 * 
 * @author Alexandra
 *
 */
public class TaskAllocationDatabase {
	private DBConnection database;
	
	public TaskAllocationDatabase() {
		database = new DBConnection();
		database.Connect("C:\\Users\\tansa\\eclipse-workspace\\TaskAllocationSystem\\tasks.db");
	}
	
	
	
	
	/* Method to delete a task */
	public void deleteTask(String taskName) {
		String sqlString = new String("DELETE FROM available_tasks WHERE name = '" + taskName + "';");
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/* Method to add a task */
	public void addTask(String name, int dur, String imp, String freq, boolean rout) {
		String sqlString = new String("INSERT INTO available_tasks (name,duration,importance,frequency,routine) VALUES('" + name + "'," + dur + 
									",'" + imp + "','" + freq + "','" + rout + "');");
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/* Method to edit a task */
	public void editTask(String name, String newName, int newDur, String newImp, String newFreq) {
		String sqlString = new String("UPDATE available_tasks SET name = '" + newName + "', duration = " + newDur + 
									", importance = '" + newImp + "', frequency = '" + newFreq + "' WHERE name = '" + name + "';");
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	
	
	
	
	
	/* Method to retrieve any table holding tasks from the database */
	public ArrayList<Task> getTasksTableData(){
		String sqlString = new String("SELECT name, duration, importance, frequency, routine FROM available_tasks;");
		
		ResultSet results = database.RunSQLQuery(sqlString);
		ArrayList<Task> tasks = new ArrayList<Task>();
		
		try {
			while(results.next()) {
				Task task = new Task();
				task.setName(results.getString(1));
				task.setDuration(results.getInt(2));
				task.setImportance(results.getString(3));
				task.setFrequency(results.getString(4));
				
				if(results.getString(5).equals("FALSE"))
					task.setRoutine(false);
				else 
					task.setRoutine(true);
				
				tasks.add(task);
			}
		}
		catch(SQLException ex) {
			 ex.printStackTrace();
		}
		
		return tasks;
	}

	public ArrayList<Caretaker> getCaretakersTableData() {
		String sqlString = new String("SELECT name, password FROM caretakers;");
		
		ResultSet results = database.RunSQLQuery(sqlString);
		ArrayList<Caretaker> caretakers = new ArrayList<Caretaker>();
		
		try {
			while(results.next()) {
				Caretaker caretaker = new Caretaker();
				caretaker.setName(results.getString(1));
				caretaker.setPassword(results.getInt(2));
				
				caretakers.add(caretaker);
			}
		}
		catch(SQLException ex) {
			 ex.printStackTrace();
		}
		
		return caretakers;
	}
}
