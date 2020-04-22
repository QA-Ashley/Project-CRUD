package project;

import java.util.Scanner;

public class ViewFunc extends Database implements Options {

	public void viewOptions(int option) {
		Scanner scan = new Scanner(System.in);
		Menu menu = new Menu();

		switch (option) {
		case 1:
			viewCustomers();
			break;
		case 2:
			viewCustomerAddress();
			break;
		case 3:
			customerOrders();
			break;
		case 4:
			viewOrders();
			break;
		case 5:
			viewProducts();
			break;
		case 6:
			viewCustomerTotalSales();
			break;
		case 7:
			menu.selectMenu();
			break;
		case 8:
			System.exit(0);
			break;
		}
		scan.close();
	}

	protected void viewCustomers() {

	}

	protected void viewCustomerAddress() {

	}

	protected void customerOrders() {

	}

	protected void viewOrders() {

	}

	protected void viewProducts() {

	}

	protected void viewCustomerTotalSales() {

	}
}
