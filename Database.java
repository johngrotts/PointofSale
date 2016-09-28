package pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;


public class Database {
	// Saves the order to a database Code pieces from https://nodehead.com/java-how-to-connect-to-xampps-mysql-in-eclipse/
	public static void saveToDatabase(double total, String custName, DefaultListModel<Sandwich> sandwiches, DefaultListModel<Drink> drinks) {
		String host = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "";
		//connect to the database

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(host, username, password);
			// creates a statement that can be used over and over again for running sql commands
			Statement stmt = con.createStatement();
			stmt.execute("USE pos");
			
			//Name and Total
			String query = "INSERT INTO pos_orders (name, total) VALUES (?, ?)";
			PreparedStatement prep;
			prep = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, custName);
			prep.setDouble(2, total);
			prep.executeUpdate();
            //String orderQuery = "INSERT INTO pos_orders (name, total) VALUES" + "(" + custName + ", " + total + ")";
            // Get Order Number
            ResultSet lastId = prep.getGeneratedKeys();
            int orderNum = 0;
            if(lastId.next())
            {
                orderNum = lastId.getInt(1);
            }
            for (int i = 0; i < sandwiches.getSize(); i++) {
            	prep = con.prepareStatement("INSERT INTO sandwiches (order_id, type, bun, cheese, lettuce, tomato, pickle, onion, ketchup, mayo, cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            	prep.setInt(1, orderNum);
            	prep.setString(2, sandwiches.getElementAt(i).getType());
                prep.setString(3, sandwiches.getElementAt(i).getBun());
                prep.setString(4, sandwiches.getElementAt(i).getCheese());
                prep.setBoolean(5, sandwiches.getElementAt(i).getLettuce());
                prep.setBoolean(6, sandwiches.getElementAt(i).getTomato());
                prep.setBoolean(7, sandwiches.getElementAt(i).getPickle());
                prep.setBoolean(8, sandwiches.getElementAt(i).getOnion());
                prep.setBoolean(9, sandwiches.getElementAt(i).getKetchup());
                prep.setBoolean(10, sandwiches.getElementAt(i).getMayo());
                prep.setDouble(11, sandwiches.getElementAt(i).getCost());
    			prep.executeUpdate();
			}
            for (int i = 0; i < drinks.getSize(); i++) {			
            	prep = con.prepareStatement("INSERT INTO drinks (order_id, type, style, size, price) VALUES (?, ?, ?, ?, ?)"); 
            	prep.setInt(1, orderNum);
            	prep.setString(2, drinks.getElementAt(i).getType());
            	prep.setString(3, drinks.getElementAt(i).getStyle());
            	prep.setString(4, drinks.getElementAt(i).getSize());
            	prep.setDouble(5, drinks.getElementAt(i).getPrice());
    			prep.executeUpdate();
			}
			
		}
		catch (Exception err) { //or fail
			err.printStackTrace();
		}
	}
	
	public static void displayOrder(int orderId) {
		String host = "jdbc:mysql://localhost:3306/";
		String username = "root";
		String password = "";
		//connect to the database
//		ArrayList columnNamesSand = new ArrayList();
//		ArrayList dataSand = new ArrayList();
//		ArrayList columnNamesDrink = new ArrayList();
//		ArrayList dataDrink = new ArrayList();
		

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(host, username, password);
			// creates a statement that can be used over and over again for running sql commands
			Statement stmt = con.createStatement();
			stmt.execute("USE pos");
			
			//get the order query
			String query = "SELECT * FROM pos_orders WHERE id=" + orderId;
			ResultSet rs = stmt.executeQuery(query);
			String orderInfo = "";
			//Get that info into a string
			while (rs.next()) {
				orderInfo = "Name: " + rs.getString("name") + " | Total: $" + rs.getDouble("total") + " \n \n";
			}
			//Prepare the sandwiches and drinks
			String sandwichesInfo = "SANDWICHES: \n";
			String drinksInfo = "DRINKS: \n";
			
			// Get the sandwiches
			query = "SELECT * FROM sandwiches WHERE order_id=" + orderId;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println("Testing");
				String type = rs.getString("type");
				String bun = rs.getString("bun");
				String cheese = rs.getString("cheese");
//				Boolean lettuce = rs.getBoolean("lettuce");
//				Boolean tomato = rs.getBoolean("tomato");
//				Boolean pickle = rs.getBoolean("pickle");
//				Boolean onion = rs.getBoolean("onion");
//				Boolean mayo = rs.getBoolean("mayo");
//				Boolean ketchup = rs.getBoolean("ketchup");
				sandwichesInfo = sandwichesInfo.concat(type + " on a " + bun + " with " + cheese + " \n");
				if (rs.getBoolean("lettuce")) {
					sandwichesInfo = sandwichesInfo.concat(" lettuce");
				}
				if (rs.getBoolean("tomato")) {
					sandwichesInfo = sandwichesInfo.concat(" tomato");
				}
				if (rs.getBoolean("pickle")) {
					sandwichesInfo = sandwichesInfo.concat(" pickle");
				}
				if (rs.getBoolean("onion")) {
					sandwichesInfo = sandwichesInfo.concat(" onion");
				}
				if (rs.getBoolean("mayo")) {
					sandwichesInfo = sandwichesInfo.concat(" mayo");
				}
				if (rs.getBoolean("ketchup")) {
					sandwichesInfo = sandwichesInfo.concat(" ketchup");
				}
				sandwichesInfo = sandwichesInfo.concat(" \n \n");
			}
			
			// Get the drinks			
			query = "SELECT * FROM drinks WHERE order_id=" + orderId;
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				String type = rs.getString("type");
				String style = rs.getString("style");
				String size = rs.getString("size");
				drinksInfo = drinksInfo.concat(size + " " + type + " -- " + style + " \n");
			}
			String totalOrder = orderInfo + sandwichesInfo + drinksInfo;
			JOptionPane.showMessageDialog(null, totalOrder);
			
			
//			
//			query = "SELECT * FROM sandwiches WHERE order_id=" + orderId;
//			ResultSet rsSand = stmt.executeQuery(query);
//			ResultSetMetaData mdSand = rsSand.getMetaData();
//			int columns = mdSand.getColumnCount();
//			//get the column names
//			for (int i = 0; i < columns; i++) {
//				columnNamesSand.add(mdSand.getColumnName(i));
//			}
//			
//			//get the data from teh table
//			while (rsSand.next()) {
//				ArrayList row = new ArrayList(columns);
//				// get each item in each row
//				for (int i = 0; i < columns; i++) {
//					row.add(rsSand.getObject(i));
//				}
//				data.add(row);
//			}
//			
//			String orderContents = "";
			
			
		}
		catch (Exception err) { //or fail
			err.printStackTrace();
		}
	}

}
