package test.java.com.qa.db;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Test;

import main.java.com.qa.db.CreateFunc;
import main.java.com.qa.db.Database;
import main.java.com.qa.db.DeleteFunc;
import main.java.com.qa.db.General;
import main.java.com.qa.menu.Order;

public class CreateTests {

	static final String db = "halloscreamtest";
	static Database database = new Database(db);
	static Statement stmt = database.getStmt();
	CreateFunc c = new CreateFunc(db);
	static DeleteFunc d = new DeleteFunc(db);
	static General g = new General();

	@Test
	public void createCustomerTest() {
		assertTrue(c.createCustomer("Bob", "Gringles", "gringlator", "bobbby1@live.co.uk", "reallySecure",
				"123 negra lane", "", "Leeds", "LE1 6DW"));
		assertFalse(c.createCustomer("Bob", "Gringles", "gringlator", "bobbby1@live.co.uk", "reallySecure",
				"123 negra lane", "", "Leeds", "LE1 6DW"));
		assertFalse(c.createCustomer("Bob", "Gringles", "gringlator2", "bobbby1@live.co.uk", "reallySecure",
				"123 negra lane", "", "Leeds", "LE1 6DW"));
		assertFalse(c.createCustomer("Bob", "Gringles", "gringlator2", "bobbby1@live.co.uk", "reallySecure",
				"123 negra lane", "", "Leeds", "LE1 6DW3GD"));
	}

	@Test
	public void createProductTest() {
		assertTrue(c.createProduct("fake blood", "misc", (short) 7, 2.49));
		assertFalse(c.createProduct("fake blood", "misc", (short) 259, 2.49));
	}

	@Test
	public void insertCustomerTest() {
		assertTrue(c.insertCustomer("Jason", "Cree", "jadoncree", "jadon@msn.com", "password"));
		assertFalse(c.insertCustomer("Jason", "Cree", "jadoncree", "jadon@msn.com", "password"));
	}

	@Test
	public void insertAddressTest() {
		c.insertCustomer("Marcus", "Prince", "markerP", "mark@live.com", "password");
		assertTrue(c.insertAddress(g.getCustomerId("markerP", stmt), "123 Green Tree", "", "Woking", "W66 6GF"));
		assertFalse(c.insertAddress(-1, "123 Green Tree", "", "Woking", "W66 6GF"));
	}

	@Test
	public void createOrderTest() {
		c.createCustomer("Peter", "Shinx", "pbody", "petershinx@live.co.uk", "reallySecure", "123 negra lane", "",
				"Leeds", "LE1 6DW");
		c.createProduct("Scream Mask", "masks", (short) 9, 6.42);
		List<Order> orders = new ArrayList<Order>();
		int id = g.getCustomerId("pbody", stmt);

		String getID = "SELECT product_id FROM product WHERE name='Scream Mask'";
		ResultSet rs = null;
		int productID = 0;
		try {
			rs = stmt.executeQuery(getID);
			while (rs.next()) {
				productID = rs.getInt("product_id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		orders.add(new Order(id, productID, 2));
		
		assertTrue(c.createOrder(orders));
	}
	
	@Test
	public void insertOrderTest() {
		assertFalse(c.insertOrder(-1));
	}
	
	@Test
	public void addToOrder() {
		assertFalse(c.addToOrder(-1, -1, -1));
		
		c.createCustomer("Keven", "Malone", "kevStews", "kevlad@live.co.uk", "reallySecure", "123 negra lane", "",
				"Leeds", "LE1 6DW");
		c.createProduct("Skull costume", "costume", (short) 9, 26.42);
		c.createProduct("Cheerleader costume", "costume", (short) 14, 19.99);
		List<Order> orders = new ArrayList<Order>();
		int id = g.getCustomerId("kevStews", stmt);

		String getID1 = "SELECT product_id FROM product WHERE name='Skull costume'";
		String getID2 = "SELECT product_id FROM product WHERE name='Cheerleader costume'";
		ResultSet rs = null;
		int productID1 = 0;
		int productID2 = 0;
		try {
			rs = stmt.executeQuery(getID1);
			while (rs.next()) {
				productID1 = rs.getInt("product_id");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			rs = stmt.executeQuery(getID2);
			while(rs.next()) {
				productID2 = rs.getInt("product_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		orders.add(new Order(id, productID1, 2));
		c.createOrder(orders);
		int orderNo = g.getOrderNo(g.getCustomerId("kevStews", stmt), stmt);
		
		assertTrue(c.addToOrder(orderNo, productID1, 1));
		assertTrue(c.addToOrder(orderNo, productID2, 3));
		assertFalse(c.addToOrder(orderNo, productID1, 10));
		assertFalse(c.addToOrder(orderNo, -1, 1));
	}

	@AfterClass
	public static void deleteData() {
		d.deleteCustomer(g.getCustomerId("gringlator", stmt));
		d.deleteCustomer(g.getCustomerId("jadoncree", stmt));
		d.deleteCustomer(g.getCustomerId("markerP", stmt));
		d.deleteCustomer(g.getCustomerId("pbody", stmt));
		d.deleteCustomer(g.getCustomerId("kevStews", stmt));
		String deleteProduct1 = "DELETE FROM product WHERE name='fake blood'";
		String deleteProduct2 = "DELETE FROM product WHERE name='Scream Mask'";
		String deleteProduct3 = "DELETE FROM product WHERE name='Skull costume'";
		String deleteProduct4 = "DELETE FROM product WHERE name='Cheerleader costume'";
		try {
			stmt.executeUpdate(deleteProduct1);
			stmt.executeUpdate(deleteProduct2);
			stmt.executeUpdate(deleteProduct3);
			stmt.executeUpdate(deleteProduct4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
