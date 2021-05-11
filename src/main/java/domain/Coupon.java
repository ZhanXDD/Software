package domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Coupon implements Serializable{
    
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private int couponNumber;

	//The amount of the coupon
    private int amount;
    @Id
    private String couponCode;
    
    private static Integer numCoupon = 1;
    
    public Coupon() {
    	super();
    }
    public Coupon(Integer amount, String code) {
    	this.amount=amount;
    	this.couponNumber = Coupon.numCoupon++;
    	this.couponCode=code;
    }

	public Integer getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(Integer couponNumber) {
		this.couponNumber = couponNumber;
	}

	public static Integer getNumCoupon() {
		return numCoupon;
	}

	public static void setNumCoupon(Integer numCoupon) {
		Coupon.numCoupon = numCoupon;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	@Override
    public String toString() {
        return "CouponId: "+couponNumber+ "Code: "+ couponCode+  "Amount: "+ amount;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coupon other = (Coupon) obj;
        if (couponNumber != other.couponNumber)
            return false;
        return true;
    }
    

}
