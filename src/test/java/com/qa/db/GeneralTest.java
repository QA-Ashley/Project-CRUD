package test.java.com.qa.db;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import main.java.com.qa.db.CreateFunc;
import main.java.com.qa.db.Database;
import main.java.com.qa.db.DeleteFunc;
import main.java.com.qa.db.General;
import main.java.com.qa.menu.Order;

public class GeneralTest {
	
	static final String db = "halloscreamtest";
	static Database database = new Database(db);
	private static Statement stmt = database.getStmt();
	General g = new General();
	CreateFunc c = new CreateFunc(db);
	DeleteFunc d = new DeleteFunc(db);
	int customerid;
	int productid;
	int orderNo;
	
	@Before
	public void insertData() {
		c.createCustomer("Jane", "Doe", "jdoe2000", "jdoubledoe@aol.co.uk", "donthackme",
				"123 oak road", "", "Birmingham", "BH12 6WZ");
		String getID = "SELECT MAX(customer_id) AS id FROM customer;";
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(getID);
			while(rs.next()) {
				customerid = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		c.createProduct("Fake Blood", "misc", (short) 9, 2.49);
		c.createProduct("Scream Mask", "mask", (short) 2, 8.99);
		getID = "SELECT MAX(product_id) AS id FROM product;";
		rs = null;
		try {
			rs = stmt.executeQuery(getID);
			while(rs.next()) {
				productid = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(customerid, productid-1, 2));
		orders.add(new Order(customerid, productid, 1));
		c.createOrder(orders);
		orderNo = g.getOrderNo(customerid, stmt);
	}
	
	@Test
	public void getCustomerIdTest() {
		assertEquals(customerid, g.getCustomerId("jdoe2000", stmt));
	}
	
	@Test
	public void uniqueUsernameTest() {
		assertTrue(g.uniqueUnameCheck("jazzyjeff", stmt));
		assertFalse(g.uniqueUnameCheck("jdoe2000", stmt));
	}
	
	@Test
	public void uniqueEmailTest() {
		assertTrue(g.uniqueEmailCheck("jazzyjeff@hotmail.com", stmt));
		assertFalse(g.uniqueEmailCheck("jdoubledoe@aol.co.uk", stmt));
	}
	
	@Test
	public void getProductStockTest() {
		assertEquals(1, g.getProductStock(productid, stmt));
	}
	
	@Test
	public void getProductPriceTest() {
		assertEquals(8.99, g.getProductPrice(productid, stmt),0);
		assertEquals(0, g.getProductPrice(-1, stmt),0);
	}
	
	@Test
	public void getProductPricesTest() {
		int[] products = {productid-1, productid};
		int[] quantity = {2,1};
		double[] expected = {4.98,8.99};
		assertArrayEquals(expected, g.getProductPrices(2, products, quantity, stmt),0.0001);
	}
	
	@Test
	public void getOrderNoTest() {
		assertEquals(orderNo, g.getOrderNo(customerid, stmt));
	}
	
	@Test
	public void retrieveOrdersTest() {
		List<Integer> orders = new ArrayList<Integer>();
		orders.add(orderNo);
		assertEquals(orders, g.retrieveOrders(customerid, stmt));
	}
	
	@Test
	public void checkStockTest() {
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(customerid, productid-1, 2));
		orders.add(new Order(customerid, productid, 3));
		int[] expected = {7,1};
		assertArrayEquals(expected, g.checkStock(orders, stmt));
	}
	
	@Test
	public void itemAlreadyExistsTest() {
		assertEquals(1, g.currentItemOrderExist(orderNo, productid, stmt));
		assertEquals(0, g.currentItemOrderExist(orderNo, -1, stmt));
	}
	
	@Test
	public void checkOrderProductQuantityTest() {
		String[] expected1 = {"2", "4.98"};
		String[] expected2 = new String[2];
		assertArrayEquals(expected1, g.checkQuantity(orderNo, productid-1, stmt));
		assertArrayEquals(expected2, g.checkQuantity(orderNo, -1, stmt));
	}
	
	@After
	public void removeData() {
		d.deleteCustomer(customerid);
		d.deleteProduct(productid-1);
		d.deleteProduct(productid);
	}
	
	@AfterClass
	public static void tearDown() {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
