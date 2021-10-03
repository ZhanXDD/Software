package utility;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Account;
import domain.CreditCard;
import domain.Event;
import domain.InfoAccount;
import domain.Team;
import exceptions.NoPaymentMethodException;
import exceptions.PaymentMethodNotFound;
import exceptions.UserNotInDBException;

public class TestUtilityDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestUtilityDataAccess()  {		
		System.out.println("Creating TestDataAccess instance");

		open();		
	}


	public void open(){

		System.out.println("Opening TestDataAccess instance ");

		String fileName=c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}

	}
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e!=null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else 
			return false;
	}

	public Event addEventWithQuestion(Team a, Team b, Date d, String question, float qty) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev=null;
		db.getTransaction().begin();
		try {
			ev=new Event(a,b,d);
			ev.addQuestion(question,  qty);
			db.persist(ev);
			db.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return ev;
	}

	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();	
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1",Event.class);   
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev:events){
			System.out.println(ev.toString());		 
			res.add(ev);
		}
		return res;
	}

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	/**
	 * Method to add money to an account from a payment method
	 * @param user The selected account
	 * @param e The selected payment method
	 * @param amount The money amount to add
	 * @return The final amount of the wallet
	 */
	public float addMoney(Account user, String e, float amount) 
			throws UserNotInDBException, NoPaymentMethodException, PaymentMethodNotFound{
		Account a1 = db.find(Account.class, user.getUser());
		String c ="";
		CreditCard credit=null;
		if(a1 == null) throw new UserNotInDBException();
		if(a1.getAllPaymentMethods().isEmpty())	throw new NoPaymentMethodException();
		for(int i = 0 ; i<a1.getAllPaymentMethods().size(); i++) {
			c=a1.getOnePaymentMethod(i).getCardNumber();
			if(c.equals(e)) {
				credit = a1.getOnePaymentMethod(i);
				break;
			}
		}
		if(credit == null) throw new PaymentMethodNotFound();
		db.getTransaction().begin();
		a1.addToWallet(credit, amount);
		db.persist(a1);
		db.getTransaction().commit();
		return a1.getWallet();
	}

	public Account addUser(Account ac) {
		Account c = db.find(Account.class, ac.getUser());
		if(c!=null) {
			System.out.println("Esta cuenta ya esta en la base de datos");;
		}
		db.getTransaction().begin();
		db.persist(ac);
		db.getTransaction().commit();
		return ac;
	}

	/**
	 * Method to add a payment method to an account
	 * @param user The selected account
	 * @param e the payment method
	 */
	public void addPaymentMethod(Account user, CreditCard e) {
		Account a1 = db.find(Account.class, user.getUser());
		if(a1!=null) {
			db.getTransaction().begin();
			a1.addPaymentMethod(e);
			db.persist(e);
			System.out.println(e.getCardNumber());
			db.persist(a1);
			System.out.println(a1.getAllPaymentMethods());
			db.getTransaction().commit();
		}
	}
	
	public boolean removeUser(Account us) {
		System.out.println(">> DataAccessTest: removeUser");
		Vector<Account> a = new Vector<Account>();
		TypedQuery<Account> query = db.createQuery("SELECT a FROM Account a WHERE a.userName=?1", Account.class);
		query.setParameter(1, us.getUser());
		List<Account> account = query.getResultList();
		for(Account a1 : account) {
			a.add(a1);	
		}
		if (a.get(0)!=null) {
			db.getTransaction().begin();
			db.remove(a.get(0));
			db.getTransaction().commit();
			return true;
		} else 
			return false;
	}
	
	/**
	 * Method to remove a payment method from an account
	 * @param user the account
	 * @param card the payment method
	 */
	public void removePaymentMethod(Account user, String card) {
		String c = "";
		CreditCard credit=null;
		Account a1 = db.find(Account.class, user.getUser());
		if(a1!=null) {
			for(int i = 0 ; i<a1.getAllPaymentMethods().size(); i++) {
				c=a1.getOnePaymentMethod(i).getCardNumber();
				if(c.equals(card)) {
					credit = a1.getOnePaymentMethod(i);
					db.getTransaction().begin();
					a1.removePaymentMethod(credit);
					db.persist(a1);
					db.remove(credit);
					db.getTransaction().commit();
					break;
				}
			}	
		}
	}
	
	public InfoAccount addUserAccount(InfoAccount inf) {
		InfoAccount c = db.find(InfoAccount.class, inf.getDni());
		if(c!=null) {
			System.out.println("Esta infocuenta ya esta en la base de datos");;
		}
		db.getTransaction().begin();

		db.persist(inf);
		db.getTransaction().commit();

		return inf;
	}
}

