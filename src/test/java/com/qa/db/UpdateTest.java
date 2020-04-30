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
import main.java.com.qa.db.UpdateFunc;
import main.java.com.qa.menu.Order;

public class UpdateTests {

	static final String db = "halloscreamtest";
	static Database database = new Database(db);
	private static Statement stmt = database.getStmt();
	CreateFunc c = new CreateFunc(db);
	UpdateFunc u = new UpdateFunc(db);
	DeleteFunc d = new DeleteFunc(db);
	General g = new General();
	int customerid;
	int productid;
	int orderNo;
	
	@Before
	public void insertData() {
		c.createCustomer("Jane", "Doe", "jdoe2000", "jdoubledoe@aol.co.uk", "donthackme",
				"123 oak road", "", "Birmingham", "BH12 6WZ");
		c.createCustomer("Josh", "String", "joshhyyy", "joshboi@live.co.uk", "donthackme",
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
		c.createProduct("Fake beard", "mask", (short) 5, 4.99);
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
		orders.add(new Order(customerid, productid-2, 5));
		orders.add(new Order(customerid, productid-1, 2));
		c.createOrder(orders);
		orderNo = g.getOrderNo(customerid, stmt);
	}
	
	@Test
	public void updateCustomerTest() {
		assertTrue(u.updateCustomer("fname", "marcus", customerid));
		assertFalse(u.updateCustomer("TEST", "test", customerid));
	}
	
	@Test
	public void updateAddressTest() {
		assertTrue(u.updateAddress("address2", "Greenwich", customerid));
		assertFalse(u.updateAddress("TEST", "test", customerid));
	}
	
	@Test
	public void updateProductTest() {
		assertTrue(u.updateProduct("name", "Magic Wand", productid));
		assertFalse(u.updateProduct("TEST", "test", productid));
		assertTrue(u.updateProduct("price", 11, productid));
		assertFalse(u.updateProduct("TEST", 11, productid));
		assertTrue(u.updateProduct("price", 11.50, productid));
		assertFalse(u.updateProduct("TEST", 11.50, productid));
	}
	
	@Test
	public void updateOrderTest() {
		assertTrue(u.updateOrder("total", 52.44, orderNo));
		assertFalse(u.updateOrder("totals", 52.44, orderNo));
	}
	
	@Test
	public void updateStockTest() {
		Database database = new Database(db);
		Statement stmt = database.getStmt();
		int entries = 2;
		int[] quantity = {1, 3};
		int[] currentStock = {2, 9};
		int[] productID = {productid, productid-1};
		u.updateStock(entries, quantity, currentStock, productID);
		assertEquals(1, g.getProductStock(productid, stmt));
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void deleteData() {
		d.deleteCustomer(customerid-1);
		d.deleteCustomer(customerid);
		d.deleteProduct(productid-2);
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
