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
public class DataAccess  {
	protected static EntityManager  db;
	protected static EntityManagerFactory emf;


	ConfigXML c=ConfigXML.getInstance();

	public DataAccess(boolean initializeMode)  {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: "+c.isDatabaseLocal()+" getDatabBaseOpenMode: "+c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess()  {	
		new DataAccess(false);
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

	/**
	 * This method creates an event
	 * @param event the event you want to create
	 * @return the created event
	 * @throws EventAlreadyExist if event already exists
	 */
	public Event createEvent(Date date, Team a, Team b) throws EventAlreadyExist{
		Event event = new Event(a, b, date);
		System.out.println(">> DataAccess: createEvent=> event= "+ event);
		
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.description = ?1 AND ev.eventDate = ?2",Event.class);
		query.setParameter(1, event.getDescription());
		query.setParameter(2, date);
		List<Event> list = query.getResultList();
		if(!list.isEmpty()) {
			System.out.println("The event already exists, throwing exception");
			throw new EventAlreadyExist();
		}
		//if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		db.persist(event);
		db.getTransaction().commit();
		return event;

	}

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws  QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= "+event+" question= "+question+" betMinimum="+betMinimum);
		if(event == null) return null;
		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		//db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;
	}

	
	
	
	
	
	
	
	
	
	public boolean findEvent(String description, Date date) {
    	TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.description = ?1 AND ev.eventDate = ?2",Event.class);
    	query.setParameter(1, description);
    	query.setParameter(2, date);
    	List<Event> list = query.getResultList();
    	return !list.isEmpty();
    }

	public boolean findBet(String bet, Question question) {
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.bet = ?1 AND b.question = ?2",Bet.class);
    	query.setParameter(1, bet);
    	query.setParameter(2, question);
    	List<Bet> list = query.getResultList();
    	return !list.isEmpty();
	}
    
	public Bet createBet(String bet, Float prize, Question q) throws BetAlreadyExist {
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.bet = ?1 AND b.question = ?2",Bet.class);
        query.setParameter(1, bet);
        query.setParameter(2, q);
        List<Bet> list = query.getResultList();
        if(!list.isEmpty()) {
            throw new BetAlreadyExist();
        }
        System.out.println(">> DataAccess: createBet=> event= "+bet);
        //if (ev.DoesQuestionExists(question)) throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
        db.getTransaction().begin();
        Bet bet2= new Bet(bet,prize,q);
        q.addBet(bet2);
        db.persist(bet2);// db.persist(q) not required when CascadeType.PERSIST is added in questions property of Event class
                        // @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
        db.merge(q);
        db.getTransaction().commit();
        return bet2;
    }
    
	
    public List<Question> findAllQuestion(Event ev){
    	Event auxEv = db.find(Event.class,ev.getEventNumber());
    	return auxEv.getQuestions();
    }
    
    
    /**
     * Check if there is an existing account in database
     * True if there is NOT
     * @param ac
     * @return
     */
    public boolean checkUser(String s) {
    	Account auxAc = db.find(Account.class, s);
    	return auxAc == null; 
    }
    
    /**
     * Check if there is an existing accountInfo in database
     * True if there is NOT
     * @param ac
     * @return
     */
    public boolean checkNid(String info) {
    	boolean exists=false;
    	InfoAccount inf = db.find(InfoAccount.class,info);
    	if(inf != null) exists=true;
    	return exists;
    }
    
  
    
	
	/**
	 * This method retrieves from the database the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
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

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();	

		Date firstDayMonthDate= UtilDate.firstDayMonth(date);
		Date lastDayMonthDate= UtilDate.lastDayMonth(date);


		TypedQuery<Date> query = db.createQuery("SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2",Date.class);   
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d:dates){
			System.out.println(d.toString());		 
			res.add(d);
		}
		return res;
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
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			db = emf.createEntityManager();
		}
	}
	
	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= "+event+" question= "+question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);
	}
	
	
	/**
	 * Method to Log In
	 */
	public Account getUserAccount(String userName) {
		Vector<Account> a = new Vector<Account>();
		TypedQuery<Account> query = db.createQuery("SELECT a FROM Account a WHERE a.userName=?1", Account.class);
		query.setParameter(1, userName);
		List<Account> account = query.getResultList();
		for(Account a1 : account) {
			a.add(a1);	
		}
		return a.get(0);

	}
	
	
	public void close(){
		db.close();
		System.out.println("DataBase closed");
	}

	public Account addUser(Account ac) {
		Account c = db.find(Account.class, ac.getUser());
		if(c!=null) {
			System.out.println("Esta cuenta ya esta en la base de datos");;
		}
		db.getTransaction().begin();
//		Account c2 = ac.getInfoAccount().setaccount(ac);
//		db.persist(c2);
		db.persist(ac);
		db.getTransaction().commit();

//		return c2;
		return ac;
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

	public Vector<Question> getQuestions(Event ev) {
		System.out.println();
		Vector<Question> q = new Vector<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event=?1", Question.class);
		query.setParameter(1, ev);
		List<Question> questions = query.getResultList();
		for(Question q1 : questions) {
			q.add(q1);
			//System.out.println(q1.toString());
		}
		return q;
	}
	public BetMade addBetMade(BetMade bet) {
		BetMade bet2 = db.find(BetMade.class, bet.getIdBetMade());
		if(bet2!=null) {
			System.out.println("Esta apuesta realizada ya esta en la base de datos");;
		}
		db.getTransaction().begin();
		db.persist(bet);
		db.getTransaction().commit();
		
		return bet;
	}
	
	public Bet getBet(Event ev, Question q) {
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.question=?2", Bet.class);

		query.setParameter(2,q);
		
		List<Bet> bets = query.getResultList();
		
		return bets.get(0);
	}
	
	public List<Bet> getAllBet(Event ev, Question q) {
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE  b.question=?1", Bet.class);

		query.setParameter(1,q);
		
		List<Bet> bets = query.getResultList();
		
		return bets;
	}
	
	public Vector<BetMade> getBetsMade(Bet bet){
        Vector<BetMade> q = new Vector<BetMade>();
        TypedQuery<BetMade> query = db.createQuery("SELECT b FROM BetMade b WHERE b.bet=?1", BetMade.class);
        query.setParameter(1, bet);
        List<BetMade> betsMade = query.getResultList();
        for(BetMade q1 : betsMade) {
            q.add(q1);
        }
        return q;
    }

	public Event closeEvent(Event event) {
		Event ev = db.find(Event.class, event.getEventNumber());
		if(ev!=null) {
			db.getTransaction().begin();
			ev.setClosed();
			db.persist(ev);
			db.getTransaction().commit();
		}
		return ev;
	}

	public float setWallet(Account user, float money) {
		Account ac = db.find(Account.class, user.getUser());
		if(ac!=null) {
			db.getTransaction().begin();
			ac.setWallet(money);
			db.persist(ac);
			db.getTransaction().commit();
		}
		return money;
	}

	public Bet setWinnerBet(Bet bet) {
		Bet b1 = db.find(Bet.class, bet.getBetNumber());
		if(b1!=null) {
			db.getTransaction().begin();
			b1.setWinner();
			db.persist(b1);
			db.getTransaction().commit();
		}
		return b1;
	}
	
	/**
	 * This method adds to the database the given coupon(if is not already in the database)
	 * @param coupon the coupon to add
	 * @return the given coupon
	 */
	public Coupon addCoupon(Coupon coupon) {
		Coupon c1 = db.find(Coupon.class, coupon.getCouponCode());
		if(c1!=null) {
			System.out.println("This coupon is already created");
			return null;
		}
		db.getTransaction().begin();
		db.persist(coupon);
		db.getTransaction().commit();
		return coupon;
	}
	
	/**
	 * This method checks if the given coupon is in the database
	 * @param coupon
	 * @return true if the coupon is in the database
	 */
	public boolean findCoupon(String coupon) {
		TypedQuery<Coupon> query = db.createQuery("SELECT c FROM Coupon c WHERE c.couponCode = ?1",Coupon.class);
    	query.setParameter(1, coupon);
    	List<Coupon> list = query.getResultList();
    	return !list.isEmpty();
    }
	
	/**
	 * Method to redeem a coupon to an account, either your account or any other
	 * @param userName the account to add the money 
	 * @param couponCode the code of the coupon to redeem
	 * @param userName2 the account that is redeeming the coupon
	 */
	public Account redeemCoupon(String userName, String couponCode, String userName2) {
		
		//Account ac = getUserAccount(userName);
		Account ac = db.find(Account.class, userName);
		Account ac2 = db.find(Account.class, userName2);
		if (ac!=null && ac2!=null) {
			//Integer code = Integer.parseInt(coupon);
			Coupon c = db.find(Coupon.class, couponCode);
			
			if (!ac2.doesCouponAlreadyUsed(c)) {
				db.getTransaction().begin();
				System.out.println(ac2.getCoupons()+"   1");
				
				ac2.addCoupon(c);
				ac.setWallet(ac.getWallet()+c.getAmount());
				System.out.println(ac2.getCoupons()+"   2");
				db.persist(ac);
				//db.persist(ac2);
				db.merge(ac2);
				db.getTransaction().commit();
				System.out.println(ac2.getCoupons()+"   3");
				System.out.println(db.find(Account.class, ac2.getUser()).getCoupons()+"    9");
	
			}
			//print para pruebas
			else {
				System.out.println("Cupón ya utilizado");
			}
		}
		return ac2;
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

	public LinkedList<CreditCard> getAllPaymentMethods(Account user) {
		LinkedList<CreditCard> payments = new LinkedList<CreditCard>();
		Account account = db.find(Account.class,user.getUser());
		if(account!=null) {
			payments=account.getAllPaymentMethods();
		}
		return payments;
		
	}

	public Coupon getCoupon(String coupon) {
		return db.find(Coupon.class, coupon);
	}
	
	/**
	 * Method to update Account into the database
	 * @param ac the account to update
	 * @return the updated account
	 */
	public Account updateAccount(Account ac) {
		System.out.println("Updating user: " + ac.toString());
		db.getTransaction().begin();
		db.merge(ac);
		db.getTransaction().commit();
		return ac;
	}

	/**
	 * Method to get Bets made by the user
	 * @param ac the account 
	 * @return a list of bets made by the account
	 */
	public List<BetMade> getBetsMadeUser(Account ac){
		List<BetMade> list = new Vector<BetMade>();
		System.out.println("Get all bets made by user: " + ac.getUser());
		Account ac2 = db.find(Account.class, ac);
		for(BetMade bet: ac2.getBetMade()) {
			list.add(bet);
		}
		return list;
	}
	
	/**
	 * Method to get all events
	 * @return a list of events
	 */
	public List<Event> getAllEvent(){
		List<Event> ret = new Vector<Event>();
		System.out.println("Get all events");
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev", Event.class);
		List<Event> list = query.getResultList();
		for(Event ev: list) {
			ret.add(ev);
		}
		return ret;
	}
	
	/**
	 * Method to get all teams
	 * @return a list of teams
	 */
	public List<Team> getAllTeam(){
		List<Team> ret = new Vector<Team>();
		System.out.println("Get all Teams");
		TypedQuery<Team> query = db.createQuery("SELECT t FROM Team t", Team.class);
		List<Team> list = query.getResultList();
		for(Team t: list) {
			ret.add(t);
		}
		return ret;
	}
	
	/**
	 * Method to get a team
	 * @param t the team to find
	 * @return the team found
	 */
	public Team getTeam(Team t) {
		List<Team> ret = new Vector<Team>();
		System.out.println("Get team: " + t.getName());
		TypedQuery<Team> query = db.createQuery("SELECT t FROM Team t WHERE t.name=?1", Team.class);
		query.setParameter(1, t.getName());
		List<Team> list = query.getResultList();
		for(Team aux: list) {
			ret.add(aux);
		}
		return ret.get(0);
	}
	
	/**
	 * Method to update team
	 * @param t the team to update
	 * @return the updated team
	 */
	public Team updateTeam(Team t) {
		System.out.println("Updating team: " + t.toString());
		db.getTransaction().begin();
		db.merge(t);
		db.getTransaction().commit();
		return t;
	}
	
	/**
	 * Method to add a Team to the database
	 * @param name the name of the team
	 * @return the added team
	 * @throws TeamAlreadyExists throws when a team with the same name already exists
	 */
	public Team addTeam(String name) throws TeamAlreadyExists {
		Team ret = new Team(name);
		if(db.find(Team.class, name) != null) throw new TeamAlreadyExists();
		db.getTransaction().begin();
		db.persist(ret);
		db.getTransaction().commit();
		return ret;
	}
}