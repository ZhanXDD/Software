package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BetMade {
	
	@Id
	@GeneratedValue
	private Integer idBetMade;
	
	private static Integer numBetMade = 1;

	private Account user;
	
	private Bet bet;
	
	private boolean winner;
	
	private boolean isActive;	//true if the bet is active, false if it´s cancelled
	
	private float moneyBet;
	
	
	public BetMade(Account user, Bet bet, float moneyBet) {
		super();
		this.user = user;
		this.bet = bet;
		this.isActive = true;
		this.moneyBet = moneyBet;
		this.idBetMade = BetMade.numBetMade++;
	}
	
	public BetMade() {
		super();
	}
	
	public Integer getIdBetMade() {
		return idBetMade;
	}

	public void setIdBetMade(Integer idBetMade) {
		this.idBetMade = idBetMade;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public Bet getBet() {
		return bet;
	}

	public void setBet(Bet bet) {
		this.bet = bet;
	}

	public boolean isWinner() {
		return winner;
	}

	public void setWinner(boolean winner) {
		this.winner = winner;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public float getMoneyBet() {
		return moneyBet;
	}
	
	public void setMoneyBet(float newMoney) {
		this.moneyBet = newMoney;
	}
	
	public String toString() {
		return idBetMade + ";" + user.getUser() + ";" + bet.getBet() + ";" + isActive + " money bet: " + moneyBet;
	}

}