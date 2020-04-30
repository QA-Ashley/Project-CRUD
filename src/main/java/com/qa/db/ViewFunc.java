package main.java.com.qa.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewFunc extends Database {

	public ViewFunc(String db) {
		super(db);
	}

	public boolean viewCustomers() {
		String sql = "SELECT * FROM customer";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int customerID = rs.getInt("customer_id");
					String fname = rs.getString("fname");
					String lname = rs.getString("lname");
					String user = rs.getString("username");
					String email = rs.getString("email");
					String password = rs.getString("password");
					System.out.println("ID: " + customerID + " First Name: " + fname + "\tLast Name: " + lname
							+ "\tUsername: " + user + "\tEmail: " + email + "\t\tPassword: " + password);
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean viewCustomerAddress(int id) {
		String sql = "SELECT * FROM address WHERE fk_customer_id=" + id;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int customerID = rs.getInt("fk_customer_id");
					String addressOne = rs.getString("address1");
					String addressTwo = rs.getString("address2");
					String town = rs.getString("town");
					String postcode = rs.getString("postcode");
					System.out.println("ID: " + customerID + " Address 1: " + addressOne + "\tAddress 2: " + addressTwo
							+ "\tTown: " + town + "\tPostcode: " + postcode);
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean customerOrders(int id) {
		String sql = "SELECT * FROM `order` WHERE fk_customer_id=" + id;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int orderNo = rs.getInt("order_number");
					int customerID = rs.getInt("fk_customer_id");
					String datePlaced = rs.getString("date_placed");
					double total = rs.getDouble("total");
					System.out.println("Order no: " + orderNo + "\tCustomer ID: " + customerID + "\tDate Placed: "
							+ datePlaced + "\tTotal: " + total);
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean viewOrders() {
		String sql = "SELECT * FROM `order`";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int orderNo = rs.getInt("order_number");
					int customerID = rs.getInt("fk_customer_id");
					String datePlaced = rs.getString("date_placed");
					double total = rs.getDouble("total");
					System.out.println("Order no: " + orderNo + "\tCustomer ID: " + customerID + "\tDate Placed: "
							+ datePlaced + "\tTotal: " + total);
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean viewSingleOrder(int orderNo) {
		String sql = "SELECT * FROM `order` WHERE order_number=" + orderNo;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int customerID = rs.getInt("fk_customer_id");
					String datePlaced = rs.getString("date_placed");
					double total = rs.getDouble("total");
					System.out.println("Order no: " + orderNo + "\tCustomer ID: " + customerID + "\tDate Placed: "
							+ datePlaced + "\tTotal: " + total);
				}
				return true;
			} else {
				System.out.println("Order doesn't exist");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean viewProducts() {
		String sql = "SELECT * FROM product";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int id = rs.getInt("product_id");
					String productName = rs.getString("name");
					String category = rs.getString("category");
					int quantity = rs.getInt("quantity");
					double price = rs.getDouble("price");
					System.out.println("Product ID: " + id + "\tName: " + productName + "\tCategory: " + category
							+ "\tQuantity: " + quantity + "\tPrice: " + price);
				}
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean viewCustomerTotalSales(int id) {
		String sql = "SELECT fk_customer_id, SUM(total) AS total FROM `order` WHERE fk_customer_id=" + id;
		ResultSet rs = null;
		int customerID = 0;
		double total = 0;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					customerID = rs.getInt("fk_customer_id");
					total = rs.getDouble("total");
					System.out.println("Customer ID: " + customerID + "\tTotal Sales: " + total);
				}
				if (total != 0) {
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}