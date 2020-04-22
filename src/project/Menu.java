package project;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {

	public void displayMainMenu() {
		System.out.println(".##..##...####...##......##.......####....####....####...#####...######...####...##...##.\n"
				+ ".##..##..##..##..##......##......##..##..##......##..##..##..##..##......##..##..###.###.\n"
				+ ".######..######..##......##......##..##...####...##......#####...####....######..##.#.##.\n"
				+ ".##..##..##..##..##......##......##..##......##..##..##..##..##..##......##..##..##...##.\n"
				+ ".##..##..##..##..######..######...####....####....####...##..##..######..##..##..##...##.\n"
				+ ".........................................................................................\n");

		System.out.println(
				"1 : View data.\n" + "2 : Amend data.\n" + "3 : Insert data.\n" + "4 : Delete data.\n" + "5 : Exit.");
	}

	public void selectMenu() {
		Scanner input = new Scanner(System.in);
		int incorrectChoice = 0;

		Integer choice = null;

		displayMainMenu();

		do {
			try {
				if (incorrectChoice == 5) {
					displayMainMenu();
					incorrectChoice = 0;
				}
				System.out.println("What would you like to do: ");
				choice = input.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println("Choice not accepted.\n");
				input.next();
				incorrectChoice++;
			}
			if (choice.intValue() == 5) {
				System.out.println("Exiting...");
				System.exit(0);
			}
			if (choice != null && choice.intValue() > 5) {
				System.out.println("Incorrect Choice");
			}
		} while (choice == null || choice.intValue() > 4);
		subMenu(choice.intValue());

		input.close();
	}

	private void subMenu(int choice) {
		String menu = "";

		switch (choice) {
		case 1:
			menu = viewMenu();
			break;
		case 2:
			menu = amendMenu();
			break;
		case 3:
			menu = insertMenu();
			break;
		case 4:
			menu = deleteMenu();
			break;
		}
		Scanner scan = new Scanner(System.in);
		boolean check = false;
		int decision = -1;
		int incorrectDecision = 0;

		System.out.println(menu);
		do {
			if (incorrectDecision == 5) {
				System.out.println(menu);
				incorrectDecision = 0;
			}
			System.out.println("Please enter you selection: ");
			check = true;
			try {
				decision = scan.nextInt();
				scan.nextLine();
			} catch (InputMismatchException e1) {
				System.out.println("Please enter a correct option.");
			}
			if (choice == 1 && decision >= 1 && decision <= 8) { // Read
				ViewFunc view = new ViewFunc();
				switch (decision) {
				case 1:
					view.viewCustomers();
					break;
				case 2:
					view.viewCustomerAddress();
					break;
				case 3:
					view.customerOrders();
					break;
				case 4:
					view.viewOrders();
					break;
				case 5:
					view.viewProducts();
					break;
				case 6:
					view.viewCustomerTotalSales();
					break;
				case 7:
					selectMenu();
					break;
				case 8:
					System.exit(0);
					break;
				}
			} else if (choice == 2 && decision >= 1 && decision <= 9) { // Update
				UpdateFunc update = new UpdateFunc();
				switch (decision) {
				case 1:
					update.changeCustomerName();
					break;
				case 2:
					update.changeCustomerEmail();
					break;
				case 3:
					update.changeCustomerUsername();
					break;
				case 4:
					update.changeCustomerPassword();
					break;
				case 5:
					update.changeProductName();
					break;
				case 6:
					update.changeProductPrice();
					break;
				case 7:
					update.changeProductStock();
					break;
				case 8:
					selectMenu();
					break;
				case 9:
					System.exit(0);
					break;
				}
			} else if (choice == 3 && decision >= 1 && decision <= 5) { // Create
				CreateFunc create = new CreateFunc();
				switch (decision) {
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

					if (create.createCustomer(fname, lname, uname, email, password, addressOne, addressTwo, town,
							postcode)) {
						System.out.println("Customer created.");
						subMenu(choice);
					} else {
						System.out.println("Error creating customer, returning to menu..");
						subMenu(choice);
					}
					break;
				case 2:
					List<Order> orders = new ArrayList<Order>();
					int amount = -1;
					do {
						try {
							System.out.println("How many products in the order? ");
							amount = scan.nextInt();
						} catch (InputMismatchException e1) {
							System.out.println("Input has to be a number");
						}
					} while (amount <= 0);
					int customerID = -1;
					int productID = -1;
					int productQuantity = -1;
					boolean proceed = false;

					do {
						try {
							System.out.println("Enter customer ID for the order: ");
							customerID = scan.nextInt();
						} catch (InputMismatchException e1) {
							System.out.println("Input must be a number");
						}
					} while (customerID <= 0);

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

					if (create.createOrder(orders)) {
						System.out.println("Order created");
						subMenu(choice);
					} else {
						System.out.println("Error creating order, returning to menu..");
						subMenu(choice);
					}
					break;
				case 3:
					System.out.println("Enter product name: ");
					String name = scan.nextLine();
					System.out.println("Enter product category: ");
					String category = scan.nextLine();
					short quantity = -1;
					double price = -1;

					do {
						try {
							System.out.println("Enter product quantity: ");
							quantity = scan.nextShort();
						} catch (InputMismatchException e1) {
							System.out.println("Value needs to be between 0-255.");
							quantity = -1;
							scan.next();
						}
					} while (quantity == -1);

					do {
						try {
							System.out.println("Enter product price: ");
							price = scan.nextDouble();
						} catch (InputMismatchException e1) {
							System.out.println("Price needs to be a number.");
							price = -1;
							scan.next();
						}
					} while (price == -1);

					if (create.createProduct(name, category, quantity, price)) {
						System.out.println("Product created.");
						subMenu(choice);
					} else {
						System.out.println("Error creating product, returning to menu..");
						subMenu(choice);
					}
					break;
				case 4:
					selectMenu();
					break;
				case 5:
					System.exit(0);
					break;
				}
			} else if (choice == 4 && decision >= 1 && decision <= 5) { // Delete
				DeleteFunc delete = new DeleteFunc();
				switch (decision) {
				case 1:
					delete.deleteCustomer();
					break;
				case 2:
					delete.deleteOrder();
					break;
				case 3:
					delete.deleteProduct();
					break;
				case 4:
					selectMenu();
					break;
				case 5:
					System.exit(0);
					break;
				}
			}
			incorrectDecision++;
		} while (!check);
		scan.close();
	}

	private String viewMenu() {
		return "1 : View customers\t2 : View a customers address\t3 : View customer orders\n"
				+ "4 : View orders\t\t5 : View products\t\t6 : View total spend by customer\n"
				+ "7 : Back to main menu\t8 : Exit application";
	}

	private String amendMenu() {
		return "Customer details\n"
				+ "1: Change a customers name\t2 : Change a customers email\t3 : Change a customers username\n"
				+ "4 : Change a customers password\n" + "Product details\n"
				+ "5 : Change a products name\t6 : Change a products price\t7 : Change a products stock\n"
				+ "8 : Back to main menu\t9 : Exit application";
	}

	private String insertMenu() {
		return "1 : Create new customer\t2 : Create new order\t3 : Create new product\n"
				+ "4 : Back to main menu\t5 : Exit application";
	}

	private String deleteMenu() {
		return "1 : Delete a customer\t2 : Delete an order\t3 : Delete a product\n"
				+ "4 : Back to main menu\t5 : Exit application";
	}
}
