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
		int orderNo;
		int productID;
		
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
			orderNo = input.getOrderNo(scan);
			
			if(deleteOrder(orderNo)) {
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
			
			if(deleteOrderProduct(orderNo, productID, quantity)) {
				System.out.println("Product deleted from order");
			} else {
				System.out.println("Error deleting product from order, returning to menu..");
			}
			menu.subMenu(previousMenu);
			break;
		case 4:
			productID = input.getProductID(scan);
			
			if(deleteProduct(productID)) {
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
	
	private boolean deleteOrderProduct(int orderNo, int productID, int quantity) {
		/*
		 * Grab quantity of product order in orderProduct table
		 * currentquantity - quantity
		 * if <0 then error
		 * delete product or update quantity and price
		 * update order table with new price
		 */
		String checkQuantity = "SELECT quantity, price FROM orderProduct WHERE fk_order_number="+orderNo+" AND fk_product_id="+productID;
		ResultSet rs = null;
		
		int orderQuantity = 0;
		double price = 0;
		try {
			rs = stmt.executeQuery(checkQuantity);
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					orderQuantity = rs.getInt("quantity");
					price = rs.getDouble("price");
				}
			} else {
				System.out.println("Order does not exist with product ID: " + productID);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		price = price/orderQuantity;
		
		orderQuantity = orderQuantity - quantity;
		
		if(orderQuantity < 0) {
			System.out.println("You are trying to delete more products than exists within the order");
			return false;
		} else if(orderQuantity == 0) {
			String delete = "DELETE FROM orderProduct WHERE fk_order_number="+orderNo+" AND fk_product_id="+productID;
			try {
				stmt.executeUpdate(delete);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String update = "UPDATE orderProduct SET quantity="+orderQuantity+" WHERE fk_order_number="+orderNo+" AND fk_product_id="+productID;
			try {
				stmt.executeUpdate(update);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String getStock = "SELECT quantity FROM product WHERE product_id="+productID;
		int currentStock = 0;
		try {
			rs = stmt.executeQuery(getStock);
			while(rs.next()) {
				currentStock = rs.getInt("quantity");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		currentStock += quantity;
		UpdateFunc update = new UpdateFunc();
		CreateFunc c = new CreateFunc();
		update.updateProduct("quantity", currentStock, productID);
		
		double total = c.getTotal(orderNo);
		
		if(update.updateOrder("total", total, orderNo)) {
			return true;
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
