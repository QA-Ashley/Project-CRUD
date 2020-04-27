package com.qa.db;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import com.qa.menu.Inputs;
import com.qa.menu.Menu;
import com.qa.menu.Security;

public class UpdateFunc extends Database implements Options {

	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();

		switch (option) {
		case 1:
			String choice = "";
			do {
				System.out.println("Options: {Yes, No}");
				System.out.println("Would you like to update address details? ");
				choice = scan.next().toLowerCase();
			} while (!choice.contains("yes") && !choice.contains("no"));
			
			int customerID = input.getCustomerID(scan);
			
			if (choice.equals("yes")) { // Updates customer address
				List<String> accepted = Arrays.asList("address1", "address2", "town", "postcode");
				String column = "";
				
				do {
					System.out.println("Options: {address1, address2, town, postcode}");
					System.out.println("What do you want to update? ");
					column = scan.next().toLowerCase();
				} while (!accepted.contains(column));
				
				System.out.println("Enter new value for " + column + ": ");
				String value = scan.next();
				
				updateAddress(column, value, customerID);
			} else { // Updates customer details
				List<String> accepted = Arrays.asList("fname", "lname", "username", "email", "password");
				String column = "";
				
				do {
					System.out.println("Options: {fname, lname, username, email, password}");
					System.out.println("What do you want to update? ");
					column = scan.next().toLowerCase();
				} while (!accepted.contains(column));
				
				System.out.println("Enter new value for " + column + ": ");
				String value = scan.next();
				
				Security security = new Security(); 
				if (column.contentEquals("password")) {
					try {
						value = security.hashPassword(value);
					} catch (NoSuchAlgorithmException e) {
						e.printStackTrace();
					}
				}
				
				updateCustomer(column, value, customerID);
			}
			menu.subMenu(previousMenu);
			break;
		case 2:
			List<String> accepted = Arrays.asList("name", "category", "stock", "price");
			String column = "";
			do {
				System.out.println("Options: {name, category, stock, price}");
				System.out.println("What do you want to update? ");
				column = scan.next().toLowerCase();
			} while (!accepted.contains(column));

			System.out.println("What product do you want to update? ");
			int productID = input.getProductID(scan);

			String value = "";

			switch (column) {
			case "name":
				column = "`" + column + "`";
				System.out.println("Enter new value for name: ");
				value = scan.nextLine();
				updateProduct(column, value, productID);
				break;
			case "category":
				System.out.println("Enter new value for category: ");
				value = scan.nextLine();
				updateProduct(column, value, productID);
				break;
			case "stock":
				column = "quantity";
				int quantity = input.getProductQuantity(scan);
				updateProduct(column, quantity, productID);
				break;
			case "price":
				double price = input.getProductPrice(scan);
				updateProduct(column, price, productID);
				break;
			}
			menu.subMenu(previousMenu);
			break;
		case 3:
			menu.selectMenu();
			break;
		case 4:
			System.exit(0);
			break;
		}
		scan.close();
	}

	private void updateCustomer(String column, String value, int id) {
		String sql = "UPDATE customer SET " + column + "='" + value + "' WHERE customer_id="+ id;
		
		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for customer ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for customer ID: " + id);
		}
	}

	private void updateAddress(String column, String value, int id) {
		String sql = "UPDATE address SET " + column + "='" + value + "' WHERE fk_customer_id="+ id;
		
		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for customer ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for customer ID: " + id);
		}
	}

	protected void updateProduct(String column, String value, int id) {
		String sql = "UPDATE product SET " + column + "='" + value + "' WHERE product_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for product ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for product ID: " + id);
		}
	}

	protected void updateProduct(String column, int value, int id) {
		String sql = "UPDATE product SET " + column + "=" + value + " WHERE product_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for product ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for product ID: " + id);
		}
	}

	private void updateProduct(String column, double value, int id) {
		String sql = "UPDATE product SET " + column + "=" + value + " WHERE product_id=" + id;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Successfully changed " + column + " to " + value + " for product ID: " + id);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error changing " + column + " to " + value + " for product ID: " + id);
		}
	}
	
	protected boolean updateOrder(String column, double price, int orderNo) {
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

}
