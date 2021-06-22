package SoftwareEngineeringPractice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.ListSelectionModel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdministratorUI extends JFrame {
	private TaskAllocationController theTaskHandler; // reference to the controller

	private JPanel contentPane;
	private HashMap<String, JTable> tables;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdministratorUI frame = new AdministratorUI(null); 
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdministratorUI(TaskAllocationController taskHandler) { 
		this.theTaskHandler = taskHandler; // initialised reference to the controller 
		this.tables = new HashMap<String, JTable>();
		
		setTitle("Task Allocation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1050, 647);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnLoginMenu = new JMenu("Logout");
		menuBar.add(mnLoginMenu);
		
		JMenu mnReportsMenu = new JMenu("Reports");
		menuBar.add(mnReportsMenu);
		
		JMenuItem mntmShowReportsItem = new JMenuItem("Show Reports");
		mnReportsMenu.add(mntmShowReportsItem);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new LineBorder(Color.GRAY, 2));
		contentPane.add(titlePanel, BorderLayout.NORTH);
		
		JLabel lblTitleLabel = new JLabel("Administrator Workspace");
		lblTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titlePanel.add(lblTitleLabel);
		
		JPanel listsPanel = new JPanel();
		contentPane.add(listsPanel, BorderLayout.CENTER);
		listsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 5));
		
		
		/* Dynamically create the administartor lists */
		for(String key: theTaskHandler.adminLists.keySet()) {
			JPanel panel = new JPanel();
			listsPanel.add(panel);
			panel.setLayout(new BorderLayout(0, 0));
			
			JPanel titlepanel = new JPanel();
			panel.add(titlepanel, BorderLayout.NORTH);
			
			/* Set the corresponding table based on the key */
			String title = "";
			if(key.equals("oneoff"))
				title = "One Off Tasks";
			else 
				title = "Routine Tasks";
			
			JLabel lbllabel = new JLabel(title);
			lbllabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
			titlepanel.add(lbllabel);
			
			JScrollPane scrollPane = new JScrollPane();
			panel.add(scrollPane, BorderLayout.CENTER);
			
			JTable table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Name", "Duration (min.)", "Importance", "Frequency"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setPreferredWidth(50);
			table.getColumnModel().getColumn(2).setPreferredWidth(65);
			table.getColumnModel().getColumn(3).setPreferredWidth(60);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(table);
			
			/* Map the corresponding table to the key */
			tables.put(key, table);
			
			JPanel buttonsPanel = new JPanel();
			panel.add(buttonsPanel, BorderLayout.SOUTH);
			
			JButton btncreateButton = new JButton("Create Task");
			btncreateButton.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					/* Show dialog */
					CreateTaskDialog dialog = new CreateTaskDialog();
					dialog.setVisible(true);
					
					/* Get the name from the dialog */
					String newTaskName = dialog.nameTextField.getText();
					
					/* If the name is empty, then Cancel was probably pressed */
					if(!newTaskName.isEmpty()){
						/* Get the duration from the spinner */
						int dur = (Integer) dialog.durationSpinner.getValue(); // if the user sends no value or 0, by default will hold 1
						
						/* Get the importance and frequency from the combo boxes */
						String imp = (String)dialog.importanceComboBox.getSelectedItem();
						String freq = (String)dialog.frequencyComboBox.getSelectedItem();
						
						theTaskHandler.addTask(newTaskName, dur, imp, freq, key);	
					}
				}
			});
			btncreateButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			buttonsPanel.add(btncreateButton);
			
			JButton btneditButton = new JButton("Edit Task");
			btneditButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/* Find the task in the selected row. */
					int rowToDelete = table.getSelectedRow();
					
					/* If row is selected, retrieve the values it holds */
					if(rowToDelete >= 0) {
						String nameToEdit = (String) table.getValueAt(rowToDelete, 0);
						int durToEdit = (Integer) table.getValueAt(rowToDelete, 1);
						String impToEdit = (String) table.getValueAt(rowToDelete, 2);
						String freqToEdit = (String) table.getValueAt(rowToDelete, 3);
						
						/* Show populated dialog with previous values */
						EditTaskDialog dialog = new EditTaskDialog(nameToEdit, durToEdit, impToEdit, freqToEdit);
						dialog.setVisible(true);
						
						/* Take the new inputed value for the name */
						String newName = dialog.nameTextField.getText();
						
						/* If the name is not empty, continue to retrieve the new values */
						if(!newName.isEmpty()){
							/* Get the duration from the spinner */
							int newDur = (Integer) dialog.durationSpinner.getValue(); // if the user sends no value or 0, by default will hold the original value
							
							/* Get the importance and frequency from the combo boxes */
							String newImp = (String)dialog.importanceComboBox.getSelectedItem();
							String newFreq = (String)dialog.frequencyComboBox.getSelectedItem();
							
							theTaskHandler.editTask(nameToEdit, newName, newDur, newImp, newFreq, key);	
						}
					}
				}
			});
			btneditButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			buttonsPanel.add(btneditButton);
			
			JButton btndeleteButton = new JButton("Delete Task");
			btndeleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/* Find the task in the selected row. */
					int rowToDelete = table.getSelectedRow();
					
					/* If row is selected, delete it */
					if(rowToDelete >= 0) {
						String nameToDelete = (String) table.getValueAt(rowToDelete, 0);
						
						theTaskHandler.deleteTask(nameToDelete, key);
					}
				}
			});
			btndeleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			buttonsPanel.add(btndeleteButton);
		}
	}
	
	
	
	
	
	/* Reloading the tables */
	void displayTableData(String key) { 
		/* Get the list based on the received key */
		ArrayList<Task> tableData = theTaskHandler.adminLists.get(key);
		
		/* Get the table based on the received key */
		DefaultTableModel tableModel = (DefaultTableModel) tables.get(key).getModel();
		
		if((tableModel != null) && (tableData != null)) {
				/* Empty the existing data */
				tableModel.setRowCount(0);
				
				/* Sort the list */
				Collections.sort(tableData);
				
			    /* Reinsert data into the table */
				for(Task task: tableData) {
					tableModel.addRow(new Object[] {task.getName(), task.getDuration(), task.getImportance(), task.getFrequency()});
				}    
			}
	}
	
	
	
	
	
	/* Notify the user if the task created already exists */
	void throwInsertingError() {
		/* Show Dialog */
		InsertingErrorDialog dialog = new InsertingErrorDialog();
		dialog.setVisible(true); 
	} 
}
