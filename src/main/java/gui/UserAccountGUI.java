package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.Account;
import domain.CreditCard;

public class UserAccountGUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField tFMoney;
	private JTextField tFWithdraw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					UserAccountGUI frame = new UserAccountGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param user 
	 */
	public UserAccountGUI() {
		setBounds(100, 100, 1020, 379);
		getContentPane().setLayout(null);
		
		BLFacade facade = MainGUI.getBusinessLogic();
		
		JLabel lblUserNameValue = new JLabel("");
		lblUserNameValue.setBounds(121, 17, 164, 14);
		getContentPane().add(lblUserNameValue);
		
		JLabel lblSuccessRemove = new JLabel("");
		lblSuccessRemove.setForeground(Color.RED);
		lblSuccessRemove.setBounds(547, 127, 197, 14);
		getContentPane().add(lblSuccessRemove);
		
		JLabel lblAddMessage = new JLabel("");
		lblAddMessage.setForeground(Color.RED);
		lblAddMessage.setBounds(10, 321, 245, 14);
		getContentPane().add(lblAddMessage);
		
		lblUserNameValue.setText(facade.getCurrentUserAccount());
		
		JComboBox<String> paymentMethods = new JComboBox<String>();
		paymentMethods.setBounds(295, 95, 205, 22);
		getContentPane().add(paymentMethods);
		
		JFormattedTextField cardNumber = new JFormattedTextField();
		cardNumber.setBounds(41, 124, 164, 20);
		getContentPane().add(cardNumber);
		
		JLabel lblCardNumber = new JLabel("Card Number\r\n");
		lblCardNumber.setBounds(10, 99, 87, 14);
		getContentPane().add(lblCardNumber);
		
		JFormattedTextField endDate = new JFormattedTextField();
		endDate.setBounds(41, 180, 86, 20);
		getContentPane().add(endDate);
		endDate.setColumns(10);
		
		JLabel lblEndDate = new JLabel("End Date\r\n");
		lblEndDate.setBounds(10, 155, 62, 14);
		getContentPane().add(lblEndDate);
		
		JFormattedTextField cvc = new JFormattedTextField();
		cvc.setBounds(156, 180, 49, 20);
		getContentPane().add(cvc);
		
		JLabel lblcvc = new JLabel("CVC");
		lblcvc.setBounds(125, 155, 46, 14);
		getContentPane().add(lblcvc);
		
		JFormattedTextField cardName = new JFormattedTextField();
		cardName.setBounds(41, 236, 164, 20);
		getContentPane().add(cardName);
		
		JLabel lblCardName = new JLabel("Card Name");
		lblCardName.setBounds(10, 211, 87, 14);
		getContentPane().add(lblCardName);
		
		
		String userName = facade.getCurrentUserAccount();
		Account user = facade.getUser(userName);
		
		
		JButton btnAddPayment = new JButton("Add  New Credit Card");
		btnAddPayment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				lblAddMessage.setEnabled(false);
				lblAddMessage.setText("");
				if(cardNumber.getText().matches("[0-9]{16}") && !cardName.getText().isEmpty() && cvc.getText().matches("[0-9]{3}") && endDate.getText().matches("[0-9]{2}[/][0-9]{2}")) {
					
						CreditCard c = new CreditCard(cardName.getText(),endDate.getText(),cvc.getText(),cardNumber.getText());
						facade.addPaymentMethod(user, c);
						lblAddMessage.setText("Successfully added");
						paymentMethods.removeAllItems();
						LinkedList<CreditCard> methods = facade.getAllPaymentMethods(facade.getUser(facade.getCurrentUserAccount()));
						System.out.println(methods);
						if(!methods.isEmpty()) {
							for(CreditCard c1: methods) {
								paymentMethods.addItem(c1.getCardNumber());
							}
						}
				}else if(cardNumber.getText().isEmpty()){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("All fields must be filed");
				}else if(cardName.getText().isEmpty()){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("All fields must be filed");
				}else if(cvc.getText().isEmpty()){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("All fields must be filed");
				}else if(endDate.getText().isEmpty()){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("All fields must be filed");
				}else if(!cardNumber.getText().matches("[0-9]{16}")){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("The card number must be 16 digits long");
				}else if(!cvc.getText().matches("[0-9]{3}")){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("The cvc must be 3 digits long");
				}else if(!endDate.getText().matches("[0-1][0-2][/][0-9]{2}")){
					lblAddMessage.setEnabled(true);
					lblAddMessage.setText("Invalid date format");
				}
			}
		});
		
		

		btnAddPayment.setBounds(55, 287, 150, 23);
		getContentPane().add(btnAddPayment);
		
		JLabel lblPaymentMethods = new JLabel("Payment Methods");
		lblPaymentMethods.setBounds(295, 17, 134, 14);
		getContentPane().add(lblPaymentMethods);
		
		JButton btnRefresh = new JButton("Refresh");
		
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paymentMethods.removeAllItems();
				LinkedList<CreditCard> methods = facade.getAllPaymentMethods(facade.getUser(facade.getCurrentUserAccount()));
				System.out.println(methods);
				if(!methods.isEmpty()) {
					for(CreditCard c: methods) {
						paymentMethods.addItem(c.getCardNumber());
					}
				}

			}
		});
		
		
		btnRefresh.setBounds(412, 62, 87, 22);
		getContentPane().add(btnRefresh);
		
		JButton btnRemovePaymentMethod = new JButton("Remove payment method");
		btnRemovePaymentMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSuccessRemove.setText("");
				if(paymentMethods.getSelectedItem()!=null) {
					facade.removePaymentMethod(user,(String) paymentMethods.getSelectedItem());
					lblSuccessRemove.setText("Successfully removed");
					paymentMethods.removeAllItems();
					LinkedList<CreditCard> methods = facade.getAllPaymentMethods(facade.getUser(facade.getCurrentUserAccount()));
					System.out.println(methods);
					if(!methods.isEmpty()) {
						for(CreditCard c: methods) {
							paymentMethods.addItem(c.getCardNumber());
						}
					}

				}
			}
		});
		btnRemovePaymentMethod.setBounds(547, 95, 197, 23);
		getContentPane().add(btnRemovePaymentMethod);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(Color.BLACK);
		separator.setBounds(265, 17, 9, 318);
		getContentPane().add(separator);
		
		
		
		JLabel lblMoneyError = new JLabel("");
		lblMoneyError.setForeground(Color.RED);
		lblMoneyError.setBounds(647, 281, 266, 14);
		getContentPane().add(lblMoneyError);
		
		JLabel lblWallet = new JLabel("Wallet:");
		lblWallet.setBounds(10, 49, 46, 14);
		getContentPane().add(lblWallet);
		
		JLabel lblWalletValue = new JLabel("");
		lblWalletValue.setBounds(55, 49, 137, 14);
		getContentPane().add(lblWalletValue);
		lblWalletValue.setText(String.valueOf(facade.getUser(facade.getCurrentUserAccount()).getWallet()));
		
		JLabel lblUserName = new JLabel("User Name:");
		lblUserName.setBounds(10, 17, 87, 14);
		getContentPane().add(lblUserName);
		
		tFMoney = new JTextField();
		tFMoney.setBounds(614, 236, 99, 20);
		getContentPane().add(tFMoney);
		tFMoney.setColumns(10);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(530, 184, 9, 145);
		getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setOrientation(SwingConstants.VERTICAL);
		separator_2.setForeground(Color.BLACK);
		separator_2.setBounds(743, 145, -200, 14);
		getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setForeground(Color.BLACK);
		separator_3.setBounds(547, 168, 197, 22);
		getContentPane().add(separator_3);
		
		JLabel lblAddFunds = new JLabel("Add Funds");
		lblAddFunds.setBounds(598, 183, 125, 14);
		getContentPane().add(lblAddFunds);
		
		JLabel lblMoney = new JLabel("Amount:");
		lblMoney.setBounds(542, 239, 62, 14);
		getContentPane().add(lblMoney);
		
		JButton btnAddFunds = new JButton("Add funds");
		btnAddFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblMoneyError.setText("");
				float cuantity = Float.parseFloat(tFMoney.getText());
				if(cuantity>0 && paymentMethods.getSelectedItem()!=null) {
					try {
						facade.addMoney(facade.getUser(facade.getCurrentUserAccount()),(String) paymentMethods.getSelectedItem(), cuantity);
						lblWalletValue.setText(String.valueOf(facade.getUser(facade.getCurrentUserAccount()).getWallet()));
						lblMoneyError.setText("Successfully added");
					}catch(Exception e) {
						e.printStackTrace();
						lblMoneyError.setText("Unexpected error ocurred, please try again");
					}
				}else if(cuantity<0) {
					lblMoneyError.setText("Enter a valid number");
				}else {
					lblMoneyError.setText("Please, select a valid credit card");
				}
			}
		});
		btnAddFunds.setBounds(614, 306, 120, 23);
		getContentPane().add(btnAddFunds);
		
		JSeparator separator_3_1 = new JSeparator();
		separator_3_1.setForeground(Color.BLACK);
		separator_3_1.setBounds(778, 168, 197, 22);
		getContentPane().add(separator_3_1);
		
		JLabel lblWithdrawals = new JLabel("Withdrawals");
		lblWithdrawals.setBounds(845, 183, 87, 14);
		getContentPane().add(lblWithdrawals);
		
		tFWithdraw = new JTextField();
		tFWithdraw.setColumns(10);
		tFWithdraw.setBounds(845, 236, 99, 20);
		getContentPane().add(tFWithdraw);
		
		JLabel lblMoney_1 = new JLabel("Amount:");
		lblMoney_1.setBounds(778, 239, 57, 14);
		getContentPane().add(lblMoney_1);
		
		JButton btnWithdrawFunds = new JButton("Withdraw funds");
		btnWithdrawFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				float cuantity = Float.parseFloat(tFWithdraw.getText());
				if(cuantity>0 & cuantity<=facade.getUser(facade.getCurrentUserAccount()).getWallet() & paymentMethods.getSelectedItem()!=null) {
					try {
						facade.addMoney(facade.getUser(facade.getCurrentUserAccount()),(String) paymentMethods.getSelectedItem(), (-1*cuantity));
						lblWalletValue.setText(String.valueOf(facade.getUser(facade.getCurrentUserAccount()).getWallet()));
						lblMoneyError.setText("Successfully withdrawed");
					}catch(Exception e) {
						e.printStackTrace();
						lblMoneyError.setText("Unexpected error ocurred, please try again");
					}
				}else if(cuantity<0) {
					lblMoneyError.setText("Enter a valid number");
				}else if(cuantity>facade.getUser(facade.getCurrentUserAccount()).getWallet()){
					lblMoneyError.setText("Please, select a valid credit card");
				}else {
					lblMoneyError.setText("Invalid Credit Card selected");
				}
			}
		});
		btnWithdrawFunds.setBounds(843, 306, 120, 23);
		getContentPane().add(btnWithdrawFunds);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}

		});
		btnClose.setBounds(886, 13, 89, 23);
		getContentPane().add(btnClose);
	
			
	}
	
	public void close() {
		this.setVisible(false);
	}
}
