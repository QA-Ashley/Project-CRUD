package main.java.com.qa.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.qa.menu.Order;

import java.sql.Statement;

public class General {
	public List<Integer> retrieveOrders(int customerID, Statement stmt) {
		String sql = "SELECT order_number FROM `order` WHERE fk_customer_id=" + customerID;
		ResultSet rs = null;
		List<Integer> orders = new ArrayList<Integer>();

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				orders.add(rs.getInt("order_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public String[] checkQuantity(int orderNo, int productID, Statement stmt) {
		String checkQuantity = "SELECT quantity, price FROM orderProduct WHERE fk_order_number=" + orderNo
				+ " AND fk_product_id=" + productID;
		ResultSet rs = null;

		String[] data = new String[2];
		try {
			rs = stmt.executeQuery(checkQuantity);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					data[0] = rs.getString("quantity");
					data[1] = rs.getString("price");
				}
			} else {
				System.out.println("Order does not exist with product ID: " + productID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public int[] checkStock(List<Order> orders, Statement stmt) {
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

	public int getProductStock(int productID, Statement stmt) {
		String getStock = "SELECT quantity FROM product WHERE product_id=" + productID;
		ResultSet rs = null;
		int currentStock = 0;
		try {
			rs = stmt.executeQuery(getStock);
			while (rs.next()) {
				currentStock = rs.getInt("quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return currentStock;
	}

	public double getProductPrice(int productID, Statement stmt) {
		String fetchPrice = "SELECT price from product WHERE product_id=" + productID;
		ResultSet rs = null;
		double price = 0;
		try {
			rs = stmt.executeQuery(fetchPrice);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					price = rs.getDouble("price");
				}
			} else {
				System.out.println("Product ID " + productID + " doesn't exist");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return price;
	}

	public double[] getProductPrices(int entries, int[] productID, int[] quantity, Statement stmt) {
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
		return prices;
	}

	public int currentItemOrderExist(int orderNo, int productID, Statement stmt) {
		String itemExist = "Select quantity FROM orderProduct WHERE fk_order_number=" + orderNo + " AND fk_product_id="
				+ productID;
		ResultSet rs = null;
		int currentOrderQuantity = 0;
		try {
			rs = stmt.executeQuery(itemExist);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					currentOrderQuantity = rs.getInt("quantity");
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return currentOrderQuantity;
	}

	public boolean uniqueUnameCheck(String username, Statement stmt) {
		String uniqueUnameCheck = "SELECT customer_ID FROM customer WHERE username='" + username + "'";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(uniqueUnameCheck);
			if (rs.isBeforeFirst()) {
				System.out.println("Username already exists in the system");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public boolean uniqueEmailCheck(String email, Statement stmt) {
		String uniqueEmailCheck = "SELECT customer_ID FROM customer WHERE email='" + email + "'";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(uniqueEmailCheck);
			if (rs.isBeforeFirst()) {
				System.out.println("Email already exists in the system");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public int getCustomerId(String uname, Statement stmt) {
		String getID = "SELECT customer_ID FROM customer WHERE username='" + uname + "'";
		ResultSet rs = null;
		int id = 0;
		try {
			rs = stmt.executeQuery(getID);
			while (rs.next()) {
				id = rs.getInt("customer_ID");
			}
		} catch (SQLException e) {
			System.out.println("Error getting customer ID");
			e.printStackTrace();
		}
		return id;
	}

	public int getOrderNo(int customerID, Statement stmt) {
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

	protected double getTotal(int orderNumber, Statement stmt) {
		String sql = "SELECT SUM(price) AS total FROM orderProduct WHERE fk_order_number=" + orderNumber;
		ResultSet rs = null;

		double totalPrice = 0;
		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				totalPrice = rs.getDouble("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return totalPrice;
	}

}
