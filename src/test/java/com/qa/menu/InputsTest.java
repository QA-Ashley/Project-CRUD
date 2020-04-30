package test.java.com.qa.menu;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import main.java.com.qa.menu.Inputs;

public class InputsTest {
	
	Inputs i = new Inputs();

	@Test
	public void getProductIdTest() {
		Scanner s = new Scanner("1");
		assertEquals(1, i.getProductID(s));
		s.close();
	}
	
	@Test
	public void getProductQuantityTest() {
		Scanner s = new Scanner("1");
		assertEquals(1, i.getProductQuantity(s));
		s.close();
	}
	
	@Test
	public void getProductPriceTest() {
		Scanner s = new Scanner("1.42");
		assertEquals(1.42, i.getProductPrice(s),0);
		s.close();
	}
	
	@Test
	public void getCustomerIdTest() {
		Scanner s = new Scanner("1");
		assertEquals(1, i.getCustomerID(s));
		s.close();
	}
	
	@Test
	public void getOrderNoTest() {
		Scanner s = new Scanner("1");
		assertEquals(1, i.getOrderNo(s));
		s.close();
	}
}
