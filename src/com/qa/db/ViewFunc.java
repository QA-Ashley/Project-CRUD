package com.qa.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.qa.menu.Inputs;
import com.qa.menu.Menu;

public class ViewFunc extends Database implements Options {

	int customerID;

	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();

		switch (option) {
		case 1:
			viewCustomers();
			break;
		case 2:
			customerID = input.getCustomerID(scan);
			viewCustomerAddress(customerID);
			break;
		case 3:
			customerID = input.getCustomerID(scan);
			customerOrders(customerID);
			break;
		case 4:
			viewOrders();
			break;
		case 5:
			int orderNo = input.getOrderNo(scan);
			viewSingleOrder(orderNo);
			break;
		case 6:
			viewProducts();
			break;
		case 7:
			customerID = input.getCustomerID(scan);
			viewCustomerTotalSales(customerID);
			break;
		case 8:
			menu.selectMenu();
			break;
		case 9:
			System.exit(0);
			break;
		}
		menu.subMenu(previousMenu);
		scan.close();
	}

	private void viewCustomers() {
		String sql = "SELECT * FROM customer";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				customerID = rs.getInt("customer_id");
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				String user = rs.getString("username");
				String email = rs.getString("email");
				String password = rs.getString("password");
				System.out.println("ID: " + customerID + " First Name: " + fname + "\tLast Name: " + lname
						+ "\tUsername: " + user + "\tEmail: " + email + "\tPassword: " + password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void viewCustomerAddress(int id) {
		String sql = "SELECT * FROM address WHERE fk_customer_id=" + id;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				customerID = rs.getInt("fk_customer_id");
				String addressOne = rs.getString("address1");
				String addressTwo = rs.getString("address2");
				String town = rs.getString("town");
				String postcode = rs.getString("postcode");
				System.out.println("ID: " + customerID + " Address 1: " + addressOne + "\tAddress 2: " + addressTwo
						+ "\tTown: " + town + "\tPostcode: " + postcode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void customerOrders(int id) {
		String sql = "SELECT * FROM `order` WHERE fk_customer_id=" + id;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int orderNo = rs.getInt("order_number");
				customerID = rs.getInt("fk_customer_id");
				String datePlaced = rs.getString("date_placed");
				double total = rs.getDouble("total");
				System.out.println("Order no: " + orderNo + "\tCustomer ID: " + customerID + "\tDate Placed: "
						+ datePlaced + "\tTotal: " + total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void viewOrders() {
		String sql = "SELECT * FROM `order`";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int orderNo = rs.getInt("order_number");
				customerID = rs.getInt("fk_customer_id");
				String datePlaced = rs.getString("date_placed");
				double total = rs.getDouble("total");
				System.out.println("Order no: " + orderNo + "\tCustomer ID: " + customerID + "\tDate Placed: "
						+ datePlaced + "\tTotal: " + total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected boolean viewSingleOrder(int orderNo) {
		String sql = "SELECT * FROM `order` WHERE order_number=" + orderNo;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					customerID = rs.getInt("fk_customer_id");
					String datePlaced = rs.getString("date_placed");
					double total = rs.getDouble("total");
					System.out.println("Order no: " + orderNo + "\tCustomer ID: " + customerID + "\tDate Placed: "
							+ datePlaced + "\tTotal: " + total);
					return true;
				}
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private void viewProducts() {
		String sql = "SELECT * FROM product";
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("product_id");
				String productName = rs.getString("name");
				String category = rs.getString("category");
				int quantity = rs.getInt("quantity");
				double price = rs.getDouble("price");
				System.out.println("Product ID: " + id + "\tName: " + productName + "\tCategory: " + category
						+ "\tQuantity: " + quantity + "\tPrice: " + price);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void viewCustomerTotalSales(int id) {
		String sql = "SELECT fk_customer_id, SUM(total) AS total FROM `order` WHERE fk_customer_id=" + id;
		ResultSet rs = null;

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				customerID = rs.getInt("fk_customer_id");
				double total = rs.getDouble("total");
				System.out.println("Customer ID: " + customerID + "\tTotal Sales: " + total);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}