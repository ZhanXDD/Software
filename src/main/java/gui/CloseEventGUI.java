package gui;

import java.text.DateFormat;
import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Question;

public class CloseEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();
	DefaultComboBoxModel<Question> modelQuestion = new DefaultComboBoxModel<Question>();
	

	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private Collection<Question> questionList;
	private Collection<Bet> betList;
	private JButton jButtonCloseEvent = new JButton("Close event"); //$NON-NLS-1$ //$NON-NLS-2$
	private JButton jButtonExit = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError = new JLabel();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	

	public CloseEventGUI(Vector<domain.Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle("Close Event");

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(277, 50, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCloseEvent.setBounds(new Rectangle(40, 276, 130, 30));
		jButtonCloseEvent.setEnabled(false);

		jButtonCloseEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCloseEvent_actionPerformed(e);
			}
		});
		jButtonExit.setBounds(new Rectangle(199, 276, 130, 30));
		jButtonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonExit_actionPerformed(e);
			}
		});
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(153, 245, 305, 20));
		jLabelError.setForeground(Color.red);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonExit, null);
		this.getContentPane().add(jButtonCloseEvent, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);



		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		JComboBox<Question> jComboBoxQuestions = new JComboBox<Question>();
		jComboBoxQuestions.setBounds(new Rectangle(277, 50, 250, 20));
		jComboBoxQuestions.setBounds(275, 107, 250, 20);
		getContentPane().add(jComboBoxQuestions);
		
		JComboBox<Bet> jComboBoxBets = new JComboBox<Bet>();
		jComboBoxBets.setBounds(new Rectangle(277, 50, 250, 20));
		jComboBoxBets.setBounds(277, 173, 250, 20);
		getContentPane().add(jComboBoxBets);
		
		JButton btnSetWiner = new JButton("Set winner"); //$NON-NLS-1$ //$NON-NLS-2$
		btnSetWiner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Bet bet = (Bet)jComboBoxBets.getSelectedItem();
				facade.setWinnerBet(bet);
				jLabelError.setText("The bet " + bet.toString() + " is the winner");
			}
		});
		btnSetWiner.setBounds(351, 276, 140, 30);
		getContentPane().add(btnSetWiner);
		
		JLabel jLabelListOfQuestions = new JLabel("List of questions: "); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelListOfQuestions.setBounds(290, 81, 109, 14);
		getContentPane().add(jLabelListOfQuestions);
		
		JLabel jLabelListOfBets = new JLabel("List of bets");
		jLabelListOfBets.setBounds(290, 148, 93, 14);
		getContentPane().add(jLabelListOfBets);

		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jComboBoxQuestions.removeAllItems();
				BLFacade facade = MainGUI.getBusinessLogic();
				if(jComboBoxEvents.getSelectedIndex() != -1) {
					questionList = facade.findAllQuestion((Event)jComboBoxEvents.getSelectedItem());
					for(Question q: questionList) {
						jComboBoxQuestions.addItem(q);
					}
				}
			}
		});
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jComboBoxBets.removeAllItems();
				BLFacade facade = MainGUI.getBusinessLogic();
				if(jComboBoxEvents.getSelectedIndex() != -1) {
					betList = facade.findAllBets((Event)jComboBoxEvents.getSelectedItem(),(Question)jComboBoxQuestions.getSelectedItem());
					for(Bet b: betList) {
						jComboBoxBets.addItem(b);
					}
				}
			}
		});
		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
				//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCloseEvent.setEnabled(false);
						else
							jButtonCloseEvent.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
	}


	public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed.


		Calendar calendar = jCalendar.getCalendar();

		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

		if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else
			offset += 5;


		for (Date d:datesWithEventsCurrentMonth){

			calendar.setTime(d);
			System.out.println(d);



			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
			//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
		}

		calendar.set(Calendar.DAY_OF_MONTH, today);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);


	}

	private void jButtonCloseEvent_actionPerformed(ActionEvent e) {
		BLFacade facade = MainGUI.getBusinessLogic();
		domain.Event event = (domain.Event) jComboBoxEvents.getSelectedItem();
		facade.closeEvent(event);
		jLabelError.setText("Event " + event.toString() + " has been closed");
	}

	private void jButtonExit_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
	
	
	
}