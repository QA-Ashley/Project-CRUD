package main.java.com.qa.menu;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import main.java.com.qa.db.CreateFunc;

public class CreateMenu implements Options{

	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();
		CreateFunc create = new CreateFunc(menu.db);

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

			create.createCustomer(fname, lname, uname, email, password, addressOne, addressTwo, town, postcode);
			menu.subMenu(previousMenu);
			break;
		case 2:
			
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
			List<Order> orders = input.getProductsForOrder(amount, customerID, scan);
			create.createOrder(orders);
			menu.subMenu(previousMenu);
			break;
		case 3:
			int orderNo = input.getOrderNo(scan);
			int newProduct = input.getProductID(scan);
			int newQuantity = input.getProductQuantity(scan);
			create.addToOrder(orderNo, newProduct, newQuantity);
			menu.subMenu(previousMenu);
			break;
		case 4:
			System.out.println("Enter product name: ");
			String name = scan.nextLine();
			System.out.println("Enter product category: ");
			String category = scan.nextLine();
			short quantity = input.getProductQuantity(scan);
			double price = input.getProductPrice(scan);

			create.createProduct(name, category, quantity, price);
			menu.subMenu(previousMenu);
			break;
		case 5:
			menu.selectMenu();
		case 6:
			System.exit(0);
		}
		scan.close();
	}
}