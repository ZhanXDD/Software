package test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import domain.Account;
import domain.CreditCard;
import exceptions.IncorrectPaymentFormatException;
import exceptions.NoPaymentMethodException;
import exceptions.NullParameterException;
import exceptions.PaymentMethodNotFound;
import exceptions.UserNotInDBException;
import utility.TestUtilityFacadeImplementation;
import dataAccess.DataAccess;

class BLFacadeAddMoneyTest {
	DataAccess mockDB = Mockito.mock(DataAccess.class);
	BLFacade sut = new BLFacadeImplementation(mockDB);
	
	static DataAccess db = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
	BLFacade sutDB = new BLFacadeImplementation(db);
	private  TestUtilityFacadeImplementation testBL= new TestUtilityFacadeImplementation();
	
	Account us = new Account("pepe", "contrasenia");
	String card = "1111111111111111";
	CreditCard tar = new CreditCard(card, "02/22", "123", card);
	
	double expected;
	@BeforeEach
	void setup() {
		us.addPaymentMethod(tar);
		us.setWallet(10); //all new accounts initialize with 10 euros
	}
	
	@Test
	@DisplayName("Test1: Correcto funcionamiento del metodo")
	void testAddMoney1() {
		//Aniºadimos 10 euros mas a cuenta us
		expected = 20; //Salida esperada
		
		try {
			Mockito.doReturn((float)20).when(mockDB)
				.addMoney(Mockito.any(Account.class), Mockito.anyString(), Mockito.anyFloat());
			
			//Salida obtenida
			float obtained = sut.addMoney(us, card, 10);
			
			assertEquals(expected, obtained, 0.001);
		}catch(Exception e) {
			fail("unexpected error, please check for each possible exception");
		}
	}
	
	@Test
	@DisplayName("Test2: lanzar IncorrectPaymentFormatException")
	void testAddMoney2() {
		String fakeCard = "a";
		assertThrows(IncorrectPaymentFormatException.class, () ->
				sut.addMoney(us, fakeCard, 10));
	}
	
	@Test
	@DisplayName("Test3: lanzar NullParameterException")
	void testAddMoney3() {
		assertThrows(NullParameterException.class, () ->
			sut.addMoney(null, card, 10));
	}
	
	@Test
	@DisplayName("Test3B: lanzar NullParameterException")
	void testAddMoney3B() {
		assertThrows(NullParameterException.class, () ->
			sut.addMoney(us, null, 10));
	}

	@Test
	@DisplayName("Test1DB: Correcto funcionamiento sin mockito")
	void testAddMoney1DB() {
		testBL.addUser(us);
		testBL.addPaymentMethod(us, tar);
		double expected = 20;
		try {
			float obtained = sutDB.addMoney(us, card, 10);
			assertEquals(expected, obtained, 0.001);
		}catch(Exception e) {
			fail("unexpected failure");
		}
		testBL.removePaymentMethod(us, card);
		testBL.removeUser(us);
	}
}
