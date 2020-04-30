package test.java.com.qa.menu;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.com.qa.menu.Order;

public class OrderTest {

	List<Order> orders = new ArrayList<Order>();
	
	@Before
	public void addOrders() {
		orders.add(new Order(1, 3, 1));
	}
	
	@Test
	public void getProductIdTest() {
		assertEquals(3, orders.get(0).getProductID());
	}
	
	@Test
	public void setProductIdTest() {
		orders.get(0).setProductID(2);
		assertEquals(2, orders.get(0).getProductID());
	}
	
	@Test
	public void getQuantityTest() {
		assertEquals(1, orders.get(0).getQuantity());
	}
	
	@Test
	public void setQuantityTest() {
		orders.get(0).setQuantity(5);
		assertEquals(5, orders.get(0).getQuantity());
	}
	
	@Test
	public void getCustomerIdTest() {
		assertEquals(1, orders.get(0).getCustomerID());
	}
	
	@Test
	public void setCustomerIdTest() {
		orders.get(0).setCustomerID(12);
		assertEquals(12, orders.get(0).getCustomerID());
	}
	
	@After
	public void removeOrders() {
		orders.clear();
	}
	
}
