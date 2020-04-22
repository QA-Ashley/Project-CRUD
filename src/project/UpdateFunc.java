package project;

import java.util.Scanner;

public class UpdateFunc extends Database implements Options {

	public void viewOptions(int option) {
		Scanner scan = new Scanner(System.in);
		Menu menu = new Menu();

		switch (option) {
		case 1:
			changeCustomerName();
			break;
		case 2:
			changeCustomerEmail();
			break;
		case 3:
			changeCustomerUsername();
			break;
		case 4:
			changeCustomerPassword();
			break;
		case 5:
			changeProductName();
			break;
		case 6:
			changeProductPrice();
			break;
		case 7:
			changeProductStock();
			break;
		case 8:
			menu.selectMenu();
			break;
		case 9:
			System.exit(0);
			break;
		}
		scan.close();
	}

	protected void changeCustomerName() {

	}

	protected void changeCustomerEmail() {

	}

	protected void changeCustomerUsername() {

	}

	protected void changeCustomerPassword() {

	}

	protected void changeProductName() {

	}

	protected void changeProductPrice() {

	}

	protected void changeProductStock() {

	}

}
