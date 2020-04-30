package main.java.com.qa.db;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import main.java.com.qa.menu.Order;
import main.java.com.qa.security.Security;

public class CreateFunc extends Database {

	public CreateFunc(String db) {
		super(db);
	}

	public boolean createCustomer(String fname, String lname, String uname, String email, String password,
			String addressOne, String addressTwo, String town, String postcode) {
		if (postcode.length() > 8) {
			System.out.println("Postcode length is too long");
			return false;
		}

		General general = new General();
		if (!general.uniqueUnameCheck(uname, stmt)) {
			return false;
		}
		if (!general.uniqueEmailCheck(email, stmt)) {
			return false;
		}

		Security security = new Security();
		try {
			password = security.hashPassword(password);// convert password into sha-256
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		insertCustomer(fname, lname, uname, email, password);

		int id = general.getCustomerId(uname, stmt);
		if (id == 0) {
			return false;
		}
		if (insertAddress(id, addressOne, addressTwo, town, postcode)) {
			System.out.println("Customer created.");
			return true;
		}
		System.out.println("Error creating customer, returning to menu..");
		return false;
	}

	public boolean createProduct(String name, String category, short quantity, double price) {
		if (quantity > 255 || quantity < 0) {
			System.out.println("Cannot create product, quantity value is too large.");
			return false;
		}
		String sql = "INSERT INTO product VALUES(0,'" + name + "','" + category + "'," + quantity + "," + price + ")";
		try {
			stmt.executeUpdate(sql);
			System.out.println("Product created.");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Error creating product, returning to menu..");
		return false;
	}

	public boolean createOrder(List<Order> orders) {
		int entries = orders.size();
		int[] productID = new int[orders.size()];
		int[] quantity = new int[orders.size()];
		int customerID = orders.get(0).getCustomerID();

		for (int i = 0; i < entries; i++) {
			productID[i] = orders.get(i).getProductID();
			quantity[i] = orders.get(i).getQuantity();
		}

		General general = new General();
		int[] currentStock = general.checkStock(orders, stmt);
		if (currentStock.length != entries) {
			return false;
		}
		insertOrder(customerID);

		int orderNumber = general.getOrderNo(customerID, stmt);
		double[] prices = general.getProductPrices(entries, productID, quantity, stmt);

		String[] sql = new String[entries];

		for (int i = 0; i < entries; i++) {
			sql[i] = "INSERT INTO orderProduct VALUES(0," + orderNumber + "," + productID[i] + "," + quantity[i] + ","
					+ prices[i] + ");";
			try {
				stmt.executeUpdate(sql[i]);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		UpdateFunc update = new UpdateFunc(db);
		update.updateStock(entries, quantity, currentStock, productID);

		double totalPrice = general.getTotal(orderNumber, stmt);
		
		if(update.updateOrder("total", totalPrice, orderNumber)) {
			System.out.println("Order created");
			return true;
		}
		System.out.println("Error creating order, returning to menu..");
		return false;
	}

	public boolean addToOrder(int orderNo, int productID, int quantity) {
		ViewFunc view = new ViewFunc(db);
		if (!view.viewSingleOrder(orderNo)) {
			System.out.println("Order Number " + orderNo + " doesn't exist");
			return false;
		}
		General general = new General();
		int currentStock = general.getProductStock(productID, stmt);

		if (currentStock < quantity) {
			System.out.println("Current stock is lower than " + quantity + ". Please try again with a lower quantity");
			return false;
		}

		double price = general.getProductPrice(productID, stmt);
		
		price = price * quantity;

		double currentOrderQuantity = general.currentItemOrderExist(orderNo, productID, stmt);
		currentOrderQuantity += quantity;

		String sql;
		if (currentOrderQuantity != quantity) {
			price = currentOrderQuantity * (price / quantity);
			sql = "UPDATE orderProduct SET quantity=" + currentOrderQuantity + ", price=" + price
					+ " WHERE fk_order_number=" + orderNo + " AND fk_product_id=" + productID;
		} else {
			sql = "INSERT INTO orderProduct VALUES(0," + orderNo + "," + productID + "," + quantity + "," + price + ")";
		}

		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		currentStock = currentStock - quantity;
		UpdateFunc update = new UpdateFunc(db);
		update.updateProduct("quantity", currentStock, productID);

		double total = general.getTotal(orderNo, stmt);

		if (update.updateOrder("total", total, orderNo)) {
			System.out.println("Product added to order");
			return true;
		}
		System.out.println("Error adding to order, returning to menu..");
		return false;
	}

	public boolean insertCustomer(String fname, String lname, String uname, String email, String password) {
		String insertCustomer = "INSERT INTO customer VALUES(0,'" + fname + "','" + lname + "','" + uname + "','"
				+ email + "','" + password + "')";

		try {
			stmt.executeUpdate(insertCustomer);
			return true;
		} catch (SQLException e) {
			System.out.println("Error inserting customer");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertAddress(int id, String addressOne, String addressTwo, String town, String postcode) {
		String insertAddress = "INSERT INTO address VALUES(0," + id + ",'" + addressOne + "','" + addressTwo + "','"
				+ town + "','" + postcode + "')";

		try {
			stmt.executeUpdate(insertAddress);
			return true;
		} catch (SQLException e) {
			System.out.println("Error inserting address");
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean insertOrder(int customerID) {
		String sql1 = "INSERT INTO `order` (fk_customer_id, total) VALUES(" + customerID + ",0.00);";
		try {
			stmt.executeUpdate(sql1);
			return true;
		} catch (SQLException e) {
			System.out.println("Error generating order number");
			e.printStackTrace();
		}
		return false;
	}
}