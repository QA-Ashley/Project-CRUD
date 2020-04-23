package com.qa.db;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.qa.menu.Inputs;
import com.qa.menu.Menu;
import com.qa.menu.Order;
import com.qa.menu.Security;

public class CreateFunc extends Database {
	
	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();
		
		switch (option) {
		case 1:
			System.out.println("Enter first name: ");
			String fname = scan.nextLine();
			System.out.println("Enter last name: ");
			String lname = scan.nextLine();
			System.out.println("Enter username: ");
			String uname = scan.nextLine();
			System.out.println("Enter email address: ");
			String email = scan.nextLine();
			System.out.println("Enter password: ");
			String password = scan.nextLine();
			System.out.println("Enter address line one: ");
			String addressOne = scan.nextLine();
			System.out.println("Enter address line two: ");
			String addressTwo = scan.nextLine();
			System.out.println("Enter town/city: ");
			String town = scan.nextLine();
			System.out.println("Enter postcode: ");
			String postcode = scan.nextLine();

			if (createCustomer(fname, lname, uname, email, password, addressOne, addressTwo, town,
					postcode)) {
				System.out.println("Customer created.");
			} else {
				System.out.println("Error creating customer, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 2:
			List<Order> orders = new ArrayList<Order>();
			int amount = -1;
			do {
				try {
					System.out.println("How many products in the order? ");
					amount = scan.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Input has to be a number");
				}
			} while (amount <= 0);
			int customerID = input.getCustomerID(scan);
			int productID = -1;
			int productQuantity = -1;
			boolean proceed = false;

			

			for (int i = 0; i < amount; i++) {
				proceed = false;
				do {
					try {
						System.out.println("Enter product id: ");
						productID = scan.nextInt();
						System.out.println("Enter quantity: ");
						productQuantity = scan.nextInt();
						orders.add(new Order(customerID, productID, productQuantity));
						proceed = true;
					} catch (InputMismatchException e1) {
						System.out.println("Input must be a number");
					}
				} while (!proceed);
			}

			if (createOrder(orders)) {
				System.out.println("Order created");
			} else {
				System.out.println("Error creating order, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 3:
			System.out.println("Enter product name: ");
			String name = scan.nextLine();
			System.out.println("Enter product category: ");
			String category = scan.nextLine();
			short quantity = input.getProductQuantity(scan);
			double price = input.getProductPrice(scan);

			if (createProduct(name, category, quantity, price)) {
				System.out.println("Product created.");
			} else {
				System.out.println("Error creating product, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 4:
			menu.selectMenu();
			break;
		case 5:
			System.exit(0);
			break;
		}
		scan.close();
	}
	
	private boolean createCustomer(String fname, String lname, String uname, String email, String password,
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

	private boolean createProduct(String name, String category, short quantity, double price) {
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

	private boolean createOrder(List<Order> orders) {
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
