package test.java.com.qa.menu;

import main.java.com.qa.menu.Menu;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MenuTests {

	Menu menu = new Menu();
	
	@Test
	public void mainMenuTest() {
		String expected = ".##..##...####...##......##.......####....####....####...#####...######...####...##...##.\n"
				+ ".##..##..##..##..##......##......##..##..##......##..##..##..##..##......##..##..###.###.\n"
				+ ".######..######..##......##......##..##...####...##......#####...####....######..##.#.##.\n"
				+ ".##..##..##..##..##......##......##..##......##..##..##..##..##..##......##..##..##...##.\n"
				+ ".##..##..##..##..######..######...####....####....####...##..##..######..##..##..##...##.\n"
				+ ".........................................................................................\n"
				+ "1 : View data.\n" + "2 : Amend data.\n" + "3 : Insert data.\n" + "4 : Delete data.\n" + "5 : Exit.";
		assertEquals(expected, menu.displayMainMenu());
	}
	
	@Test
	public void viewMenuTest() {
		String expected = "1 : View customers\t\t2 : View a customers address\t\t3 : View customer orders\n"
				+ "4 : View orders\t\t\t5 : View single order\t\t\t6 : View products\n"
				+ "7 : Customer total spend\t8 : Back to main menu\t\t\t9 : Exit application";
		assertEquals(expected, menu.viewMenu());
	}
	
	@Test
	public void amendMenuTest() {
		String expected = "1 : Update customer details\t2 : Update product details\t3 : Back to main menu\n"
				+ "4 : Exit application";
		assertEquals(expected, menu.amendMenu());
	}
	
	@Test
	public void insertMenuTest() {
		String expected = "1 : Create new customer\t2 : Create new order\t3 : Add product to order\n"
				+ "4 : Create new product\t5 : Back to main menu\t6 : Exit application";
		assertEquals(expected, menu.insertMenu());
	}
	
	@Test
	public void deleteMenuTest() {
		String expected = "1 : Delete a customer\t2 : Delete an order\t3 : Delete a product from an order\n"
				+ "4 : Delete a product\t5 : Back to main menu\t6 : Exit application";
		assertEquals(expected, menu.deleteMenu());
	}
}
