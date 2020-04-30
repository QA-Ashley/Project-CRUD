package main.java.com.qa.menu;

import java.util.Scanner;

import main.java.com.qa.db.ViewFunc;

public class ViewMenu implements Options{
	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();
		ViewFunc view = new ViewFunc(menu.db);
		int customerID;

		switch (option) {
		case 1:
			view.viewCustomers();
			break;
		case 2:
			customerID = input.getCustomerID(scan);
			view.viewCustomerAddress(customerID);
			break;
		case 3:
			customerID = input.getCustomerID(scan);
			view.customerOrders(customerID);
			break;
		case 4:
			view.viewOrders();
			break;
		case 5:
			int orderNo = input.getOrderNo(scan);
			view.viewSingleOrder(orderNo);
			break;
		case 6:
			view.viewProducts();
			break;
		case 7:
			customerID = input.getCustomerID(scan);
			view.viewCustomerTotalSales(customerID);
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
}
