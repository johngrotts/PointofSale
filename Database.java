package pos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;

import javax.swing.DefaultListModel;

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

}
