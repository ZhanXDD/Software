package domain;

import java.util.LinkedList;
import java.util.Vector;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Account {
    @Id
    private String userName;
    
    private String password;
    
    private float wallet;
    
    private boolean admin; //true si es admin
    @OneToOne
    private InfoAccount InfoAccount;
    
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    private Vector<Coupon> coupons;
    
    private LinkedList<CreditCard> paymentMethods;
    
    @OneToMany
    private Vector<BetMade> betsMade;
    
    public Account() {
    	super();
    }
    
    public Account(String User, String Password, InfoAccount InfoAccount) {
        this.userName = User;
        this.password = Password;
        this.admin = false;
        this.InfoAccount = InfoAccount; 
        this.wallet = 10;
        coupons = new Vector<Coupon>();
        paymentMethods = new LinkedList<CreditCard>();
        betsMade = new Vector<BetMade>();
    }
    ///////////////
    //TEMPORAL////////////////////////////////////////////////////////////////////////////////////////////////
    public Account(String User, String Password) {
        this.userName = User;
        this.password = Password;
        this.admin = false;
        coupons = new Vector<Coupon>();
        paymentMethods = new LinkedList<CreditCard>();
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String User) {
        this.userName = User;
    }

    public String getPassword() {
        return password;
    }
    
    public float getWallet() {
    	return wallet;
    }

    public void setWallet(float money) {
    	wallet = money;
    }
    
    public void setPassword(String Password) {
        this.password = Password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
    
    public Vector<Coupon> getCoupons(){
    	return coupons;
    }

    public InfoAccount getInfoAccount() {
        return InfoAccount;
    }

    public void setInfoAccount(InfoAccount infoAccount) {
        this.InfoAccount = infoAccount;
    }
  
    /**
     * This method add the coupon to the users used coupons list
     * @param coupon1 the coupon to be added
     */
    public void addCoupon(Coupon coupon1) {
    	this.coupons.add(coupon1);
    }
    
    /**
     * This methods looks if the given coupon is already used by the user
     * @param coupon1 the coupon that needs to be checked if is used
     * @return true if is used, false if not used
     */
    public boolean doesCouponAlreadyUsed(Coupon coupon1) {
    	if (this.coupons!=null) {
    		for(Coupon coupon2: this.coupons) {
    			if(coupon2.equals(coupon1)) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * Method to add a betMade to the list of bets made
     * @param betMade the bet made to add
     */
    public void addBetMade(BetMade betMade) {
    	this.betsMade.add(betMade);
    }
    
    /**
     * Method to get the list of bets made
     * @return the list of bets made
     */
    public Vector<BetMade> getBetMade() {
    	return this.betsMade;
    }
    
    @Override
    public String toString() {
        return "User: "+userName+ ", Contrasenia: "+ password+", "+"Coupons: "+coupons;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (userName != other.userName)
            return false;
        return true;
    }
    
    /**
     * Method to withdraw money from the wallet
     * @param payment the selected payment method to add the money to
     * @param amount the amount of money desired to withdraw
     * @return the final quantity of the wallet
     */
    public float withdrawFromWallet(CreditCard e, float amount) {
    	return wallet-=amount;
    }
    
    /**
     * Method to get all the payment methods of the account
     * @return An array list of the payment methods
     */
    public LinkedList<CreditCard> getAllPaymentMethods() {
    	return paymentMethods;
    }
    
   /**
    * Method to get one of the payment methods of the account
    * @param index the index of the desired payment method in the ayyar list
    * @return A string containing the selected method
    */
     public CreditCard getOnePaymentMethod(int index) {
    	return paymentMethods.get(index);
    }
     
     /**
      * Method to add one payment method to the account
      * @param payment the desired payment method
      * @return An array list of the payment methods
      */
    public LinkedList<CreditCard> addPaymentMethod(CreditCard e) {
    	paymentMethods.add(e);
    	return paymentMethods;	
    }
    
    /**
     * Method to remove a payment method
     * @param e the payment method
     */
    public void removePaymentMethod(CreditCard e) {
    	System.out.println(paymentMethods.remove(e));
    }
    
    /**
     * Method to add money to the wallet from a payment method
     * @param payment the selected payment method
     * @param amount the amount of money desired to add
     * @return the final quantity of the wallet
     */
    public float addToWallet(CreditCard e, float amount) {
    	return wallet+=amount;

    }
}