package main.java.com.qa.menu;

import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import main.java.com.qa.db.UpdateFunc;
import main.java.com.qa.security.Security;

public class UpdateMenu implements Options{
	
	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();
		UpdateFunc update = new UpdateFunc(menu.db);

		switch (option) {
		case 1:
			String choice = "";
			do {
				System.out.println("Options: {Yes, No}");
				System.out.println("Would you like to update address details? ");
				choice = scan.next().toLowerCase();
			} while (!choice.contains("yes") && !choice.contains("no"));

			int customerID = input.getCustomerID(scan);

			if (choice.equals("yes")) {
				List<String> accepted = Arrays.asList("address1", "address2", "town", "postcode");
				String column = "";

				do {
					System.out.println("Options: {address1, address2, town, postcode}");
					System.out.println("What do you want to update? ");
					column = scan.next().toLowerCase();
				} while (!accepted.contains(column));

				System.out.println("Enter new value for " + column + ": ");
				String value = scan.next();

				update.updateAddress(column, value, customerID);
			} else {
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
				update.updateCustomer(column, value, customerID);
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
				update.updateProduct(column, value, productID);
				break;
			case "category":
				System.out.println("Enter new value for category: ");
				value = scan.nextLine();
				update.updateProduct(column, value, productID);
				break;
			case "stock":
				column = "quantity";
				int quantity = input.getProductQuantity(scan);
				update.updateProduct(column, quantity, productID);
				break;
			case "price":
				double price = input.getProductPrice(scan);
				update.updateProduct(column, price, productID);
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
}
