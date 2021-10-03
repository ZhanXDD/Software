package utility;



import java.util.Date;
import java.util.Vector;

import javax.jws.WebMethod;

import configuration.ConfigXML;
import domain.Account;
import domain.CreditCard;
import domain.Event;
import domain.InfoAccount;
import domain.Team;

/**
 * Utilities to access Data Base
 * @author IS2
 *
 */
public class TestUtilityFacadeImplementation {
	private TestUtilityDataAccess dbManagerTest;


	public TestUtilityFacadeImplementation()  {			
		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML.getInstance();
		dbManagerTest=new TestUtilityDataAccess(); 
		dbManagerTest.close();
	}


	public boolean removeEvent(Event ev) {
		dbManagerTest.open();
		boolean b=dbManagerTest.removeEvent(ev);
		dbManagerTest.close();
		return b;

	}

	public Event addEventWithQuestion(Team a, Team b, Date d, String question, float qty) {
		dbManagerTest.open();
		Event o=dbManagerTest.addEventWithQuestion(a,b,d, question, qty);
		dbManagerTest.close();
		return o;

	}

	public Vector<Event> getEvents(Date date)  {
		dbManagerTest.open();
		Vector<Event>  events=dbManagerTest.getEvents(date);
		dbManagerTest.close();
		return events;
	}

	/**
	 * Method to add user and its information into the database
	 * 
	 * @param name     the real name
	 * @param lastName the last name
	 * @param email    the email
	 * @param nid      the dni
	 * @param user     the username
	 * @param password the password to log in
	 */
	@WebMethod
	public Account addUser(String user, String password) {
		dbManagerTest.open();
		Account ac = new Account(user, password);
		dbManagerTest.addUser(ac);
		dbManagerTest.close();
		return ac;
	}
	
	public Account addUser(Account ac) {
		dbManagerTest.open();
		dbManagerTest.addUser(ac);
		dbManagerTest.close();
		return ac;
	}

	/**
	 * Method to add a payment method to an account
	 * @param user The selected account
	 * @param e the payment method
	 */
	@WebMethod
	public void addPaymentMethod(Account user, CreditCard e) {
		dbManagerTest.open();
		dbManagerTest.addPaymentMethod(user,e);
		dbManagerTest.close();
	}
	
	/**
	 * Method to remove a payment method from an account
	 * @param user the account
	 * @param card the payment method
	 */
	@WebMethod
	public void removePaymentMethod(Account user, String card) {
		dbManagerTest.open();
		dbManagerTest.removePaymentMethod(user,card);
		dbManagerTest.close();
	}
	
	/**
	 * Method to remove an account
	 * @param user the account
	 */
	@WebMethod
	public void removeUser(Account user) {
		dbManagerTest.open();
		dbManagerTest.removeUser(user);
		dbManagerTest.close();
	}

}
