package project;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CreateFunc extends Database {
	protected boolean createCustomer(String fname, String lname, String uname, String email, String password,
			String addressOne, String addressTwo, String town, String postcode) {
		if (postcode.length() > 8) {
			System.out.println("Postcode length is too long");
			return false;
		}
		String uniqueUnameCheck = "SELECT customer_ID FROM customer WHERE username='" + uname + "'";
		String uniqueEmailCheck = "SELECT customer_ID FROM customer WHERE email='" + email + "'";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(uniqueUnameCheck);
			if (rs.next()) {
				System.out.println("Username already exists in the system");
				return false;
			}
			rs = stmt.executeQuery(uniqueEmailCheck);
			if (rs.next()) {
				System.out.println("Email already exists in the system");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Security security = new Security();
		try {
			password = security.hashPassword(password);// convert password into sha-256
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		String insertCustomer = "INSERT INTO customer VALUES(0,'" + fname + "','" + lname + "','" + uname + "','"
				+ email + "','" + password + "')";

		try {
			stmt.executeUpdate(insertCustomer);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String getID = "SELECT customer_ID FROM customer WHERE username='" + uname + "'";
		rs = null;
		int id = 0;
		try {
			rs = stmt.executeQuery(getID);
			while (rs.next()) {
				id = rs.getInt("customer_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String insertAddress = "INSERT INTO address VALUES(0," + id + ",'" + addressOne + "','" + addressTwo + "','"
				+ town + "','" + postcode + "')";

		try {
			stmt.executeUpdate(insertAddress);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean createProduct(String name, String category, short quantity, double price) {
		if (quantity > 255 && quantity < 0) {
			System.out.println("Cannot create product, quantity value is too large.");
			return false;
		}
		String sql = "INSERT INTO product VALUES(0,'" + name + "','" + category + "'," + quantity + "," + price + ")";
		try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean createOrder(List<Order> orders) {
		int entries = orders.size();
		int[] productID = new int[orders.size()];
		int[] quantity = new int[orders.size()];
		int customerID = orders.get(0).getCustomerID();

		for (int i = 0; i < entries; i++) {
			productID[i] = orders.get(i).getProductID();
			quantity[i] = orders.get(i).getQuantity();
		}

		// Checks if the current stock is less than what is being put into the order
		int[] currentStock = checkStock(orders);
		if (currentStock.length != entries) {
			return false;
		}

		// Creates initial insert into order table to generate a order number
		String sql1 = "INSERT INTO `order` (fk_customer_id, total) VALUES(" + customerID + ",0.00);";
		try {
			stmt.executeUpdate(sql1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		int orderNumber = getOrderNo(customerID);

		String[] fetchPrice = new String[entries];
		double[] prices = new double[entries];
		ResultSet rs = null;

		for (int i = 0; i < fetchPrice.length; i++) {
			fetchPrice[i] = "SELECT price FROM product WHERE product_id=" + productID[i];
			try {
				rs = stmt.executeQuery(fetchPrice[i]);
				while (rs.next()) {
					prices[i] = rs.getDouble("price") * quantity[i];
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// create orderProduct entry with order_number from Order table
		String[] sql = new String[entries];

		for (int i = 0; i < entries; i++) {
			sql[i] = "INSERT INTO orderProduct VALUES(0," + orderNumber + "," + productID[i] + "," + quantity[i] + ","
					+ prices[i] + ");";
			try {
				stmt.executeUpdate(sql[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Update stock from the order just placed
		String[] updateQuantity = new String[entries];
		for (int i = 0; i < entries; i++) {
			quantity[i] = currentStock[i] - quantity[i];
			updateQuantity[i] = "UPDATE product SET quantity=" + quantity[i] + " WHERE product_id=" + productID[i];
			try {
				stmt.executeUpdate(updateQuantity[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// Calculates the total price of the order
		double totalPrice = getTotal(orderNumber);

		String sql2 = "UPDATE `order` SET total=" + totalPrice + " WHERE order_number=" + orderNumber;

		try {
			stmt.executeUpdate(sql2);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private double getTotal(int orderNumber) {
		String sql3 = "SELECT SUM(price) AS total FROM orderProduct WHERE fk_order_number=" + orderNumber;
		ResultSet rs = null;

		double totalPrice = 0;
		try {
			rs = stmt.executeQuery(sql3);
			while (rs.next()) {
				totalPrice = rs.getDouble("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalPrice;
	}

	private int getOrderNo(int customerID) {
		String sql = "SELECT MAX(order_number) AS order_number FROM `order` WHERE fk_customer_id=" + customerID;
		ResultSet rs = null;
		int orderNumber = 0;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				orderNumber = rs.getInt("order_number");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderNumber;
	}

	private int[] checkStock(List<Order> orders) {
		int entries = orders.size();
		int[] productID = new int[orders.size()];
		int[] quantity = new int[orders.size()];

		for (int i = 0; i < entries; i++) {
			productID[i] = orders.get(i).getProductID();
			quantity[i] = orders.get(i).getQuantity();
		}
		String[] checkQuantity = new String[entries];
		int[] currentStock = new int[entries];
		ResultSet rs = null;

		for (int i = 0; i < entries; i++) {
			checkQuantity[i] = "SELECT quantity FROM product WHERE product_id=" + productID[i];
			try {
				rs = stmt.executeQuery(checkQuantity[i]);
				while (rs.next()) {
					currentStock[i] = rs.getInt("quantity");
				}
				if (currentStock[i] < quantity[i]) {
					System.out.println(
							"Error: Current stock of productID: " + productID[i] + " is only " + currentStock[i]);
					return currentStock;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return currentStock;
	}
}
