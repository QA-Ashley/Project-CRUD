package main.java.com.qa.db;

import java.sql.SQLException;
import java.util.List;

public class DeleteFunc extends Database {

	public DeleteFunc(String db) {
		super(db);
	}

	public boolean deleteCustomer(int customerID) {
		General general = new General();
		List<Integer> orders = general.retrieveOrders(customerID, stmt);
		if (!orders.isEmpty()) {
			deleteCustomerOrders(orders, customerID);
		}
		if (cleanUpCustomer(customerID)) {
			System.out.println("Customer deleted");
			return true;
		}
		System.out.println("Error deleting customer, returning to menu..");
		return false;
	}

	public boolean deleteOrder(int orderNumber) {
		String sql = "DELETE FROM orderProduct WHERE fk_order_number=" + orderNumber;
		String sql2 = "DELETE FROM `order` WHERE order_number=" + orderNumber;
		try {
			stmt.executeUpdate(sql);
			stmt.executeUpdate(sql2);
			System.out.println("Order deleted");
			return true;
		} catch (SQLException e) {
			System.out.println("Error deleting order, returning to menu..");
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteProductOrder(int orderNo, int productID, int quantity) {
		General general = new General();
		String[] data = general.checkQuantity(orderNo, productID, stmt);
		if (data[0] == null) {
			return false;
		}
		int orderQuantity = Integer.valueOf(data[0]);
		double price = Double.parseDouble(data[1]);
		price = price / orderQuantity;

		orderQuantity = orderQuantity - quantity;

		if (orderQuantity < 0) {
			System.out.println("You are trying to delete more products than exists within the order");
			return false;
		} else if (orderQuantity == 0) {
			String delete = "DELETE FROM orderProduct WHERE fk_order_number=" + orderNo + " AND fk_product_id="
					+ productID;
			try {
				stmt.executeUpdate(delete);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			String update = "UPDATE orderProduct SET quantity=" + orderQuantity + " WHERE fk_order_number=" + orderNo
					+ " AND fk_product_id=" + productID;
			try {
				stmt.executeUpdate(update);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		int currentStock = general.getProductStock(productID, stmt);
		currentStock += quantity;
		UpdateFunc u = new UpdateFunc(db);
		u.updateProduct("quantity", currentStock, productID);

		double total = general.getTotal(orderNo, stmt);

		if (u.updateOrder("total", total, orderNo)) {
			System.out.println("Product deleted from order");
			return true;
		}
		System.out.println("Error deleting product from order, returning to menu..");
		return false;
	}

	public boolean deleteProduct(int productID) {
		String sql = "DELETE FROM product WHERE product_id=" + productID;

		try {
			stmt.executeUpdate(sql);
			System.out.println("Product deleted");
			return true;
		} catch (SQLException e) {
			System.out.println("Error deleting product, returning to menu..");
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteCustomerOrders(List<Integer> orders, int customerID) {
		for (int i = 0; i < orders.size(); i++) {
			String sql2 = "DELETE FROM orderProduct WHERE fk_order_number=" + orders.get(0);
			String sql3 = "DELETE FROM `order` WHERE fk_customer_id=" + customerID;
			try {
				stmt.executeUpdate(sql2);
				stmt.executeUpdate(sql3);
				return true;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public boolean cleanUpCustomer(int customerID) {
		String sql3 = "DELETE FROM `order` WHERE fk_customer_id=" + customerID;
		String sql4 = "DELETE FROM address WHERE fk_customer_id=" + customerID;
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
}