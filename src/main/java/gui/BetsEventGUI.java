package gui;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.BetMade;
import domain.Event;

import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

public class BetsEventGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private BLFacade facade;
	
	private JComboBox<Event> JComboBoxEvents;
	private DefaultListModel<BetMade> model;
	private JList<BetMade> JListBetMade;
	private JLabel JLabelErrors = new JLabel("");
	private final JScrollPane scrollPane = new JScrollPane();
	/**
	 * Create the frame.
	 */
	public BetsEventGUI() {
		JComboBoxEvents = new JComboBox<>();
		model = new DefaultListModel<BetMade>();
		JListBetMade = new JList<BetMade>();
		contentPane = new JPanel();
		facade = MainGUI.getBusinessLogic();
		
		scrollPane.setViewportView(JListBetMade);
		JComboBoxEvents.setBounds(5, 5, 189, 21);
		JListBetMade.setBounds(5, 26, 497, 274);
		JListBetMade.setModel(model);
		JLabelErrors.setBounds(204, 9, 298, 13);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 521, 342);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		contentPane.add(JComboBoxEvents);
		//contentPane.add(JListBetMade);
		contentPane.add(scrollPane);
		contentPane.add(JLabelErrors);
		setContentPane(contentPane);
		scrollPane.setBounds(5, 26, 497, 269);
		contentPane.add(scrollPane);
		
		initialize();
	}
	
	public void initialize() {
		List<Event> allEvents = facade.getAllEvents();
		if(!allEvents.isEmpty()) {
			for(Event ev: allEvents) {
				JComboBoxEvents.addItem(ev);
			}
		}else {
			JLabelErrors.setText("There are no events");
		}
		JComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBoxEventsAction();
			}
		});
	}
	
	public void JComboBoxEventsAction() {
		List<BetMade> bmList;
		if(JComboBoxEvents.getSelectedIndex() != -1) {
			JLabelErrors.setText("");
			domain.Event ev = (domain.Event) JComboBoxEvents.getSelectedItem();
			bmList = facade.getBetsFromEvents(ev);
			if(!bmList.isEmpty()) {	
				for(BetMade bm: bmList) {
					model.addElement(bm);
				}
			}else {
				JLabelErrors.setText("Nobody made a bet for this event");
			}
		}
	}
}


