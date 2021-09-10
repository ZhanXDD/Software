package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Question;
import domain.Team;
import domain.Account;
import domain.Bet;
import domain.BetMade;
import domain.Coupon;
import domain.CreditCard;
import domain.Event;
import domain.InfoAccount;
import exceptions.BetAlreadyExist;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.TeamAlreadyExists;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;
	String currentUserAccount;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
			dbManager.close();
		}
		currentUserAccount="";

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			// da.open(false);
			// System.out.println("DB opened");
			da.close();

		}
		dbManager = da;
	}

	/**
	 * Returns the logged user account
	 * 
	 * @return Account of the logged user
	 */

	public String getCurrentUserAccount() { return currentUserAccount; }

	/**
	 * Sets the logged user by the given one
	 * 
	 * @param currentUserAccount the new logged user account
	 */
	public void setCurrentUserAccount(String currentUserAccount) {
		this.currentUserAccount = currentUserAccount; }





	/**
	 * This method invokes the data access to attempt to create an event
	 * 
	 * @throws if event already exists
	 * @param date in which the event is created
	 * @param des  the description about the event
	 * @return the created event
	 */
	@WebMethod
	public Event createEvent(Date date, Team a, Team b) throws EventAlreadyExist{
		dbManager.open(false);
		Event ev = dbManager.createEvent(date, a, b);
		Team teamA = dbManager.getTeam(a);
		Team teamB = dbManager.getTeam(b);
		teamA.addEvent(ev);
		teamB.addEvent(ev);
		dbManager.updateTeam(teamA);
		dbManager.updateTeam(teamB);
		dbManager.close();
		return ev;
	}

	/**
	 * This method finds a specific event
	 * 
	 * @param event
	 * @param date
	 * @return boolean if
	 */
	@WebMethod
	public boolean findEvent(String event, Date date) {
		dbManager.open(false);
		boolean bool = dbManager.findEvent(event, date);
		dbManager.close();
		return bool;
	}

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum);

		dbManager.close();

		return qry;
	};

	/**
	 * Method to find all questions of an event
	 * 
	 * @param ev the event
	 * @return a collections of question of the event ev
	 */
	@WebMethod
	public List<Question> findAllQuestion(Event ev) {
		dbManager.open(false);
		List<Question> list = dbManager.findAllQuestion(ev);
		dbManager.close();
		return list;
	}

	/**
	 * Method to find all questions of an event
	 * 
	 * @param ev the event
	 * @return a vector of question of the event ev
	 */
	@WebMethod
	public Vector<Question> getQuestions(Event ev) {
		dbManager.open(false);
		Vector<Question> q = dbManager.getQuestions(ev);
		dbManager.close();
		return q;
	}

	/**
	 * This method invokes the data access to create a bet
	 * 
	 * @throws if bet already exists
	 * @param bet   a description of the bet
	 * @param prize the prize multiplier
	 * @param q     the question where the bet is created
	 * @return the created bet
	 */
	public Bet createBet(String bet, float prize, Question q) throws BetAlreadyExist {
		dbManager.open(false);
		Bet auxBet = dbManager.createBet(bet, prize, q);

		dbManager.close();
		return auxBet;
	}

	/**
	 * Method to find a specific bet of a question
	 * @param bet the bet
	 * @param q   the question the bet is related to
	 * @return boolean, if bet is found then true else false
	 */
	@WebMethod
	public boolean findBet(String bet, Question question) {
		dbManager.open(false);
		boolean bool = dbManager.findBet(bet, question);
		dbManager.close();
		return bool;
	}

	/**
	 * Method to Log in to MainGUI
	 * 
	 * @param userName the username of the account
	 * @param password the password of the account
	 * @return boolean, if log in successful then true else false
	 */
	@WebMethod
	public boolean logIn(String userName, String password) {
		dbManager.open(false);
		Account ac = dbManager.getUserAccount(userName);
		dbManager.close();

		if (ac != null) {
			if (password.equals(ac.getPassword())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Method to check if logged user is admin
	 * 
	 * @param user the user to check
	 * @return boolean if user is admin then true else false
	 */
	@WebMethod
	public boolean isAdmin(String user) {
		dbManager.open(false);
		Account a = dbManager.getUserAccount(user);
		dbManager.close();
		return a.isAdmin();

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
	public void addUser(String name, String lastName, String email, String nid, String user, String password) {
		dbManager.open(false);
		Account ac = new Account(user, password);
		InfoAccount inf = new InfoAccount(nid, name, lastName, email, ac);
		ac.setInfoAccount(inf);

		dbManager.addUser(ac);
		dbManager.addUserAccount(inf);

		dbManager.close();
	}

	/**
	 * Method to check if inserted user is a valid user
	 * 
	 * @param userName the username inserted
	 * @return boolean if user is valid then true else false
	 */
	@WebMethod
	public boolean checkUser(String userName) {
		dbManager.open(false);
		boolean bool = dbManager.checkUser(userName);
		dbManager.close();
		return bool;
	}

	/**
	 * Method to check if nid is a valid nid
	 * 
	 * @param nid the nid
	 * @return boolean if nid is valid then true else false
	 */
	@WebMethod
	public boolean checkNid(String nid) {
		dbManager.open(false);
		boolean bool = dbManager.checkNid(nid);
		dbManager.close();
		return bool;
	}

	/**
	 * Method to check if dni has DNI format
	 * 
	 * @param nid the nid
	 * @return boolean, if dni has dni format then true else false
	 */
	@WebMethod
	public boolean dniValido(String nid) {
		return nid.matches("[0-9]{8}[A-Z]");
	}

	// TODO: Comment method
	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * Method to add a bet made by a user
	 * @param user the user that made the bet
	 * @param bet the bet it belongs to
	 * @param money the money the user gambled
	 */
	@WebMethod
	public void addBetMade(Account user, Bet bet, float money) {
		dbManager.open(false);
		Account update = dbManager.getUserAccount(user.getUser());
		BetMade madebet = new BetMade(user,bet, money);
		update.addBetMade(madebet);
		update.setWallet(dbManager.setWallet(update,update.getWallet()-money));
		dbManager.addBetMade(madebet);
		dbManager.updateAccount(update);
		dbManager.close();
	}

	/**
	 * Method to get bet of a question
	 * @param ev the event
	 * @param q the question
	 * @return the bet
	 */
	@WebMethod
	public Bet getBet(Event ev, Question q) {
		dbManager.open(false);
		Bet bet = dbManager.getBet(ev, q);
		dbManager.close();
		return bet;
	}

	/**
	 * Method to get an account
	 * @param user the user name of the account
	 * @param pass the password of the account
	 * @return the account
	 */
	@WebMethod
	public Account getUser(String user) {
		dbManager.open(false);
		Account usuario = dbManager.getUserAccount(user);
		dbManager.close();
		return usuario;
	}

	/**
	 * Method to close event
	 * @param event the event to close
	 */
	@WebMethod
	public void closeEvent(Event event) {
		float wallet;
		float money;
		float reward;
		dbManager.open(false);
		// Vector<Question> questions = dbManager.getQuestions(event);
		Vector<Question> questions = event.getQuestions();
		for (Question q : questions) {
			ArrayList<Bet> bets = (ArrayList<Bet>) dbManager.getAllBet(event, q);

			for (Bet b : bets) {

				Vector<BetMade> betsMade = dbManager.getBetsMade(b);
				reward = b.getReward();

				for (BetMade bM : betsMade) {
					if (b.getWinner()) {
						wallet = bM.getUser().getWallet();
						money = bM.getMoneyBet();
						System.out.println(money);
						System.out.println(wallet);
						System.out.println(reward);
						// bM.getUser().setWallet(wallet + (money*reward));
						bM.getUser().setWallet(dbManager.setWallet(bM.getUser(), wallet + (money * reward)));
						bM.setWinner(true);
					}
					bM.setActive(false);

				}
			}
		}

		event = dbManager.closeEvent(event);
		dbManager.close();
	}

	/**
	 * Method to get all bets of a question
	 * @param event the event
	 * @param question the question
	 * @return a list of bets that belong to the question that belongs to the event
	 */
	@WebMethod
	public List<Bet> findAllBets(Event ev, Question b) {
		dbManager.open(false);
		List<Bet> list = dbManager.getAllBet(ev, b);
		dbManager.close();
		return list;
	}

	/**
	 * Method to set winner of a bet
	 * @param bet the bet to set
	 */
	@WebMethod
	public void setWinnerBet(Bet bet) {
		dbManager.open(false);
		bet = dbManager.setWinnerBet(bet);
		dbManager.close();
	}

	/**
	 * Method that creates and adds coupon into the database
	 * @param amount the coupon amount
	 */
	@WebMethod
	public Coupon addCoupon(Integer amount, String code) {
		Coupon c1 = new Coupon(amount,code);
		dbManager.open(false);
		dbManager.addCoupon(c1);
		dbManager.close();
		return c1;
	}

	/**
	 * This method finds the given coupon
	 * @param coupon
	 * @return boolean
	 */
	@WebMethod
	public boolean findCoupon(String coupon) {
		dbManager.open(false);
		boolean bool = dbManager.findCoupon(coupon);
		dbManager.close();
		return bool;
	}

	/**
	 * If the given method is in the database, it is redeem by the user
	 * @param coupon
	 */
	@WebMethod
	public void redeemCoupon(String userName, String coupon) {
		if (findCoupon(coupon)) {
			dbManager.open(false);
			dbManager.redeemCoupon(userName, coupon,getCurrentUserAccount());
			dbManager.close();
			//System.out.println(cu.getCoupons());
		}
	}
	
	/**
	 * Method to add money to an account from a payment method, if entered a negative amount it will withdraw it.
	 * @param user The selected account
	 * @param e The selected payment method
	 * @param amount the money amount to add
	 */
	@WebMethod
	public void addMoney(Account user, String e, float amount) {
		dbManager.open(false);
		dbManager.addMoney(user,e,amount);
		dbManager.close();
	}

	/**
	 * Method to add a payment method to an account
	 * @param user The selected account
	 * @param e the payment method
	 */
	@WebMethod
	public void addPaymentMethod(Account user, CreditCard e) {
		dbManager.open(false);
		dbManager.addPaymentMethod(user,e);
		//System.out.println(e.getCardNumber());
		dbManager.close();
	}

	/**
	 * Method to remove a payment method from an account
	 * @param user the account
	 * @param card the payment method
	 */
	@WebMethod
	public void removePaymentMethod(Account user, String card) {
		dbManager.open(false);
		dbManager.removePaymentMethod(user,card);
		dbManager.close();
	}

	/**
	 * Method to get all the payment methods of an account
	 * @param user the account
	 * @return A vector of all the payment methods
	 */
	@WebMethod
	public LinkedList<CreditCard> getAllPaymentMethods(Account user) {
		dbManager.open(false);
		
		LinkedList<CreditCard> cards = dbManager.getAllPaymentMethods(user);
		System.out.println(cards.size());
		dbManager.close();
		System.out.println(cards.size());
		return cards;
	}
	
	/**
	 * Method to get a coupon from the data base
	 * @param coupon the code of the coupon to search
	 * @return the coupon
	 */
	@WebMethod
	public Coupon getCoupon(String coupon) {
		dbManager.open(false);
		Coupon c = dbManager.getCoupon(coupon);
		dbManager.close();
		return c;
		
	}

	/**
	 * Method to get all bets made by a user
	 * @param the account to get all bets made
	 * @return a collection of bets made by the account
	 */
	@WebMethod
	public List<BetMade> getBetsMade(Account ac){
		List<BetMade> ret;
		dbManager.open(false);
		ret = dbManager.getBetsMadeUser(ac);
		dbManager.close();
		return ret;
	}
	
	/**
	 * Method to get all active events from database
	 * @return a list of all active events
	 */
	@WebMethod
	public List<Event> getAllEvents(){
		List<Event> ret;
		dbManager.open(false);
		ret = dbManager.getAllEvent();
		dbManager.close();
		return ret;
	}
	
	/**
	 * Method to get all bets made from an active event
	 * @param ev the event
	 * @return a list of bets made
	 */
	@WebMethod
	public List<BetMade> getBetsFromEvents(Event ev){
		List<Question> qList = new Vector<Question>();
		List<Bet> bList = new Vector<Bet>();
		List<BetMade> aux = new Vector<BetMade>();
		List<BetMade> ret = new Vector<BetMade>();
		
		dbManager.open(false);
		qList = ev.getQuestions();
		for(Question q: qList) {
			bList = dbManager.getAllBet(ev, q);
			for(Bet b: bList) {
				aux = dbManager.getBetsMade(b);
				//Necessary in order to store the BetMade in memory
				for(BetMade bm: aux) {
					ret.add(bm);
				}
			}
		}
		dbManager.close();
		return ret;
	}
	
	/**
	 * Method to get All teams
	 * @return a list of teams
	 */
	@WebMethod
	public List<Team> getAllTeams(){
		List<Team> ret = null;
		dbManager.open(false);
		ret = dbManager.getAllTeam();
		dbManager.close();
		return ret;
	}
	
	/**
	 * Method to add a team into the database
	 * @param name the name of the team
	 * @return the added team
	 */
	@WebMethod
	public Team addTeam(String name) throws TeamAlreadyExists{
		dbManager.open(false);
		dbManager.addTeam(name);
		dbManager.close();
		return null;
	}
}