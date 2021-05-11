package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class RegisterGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldEMail;
	private JTextField textFieldUserName;
	private JTextField textFieldNid;
	private JTextField textFieldLastName;
	private JTextField textFieldName;
	private JTextField textFieldPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI();
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
	public RegisterGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 525);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textFieldEMail = new JTextField();
		textFieldEMail.setBounds(108, 158, 257, 28);
		contentPane.add(textFieldEMail);
		textFieldEMail.setColumns(10);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(108, 221, 257, 28);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Password:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(32, 259, 76, 28);
		contentPane.add(lblNewLabel_2);
		
		textFieldNid = new JTextField();
		textFieldNid.setColumns(10);
		textFieldNid.setBounds(108, 118, 257, 28);
		contentPane.add(textFieldNid);
		
		textFieldLastName = new JTextField();
		textFieldLastName.setColumns(10);
		textFieldLastName.setBounds(108, 79, 257, 28);
		contentPane.add(textFieldLastName);
		
		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(108, 40, 257, 28);
		contentPane.add(textFieldName);
		
		textFieldPass = new JTextField();
		textFieldPass.setColumns(10);
		textFieldPass.setBounds(108, 260, 257, 28);
		contentPane.add(textFieldPass);
		
		JButton btnNewButton_1 = new JButton("Confirm");
		JLabel lblNewLabel_2_1 = new JLabel("User name:");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(32, 220, 76, 28);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("E-mail");
		lblNewLabel_2_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_2.setBounds(32, 156, 76, 28);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_2_3 = new JLabel("NID:");
		lblNewLabel_2_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_3.setBounds(32, 117, 76, 28);
		contentPane.add(lblNewLabel_2_3);
		
		JLabel lblNewLabel_2_4 = new JLabel("Last name:");
		lblNewLabel_2_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_4.setBounds(32, 78, 76, 28);
		contentPane.add(lblNewLabel_2_4);
		
		JLabel lblNewLabel_2_5 = new JLabel("Name:");
		lblNewLabel_2_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_5.setBounds(32, 39, 76, 28);
		contentPane.add(lblNewLabel_2_5);
	
		JCheckBox chckbxIWantTo = new JCheckBox("I'm interested in receiving emails about upcoming events.\r\n");
		chckbxIWantTo.setSelected(true);
		chckbxIWantTo.setFont(new Font("Tahoma", Font.ITALIC, 10));
		chckbxIWantTo.setBounds(72, 402, 324, 23);
		contentPane.add(chckbxIWantTo);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		chckbxNewCheckBox.setBounds(92, 351, 21, 23);
		contentPane.add(chckbxNewCheckBox);
		
		JTextArea txtrIConfirmThat = new JTextArea();
		txtrIConfirmThat.setEditable(false);
		txtrIConfirmThat.setFont(new Font("Tahoma", Font.ITALIC, 10));
		txtrIConfirmThat.setBackground(SystemColor.menu);
		txtrIConfirmThat.setRows(3);
		txtrIConfirmThat.setText("I confirm that I have read, consent and agree \r\nto Bets21 User agreement and Privacy Policy,\r\nand I am of legal age.");
		txtrIConfirmThat.setBounds(113, 348, 219, 58);
		contentPane.add(txtrIConfirmThat);
		
		JLabel lblError = new JLabel();
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblError.setBounds(20, 308, 406, 14);
		contentPane.add(lblError);
		
		JLabel lblNewLabel = new JLabel("Create a new Bet21 account:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(32, 11, 227, 14);
		contentPane.add(lblNewLabel);
		
		btnNewButton_1.setEnabled(true);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				//Check user and Nid
				lblError.setVisible(false);
				boolean checkUser = facade.checkUser(textFieldUserName.getText());
				boolean checkNid = facade.checkNid(textFieldNid.getText());
				boolean validNid = facade.dniValido(textFieldNid.getText());
				if(chckbxNewCheckBox.isSelected()) {
					if(!textFieldEMail.getText().isEmpty() & !textFieldUserName.getText().isEmpty() & !textFieldNid.getText().isEmpty()
						& !textFieldLastName.getText().isEmpty() & !textFieldName.getText().isEmpty() & !textFieldPass.getText().isEmpty()) {
						if(!checkUser || checkNid || !validNid) {
							lblError.setVisible(true);
							if(!checkUser) lblError.setText("The inserted username already exists");
							if(checkNid) lblError.setText("The inserted Dni already exists");
							if(!validNid) lblError.setText("The inserted Dni must have 8 numbers followed by one capital character");
						}else {
							facade.addUser(textFieldName.getText(), textFieldLastName.getText(), textFieldEMail.getText(), textFieldNid.getText(), textFieldUserName.getText(), textFieldPass.getText());
							JFrame a = new LogInGUI();
							dispose();
							a.setVisible(true);
						}
					}else {
						lblError.setVisible(true);
						lblError.setText("You must fill all the gaps\r\n");
					}	
				}else {
					lblError.setVisible(true);
					lblError.setText("You must accept the Privacy Policy and Terms & Conditions\r\n");
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBounds(118, 443, 180, 36);
		contentPane.add(btnNewButton_1);
	}	
}
