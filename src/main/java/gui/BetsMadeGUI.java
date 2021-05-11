package gui;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Account;
import domain.BetMade;
import javax.swing.JScrollPane;

public class BetsMadeGUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private Account ac;
	private BLFacade facade;
	
	private JScrollPane scrollPane = new JScrollPane();
	private JPanel contentPane;
	private DefaultListModel<BetMade> model;
	private JList<BetMade> list;
	/**
	 * Create the application.
	 */
	public BetsMadeGUI() {
		facade = MainGUI.getBusinessLogic();
		this.ac = facade.getUser(facade.getCurrentUserAccount());
		
		setTitle("Bets Made");
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		model = new DefaultListModel<>();
		contentPane.setLayout(null);
		list = new JList<BetMade>();
		list.setSize(436, 243);
		list.setLocation(0, 10);
		list.setModel(model);
		scrollPane.setBounds(10, 10, 416, 243);
		scrollPane.setViewportView(list);
		contentPane.add(scrollPane);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		System.out.println(ac);
		List<BetMade> listOfBets = facade.getBetsMade(ac);
		System.out.println(listOfBets);
		for(BetMade bet: listOfBets) {
			System.out.println(bet.toString());
			model.addElement(bet);
		}
	}

}
