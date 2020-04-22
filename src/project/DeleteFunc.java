package project;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeleteFunc extends Database {
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
