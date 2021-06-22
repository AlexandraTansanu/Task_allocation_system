package SoftwareEngineeringPractice;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.Random;



import java.util.Collections;

public class TaskAllocationController  {
	private AdministratorUI theMainWindow;			
	private CaretakersUI caretakersMainWindow;
	
	private TaskAllocationDatabase db;
	
	HashMap<String, ArrayList<Task>> caretakers; 
	HashMap<String, ArrayList<Task>> adminLists;
	
	
	
	/* This is just to run the program */
	public static void main(String[] args) {
		TaskAllocationController controller = new TaskAllocationController();
	}
	
	
	/* Constructor. Creates the GUI and displays the data.
	 This does more than a constructor normally should. */
	public TaskAllocationController(){
		db = new TaskAllocationDatabase();
		
		adminLists = new HashMap<String, ArrayList<Task>>();
		loadAdminLists();
		
		caretakers = new HashMap<String, ArrayList<Task>>();
		loadCaretakers();
	    generateCaretakerTasks();
	    
	    
	    theMainWindow = new AdministratorUI(this);
	    theMainWindow.setVisible(true);
		caretakersMainWindow = new CaretakersUI(this);
		caretakersMainWindow.setVisible(true);
        
		for(String key: adminLists.keySet()) 
			theMainWindow.displayTableData(key);
		
		
		for(String key: caretakers.keySet()) 
			caretakersMainWindow.displayTableData(key);
	}
	
	/* Initialises HashMap holding all the names of the 
	 * shared lists with and the shared lists speciffically fot he AdministratorUI
	 * to be easier to delete, edit and add into the database.
	 */
	void loadAdminLists() {
		ArrayList<Task> oneOffTasks = new ArrayList<Task>();
		ArrayList<Task> routineTasks = new ArrayList<Task>();
		
		for(Task task: db.getTasksTableData())
			if(task.isRoutine() == true) 
				routineTasks.add(task);
			else
				oneOffTasks.add(task);
		
		adminLists.put("routine", routineTasks);
		adminLists.put("oneoff", oneOffTasks);
	}
	
	/* Initialise HashMap that maps each caretaker to its
	 * corresponding list. */
	void loadCaretakers() {
		for(Caretaker caretaker: db.getCaretakersTableData()) {
			caretakers.put(caretaker.getName(), new ArrayList<Task>());
		}
	}
	
	
	/* Will generate randomly each caretaker's list one every 24h.
	 * For now, that happens every time the program will run. */
	void generateCaretakerTasks() {
		/* Create a copy of routineTasks so Tasks can be deleted once they have been assigned */
		ArrayList<Task> routineCopy = new ArrayList<Task>();
		routineCopy.addAll(adminLists.get("routine"));
		
		
		/* Clear all the prious routine tasks */
		ArrayList<Task> whichList = null;
		
		for(String key: caretakers.keySet()) {
			whichList = caretakers.get(key);
			
			
			for(Task task: whichList) 
				if(adminLists.get("routine").contains(task)) 
					whichList.remove(task);
		}
		
		
		/* Populate each caretaker list */
		while(!routineCopy.isEmpty()) {
			for(String key: caretakers.keySet()) {
				whichList = caretakers.get(key);
				int routineSize = routineCopy.size();
				
				if(routineSize != 0) {
					/* Randomly choose one routine task to be assinged and remove it from the routine copy list */
					Random rand = new Random();
					Task randomTask = routineCopy.get(rand.nextInt(routineCopy.size()));
					
					whichList.add(randomTask);
					
					routineCopy.remove(randomTask);
				}
			}
		}
	}
	
	
	
	

	/* Deletes a specific task, and updates the JTable. */
	void deleteTask(String taskName, String key) {
		/* Delete the task from the database */
		db.deleteTask(taskName);
		
		/* Reconstruct the list from the database */
		loadAdminLists();
		
        /* Reload the table data */
		theMainWindow.displayTableData(key);
	}
	
	 void editTask(String taskName, String newName, int newDur, String newImp, String newFreq, String key) {
		 /* Edit the task from database */
		 ArrayList<Task> list = adminLists.get(key);
		 boolean shouldEdit = true;
		 
		 /* Check if the new name is the same as the old one. Else, the just edit the details*/
		 if(!taskName.equals(newName)) {
			 /* Check if there is another existent name in the list */
			 for (Task task: list) 
			     if(task.getName().equals(newName)) 
			    	 shouldEdit = false;
		 }
		 
		 if(shouldEdit == true) {
			 /* Update task in database */
	         db.editTask(taskName, newName, newDur, newImp, newFreq);
	         
	         /* Reconstruct the list from the database */
	 		loadAdminLists();
	 		
	 		/* Reload the table data */
	 		theMainWindow.displayTableData(key);	
	        }
		 else {
			 /* Throw an iserting error */
			 theMainWindow.throwInsertingError();
		 }
	} 
	
	void addTask(String taskName, int duration, String importance, String frequency, String key) { 
		/* Get the list based on the recieved key */
		ArrayList<Task> list = adminLists.get(key);
		
		boolean found = false;
		
		/* Check if the task already exists */
			for(Task task: list) 
				if(task.getName().equals(taskName))
					found = true;
			
			/* If it doesn't then  */
			if(found == false) {
				/* Add the task to the database */
				boolean routine = false;
				if(key.equals("routine"))
					routine = true;
				
				db.addTask(taskName, duration, importance, frequency, routine);
				
				/* Reconstruct the list from the database */
				loadAdminLists();
				
		        /* Reload the table data */
				theMainWindow.displayTableData(key);
			}
			else {  /* Notify the user */ 
				theMainWindow.throwInsertingError();
			} 
	}
	
	
	
	
	
	
	void completeTask(Task whichTask, String key) { 
		/* Get the caretaker list based on the received key */
		ArrayList<Task> whichList = caretakers.get(key);
		
		Task found = null;
		
		/* Find a reference to the task in the list, if any and delete it */
		for (Task task: whichList) 
	        if(task.equals(whichTask)) 
	        	found = task;
			
		if(found != null) 
				whichList.remove(found);
		
	        
		/* Send the data to the JTable. This reloads the entire damn
        table. Not efficient, but safe and simple. */
		caretakersMainWindow.displayTableData(key); 
	}
	
	

	
	
	
	void addOneOffTaskToCaretakerList(String nameToAdd, String key) { 
			/* Get the caretaker list based on the received key */
			ArrayList<Task> whichList = caretakers.get(key);
			
			Task found = null;
			
			/* Get the task object from one-off tasks */
			for(Task task: adminLists.get("oneoff")) 
				if(task.getName().equals(nameToAdd))
					found = task;
				
			if(found != null) {
					whichList.add(found);
					/* Delete the one off task from main list */
					db.deleteTask(found.getName());
					
					/* Reload the administrator lists */
					loadAdminLists();
				
					/* Send the data to the JTable. This reloads the entire damn
		         	table. Not efficient, but safe and simple. */
					caretakersMainWindow.displayTableData(key); 
					theMainWindow.displayTableData("oneoff"); 
			}
	}
	
	
	void tradeTask(String thisName, String thisTask, String otherName, String otherTask) {
		/* Get the list of the caretaker that trades based on the received key */
		ArrayList<Task> thisCaretaker = caretakers.get(thisName);
		
		/* Get the list of the caretaker whith whom it will be traded based on the received key */
		ArrayList<Task> otherCaretaker = null;
		
		if(caretakers.get(otherName) != null)
			otherCaretaker = caretakers.get(otherName);
		else // this means the "Back to One-off tasks" option has been chosen 
			otherCaretaker = adminLists.get("oneoff");
		
		
		
		
		
		Task taskToTrade = null;
		Task taskToReceive = null;
		
		for(Task task: thisCaretaker) 
			if(task.getName().equals(thisTask)) 
				taskToTrade = task;
			
		if(!otherCaretaker.equals(adminLists.get("oneoff"))) {
			for(Task task: otherCaretaker) 
				if(task.getName().equals(otherTask)) 
					taskToReceive = task;
				
			
			
			thisCaretaker.remove(taskToTrade);
			thisCaretaker.add(taskToReceive);
			
			otherCaretaker.remove(taskToReceive);
			otherCaretaker.add(taskToTrade);
			
			/* Send the data to the JTable. This reloads the entire damn
         	 table. Not efficient, but safe and simple. */
			caretakersMainWindow.displayTableData(thisName);
			caretakersMainWindow.displayTableData(otherName); 
		}
		else {
			if(taskToTrade.isRoutine() == false) { // if it actually a one-off task, then complete the trade
				thisCaretaker.remove(taskToTrade);
				otherCaretaker.add(taskToTrade);
				
				/* Send the data to the JTable. This reloads the entire damn
	         	 table. Not efficient, but safe and simple. */
				caretakersMainWindow.displayTableData(thisName); 
			}
			else { // it tries to make an illegal trade
				TradeErrorDialog error = new TradeErrorDialog();
				error.setVisible(true);
			}
		}
	}
}
