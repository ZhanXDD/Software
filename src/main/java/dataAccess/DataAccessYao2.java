package dataAccess;

//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Event;
import domain.Question;
import domain.Team;
import domain.Account;
import domain.Bet;
import domain.BetMade;
import domain.Coupon;
import domain.CreditCard;
import domain.InfoAccount;
import exceptions.BetAlreadyExist;
import exceptions.EventAlreadyExist;
import exceptions.NoPaymentMethodException;
import exceptions.PaymentMethodNotFound;
import exceptions.QuestionAlreadyExist;
import exceptions.TeamAlreadyExists;
import exceptions.UserNotInDBException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessYao2  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccessYao2(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccessYao2()  {	
		new DataAccessYao2(false);
	}


	/**
	 * This is the data access method that initializes the database with some events and questions.
	 * This method is invoked by the business logic (constructor of BLFacadeImplementation) when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	public void initializeDB(){

		/**db.getTransaction().begin();
		try {
			//UserData and UserNames
			Account ac1 = new Account("admin", "admin",null);
			ac1.setAdmin(true);
			Account ac2 = new Account("user1", "user1",null);
			Account ac3 = new Account("user2", "user2",null);
			Account ac4 = new Account("user3", "user3",null);
			Account ac5 = new Account("user4", "user4",null);
			Account ac6 = new Account("user5", "user5",null);
			Account ac7 = new Account("user6", "user6",null);
			Account ac8 = new Account("user7", "user7",null);
			Account ac9 = new Account("user8", "user8",null);
			Account ac10 = new Account("user9", "user9",null);
			
			String r = "Roberto";
			InfoAccount inf1 = new InfoAccount("999999999", r, "Riaño", "correoPersonal", ac1);
			InfoAccount inf2 = new InfoAccount("999999998", r, "Riaño", "correoPersonal", ac2);
			InfoAccount inf3 = new InfoAccount("999999997", r, "Riaño", "correoPersonal", ac3);
			InfoAccount inf4 = new InfoAccount("999999996", r, "Riaño", "correoPersonal", ac4);
			InfoAccount inf5 = new InfoAccount("999999995", r, "Riaño", "correoPersonal", ac5);
			InfoAccount inf6 = new InfoAccount("999999994", r, "Riaño", "correoPersonal", ac6);
			InfoAccount inf7 = new InfoAccount("999999993", r, "Riaño", "correoPersonal", ac7);
			InfoAccount inf8 = new InfoAccount("999999992", r, "Riaño", "correoPersonal", ac8);
			InfoAccount inf9 = new InfoAccount("999999991", r, "Riaño", "correoPersonal", ac9);
			InfoAccount inf10 = new InfoAccount("999999990", r, "Riaño", "correoPersonal", ac10);
			
			ac1.setInfoAccount(inf1);
			ac2.setInfoAccount(inf2);
			ac3.setInfoAccount(inf3);
			ac4.setInfoAccount(inf4);
			ac5.setInfoAccount(inf5);
			ac6.setInfoAccount(inf6);
			ac7.setInfoAccount(inf7);
			ac8.setInfoAccount(inf8);
			ac9.setInfoAccount(inf9);
			ac10.setInfoAccount(inf10);
			
			//Events and questions
			Calendar today = Calendar.getInstance();

			int month=today.get(Calendar.MONTH);
			month+=1;
			int year=today.get(Calendar.YEAR);
			if (month==12) { month=0; year+=1;}
			
			Team t1 = new Team("Atlético");
			Team t2 = new Team("Athletic");
			Team t3 = new Team("Eibar");
			Team t4 = new Team("Barcelona");
			Team t5 = new Team("Getafe");
			Team t6 = new Team("Celta");
			Team t7 = new Team("Alavés");
			Team t8 = new Team("Deportivo");
			Team t9 = new Team("Español");
			Team t10 = new Team("Villareal");
			Team t11 = new Team("Las Palmas");
			Team t12 = new Team("Sevilla");
			Team t13 = new Team("Malaga");
			Team t14 = new Team("Valencia");
			Team t15 = new Team("Girona");
			Team t16 = new Team("Leganés");
			Team t17 = new Team("Real Sociedad");
			Team t18 = new Team("Levante");
			Team t19 = new Team("Betis");
			Team t20 = new Team("Real Madrid");
			
			Event ev1=new Event(t1, t2, UtilDate.newDate(year,month,17));
			Event ev2=new Event(t3, t4, UtilDate.newDate(year,month,17));
			Event ev3=new Event(t5, t6, UtilDate.newDate(year,month,17));
			Event ev4=new Event(t7, t8, UtilDate.newDate(year,month,17));
			Event ev5=new Event(t9, t10, UtilDate.newDate(year,month,17));
			Event ev6=new Event(t11, t12, UtilDate.newDate(year,month,17));
			Event ev7=new Event(t13, t14, UtilDate.newDate(year,month,17));
			Event ev8=new Event(t15, t16, UtilDate.newDate(year,month,17));
			Event ev9=new Event(t17, t18, UtilDate.newDate(year,month,17));
			Event ev10=new Event(t19, t20, UtilDate.newDate(year,month,17));

			Event ev11=new Event(t1, t2, UtilDate.newDate(year,month,1));
			Event ev12=new Event(t3, t4, UtilDate.newDate(year,month,1));
			Event ev13=new Event(t5, t6, UtilDate.newDate(year,month,1));
			Event ev14=new Event(t7, t8, UtilDate.newDate(year,month,1));
			Event ev15=new Event(t9, t10, UtilDate.newDate(year,month,1));
			Event ev16=new Event(t11, t12, UtilDate.newDate(year,month,1));

			Event ev17=new Event(t13, t14, UtilDate.newDate(year,month+1,28));
			Event ev18=new Event(t15, t16, UtilDate.newDate(year,month+1,28));
			Event ev19=new Event(t17, t18, UtilDate.newDate(year,month+1,28));
			Event ev20=new Event(t19, t20, UtilDate.newDate(year,month+1,28));
			
			t1.addEvent(ev1);
			t1.addEvent(ev11);
			t2.addEvent(ev1);
			t2.addEvent(ev11);
			t3.addEvent(ev2);
			t3.addEvent(ev12);
			t4.addEvent(ev2);
			t4.addEvent(ev12);
			t5.addEvent(ev3);
			t5.addEvent(ev13);
			t6.addEvent(ev3);
			t6.addEvent(ev13);
			t7.addEvent(ev4);
			t7.addEvent(ev14);
			t8.addEvent(ev4);
			t8.addEvent(ev14);
			t9.addEvent(ev5);
			t9.addEvent(ev15);
			t10.addEvent(ev5);
			t10.addEvent(ev15);
			t11.addEvent(ev6);
			t11.addEvent(ev16);
			t12.addEvent(ev6);
			t12.addEvent(ev16);
			t13.addEvent(ev7);
			t13.addEvent(ev17);
			t14.addEvent(ev7);
			t14.addEvent(ev17);
			t15.addEvent(ev8);
			t15.addEvent(ev18);
			t16.addEvent(ev8);
			t16.addEvent(ev18);
			t17.addEvent(ev9);
			t17.addEvent(ev19);
			t18.addEvent(ev9);
			t18.addEvent(ev19);
			t19.addEvent(ev10);
			t19.addEvent(ev20);
			t20.addEvent(ev10);
			t20.addEvent(ev20);
			
			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1=ev1.addQuestion("¿Quién ganará el partido?",1);
				q2=ev1.addQuestion("¿Quién meterá el primer gol?",2);
				q3=ev11.addQuestion("¿Quién ganará el partido?",1);
				q4=ev11.addQuestion("¿Cuántos goles se marcarán?",2);
				q5=ev17.addQuestion("¿Quién ganará el partido?",1);
				q6=ev17.addQuestion("¿Habrá goles en la primera parte?",2);
			}
			else if (Locale.getDefault().equals(new Locale("en"))) {
				q1=ev1.addQuestion("Who will win the match?",1);
				q2=ev1.addQuestion("Who will score first?",2);
				q3=ev11.addQuestion("Who will win the match?",1);
				q4=ev11.addQuestion("How many goals will be scored in the match?",2);
				q5=ev17.addQuestion("Who will win the match?",1);
				q6=ev17.addQuestion("Will there be goals in the first half?",2);
			}			
			else {
				q1=ev1.addQuestion("Zeinek irabaziko du partidua?",1);
				q2=ev1.addQuestion("Zeinek sartuko du lehenengo gola?",2);
				q3=ev11.addQuestion("Zeinek irabaziko du partidua?",1);
				q4=ev11.addQuestion("Zenbat gol sartuko dira?",2);
				q5=ev17.addQuestion("Zeinek irabaziko du partidua?",1);
				q6=ev17.addQuestion("Golak sartuko dira lehenengo zatian?",2);

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6); 

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);	
			
			db.persist(inf1);
			db.persist(inf2);
			db.persist(inf3);
			db.persist(inf4);
			db.persist(inf5);
			db.persist(inf6);
			db.persist(inf7);
			db.persist(inf8);
			db.persist(inf9);
			db.persist(inf10);
			
			db.persist(ac1);
			db.persist(ac2);
			db.persist(ac3);
			db.persist(ac4);
			db.persist(ac5);
			db.persist(ac6);
			db.persist(ac7);
			db.persist(ac8);
			db.persist(ac9);
			db.persist(ac10);

			Team teams[] = {t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20};
			for(Team t: teams) {
				db.persist(t);
			}
			
			db.getTransaction().commit();
			System.out.println("Db initialized");
		}
		catch (Exception e){
			e.printStackTrace();
		}**/
	}

	public void open(boolean initializeMode){

		System.out.println("Opening DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		String fileName=c.getDbFilename();
		if (initializeMode) {
			fileName=fileName+";drop";
			System.out.println("Deleting the DataBase");
		}else {
			System.out.println("Not deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
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
		a1.addToWallet(amount);
		db.persist(a1);
		db.getTransaction().commit();
		return a1.getWallet();
	}
}