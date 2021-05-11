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
import domain.Account;
import domain.Bet;
import domain.Event;
import domain.Question;

public class MakeBetGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JComboBox<Question> jComboBoxQuestions = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestion = new DefaultComboBoxModel<Question>();

	private JComboBox<Bet> jComboBoxBets = new JComboBox<Bet>();
	DefaultComboBoxModel<Bet> modelBets = new DefaultComboBoxModel<Bet>();

	private Collection<Question> questionList;
	private Collection<Bet> betList;
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelBets = new JLabel("Bets");
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonMake = new JButton("Make bet");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private final JLabel lblNoQuestionSelected = new JLabel();
	private JTextField textFieldDinero = new JTextField();

	//private Event selectedEvent;
	//private Question selectedQuestion;
	private Account user;

	public MakeBetGUI(Vector<domain.Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) throws Exception {

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
		
		user= facade.getUser(facade.getCurrentUserAccount());

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle("Make Bet"); //$NON-NLS-1$ //$NON-NLS-2$

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelBets.setBounds(new Rectangle(80, 211, 75, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonMake.setBounds(new Rectangle(135, 275, 130, 30));
		jButtonMake.setEnabled(false);

		jButtonMake.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonMake_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(312, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonMake, null);
		this.getContentPane().add(jLabelBets, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);
		this.getContentPane().add(jCalendar, null);

		this.getContentPane().add(textFieldDinero, null);



		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);

		jComboBoxQuestions.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				//selectedQuestion = (Question) jComboBoxQuestions.getSelectedItem();
				jComboBoxBets.removeAllItems();
				BLFacade facade = MainGUI.getBusinessLogic();
				Event ev = (Event)jComboBoxEvents.getSelectedItem();
				if(jComboBoxQuestions.getSelectedIndex() != -1 && !ev.getClosed()) {
					betList = facade.findAllBets((Event)jComboBoxEvents.getSelectedItem(),(Question)jComboBoxQuestions.getSelectedItem());
					for(Bet b: betList) {
						jComboBoxBets.addItem(b);
					}
					jButtonMake.setEnabled(true);
					if(jComboBoxBets.getSelectedIndex() == -1) {
						jButtonMake.setEnabled(false);
					}
				}else{
					jButtonMake.setEnabled(false);
				}
			}
		});
		jComboBoxQuestions.setBounds(new Rectangle(275, 47, 250, 20));
		jComboBoxQuestions.setBounds(275, 103, 250, 20);
		getContentPane().add(jComboBoxQuestions);

		JLabel lblNewLabel = new JLabel("Create bet"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(285, 80, 94, 13);
		getContentPane().add(lblNewLabel);
		lblNoQuestionSelected.setText("There is no Question selected"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNoQuestionSelected.setForeground(Color.RED);
		lblNoQuestionSelected.setBounds(new Rectangle(153, 245, 305, 20));
		lblNoQuestionSelected.setBounds(290, 155, 305, 20);
		lblNoQuestionSelected.setVisible(false);
		getContentPane().add(lblNoQuestionSelected);

		//jComboBoxBets (PRONOSTICOS)
		jComboBoxBets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				jComboBoxBets.getSelectedItem();

			}
		});

		jComboBoxBets.setBounds(new Rectangle(238, 211, 242, 22));
		jComboBoxBets.setBounds(238, 211, 242, 22);
		getContentPane().add(jComboBoxBets);

		textFieldDinero.setBounds(238, 244, 242, 20);
		getContentPane().add(textFieldDinero);
		textFieldDinero.setColumns(10);

		JLabel jLabelDinero = new JLabel("Money: "); //$NON-NLS-1$ //$NON-NLS-2$
		jLabelDinero.setBounds(80, 242, 46, 14);
		getContentPane().add(jLabelDinero);



		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//selectedEvent = (Event) jComboBoxEvents.getSelectedItem();

				jComboBoxQuestions.removeAllItems();
				BLFacade facade = MainGUI.getBusinessLogic();
				if(jComboBoxEvents.getSelectedIndex() != -1) {
					questionList = facade.findAllQuestion((Event)jComboBoxEvents.getSelectedItem());
					for(Question q: questionList) {
						jComboBoxQuestions.addItem(q);
					}
					//jButtonMake.setEnabled(true);
					if(jComboBoxQuestions.getSelectedIndex() == -1) {
						jButtonMake.setEnabled(false);
					}
				}else{
					jButtonMake.setEnabled(false);
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
							// Si en JCalendar est� 30 de enero y se avanza al mes siguiente, devolver�a 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este c�digo se dejar� como 1 de febrero en el JCalendar
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
							jButtonMake.setEnabled(false);
						//else
						//jButtonMake.setEnabled(true);

					} catch (Exception e1) {

						//jLabelError.setText(e1.getMessage());
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

	private void jButtonMake_actionPerformed(ActionEvent e) {
		//domain.Question question = ((domain.Question) jComboBoxQuestions.getSelectedItem());
		domain.Question question = ((domain.Question) jComboBoxQuestions.getSelectedItem());
		//domain.Event event = ((domain.Event) jComboBoxEvents.getSelectedItem());
		domain.Bet bet = (domain.Bet) jComboBoxBets.getSelectedItem();
		try {
			//	jLabelError.setText("");

			BLFacade facade = MainGUI.getBusinessLogic();
			// Displays an exception if the query field is empty
			//String inputQuery = jTextFieldQuery.getText();
			String inputQuery = jComboBoxQuestions.getActionCommand();
			//System.out.println(inputQuery);
			if(jComboBoxBets.getSelectedIndex() == -1) {
				jButtonMake.setEnabled(false);
			}else{
				jButtonMake.setEnabled(true);
				if (inputQuery.length() > 0) {
					facade = MainGUI.getBusinessLogic();
					lblNoQuestionSelected.setVisible(true);
					if (bet != null && facade.findBet(bet.getBet(), question)) {
						try {
							float inputPrice = (float) Float.parseFloat(textFieldDinero.getText());
							if(user.getWallet()>=inputPrice) {
								if(inputPrice>=question.getBetMinimum()) {
									facade.addBetMade(user, bet, inputPrice);
									lblNoQuestionSelected.setText("Bet successfully made");
								}else {
									lblNoQuestionSelected.setText("Insuficient amount for the bet");
								}
	
							}else {
								lblNoQuestionSelected.setText("Insuficient money in wallet");
							}
						}catch(NumberFormatException e2) {
							lblNoQuestionSelected.setText("Please insert a number");
						}
					}else {

						lblNoQuestionSelected.setText("No bet found");
					}


					//				//Question q = (Question) jComboBoxQuestions.getSelectedItem();
					//				if(question==null) {
					//					//lblNoQuestionSelected.setText("No question selected");
					//					//lblNoQuestionSelected.setVisible(true);
					//				}else {
					//					//lblNoQuestionSelected.setVisible(false);
					//					if(!facade.findBet(inputQuery, question)) {
					//						System.out.println(question);
					//						facade.createBet(inputQuery, 0, question);
					//					}else {
					//				//		jLabelError.setText("The bet already exists");
					//					}
					//				}

				}
			}
			//} catch (EventAlreadyExist e1) {
			//	jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		}catch (Exception e1) {

			e1.printStackTrace();

		}


	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}