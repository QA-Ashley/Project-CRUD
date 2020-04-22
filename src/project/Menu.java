package project;

import java.util.InputMismatchException;
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
			}
			if (choice.intValue() == 5) {
				System.out.println("Exiting...");
				System.exit(0);
			}
			if (choice != null && choice.intValue() > 5) {
				System.out.println("Incorrect Choice");
			}
			incorrectChoice++;
		} while (choice == null || choice.intValue() > 4 || choice.intValue() <= 0);
		subMenu(choice.intValue());

		input.close();
	}

	protected void subMenu(int choice) {
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
				view.viewOptions(decision);
			} else if (choice == 2 && decision >= 1 && decision <= 9) { // Update
				UpdateFunc update = new UpdateFunc();
				update.viewOptions(decision);
			} else if (choice == 3 && decision >= 1 && decision <= 5) { // Create
				CreateFunc create = new CreateFunc();
				create.viewOptions(decision);
			} else if (choice == 4 && decision >= 1 && decision <= 5) { // Delete
				DeleteFunc delete = new DeleteFunc();
				delete.viewOptions(decision);
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
