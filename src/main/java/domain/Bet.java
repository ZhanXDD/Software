package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Bet implements Serializable{
    @Id 
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer betNumber;
    private String bet;
    private Float reward;
    private boolean winner;
    @XmlIDREF
    private Question question;
    
    private static Integer numBet = 1;
    
    public Bet() {
        super();
    }
    public Bet(Integer betNumber, String bet, Float prize, Question q) {
        super();
        this.betNumber=betNumber;
        this.bet=bet;
        this.reward=prize;
        this.question=q;
        this.winner=false;
    }
    public Bet(String bet, Float prize, Question q) {
        super();
        this.bet=bet;
        this.reward=prize;
        this.question=q;
        this.betNumber=Bet.numBet++;
        this.winner=false;
    }
    public Integer getBetNumber() {
        return betNumber;
    }
    public void setBetNumber(Integer betNumber) {
        this.betNumber = betNumber;
    }
    public String getBet() {
        return bet;
    }
    public void setBet(String bet) {
        this.bet = bet;
    }
    public Float getReward() {
        return reward;
    }
    public void setReward(Float prize) {
        this.reward = prize;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public String toString() {
        return betNumber + ";" + bet + ";" + reward;
    }
	public void setWinner() {
		winner=true;	
	}
	public boolean getWinner() {
		return winner;
	}
}