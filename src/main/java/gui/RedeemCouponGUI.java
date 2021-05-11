package gui;

import java.awt.EventQueue;

import businessLogic.BLFacade;
import domain.Account;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Color;

@SuppressWarnings("serial")
public class RedeemCouponGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtInsertYourCoupon;
	
	private static BLFacade appFacadeInterface;
	private JTextField txtInsertUserName;
	
	
	public static BLFacade getBusinessLogic() {
		return appFacadeInterface;
	}
	
	public static void setBussinessLogic(BLFacade a) {
		appFacadeInterface=a;
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RedeemCouponGUI frame = new RedeemCouponGUI();
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
	public RedeemCouponGUI() {
		
		setTitle("Redeem Cupon");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblRedeemYourCoupon = new JLabel("Redeem your coupon:");
		lblRedeemYourCoupon.setBounds(31, 168, 115, 23);
		contentPane.add(lblRedeemYourCoupon);
		
		txtInsertYourCoupon = new JTextField();
		txtInsertYourCoupon.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtInsertYourCoupon.setBounds(183, 169, 215, 20);
		contentPane.add(txtInsertYourCoupon);
		txtInsertYourCoupon.setColumns(10);
		
		JLabel lblDoYouHaveCoupon = new JLabel("Do you have a coupon?");
		lblDoYouHaveCoupon.setVerticalAlignment(SwingConstants.TOP);
		lblDoYouHaveCoupon.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblDoYouHaveCoupon.setBounds(118, 23, 195, 23);
		contentPane.add(lblDoYouHaveCoupon);
		
		JLabel lblRedeemItHere = new JLabel("Redeem it here!");
		lblRedeemItHere.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblRedeemItHere.setBounds(152, 57, 125, 23);
		contentPane.add(lblRedeemItHere);
		
		JButton btnRedeemButton = new JButton("Redeem");
		btnRedeemButton.setBounds(164, 206, 89, 23);
		contentPane.add(btnRedeemButton);
		
		JLabel lblRedeemInfo = new JLabel("");
		lblRedeemInfo.setForeground(Color.RED);
		lblRedeemInfo.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblRedeemInfo.setBounds(86, 103, 289, 23);
		lblRedeemInfo.setVisible(false);
		contentPane.add(lblRedeemInfo);
		
		txtInsertUserName = new JTextField();
		txtInsertUserName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		txtInsertUserName.setBounds(183, 137, 215, 20);
		contentPane.add(txtInsertUserName);
		txtInsertUserName.setColumns(10);
		
		JLabel lblUserNameLabel = new JLabel("User name");
		lblUserNameLabel.setBounds(31, 140, 115, 14);
		contentPane.add(lblUserNameLabel);
		
		
		btnRedeemButton.addActionListener(new ActionListener() {
			/**
			 * Action performed for the redeem button
			 */
			public void actionPerformed(ActionEvent e) {
				
				BLFacade facade = MainGUI.getBusinessLogic();
				
				String userName =  txtInsertUserName.getText();
				String coupon = txtInsertYourCoupon.getText();
				
				System.out.println(userName);
				System.out.println(coupon);
				boolean validUser = facade.checkUser(userName); //devuelve true si no esta
				if (!validUser) {
					if (coupon.length() > 0) {
						boolean validCoupon = facade.findCoupon(coupon);
						
						if (validCoupon) {
							
							Account cu = facade.getUser(facade.getCurrentUserAccount());
//							System.out.println(cu.getCoupons());
							boolean u = cu.doesCouponAlreadyUsed(facade.getCoupon(coupon));
//							System.out.println(u);
//							System.out.println(cu.getCoupons());
							if(!u) {
								facade.redeemCoupon(userName, coupon);
								lblRedeemInfo.setForeground(Color.BLACK);
								lblRedeemInfo.setText("Coupon successfully redeemed");
								lblRedeemInfo.setVisible(true);
							}else {
								lblRedeemInfo.setForeground(Color.RED);
								lblRedeemInfo.setText("The coupon is already redeemed");
								lblRedeemInfo.setVisible(true);
							}
						}
						else {
							lblRedeemInfo.setForeground(Color.RED);
							lblRedeemInfo.setText("There is no coupon with the given code");
							lblRedeemInfo.setVisible(true);
						}
					}
				}
				else {
					lblRedeemInfo.setForeground(Color.RED);
					lblRedeemInfo.setText("The given user does not exist");
					lblRedeemInfo.setVisible(true);
				}
				
				
				
			}
		});
	}
}
