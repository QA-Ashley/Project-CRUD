# Project-CRUD

Project-CRUD is an inventory management system which fulfils many user requirements such as full CRUD (Create, Read, Update, Delete) support.

## Pre-requirements

To run this application you will need to have a Java Runtime Environment installed on your system.

[Download JRE](https://www.oracle.com/java/technologies/javase-jre8-downloads.html)

Check you have java installed by running the following command:

```
java -version
```

## Usage

```
java -jar projectCRUD-jar-with-dependencies.jar

You will be presented with the main menu of the application.
```
![](https://github.com/QA-Ashley/Project-CRUD/blob/master/documentation/application-startScreen.png)

## Navigation

1. View data
	+ View customers
	+ View a customers address
	+ View customer orders
	+ View orders
	+ View single order
	+ View products
	+ Customer total spend
	+ Back to main menu
	+ Exit application
2. Amend data
	+ Update customer details
	+ Update product details
	+ Back to main menu
	+ Exit application
3. Insert data
	+ Create new customer
	+ Create new order
	+ Add product to order
	+ Create new product
	+ Back to main menu
	+ Exit application
4. Delete data
	+ Delete a customer
	+ Delete an order
	+ Delete a product from an order
	+ Delete a product 
	+ Back to main menu
	+ Exit application
5. Exit


## Entity Relationship Diagram
![](https://github.com/QA-Ashley/Project-CRUD/blob/master/documentation/ERD.png)

## Test Coverage

Tests conducted with junit and has a coverage of 80.1%. Limitations on time has prevented a higher percentage and code had to be refactored in order for some tests to be run.
![](https://github.com/QA-Ashley/Project-CRUD/blob/master/documentation/coverage.png)

## Acknowledgments

Thanks to the dream team of Vinesh, Piers and Savannah for helping guide me when I was massively confused.
