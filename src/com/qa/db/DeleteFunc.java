package com.qa.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.qa.menu.Inputs;
import com.qa.menu.Menu;

public class DeleteFunc extends Database {
	
	public void viewOptions(int option, int previousMenu) {
		Scanner scan = new Scanner(System.in);
		Inputs input = new Inputs();
		Menu menu = new Menu();
		
		switch (option) {
		case 1:
			int customerID = input.getCustomerID(scan);
			
			if(deleteCustomer(customerID)) {
				System.out.println("Customer deleted");
			} else {
				System.out.println("Error deleting customer, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 2:
			int orderNo = input.getOrderNo(scan);
			
			if(deleteOrder(orderNo)) {
				System.out.println("Order deleted");
			} else {
				System.out.println("Error deleting order, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 3:
			int productID = input.getProductID(scan);
			
			if(deleteProduct(productID)) {
				System.out.println("Product created");
			} else {
				System.out.println("Error deleting product, returning to menu..");
			}
			menu.subMenu(previousMenu);
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
	
	private boolean deleteCustomer(int customerID) {
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

	private boolean deleteOrder(int orderNumber) {
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

	private boolean deleteProduct(int productID) {
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
