package SoftwareEngineeringPractice;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CreateTaskDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	public JTextField nameTextField;
	public JSpinner durationSpinner;
	public JComboBox importanceComboBox;
	public JComboBox frequencyComboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CreateTaskDialog dialog = new CreateTaskDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CreateTaskDialog() {
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setModal(true);
		setTitle("Create Task ");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNameLabel = new JLabel("Task name");
			lblNameLabel.setBounds(5, 6, 133, 54);
			lblNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblNameLabel);
		}
		{
			JLabel lblDurationLabel = new JLabel("Duration (min.)");
			lblDurationLabel.setBounds(5, 60, 137, 54);
			lblDurationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblDurationLabel);
		}
		
		durationSpinner = new JSpinner();
		durationSpinner.setBounds(152, 71, 83, 34);
		durationSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		durationSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		contentPanel.add(durationSpinner);
		{
			JLabel lblImportanceLabel = new JLabel("Importance");
			lblImportanceLabel.setBounds(5, 114, 133, 54);
			lblImportanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
			contentPanel.add(lblImportanceLabel);
		}
		
		importanceComboBox = new JComboBox();
		importanceComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		importanceComboBox.setBounds(152, 129, 213, 34);
		importanceComboBox.setModel(new DefaultComboBoxModel(new String[] {"urgent", "important", "unimportant"}));
		contentPanel.add(importanceComboBox);
		
		nameTextField = new JTextField();
		nameTextField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		nameTextField.setBounds(148, 20, 277, 38);
		contentPanel.add(nameTextField);
		nameTextField.setColumns(10);
		
		JLabel lblFrequencyLabel = new JLabel("Frequency level");
		lblFrequencyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblFrequencyLabel.setBounds(10, 178, 137, 40);
		contentPanel.add(lblFrequencyLabel);
		
		frequencyComboBox = new JComboBox();
		frequencyComboBox.setModel(new DefaultComboBoxModel(new String[] {"high", "low"}));
		frequencyComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		frequencyComboBox.setBounds(152, 186, 213, 34);
		contentPanel.add(frequencyComboBox);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Ok");
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
						nameTextField.setText("");
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
