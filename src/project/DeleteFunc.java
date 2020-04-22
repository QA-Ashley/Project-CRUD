package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class DeleteFunc extends Database {
	
	public void viewOptions(int option) {
		Scanner scan = new Scanner(System.in);
		Menu menu = new Menu();
		
		switch (option) {
		case 1:
			int customerID = 0;
			do {
				try {
					System.out.println("Enter customer ID: ");
					customerID = scan.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Customer ID needs to be a number.");
					scan.next();
				}
			} while (customerID == 0);
			if(deleteCustomer(customerID)) {
				System.out.println("Customer deleted");
				menu.subMenu(option);
			} else {
				System.out.println("Error deleting customer, returning to menu..");
				menu.subMenu(option);
			}
			break;
		case 2:
			int orderNo = 0;
			do {
				try {
					System.out.println("Enter Order number: ");
					orderNo = scan.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Order number needs to be a number.");
					scan.next();
				}
			} while (orderNo == 0 || orderNo < 0);
			if(deleteOrder(orderNo)) {
				System.out.println("Order deleted");
				menu.subMenu(option);
			} else {
				System.out.println("Error deleting order, returning to menu..");
				menu.subMenu(option);
			}
			break;
		case 3:
			int productID = 0;
			do {
				try {
					System.out.println("Enter product ID: ");
					productID = scan.nextInt();
				} catch (InputMismatchException e1) {
					System.out.println("Product ID needs to be a number.");
					scan.next();
				}
			} while (productID == 0);
			if(deleteProduct(productID)) {
				System.out.println("Product created");
				menu.subMenu(option);
			} else {
				System.out.println("Error deleting product, returning to menu..");
			}
			break;
		case 4:
			menu.selectMenu();
			break;
		case 5:
			System.exit(0);
			break;
		}
		scan.close();
	}
	
	protected boolean deleteCustomer(int customerID) {
		String sql = "SELECT order_number FROM `order` WHERE fk_customer_id=" + customerID;
		ResultSet rs = null;
		List<Integer> orders = new ArrayList<Integer>();

		try {
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				orders.add(rs.getInt("order_number"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < orders.size(); i++) {
			String sql2 = "DELETE FROM orderProduct WHERE fk_order_number=" + orders.get(0);
			try {
				stmt.executeUpdate(sql2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String sql3 = "DELETE FROM `order` WHERE fk_customer_id=" + customerID;
		String sql4 = "DELETE FROM address WHERE fk_customer_id="+customerID;
		String sql5 = "DELETE FROM customer WHERE customer_id=" + customerID;
		try {
			stmt.executeUpdate(sql3);
			stmt.executeUpdate(sql4);
			stmt.executeUpdate(sql5);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean deleteOrder(int orderNumber) {
		String sql = "DELETE FROM orderProduct WHERE fk_order_number=" + orderNumber;
		String sql2 = "DELETE FROM `order` WHERE order_number=" + orderNumber;
		try {
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	protected boolean deleteProduct(int productID) {
		String sql = "DELETE FROM product WHERE product_id=" + productID;

		try {
			stmt.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
