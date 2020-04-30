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
import main.java.com.qa.db.ViewFunc;
import main.java.com.qa.menu.Order;

public class ViewTest {
	
	static final String db = "halloscreamtest";
	static Database database = new Database(db);
	private static Statement stmt = database.getStmt();
	CreateFunc c = new CreateFunc(db);
	DeleteFunc d = new DeleteFunc(db);
	ViewFunc v = new ViewFunc(db);
	General g = new General();
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
	
//	@Test
//	public void viewOptionsTest() {
//		ViewFunc view = mock(ViewFunc.class);
//		when(view.viewCustomers()).thenReturn(true);
//		
//		verify(menu).viewOptions(1, 1);
//	}
	
	@Test
	public void viewCustomersTest() {
		assertTrue(v.viewCustomers());
	}
	
	@Test
	public void viewAddressTest() {
		assertTrue(v.viewCustomerAddress(customerid));
		assertFalse(v.viewCustomerAddress(-1));
	}
	
	@Test
	public void viewCustomerOrdersTest() {
		assertTrue(v.customerOrders(customerid));
		assertFalse(v.customerOrders(-1));
	}
	
	@Test
	public void viewOrdersTest() {
		assertTrue(v.viewOrders());
	}
	
	@Test
	public void viewSingleOrderTest() {
		assertTrue(v.viewSingleOrder(orderNo));
		assertFalse(v.viewSingleOrder(-1));
	}
	
	@Test
	public void viewProductsTest() {
		assertTrue(v.viewProducts());
	}
	
	@Test
	public void viewCustomerSalesTest() {
		assertTrue(v.viewCustomerTotalSales(customerid));
		assertFalse(v.viewCustomerTotalSales(-1));
	}
	
	@Test
	public void emptyCustomersTest() {
		d.deleteCustomer(customerid);
		d.deleteProduct(productid-1);
		d.deleteProduct(productid);
		assertFalse(v.viewCustomers());
	}
	
	@Test
	public void emptyProductsTest() {
		d.deleteCustomer(customerid);
		d.deleteProduct(productid-1);
		d.deleteProduct(productid);
		assertFalse(v.viewProducts());
	}
	
	@Test
	public void emptySingleOrderTest() {
		d.deleteCustomer(customerid);
		d.deleteProduct(productid-1);
		d.deleteProduct(productid);
		assertFalse(v.viewSingleOrder(-1));
	}
	
	@Test
	public void emptyOrdersTest() {
		d.deleteCustomer(customerid);
		d.deleteProduct(productid-1);
		d.deleteProduct(productid);
		assertFalse(v.viewOrders());
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
