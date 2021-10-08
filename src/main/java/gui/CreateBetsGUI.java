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
import domain.Event;
import domain.Question;

public class CreateBetsGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JComboBox<Question> jComboBoxQuestions = new JComboBox<Question>();
	DefaultComboBoxModel<Question> modelQuestion = new DefaultComboBoxModel<Question>();
	
	private Collection<Question> questionList;
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel("New Bet");
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField jTextFieldQuery = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonCreate = new JButton("Create Bet");
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelError = new JLabel();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private final JLabel lblNoQuestionSelected = new JLabel();
	private JTextField jTextFieldReward = new JTextField();
	

	public CreateBetsGUI(Vector<domain.Event> v) {
		try {
			jbInit(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v) {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(604, 370));
		this.setTitle("Create Bets");

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(275, 47, 250, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(27, 211, 75, 20));
		jTextFieldQuery.setBounds(new Rectangle(100, 210, 429, 20));
		jTextFieldReward.setBounds(new Rectangle(100, 241, 429, 20));
		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(100, 275, 130, 30));
		jButtonCreate.setEnabled(false);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e);
			}
		});
		jButtonClose.setBounds(new Rectangle(275, 275, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(275, 180, 305, 20));
		jLabelError.setForeground(Color.red);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(jTextFieldQuery, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);
		this.getContentPane().add(jTextFieldReward, null);
		this.getContentPane().add(jCalendar, null);

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);



		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		jComboBoxQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jComboBoxQuestions.getSelectedItem();
			}
		});
		jComboBoxQuestions.setBounds(new Rectangle(275, 47, 250, 20));
		jComboBoxQuestions.setBounds(275, 103, 250, 20);
		getContentPane().add(jComboBoxQuestions);
		
		JLabel lblNewLabel = new JLabel("List of Questions: "); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(285, 80, 94, 13);
		getContentPane().add(lblNewLabel);
		lblNoQuestionSelected.setText("There is no question selected"); //$NON-NLS-1$ //$NON-NLS-2$
		lblNoQuestionSelected.setForeground(Color.RED);
		lblNoQuestionSelected.setBounds(new Rectangle(153, 245, 305, 20));
		lblNoQuestionSelected.setBounds(0, 0, 305, 20);
		lblNoQuestionSelected.setVisible(false);
		getContentPane().add(lblNoQuestionSelected);
		
		JLabel lblReward = new JLabel("Reward: "); //$NON-NLS-1$ //$NON-NLS-2$
		lblReward.setBounds(27, 245, 63, 14);
		getContentPane().add(lblReward);

		jComboBoxEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jComboBoxQuestions.removeAllItems();
				BLFacade facade = MainGUI.getBusinessLogic();
				if(jComboBoxEvents.getSelectedIndex() != -1) {
					questionList = facade.findAllQuestion((Event)jComboBoxEvents.getSelectedItem());
					for(Question q: questionList) {
						jComboBoxQuestions.addItem(q);
					}
					jButtonCreate.setEnabled(true);
					if(jComboBoxQuestions.getSelectedIndex() == -1) {
						jButtonCreate.setEnabled(false);
					}
				}else{
					jButtonCreate.setEnabled(false);
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
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

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

	private void jButtonCreate_actionPerformed(ActionEvent e) {
		//domain.Question question = ((domain.Question) jComboBoxQuestions.getSelectedItem());
		domain.Question question = ((domain.Question) jComboBoxQuestions.getSelectedItem());
		try {
			jLabelError.setText("");

			BLFacade facade = MainGUI.getBusinessLogic();
			// Displays an exception if the query field is empty
			String inputQuery = jTextFieldQuery.getText();
			String inputReward = jTextFieldReward.getText();
			//System.out.println(inputQuery);
			if (inputQuery.length() > 0) {
				//Question q = (Question) jComboBoxQuestions.getSelectedItem();
				if(question==null) {
					lblNoQuestionSelected.setVisible(true);
				}else {
					lblNoQuestionSelected.setVisible(false);
					if(!facade.findBet(inputQuery, question)) {
						try {
							Float number = Float.valueOf(inputReward);
							if(number > 0) {
								facade.createBet(inputQuery, number, question);
								jLabelError.setText("The bet has been successfully created");
							}else {
								jLabelError.setText("The reward must be a positibe number");
							}
						}catch(NumberFormatException e2) {
							jLabelError.setText("The reward must be a positive number");
						}
					}else {
						jLabelError.setText("The bet already exists");
					}
				}
				
			}
			//} catch (EventAlreadyExist e1) {
			//	jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}

	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}
}