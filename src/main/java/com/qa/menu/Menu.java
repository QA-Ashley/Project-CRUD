package main.java.com.qa.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
	protected String db = "halloscream";

	public String displayMainMenu() {
		return ".##..##...####...##......##.......####....####....####...#####...######...####...##...##.\n"
				+ ".##..##..##..##..##......##......##..##..##......##..##..##..##..##......##..##..###.###.\n"
				+ ".######..######..##......##......##..##...####...##......#####...####....######..##.#.##.\n"
				+ ".##..##..##..##..##......##......##..##......##..##..##..##..##..##......##..##..##...##.\n"
				+ ".##..##..##..##..######..######...####....####....####...##..##..######..##..##..##...##.\n"
				+ ".........................................................................................\n"
				+ "1 : View data.\n" + "2 : Amend data.\n" + "3 : Insert data.\n" + "4 : Delete data.\n" + "5 : Exit.";
	}

	public void selectMenu() {
		Scanner input = new Scanner(System.in);
		int incorrectChoice = 0;
		Integer choice = null;

		System.out.println(displayMainMenu());

		do {
			try {
				if (incorrectChoice == 5) {
					System.out.println(displayMainMenu());
					incorrectChoice = 0;
				}
				System.out.println("What would you like to do: ");
				choice = input.nextInt();
			} catch (InputMismatchException e1) {
				System.out.println("Choice not accepted.\n");
				input.next();
			}
			
			if (choice != null && choice.intValue() > 5) {
				System.out.println("Incorrect Choice");
			}
			incorrectChoice++;
		} while (choice == null || choice.intValue() > 5 || choice.intValue() <= 0);
		if (choice.intValue() == 5) {
			System.out.println("Exiting...");
			System.exit(0);
		}
		subMenu(choice.intValue());

		input.close();
	}


	public void subMenu(int choice) {
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
			
			try {
				decision = scan.nextInt();
				scan.nextLine();
			} catch (InputMismatchException e1) {
				System.out.println("Please enter a correct option.");
			}

			if (choice == 1 && decision >= 1 && decision <= 9) { // Read
				check = true;
				ViewMenu view = new ViewMenu();
				view.viewOptions(decision, choice);
			} else if (choice == 2 && decision >= 1 && decision <= 4) { // Update
				check = true;
				UpdateMenu update = new UpdateMenu();
				update.viewOptions(decision, choice);
			} else if (choice == 3 && decision >= 1 && decision <= 6) { // Create
				check = true;
				CreateMenu create = new CreateMenu();
				create.viewOptions(decision, choice);
			} else if (choice == 4 && decision >= 1 && decision <= 6) { // Delete
				check = true;
				DeleteMenu delete = new DeleteMenu();
				delete.viewOptions(decision, choice);
			}
			incorrectDecision++;
		} while (!check);
		scan.close();
	}

	public String viewMenu() {
		return "\n1 : View customers\t\t2 : View a customers address\t\t3 : View customer orders\n"
				+ "4 : View orders\t\t\t5 : View single order\t\t\t6 : View products\n"
				+ "7 : Customer total spend\t8 : Back to main menu\t\t\t9 : Exit application";
	}

	public String amendMenu() {
		return "\n1 : Update customer details\t2 : Update product details\t3 : Back to main menu\n"
				+ "4 : Exit application";
	}

	public String insertMenu() {
		return "\n1 : Create new customer\t2 : Create new order\t3 : Add product to order\n"
				+ "4 : Create new product\t5 : Back to main menu\t6 : Exit application";
	}

	public String deleteMenu() {
		return "\n1 : Delete a customer\t2 : Delete an order\t3 : Delete a product from an order\n"
				+ "4 : Delete a product\t5 : Back to main menu\t6 : Exit application";
	}
	
}
