package SoftwareEngineeringPractice;

import java.util.HashMap;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JLayeredPane;
import java.awt.Font;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.ListSelectionModel;
import java.awt.CardLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.ScrollPane;

public class CaretakersUI extends JFrame {
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
					CaretakersUI frame = new CaretakersUI(null);
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
	public CaretakersUI(TaskAllocationController taskHandler) {
		this.theTaskHandler = taskHandler; // initialised reference to the controller
		this.tables = new HashMap<String, JTable>();
		
		setTitle("Task Allocation System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1063, 993);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 15));
		setContentPane(contentPane);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBorder(new LineBorder(Color.GRAY, 2));
		contentPane.add(titlePanel, BorderLayout.NORTH);
		
		JLabel lblTitleLabel = new JLabel("Caretakers Workspace - daily scheduled routine tasks");
		lblTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 30));
		titlePanel.add(lblTitleLabel);
		
		JPanel buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.SOUTH);
		
		JButton btnTradeButton = new JButton("Trade task");
		btnTradeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Create Dialog */
				TradeTaskDialog dialog = new TradeTaskDialog(theTaskHandler.caretakers);
				dialog.setVisible(true);
				
				String thisName = (String) dialog.nameComboBox.getSelectedItem();
				String thisTask = (String) dialog.taskComboBox.getSelectedItem();
				
				String otherName = (String) dialog.otherNameComboBox.getSelectedItem();
				String otherTask = (String) dialog.otherTaskComboBox.getSelectedItem();
				
				if(dialog.readyToTrade == true)
					theTaskHandler.tradeTask(thisName, thisTask, otherName, otherTask);
			}
		});
	
		btnTradeButton.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		buttonPanel.add(btnTradeButton);
		
		ScrollPane listsScrollPane = new ScrollPane();
		listsScrollPane.setBounds(0, 0, 100, 100);
		contentPane.add(listsScrollPane);
		
		JPanel listsPanel = new JPanel();
		listsScrollPane.add(listsPanel);
		listsPanel.setLayout(new GridLayout(0, 3, 40, 20));
		
		
		/* Dynamically generate each caretaker's list */
		for(String key: theTaskHandler.caretakers.keySet()) {
			/* Create a JPanel to hold everything */
			JPanel caretakerPanel = new JPanel();
			listsPanel.add(caretakerPanel);
			caretakerPanel.setLayout(new BorderLayout(0, 0));
			
			JPanel caretakerTitlePanel = new JPanel();
			caretakerPanel.add(caretakerTitlePanel, BorderLayout.NORTH);
			
			JLabel lblNameLabel = new JLabel(key);
			lblNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
			caretakerTitlePanel.add(lblNameLabel);
			
			JScrollPane scrollPane = new JScrollPane();
			caretakerPanel.add(scrollPane, BorderLayout.CENTER);
			
			JTable table = new JTable();
			table.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Name", "Duration (min.)", "Importance", "Frequency ", "Is routine?"
				}
			) {
				boolean[] columnEditables = new boolean[] {
					false, false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			});
			
			/* Map the corresponding table to the key */
			tables.put(key, table);
			
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			scrollPane.setViewportView(table);
			
			JPanel buttonsPanel = new JPanel();
			caretakerPanel.add(buttonsPanel, BorderLayout.SOUTH);
			buttonsPanel.setLayout(new GridLayout(0, 1, 0, 0));
			
			JButton btnCompleteButton = new JButton("Complete Task");
			btnCompleteButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/* Find the task in the selected row. */
					int rowToComplete = table.getSelectedRow();
					
					/* If row is selected, delete it and send to report */
					if(rowToComplete >= 0) {
						String taskName = (String) table.getValueAt(rowToComplete, 0);
						int taskDur = (Integer) table.getValueAt(rowToComplete, 1);
						String taskImp = (String) table.getValueAt(rowToComplete, 2);
						String taskFreq = (String) table.getValueAt(rowToComplete, 3);
						boolean taskRoutine = (boolean) table.getValueAt(rowToComplete, 4);
						
						Task completeTask = new Task();
						completeTask.setName(taskName);
						completeTask.setDuration(taskDur);
						completeTask.setImportance(taskImp);
						completeTask.setFrequency(taskFreq);
						completeTask.setRoutine(taskRoutine);
						
						theTaskHandler.completeTask(completeTask, key);
					}	
				}
			});
		
			btnCompleteButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			buttonsPanel.add(btnCompleteButton);
			
			JButton btnAddButton = new JButton("Add One-off Task");
			btnAddButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					/* Show Dialog */ 
					ChooseOneOffTasksDialog dialog = new ChooseOneOffTasksDialog(theTaskHandler.adminLists.get("oneoff"));
					dialog.setVisible(true);
					
					String taskName = (String) dialog.comboBox.getSelectedItem();
					
					/* If cancel has not been pressed, then carry the task */
					if(taskName != null) {
						theTaskHandler.addOneOffTaskToCaretakerList(taskName, key);
					}
				}
			});

			btnAddButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
			buttonsPanel.add(btnAddButton);
		}
	}
	
	
	
	/* Reloading the tables */
	void displayTableData(String key) { 
		/* Get the list based on the received key */
		ArrayList<Task> tableData = theTaskHandler.caretakers.get(key);
		
		/* Get the caretaker's table based on the received key */
		DefaultTableModel tableModel = (DefaultTableModel) tables.get(key).getModel();
		
	
		if((tableModel != null) && (tableData != null)) {
			/* Empty the existing data */
			tableModel.setRowCount(0);
			
			/* Sort the list */
			Collections.sort(tableData);
			
		    /* Reinsert data into the table */
			for(Task task: tableData) {
				tableModel.addRow(new Object[] {task.getName(), task.getDuration(), task.getImportance(), task.getFrequency(), task.isRoutine()});
			}	
		}
	}
}
