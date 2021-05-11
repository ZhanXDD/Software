package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CreditCard {
	
	@Id
	private String cardNumber;
	private String cardName;
	private String endDate;
	private String cvc;
	
	
	public CreditCard(String cardName,String endDate, String cvc, String cardNumber) {
		this.cardName=cardName;
		this.endDate=endDate;
		this.cvc=cvc;
		this.cardNumber=cardNumber;
	}
	
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCvc() {
		return cvc;
	}
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
