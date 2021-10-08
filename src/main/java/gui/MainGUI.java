package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;

	private static BLFacade appFacadeInterface;

	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}

	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton jButtonCreateEvents;
	private JButton jButtonCreateBets;

	private Boolean admini;
	private JButton jButtonCloseEvent;
	private JButton btnMakeBet;
	private JButton btnLogOut;

	private JButton btnUserAccount;
	private JButton btnEventBets;
	private JButton btnViewBets;
	private JButton btnCreateCoupon;
	private JButton btnRedeemCoupon;
	private JButton btnAddTeam;
	
	/**
	 * This is the default constructor
	 */
	public MainGUI(boolean admin) {

		super();
		admini=admin;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}



	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {

		// this.setSize(271, 295);
		this.setSize(1032, 290);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getPanel());
			jContentPane.add(getJButtonCreateEvents());
			jContentPane.add(getJButtonCreateBets());
			jContentPane.add(getJButtonCloseEvent());
			jContentPane.add(getBtnMakeBet());

			jContentPane.add(getBtnLogOut());
			jContentPane.add(getBtnUserAccount());
			jContentPane.add(getBtnEventBets());
			jContentPane.add(getBtnViewBets());
			jContentPane.add(getBtnCreateCoupon());
			jContentPane.add(getBtnRedeemCoupon());
			jContentPane.add(getBtnAddTeam());
			
			JButton btnMyBets = new JButton("My Bets\r\n");
			btnMyBets.setBounds(263, 119, 241, 63);

			JButton jButtonCloseEvent = new JButton();
			jButtonCloseEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

				}
			});
			jButtonCloseEvent.setText("Close Event"); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCloseEvent.setBounds(481, 63, 241, 63);

			if(!admini){
				getJButtonCreateEvents().setEnabled(false);
				getJButtonCreateBets().setEnabled(false);
				getJButtonCloseEvent().setEnabled(false);
				getBtnEventBets().setEnabled(false);
				getBtnCreateCoupon().setEnabled(false);
				getBtnAddTeam().setEnabled(false);
			}

		}
		return jContentPane;
	}


	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setBounds(10, 120, 140, 63);
			jButtonCreateQuery.setText("Create Question");
			if(!admini){
				jButtonCreateQuery.setEnabled(false);
			}
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new CreateQuestionGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateQuery;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setBounds(10, 46, 140, 63);
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));

			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					JFrame a = new FindQuestionsGUI();

					a.setVisible(true);
				}
			});
		}
		return jButtonQueryQueries;
	}


	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(276, 0, 481, 63);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBounds(276, 188, 481, 63);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	private JButton getJButtonCreateEvents() {
		if (jButtonCreateEvents == null) {
			jButtonCreateEvents = new JButton("Create Event"); //$NON-NLS-1$ //$NON-NLS-2$
			jButtonCreateEvents.setBounds(174, 120, 140, 63);
			jButtonCreateEvents.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateEventGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateEvents;
	}
	
	private JButton getJButtonCreateBets() {
		if (jButtonCreateBets == null) {
			jButtonCreateBets = new JButton("Create Bets");
			jButtonCreateBets.setBounds(517, 120, 140, 63);
			jButtonCreateBets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new CreateBetsGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCreateBets;
	}
	
	private JButton getJButtonCloseEvent() {
		if (jButtonCloseEvent == null) {
			jButtonCloseEvent = new JButton("Close Event");
			jButtonCloseEvent.setBounds(346, 120, 140, 63);
			jButtonCloseEvent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new CloseEventGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return jButtonCloseEvent;
	}
	
	private JButton getBtnMakeBet() {
		if (btnMakeBet == null) {
			btnMakeBet = new JButton("Make Bet");
			btnMakeBet.setBounds(174, 46, 140, 63);

			btnMakeBet.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame a = new MakeBetGUI(new Vector<Event>());
					a.setVisible(true);
				}
			});
		}
		return btnMakeBet;
	}
	private JButton getBtnLogOut() {
		if (btnLogOut == null) {
			btnLogOut = new JButton("Log Out\r\n");
			btnLogOut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new LogInGUI();
					a.setVisible(true);
					dispose();
				}
			});
			btnLogOut.setBounds(848, 213, 98, 26);
		}
		return btnLogOut;
	}
	
	private JButton getBtnUserAccount() {
		if (btnUserAccount == null) {
			btnUserAccount = new JButton("User Account");
			btnUserAccount.setBounds(346, 46, 140, 63);
			btnUserAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new UserAccountGUI();
					a.setVisible(true);
				}
			});
		}
		return btnUserAccount;
	}
	
	private JButton getBtnEventBets() {
		if (btnEventBets == null) {
			btnEventBets = new JButton("Event Bets");
			btnEventBets.setBounds(690, 120, 140, 63);
			btnEventBets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new BetsEventGUI();
					a.setVisible(true);
				}
			});
		}
		return btnEventBets;
	}
	
	private JButton getBtnViewBets() {
		if (btnViewBets == null) {
			btnViewBets = new JButton("View Bets");
			btnViewBets.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new BetsMadeGUI();
					a.setVisible(true);
				}
			});
			btnViewBets.setBounds(517, 46, 140, 63);
		}
		return btnViewBets;
	}
	
	private JButton getBtnAddTeam() {
		if (btnAddTeam == null) {
			btnAddTeam = new JButton("Add Team");
			btnAddTeam.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame a = new AddTeamGUI();
					a.setVisible(true);
				}
			});
			btnAddTeam.setBounds(861, 46, 144, 63);
		}
		return btnAddTeam;
	}
	
	private JButton getBtnCreateCoupon() {
		if (btnCreateCoupon == null) {
			btnCreateCoupon = new JButton("Create Coupon");
			btnCreateCoupon.setBounds(861, 120, 144, 63);
			btnCreateCoupon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JFrame create = new CreateCouponGUI();
					create.setVisible(true);
				}
			});
			
		}
		return btnCreateCoupon;
	}
	
	private JButton getBtnRedeemCoupon() {
		if (btnRedeemCoupon == null) {
			btnRedeemCoupon = new JButton("Redeem Coupon");
			btnRedeemCoupon.setBounds(690, 46, 140, 63);
			btnRedeemCoupon.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JFrame redeem = new RedeemCouponGUI();
					redeem.setVisible(true);
				}
			});
		}
		return btnRedeemCoupon;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

