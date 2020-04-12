CREATE DATABASE IF NOT EXISTS halloscream;

CREATE TABLE IF NOT EXISTS halloscream.customer (
	customer_id INT AUTO_INCREMENT,
    fname VARCHAR(30) NOT NULL,
    lname VARCHAR(30) NOT NULL,
    username VARCHAR(20) NOT NULL,
    UNIQUE (username), # Usernames should be unique to each customer
    email VARCHAR(255) NOT NULL,
    UNIQUE (email), # No customers should be using the same email address
    password CHAR(64) NOT NULL, # Will implement SHA256
    PRIMARY KEY (customer_id)
);
CREATE TABLE IF NOT EXISTS halloscream.address (
	address_id INT AUTO_INCREMENT,
    fk_customer_id INT NOT NULL,
    address1 VARCHAR(50) NOT NULL,
    address2 VARCHAR(50) DEFAULT NULL, # Address2 may not always be populated so defaults to NULL if no data entered for this field
    town VARCHAR(20) NOT NULL,
    postcode VARCHAR(8) NOT NULL,
    PRIMARY KEY (address_id),
    FOREIGN KEY (fk_customer_id) REFERENCES halloscream.customer(customer_id)
);
CREATE TABLE IF NOT EXISTS halloscream.product (
	product_id INT AUTO_INCREMENT,
    `name` VARCHAR(100) NOT NULL,
    category VARCHAR(25) NOT NULL,
    price DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (product_id)
);
CREATE TABLE IF NOT EXISTS halloscream.`order` (
	order_id INT AUTO_INCREMENT,
    fk_customer_id INT NOT NULL,
    fk_product_id INT NOT NULL,
    quantity TINYINT NOT NULL,
    date_placed DATETIME DEFAULT CURRENT_TIMESTAMP, # Default is the time of order
    total DECIMAL(6,2),
    PRIMARY KEY (order_id),
    FOREIGN KEY (fk_customer_id) REFERENCES halloscream.customer(customer_id),
    FOREIGN KEY (fk_product_id) REFERENCES halloscream.product(product_id)
);
