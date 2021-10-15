package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import exceptions.TeamAlreadyExists;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;


public class AddTeamGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BLFacade facade;
	private JTextField textTeam;
	private JLabel jLabelText = new JLabel("Team:");
	private JLabel jLabelOutput = new JLabel("");
	private JLabel jLabelWarning = new JLabel("Note: it is Case sensitive");
	
	private JButton jButtonAddTeam = new JButton("Add Team");
	private JButton jButtonClose = new JButton("Close");
	
	/**
	 * Create the frame.
	 */
	public AddTeamGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		facade = MainGUI.getBusinessLogic();
		
		textTeam = new JTextField();
		textTeam.setBounds(121, 77, 223, 19);
		contentPane.add(textTeam);
		textTeam.setColumns(10);
		
		jLabelText.setBounds(63, 80, 48, 13);
		contentPane.add(jLabelText);

		jButtonAddTeam.setBounds(63, 200, 136, 21);
		contentPane.add(jButtonAddTeam);
		
		jButtonClose.setBounds(238, 200, 136, 21);
		contentPane.add(jButtonClose);
		jLabelOutput.setHorizontalAlignment(SwingConstants.CENTER);
		

		jLabelOutput.setBounds(63, 132, 311, 28);
		contentPane.add(jLabelOutput);
		jLabelWarning.setHorizontalAlignment(SwingConstants.CENTER);
		jLabelWarning.setBounds(63, 106, 311, 13);
		
		contentPane.add(jLabelWarning);
		
		jButtonAddTeam.addActionListener(e -> {
				actionJButtonAddTeam();
		});
		
		jButtonClose.addActionListener(e -> {
				dispose();
		});
	}
	
	public void actionJButtonAddTeam() {
		String text = textTeam.getText();
		System.out.println(text);
		try {
			facade.addTeam(text);
			jLabelOutput.setText("The team has been successfully added");
		}catch(TeamAlreadyExists e) {
			System.out.println("Team already exists exception thrown");
			jLabelOutput.setText("The team already exists");
		}
	}
}
