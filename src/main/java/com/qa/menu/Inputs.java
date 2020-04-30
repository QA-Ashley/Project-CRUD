package main.java.com.qa.menu;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Inputs {
	//Scanner scan = new Scanner(System.in);
	
	public int getProductID(Scanner scan) {
		int productID = 0;
		do {
			try {
				System.out.println("Enter product ID: ");
				productID = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println("Product ID needs to be a number.");
				scan.next();
			}
		} while (productID <= 0);
		return productID;
	}
	
	public List<Order> getProductsForOrder(int amount, int customerID, Scanner scan){
		List<Order> orders = new ArrayList<Order>();
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
		return orders;
	}
	
	public short getProductQuantity(Scanner scan) {
		short quantity = -1;
		do {
			try {
				System.out.println("Enter product quantity: ");
				quantity = scan.nextShort();
			} catch (InputMismatchException e1) {
				System.out.println("Value needs to be between 0-255.");
				quantity = -1;
				scan.next();
			}
		} while (quantity <= -1);
		return quantity;
	}
	
	public double getProductPrice(Scanner scan) {
		double price = -1;
		do {
			try {
				System.out.println("Enter product price: ");
				price = scan.nextDouble();
			} catch (InputMismatchException e1) {
				System.out.println("Price needs to be a number.");
				price = -1;
				scan.next();
			}
		} while (price <= -1);
		return price;
	}
	
	public int getCustomerID(Scanner scan) {
		int customerID = -1;
		do {
			try {
				System.out.println("Enter customer ID: ");
				customerID = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println("Input must be a number");
			}
		} while (customerID < 1);
		return customerID;
	}
	
	public int getOrderNo(Scanner scan) {
		int orderNo = 0;
		do {
			try {
				System.out.println("Enter Order number: ");
				orderNo = scan.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println("Order number needs to be a number.");
				scan.next();
			}
		} while (orderNo <= 0);
		return orderNo;
	}
}
