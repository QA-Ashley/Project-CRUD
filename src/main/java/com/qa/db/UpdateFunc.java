package main.java.com.qa.db;

import java.sql.SQLException;

public class UpdateFunc extends Database {

	public UpdateFunc(String db) {
		super(db);
	}

	public boolean updateCustomer(String column, String value, int id) {
		String sql = "UPDATE customer SET " + column + "='" + value + "' WHERE customer_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for customer ID: " + id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for customer ID: " + id);
		}
		return false;
	}

	public boolean updateAddress(String column, String value, int id) {
		String sql = "UPDATE address SET " + column + "='" + value + "' WHERE fk_customer_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for customer ID: " + id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for customer ID: " + id);
		}
		return false;
	}

	public boolean updateProduct(String column, String value, int id) {
		String sql = "UPDATE product SET " + column + "='" + value + "' WHERE product_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for product ID: " + id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for product ID: " + id);
		}
		return false;
	}

	public boolean updateProduct(String column, int value, int id) {
		String sql = "UPDATE product SET " + column + "=" + value + " WHERE product_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for product ID: " + id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for product ID: " + id);
		}
		return false;
	}

	public boolean updateProduct(String column, double value, int id) {
		String sql = "UPDATE product SET " + column + "=" + value + " WHERE product_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for product ID: " + id);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for product ID: " + id);
		}
		return false;
	}

	public boolean updateOrder(String column, double price, int orderNo) {
		String sql = "UPDATE `order` SET " + column + "=" + price + " WHERE order_number=" + orderNo;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + price + " for Order number: " + orderNo);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + price + " for Order number: " + orderNo);
		}
		return false;
	}

	public void updateStock(int entries, int[] quantity, int[] currentStock, int[] productID) {
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
	}
}
