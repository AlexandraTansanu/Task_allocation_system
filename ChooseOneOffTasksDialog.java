package SoftwareEngineeringPractice;

import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChooseOneOffTasksDialog extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	public JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChooseOneOffTasksDialog dialog = new ChooseOneOffTasksDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChooseOneOffTasksDialog(ArrayList<Task> copyOneOffTasks) {
		setModal(true);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE); // reference of existent one-off tasks
		setTitle("Choose One-Off Task");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblChooseLabel = new JLabel("Choose from available one-off tasks:");
			lblChooseLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			lblChooseLabel.setBounds(5, 47, 426, 46);
			contentPanel.add(lblChooseLabel);
		}
		{
			comboBox = new JComboBox();
			
			/* Dynamically generate combo box options from list of available one-off tasks */
			String [] comboBoxOptions = new String [copyOneOffTasks.size()];
			
			for(int i = 0; i < copyOneOffTasks.size(); i++) {
				comboBoxOptions[i] = copyOneOffTasks.get(i).getName();
			}
			
			comboBox.setModel(new DefaultComboBoxModel(comboBoxOptions)); 
			comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
			comboBox.setBounds(5, 103, 426, 46);
			contentPanel.add(comboBox);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setModal(false);
				        dispose();
					}
				});
				okButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						/* Empty the value hold by the combo box */
						comboBox.setSelectedItem(null);
						
						setModal(false);
				        dispose();
					}
				});
				cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
