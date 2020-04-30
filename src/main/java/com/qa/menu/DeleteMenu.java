package main.java.com.qa.menu;

import java.util.Scanner;

import main.java.com.qa.db.DeleteFunc;

public class DeleteMenu implements Options{

	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();
		DeleteFunc delete = new DeleteFunc(menu.db);
		int orderNo;
		int productID;

		switch (option) {
		case 1:
			int customerID = input.getCustomerID(scan);
			delete.deleteCustomer(customerID);
			menu.subMenu(previousMenu);
			break;
		case 2:
			orderNo = input.getOrderNo(scan);
			delete.deleteOrder(orderNo);
			menu.subMenu(previousMenu);
			break;
		case 3:
			orderNo = input.getOrderNo(scan);
			productID = input.getProductID(scan);
			int quantity = input.getProductQuantity(scan);
			delete.deleteProductOrder(orderNo, productID, quantity);
			menu.subMenu(previousMenu);
			break;
		case 4:
			productID = input.getProductID(scan);
			delete.deleteProduct(productID);
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
