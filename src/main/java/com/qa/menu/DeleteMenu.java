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

			if (delete.deleteCustomer(customerID)) {
				System.out.println("Customer deleted");
			} else {
				System.out.println("Error deleting customer, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 2:
			orderNo = input.getOrderNo(scan);

			if (delete.deleteOrder(orderNo)) {
				System.out.println("Order deleted");
			} else {
				System.out.println("Error deleting order, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 3:
			orderNo = input.getOrderNo(scan);
			productID = input.getProductID(scan);
			int quantity = input.getProductQuantity(scan);

			if (delete.deleteProductOrder(orderNo, productID, quantity)) {
				System.out.println("Product deleted from order");
			} else {
				System.out.println("Error deleting product from order, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 4:
			productID = input.getProductID(scan);

			if (delete.deleteProduct(productID)) {
				System.out.println("Product created");
			} else {
				System.out.println("Error deleting product, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 5:
			menu.selectMenu();
			break;
		case 6:
			System.exit(0);
			break;
		}
		scan.close();
	}
}
