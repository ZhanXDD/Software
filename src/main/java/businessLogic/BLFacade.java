package businessLogic;

import java.util.Vector;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//import domain.Booking;
import domain.Question;
import domain.Team;
import domain.Account;
import domain.Bet;
import domain.BetMade;
import domain.Coupon;
import domain.CreditCard;
import domain.Event;

import exceptions.BetAlreadyExist;
import exceptions.EventAlreadyExist;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.TeamAlreadyExists;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {

	/**
	 * Returns the logged user account
	 * 
	 * @return Account of the logged user
	 */

	public String getCurrentUserAccount();

	/**
	 * Sets the logged user by the given one
	 * 
	 * @param currentUserAccount the new logged user account
	 */
	public void setCurrentUserAccount(String currentUserAccount);
	
	
	
	
	/**
	 * This method invokes the data access to attempt to create an event
	 * @throws if event already exists
	 * @param date in which the event is created
	 * @param des the description about the event
	 * @return the created event
	 */
	@WebMethod public Event createEvent(Date date, Team a, Team b) throws EventAlreadyExist;

	/**
	 * This method finds a specific event 
	 * @param event
	 * @param date
	 * @return boolean if
	 */
	@WebMethod public boolean findEvent(String event, Date date);


	/**
	 * This method retrieves the events of a given date 
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod public Vector<Event> getEvents(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved 
	 * @return collection of dates
	 */
	@WebMethod public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method creates a question for an event, with a question text and the minimum bet
	 * 
	 * @param event to which question is added
	 * @param question text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the event
	 */
	@WebMethod Question createQuestion(Event event, String question, float betMinimum) throws EventFinished, QuestionAlreadyExist;

	/**
	 * Method to find all questions of an event
	 * @param ev the event
	 * @return a vector of question of the event ev
	 */
	@WebMethod public Vector<Question> getQuestions(Event ev);

	/**
	 * Method to find all questions of an event
	 * @param ev the event
	 * @return a collections of question of the event ev
	 */
	@WebMethod public List<Question> findAllQuestion(Event ev);

	/**
	 * This method invokes the data access to create a bet
	 * @throws if bet already exists
	 * @param bet a description of the bet
	 * @param prize the prize multiplier
	 * @param q the question where the bet is created
	 * @return the created bet
	 */
	@WebMethod public Bet createBet(String bet, float money, Question q) throws BetAlreadyExist;
	
	/**
	 * Method to find a specific bet of a question
	 * @param bet the bet
	 * @param q the question the bet is related to
	 * @return boolean, if bet is found then true else false
	 */
	@WebMethod public boolean findBet(String inputQuery, Question q);

	/**
	 * Method to Log in to MainGUI
	 * @param userName the username of the account
	 * @param password the password of the account
	 * @return boolean, if log in successful then true else false
	 */
	@WebMethod public boolean logIn(String userName, String password);

	/**
	 * Method to check if logged user is admin
	 * @param user the user to check
	 * @return boolean if user is admin then true else false
	 */
	@WebMethod public boolean isAdmin(String user);

	/**
	 * Method to add user and its information into the database
	 * @param name the real name
	 * @param lastName the last name
	 * @param email the email
	 * @param nid the dni
	 * @param user the username
	 * @param password the password to log in
	 */
	@WebMethod public void addUser(String name, String lastName, String email, String nid, String user, String password);

	/**
	 * Method to check if inserted user is a valid user
	 * @param userName the username inserted
	 * @param pass the password inserted
	 * @return boolean if user is valid then true else false
	 */
	@WebMethod public boolean checkUser(String userName);

	/**
	 * Method to check if nid is a valid nid
	 * @param nid the nid
	 * @param name the real name
	 * @param lastName the last name
	 * @param email the email
	 * @return boolean if nid is valid then true else false
	 */
	@WebMethod public boolean checkNid(String nid);

	/**
	 * Method to check if dni has DNI format
	 * @param nid the nid
	 * @param name the real name
	 * @param lastName the last name
	 * @param email the email
	 * @return boolean, if dni has dni format then true else false
	 */
	@WebMethod  public boolean dniValido(String nid);

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();
	
	/**
	 * Method to add a bet made by a user
	 * @param user the user that made the bet
	 * @param bet the bet it belongs to
	 * @param money the money the user gambled
	 */
	@WebMethod public void addBetMade(Account user, Bet bet, float money);

	/**
	 * Method to get bet of a question
	 * @param ev the event
	 * @param q the question
	 * @return the bet
	 */
	@WebMethod public Bet getBet(Event ev, Question q);

	/**
	 * Method to get an account
	 * @param user the user name of the account
	 * @param pass the password of the account
	 * @return the account
	 */
	@WebMethod public Account getUser(String user);

	/**
	 * Method to close event
	 * @param event the event to close
	 */
	@WebMethod public void closeEvent(Event event);

	/**
	 * Method to get all bets of a question
	 * @param event the event
	 * @param question the question
	 * @return a list of bets that belong to the question that belongs to the event
	 */
	@WebMethod public List<Bet> findAllBets(Event event, Question question);

	/**
	 * Method to set winner of a bet
	 * @param bet the bet to set
	 */
	@WebMethod public void setWinnerBet(Bet bet);
	
	/**
	 * Method to add a coupon
	 * @param amount the prize amount of the coupon
	 */
	@WebMethod public Coupon addCoupon(Integer amount, String code);
	
	/**
	 * This method finds the given coupon
	 * @param coupon
	 * @return boolean
	 */
	@WebMethod public boolean findCoupon(String coupon);
	
	/**
	 * If the given method is in the database, it is redeem by the user
	 * @param coupon
	 */
	@WebMethod public void redeemCoupon(String userName, String coupon);
	
	/**
	 * Method to add money to an account from a payment method, if entered a negative amount it will withdraw it.
	 * @param user The selected account
	 * @param e The selected payment method
	 * @param amount the money amount to add
	 */
	@WebMethod public void addMoney(Account user,String e, float amount);


	/**
	 * Method to add a payment method to an account
	 * @param user The selected account
	 * @param e the payment method
	 */
	@WebMethod public void addPaymentMethod(Account user,CreditCard e);

	/**
	 * Method to remove a payment method from an account
	 * @param user the account
	 * @param card the payment method
	 */
	@WebMethod public void removePaymentMethod(Account user,String card);
	
	/**
	 * Method to get all the payment methods of an account
	 * @param user the account
	 * @return A vector of all the payment methods
	 */
	@WebMethod public LinkedList<CreditCard> getAllPaymentMethods(Account user);
	
	/**
	 * Method to get a coupon from the data base
	 * @param coupon the code of the coupon to search
	 * @return the coupon
	 */
	@WebMethod public Coupon getCoupon(String coupon);

	/**
	 * Method to get all bets made by a user
	 * @param ac the account to get all bets made
	 * @return a collection of bets made by the account
	 */
	@WebMethod public List<BetMade> getBetsMade(Account ac);
	
	/**
	 * Method to get all active events
	 * @return a list of all events
	 */
	@WebMethod public List<Event> getAllEvents();
	
	/**
	 * Method to get all bets made from an active event
	 * @param ev the event
	 * @return a list of bets made
	 */
	@WebMethod public List<BetMade> getBetsFromEvents(Event ev);
	
	/**
	 * Method to get all teams
	 * @return a list of teams
	 */
	@WebMethod public List<Team> getAllTeams();
	
	/**
	 * Method to add a team into the database
	 * @param name the name of the team
	 * @return the added team
	 */
	@WebMethod public Team addTeam(String name) throws TeamAlreadyExists;


}