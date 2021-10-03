package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Account;
import domain.CreditCard;
import exceptions.NoPaymentMethodException;
import exceptions.PaymentMethodNotFound;
import exceptions.UserNotInDBException;
import utility.TestUtilityDataAccess;

class DataAccessAddMoneyTest {
	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();

	Account us1 = new Account("pepe", "");
	Account us2 = new Account("pedro", " ");
	String card = "1111111111111111";
	CreditCard tar = new CreditCard(card, "02/22", "123", card);
	
	@BeforeEach
	void setup() {
		us1.setWallet(0);
		us2.setWallet(0);
	}

	@Test
	@DisplayName("Test1: Correcto funcionamiento del metodo")
	void testAddMoney1() {
		testDA.open();
		testDA.addUser(us1);
		testDA.addUser(us2);
		testDA.addPaymentMethod(us1, tar);
		testDA.close();
		
		float expected = 10;
		try {
			testDA.open();
			float obtained = sut.addMoney(us1, card, 10);
			testDA.close();
			assertEquals(expected, obtained, 0.001);
		}catch(Exception e) {
			fail("unexpected error");
		}
		testDA.open();
		testDA.removePaymentMethod(us1, card);
		System.out.println(testDA.removeUser(us1));
		System.out.println(testDA.removeUser(us2));
		testDA.close();
	}

	@Test
	@DisplayName("Test2: lanzado PaymentMethodNotFound")
	void testAddMoney2() {
		testDA.open();
		testDA.addUser(us1);
		testDA.addUser(us2);
		testDA.addPaymentMethod(us1, tar);
		testDA.close();
		String fakeCard = "1111111111111112";
		try {
			testDA.open();
			assertThrows(PaymentMethodNotFound.class, () ->
				sut.addMoney(us1, fakeCard, 10));
			testDA.close();
		}catch(Exception e) {
			fail("unexpected error");
		}
		testDA.open();
		testDA.removePaymentMethod(us1, card);
		System.out.println(testDA.removeUser(us1));
		System.out.println(testDA.removeUser(us2));
		testDA.close();
	}

	@Test
	@DisplayName("Test3: lanzado NoPaymentMethodNotFound")
	void testAddMoney3() {
		testDA.open();
		testDA.addUser(us1);
		testDA.addUser(us2);
		testDA.addPaymentMethod(us1, tar);
		testDA.close();
		try {
			testDA.open();
			assertThrows(NoPaymentMethodException.class, () ->
				sut.addMoney(us2, card, 10));
			testDA.close();
		}catch(Exception e) {
			fail("unexpected error");
		}
		testDA.open();
		testDA.removePaymentMethod(us1, card);
		System.out.println(testDA.removeUser(us1));
		System.out.println(testDA.removeUser(us2));
		testDA.close();
	}

	@Test
	@DisplayName("Test4: lanzado UserNotInDBException")
	void testAddMoney4() {
		testDA.open();
		assertThrows(UserNotInDBException.class, () ->
				sut.addMoney(new Account("a",""), card, 10));
		testDA.close();
	}
}
