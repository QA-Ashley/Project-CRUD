package com.qa.db;

import java.util.Scanner;

import com.qa.menu.Menu;

public class ViewFunc extends Database implements Options {

	public void viewOptions(int option, int previousMenu) {
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

	private void viewCustomers() {

	}

	private void viewCustomerAddress() {

	}

	private void customerOrders() {

	}

	private void viewOrders() {

	}

	private void viewProducts() {

	}

	private void viewCustomerTotalSales() {

	}
}
