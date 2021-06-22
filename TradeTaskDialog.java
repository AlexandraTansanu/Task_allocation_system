package SoftwareEngineeringPractice;

import java.util.HashMap;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class TradeTaskDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	public JComboBox nameComboBox;
	public JComboBox otherNameComboBox;
	public JComboBox taskComboBox;
	public JComboBox otherTaskComboBox;
	
	public boolean readyToTrade;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TradeTaskDialog dialog = new TradeTaskDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public TradeTaskDialog(HashMap<String, ArrayList<Task>> caretakers) {		
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setTitle("Trade Task");
		setBounds(100, 100, 798, 391);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new GridLayout(4, 2, 20, 10));
		{
			JLabel lblThisCaretakerLabel = new JLabel("Select your name:");
			lblThisCaretakerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblThisCaretakerLabel);
		}
		{
			JLabel lblThisTaskLabel = new JLabel("Select the task you want to trade:");
			lblThisTaskLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblThisTaskLabel);
		}
		{
			nameComboBox = new JComboBox();
			nameComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			contentPanel.add(nameComboBox);
			
			
			
			/* Generate each caretaker's name as an option for the combo box */
			String [] namesComboBoxOptions = new String[caretakers.size()];
			
			for(int i = 0; i < namesComboBoxOptions.length; i++) {
				namesComboBoxOptions[i] = (String)caretakers.keySet().toArray()[i];
			}
			
			nameComboBox.setModel(new DefaultComboBoxModel(namesComboBoxOptions));
			
			
			
			
			
		}
		{
			taskComboBox = new JComboBox();
			taskComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			contentPanel.add(taskComboBox);
			
			
			
			
			/* Generate the corresponding list for the chosen caretaker */
			String chosenCaretaker = (String)nameComboBox.getSelectedItem();
			
			ArrayList <Task> thisTasks = (ArrayList<Task>)caretakers.get(chosenCaretaker);
			String [] tasksComboBoxOptions = new String[thisTasks.size()];
			
			for(int i = 0; i < tasksComboBoxOptions.length; i++) {
				tasksComboBoxOptions[i] = (String)thisTasks.get(i).getName();
			}
			
			taskComboBox.setModel(new DefaultComboBoxModel(tasksComboBoxOptions));

			
			
			
			
		}
		{
			JLabel lblOtherCaretakerLabel = new JLabel("Select the name you want to trade with:");
			lblOtherCaretakerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblOtherCaretakerLabel);
		}
		{
			JLabel lblOtherTaskLabel = new JLabel("Select the task you want in return:");
			lblOtherTaskLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblOtherTaskLabel);
		}
		{
			otherNameComboBox = new JComboBox();
			otherNameComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			contentPanel.add(otherNameComboBox);
			
			/* Generate the other caretaker's name as an option for the combo box */
			String chosenCaretaker = (String)nameComboBox.getSelectedItem();
			
			String [] otherNamesComboBoxOptions = new String[caretakers.size()];
			
			for(int i = 0; i < otherNamesComboBoxOptions.length; i++) {
				String thisCaretaker = (String)caretakers.keySet().toArray()[i];
				
				if(!thisCaretaker.equals(chosenCaretaker))
					otherNamesComboBoxOptions[i] = thisCaretaker;
				else 
					otherNamesComboBoxOptions[i] = "Back to One-off tasks";
			}
			
			
			otherNameComboBox.setModel(new DefaultComboBoxModel(otherNamesComboBoxOptions));
		}
		{
			otherTaskComboBox = new JComboBox();
			otherTaskComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			contentPanel.add(otherTaskComboBox);
			
			/* Generate the corresponding list for the other caretaker */
			String otherChosenCaretaker = (String)otherNameComboBox.getSelectedItem();
			
			ArrayList <Task> otherTasks = null;
			String [] otherTasksComboBoxOptions;
			
			if(!otherChosenCaretaker.equals("Back to One-off tasks")) {
				otherTasks = (ArrayList<Task>)caretakers.get(otherChosenCaretaker);
				otherTasksComboBoxOptions = new String[otherTasks.size()];
				
				for(int i = 0; i < otherTasksComboBoxOptions.length; i++) {
					otherTasksComboBoxOptions[i] = (String)otherTasks.get(i).getName();
				}
			}
			else {
				otherTasksComboBoxOptions = new String[1];
				otherTasksComboBoxOptions[0] = "Nothing required";
				
			}
			
			
			otherTaskComboBox.setModel(new DefaultComboBoxModel(otherTasksComboBoxOptions)); 
		}
		
		
		
		
		/* Action listener which generates the appropiate options for the other combo boxes.
		 * Prevents users not to trade a task with themselves.
		 * Allows for users to put back a one-off task on the main list.
		 * Generates the right tasks for the selected cratekare. */
		nameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Generate the corresponding list for the chosen caretaker */
				String chosenCaretaker = (String)nameComboBox.getSelectedItem();
				
				ArrayList <Task> thisTasks = (ArrayList<Task>)caretakers.get(chosenCaretaker);
				String [] tasksComboBoxOptions = new String[thisTasks.size()];
				
				for(int i = 0; i < tasksComboBoxOptions.length; i++) {
					tasksComboBoxOptions[i] = (String)thisTasks.get(i).getName();
				}
				
				taskComboBox.setModel(new DefaultComboBoxModel(tasksComboBoxOptions)); 
				
				
				
				
				
				/* Generate the other caretaker's name as an option for the combo box */
				String [] otherNamesComboBoxOptions = new String[caretakers.size()];
				
				for(int i = 0; i < otherNamesComboBoxOptions.length; i++) {
					String thisCaretaker = (String)caretakers.keySet().toArray()[i];
					
					if(!thisCaretaker.equals(chosenCaretaker))
						otherNamesComboBoxOptions[i] = thisCaretaker;
					else 
						otherNamesComboBoxOptions[i] = "Back to One-off tasks";
				}
				
				otherNameComboBox.setModel(new DefaultComboBoxModel(otherNamesComboBoxOptions));
				
				
				
				/* Generate the corresponding list for the other caretaker */
				String otherChosenCaretaker = (String)otherNameComboBox.getSelectedItem();
				
				ArrayList <Task> otherTasks = null;
				String [] otherTasksComboBoxOptions;
				
				if(!otherChosenCaretaker.equals("Back to One-off tasks")) {
					otherTasks = (ArrayList<Task>)caretakers.get(otherChosenCaretaker);
					otherTasksComboBoxOptions = new String[otherTasks.size()];
					
					for(int i = 0; i < otherTasksComboBoxOptions.length; i++) {
						otherTasksComboBoxOptions[i] = (String)otherTasks.get(i).getName();
					}
				}
				else {
					otherTasksComboBoxOptions = new String[1];
					otherTasksComboBoxOptions[0] = "Nothing required";
				}
				
				
				otherTaskComboBox.setModel(new DefaultComboBoxModel(otherTasksComboBoxOptions));
				
			}
		});
		
		
		
		/* Action listener to generate the right tasks for the selected caretaker
		 * a user wants to trade with. */
		otherNameComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* Generate the corresponding list for the other caretaker */
				String otherChosenCaretaker = (String)otherNameComboBox.getSelectedItem();
				
				ArrayList <Task> otherTasks = null;
				String [] otherTasksComboBoxOptions;
				
				if(!otherChosenCaretaker.equals("Back to One-off tasks")) {
					otherTasks = (ArrayList<Task>)caretakers.get(otherChosenCaretaker);
					otherTasksComboBoxOptions = new String[otherTasks.size()];
					
					for(int i = 0; i < otherTasksComboBoxOptions.length; i++) {
						otherTasksComboBoxOptions[i] = (String)otherTasks.get(i).getName();
					}
				}
				else {
					otherTasksComboBoxOptions = new String[1];
					otherTasksComboBoxOptions[0] = "Nothing required";
				}
				
				
				otherTaskComboBox.setModel(new DefaultComboBoxModel(otherTasksComboBoxOptions));
			}
		});
		
		
		
		
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton completeButton = new JButton("Complete");
				completeButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						readyToTrade = true;
						
						setModal(false);
				        dispose();
					}
				});
				completeButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				completeButton.setActionCommand("OK");
				buttonPane.add(completeButton);
				getRootPane().setDefaultButton(completeButton);
			}
			{
				JButton discardButton = new JButton("Discard");
				discardButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						readyToTrade = false;
						
						setModal(false);
				        dispose();
					}
				});
				discardButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				discardButton.setActionCommand("Cancel");
				buttonPane.add(discardButton);
			}
		}
		
	}

}
