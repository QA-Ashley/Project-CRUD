package project;

import java.util.Scanner;

public class Menu {
	public void displayMenu() {
		System.out.println(".##..##...####...##......##.......####....####....####...#####...######...####...##...##.\n"
				+ ".##..##..##..##..##......##......##..##..##......##..##..##..##..##......##..##..###.###.\n"
				+ ".######..######..##......##......##..##...####...##......#####...####....######..##.#.##.\n"
				+ ".##..##..##..##..##......##......##..##......##..##..##..##..##..##......##..##..##...##.\n"
				+ ".##..##..##..##..######..######...####....####....####...##..##..######..##..##..##...##.\n"
				+ ".........................................................................................\n");

		System.out.println("1. View data.\n" + "2. Amend data.\n" + "3. Insert data.\n" + "4. Delete data.");
	}

	public void selectMenu() {
		Scanner input = new Scanner(System.in);
		System.out.println("What would you like to do: ");

		Integer choice = null;

		do {
			try {
				choice = input.nextInt();
			} catch (java.util.InputMismatchException e) {
				System.out.println("Choice not accepted.");
				input.next();
			}

			if (choice != null && choice.intValue() > 4) {
				System.out.println("Incorrect Choice");
			}
		} while (choice == null || choice.intValue() > 4);

		switch (choice.intValue()) {
		case 1:
			System.out.println("View Data"); // View data
			break;
		case 2:
			System.out.println("Amend Data"); // Amend data
			break;
		case 3:
			System.out.println("Insert Data"); // Insert data
			break;
		case 4:
			System.out.println("Delete Data"); // Delete data
			break;
		default:
			System.out.println("Incorrect Choice");
		}
		input.close();
	}
}
