package gui;


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Coupon;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;

@SuppressWarnings("serial")
public class CreateCouponGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfCouponReward;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateCouponGUI frame = new CreateCouponGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	JLabel lblWarning = new JLabel("");
	private JTextField tfCouponCode;
	
	/**
	 * Create the frame.
	 */
	public CreateCouponGUI() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTittle = new JLabel("CREATE COUPON");
		lblTittle.setBounds(5, 10, 425, 30);
		lblTittle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblTittle.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblTittle);
		
		tfCouponReward = new JTextField();
		tfCouponReward.setBounds(220, 90, 146, 30);
		contentPane.add(tfCouponReward);
		tfCouponReward.setColumns(10);
		
		JLabel lblReward = new JLabel("Coupon reward:");
		lblReward.setHorizontalAlignment(SwingConstants.LEFT);
		lblReward.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblReward.setBounds(80, 90, 130, 30);
		contentPane.add(lblReward);
		lblWarning.setForeground(Color.RED);
		
		
		lblWarning.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarning.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblWarning.setBounds(80, 50, 280, 30);
		contentPane.add(lblWarning);
		
		JButton btnCreateCoupon = new JButton("Create Coupon");
		btnCreateCoupon.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCreateCoupon.setBounds(140, 170, 140, 30);
		contentPane.add(btnCreateCoupon);
		
		JLabel lblCouponCode = new JLabel("Coupon Code:");
		lblCouponCode.setHorizontalAlignment(SwingConstants.LEFT);
		lblCouponCode.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblCouponCode.setBounds(80, 131, 130, 30);
		contentPane.add(lblCouponCode);
		
		tfCouponCode = new JTextField();
		tfCouponCode.setColumns(10);
		tfCouponCode.setBounds(220, 129, 146, 30);
		contentPane.add(tfCouponCode);
		
		btnCreateCoupon.addActionListener(new ActionListener() {		
			public void actionPerformed(ActionEvent e) {
				int prize;
				try {
					BLFacade facade = MainGUI.getBusinessLogic();
					String couponReward = tfCouponReward.getText();
					String couponCode = tfCouponCode.getText();		
					if (couponReward.length() > 0 && couponCode.length() > 0) {
						try {
							prize = Integer.parseInt(couponReward);
							boolean c2 = facade.findCoupon(couponCode);
							if(c2) {
								System.out.println("Error creating coupon (coupon already exist)");
								lblWarning.setForeground(Color.RED);
								lblWarning.setText("Coupon already exist");
							}else {
								Coupon c1 = facade.addCoupon(prize,couponCode);
								if(c1==null) {
									System.out.println("Error creating coupon");
									lblWarning.setForeground(Color.RED);
									lblWarning.setText("Error creating coupon");
								}else{
									lblWarning.setForeground(Color.BLACK);
									lblWarning.setText("Coupon successfully created");
								}
							}
						}
						catch (Exception aspm) {
							lblWarning.setForeground(Color.RED);
							lblWarning.setText("Please, enter a number");
							aspm.printStackTrace();
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	
	
}
