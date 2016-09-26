package pos;

/*
 * John Grotts
 */


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.*;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;


//import javax.swing.JScrollPane;
//for writing
import java.io.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// import for database writing
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class PointOfSale {

	private JFrame frmPos;
	//private JScrollPane frame;
	private JTextField custName;
	private JTextField totalPrice;
	private DefaultListModel<Sandwich> sandwiches;
	private JList list;
	private DefaultListModel<Drink> drinks;
	private JList drinkList;
	private PrintWriter pWriter;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PointOfSale window = new PointOfSale();
					window.frmPos.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PointOfSale() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frmPos = new JFrame();
		frmPos.getContentPane().setBackground(new Color(240, 255, 255));
		frmPos.setTitle("POS");
		frmPos.setBounds(100, 100, 900, 650);
		frmPos.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPos.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 13, 342, 577);
		frmPos.getContentPane().add(tabbedPane);
		
		
	//Sandwich Tab
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("Sandwiches", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(12, 13, 56, 16);
		panel.add(lblType);
		
		//Sandwich Dropdown
		String[] sandOptions = {"Cheese", "Chicken", "Ham", "Turkey"};
		JComboBox sandComboBox = new JComboBox(sandOptions);
		sandComboBox.setBounds(12, 42, 313, 22);
		panel.add(sandComboBox);
		
		JLabel lblBun = new JLabel("Bun:");
		lblBun.setBounds(12, 77, 56, 16);
		panel.add(lblBun);
		
		JComboBox bunComboBox = new JComboBox();
		bunComboBox.setModel(new DefaultComboBoxModel(new String[] {"Regular", "Pretzel", "Wheat", "No Bun"}));
		bunComboBox.setBounds(12, 106, 313, 22);
		panel.add(bunComboBox);
		
		JLabel lblCustomize = new JLabel("CUSTOMIZE");
		lblCustomize.setBounds(94, 141, 80, 16);
		panel.add(lblCustomize);
		
		JLabel lblCheese = new JLabel("Cheese:");
		lblCheese.setBounds(61, 170, 56, 16);
		panel.add(lblCheese);
		
		JComboBox cheeseComboBox = new JComboBox();
		cheeseComboBox.setModel(new DefaultComboBoxModel(new String[] {"None", "Swiss", "Cheddar", "Gouda"}));
		cheeseComboBox.setBounds(129, 167, 196, 22);
		panel.add(cheeseComboBox);
		
		JCheckBox chckbxLettuce = new JCheckBox("Lettuce");
		chckbxLettuce.setBackground(Color.WHITE);
		chckbxLettuce.setBounds(12, 226, 113, 25);
		panel.add(chckbxLettuce);
		
		JCheckBox chckbxTomato = new JCheckBox("Tomato");
		chckbxTomato.setBackground(Color.WHITE);
		chckbxTomato.setBounds(12, 256, 113, 25);
		panel.add(chckbxTomato);
		
		JCheckBox chckbxPickle = new JCheckBox("Pickle");
		chckbxPickle.setBackground(Color.WHITE);
		chckbxPickle.setBounds(12, 286, 113, 25);
		panel.add(chckbxPickle);
		
		JCheckBox chckbxOnion = new JCheckBox("Onion");
		chckbxOnion.setBackground(Color.WHITE);
		chckbxOnion.setBounds(12, 316, 113, 25);
		panel.add(chckbxOnion);
		
		JCheckBox chckbxMayo = new JCheckBox("Mayo");
		chckbxMayo.setBackground(Color.WHITE);
		chckbxMayo.setBounds(129, 226, 113, 25);
		panel.add(chckbxMayo);
		
		JCheckBox chckbxKetchup = new JCheckBox("Ketchup");
		chckbxKetchup.setBackground(Color.WHITE);
		chckbxKetchup.setBounds(129, 256, 113, 25);
		panel.add(chckbxKetchup);
		
		JLabel sandErrorLabel = new JLabel("");
		sandErrorLabel.setForeground(Color.RED);
		sandErrorLabel.setBounds(44, 480, 254, 16);
		panel.add(sandErrorLabel);
		
		JButton btnAddSandwichTo = new JButton("Add Sandwich to Order");
		btnAddSandwichTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String typ = (String)sandComboBox.getSelectedItem();
				String bun = (String)bunComboBox.getSelectedItem();
				String che = (String)cheeseComboBox.getSelectedItem();
				boolean let = chckbxLettuce.isSelected();
				boolean tom = chckbxTomato.isSelected();
				boolean pic = chckbxPickle.isSelected();
				boolean oni = chckbxOnion.isSelected();
				boolean may = chckbxMayo.isSelected();
				boolean ket = chckbxKetchup.isSelected();
				// Order of ingredients: tbc,ltpomk
				addSandToOrder(typ, bun, che, let, tom, pic, oni, may, ket);
				
				
				//Clear the Sandwich Menu
				sandComboBox.setSelectedIndex(0);
				bunComboBox.setSelectedIndex(0);
				cheeseComboBox.setSelectedIndex(0);
				chckbxLettuce.setSelected(false);
				chckbxTomato.setSelected(false);
				chckbxPickle.setSelected(false);
				chckbxOnion.setSelected(false);
				chckbxMayo.setSelected(false);
				chckbxKetchup.setSelected(false);
			}
		});
		btnAddSandwichTo.setBounds(44, 509, 254, 25);
		panel.add(btnAddSandwichTo);		
		
		
		
	//Drinks Tab
		JPanel drinksPanel = new JPanel();
		drinksPanel.setBackground(Color.WHITE);
		tabbedPane.addTab("Drinks", null, drinksPanel, null);
		drinksPanel.setLayout(null);
		
		JLabel lblDrinkType = new JLabel("Type:");
		lblDrinkType.setBounds(12, 55, 56, 16);
		drinksPanel.add(lblDrinkType);
		
		JComboBox drinkComboBox = new JComboBox();
		drinkComboBox.setModel(new DefaultComboBoxModel(new String[] {"Fountain Drink", "Coffee"}));
		drinkComboBox.setBounds(101, 52, 224, 22);
		drinksPanel.add(drinkComboBox);
		
		JLabel lblSelection = new JLabel("Fountain Drink Selection:");
		lblSelection.setBounds(12, 118, 150, 16);
		drinksPanel.add(lblSelection);
		
		JComboBox fountainComboBox = new JComboBox();
		fountainComboBox.setModel(new DefaultComboBoxModel(new String[] {"Cola", "Diet Cola", "Dr. Pepper", "7-Up", "Ice Tea", "Lemonade"}));
		fountainComboBox.setBounds(12, 147, 130, 22);
		drinksPanel.add(fountainComboBox);
		
		JLabel lblCoffeeSel = new JLabel("Coffee Selection:");
		lblCoffeeSel.setBounds(180, 118, 145, 16);
		drinksPanel.add(lblCoffeeSel);
		
		JComboBox coffeeComboBox = new JComboBox();
		coffeeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Leaded", "Unleaded", "Flavored"}));
		coffeeComboBox.setBounds(180, 147, 145, 22);
		drinksPanel.add(coffeeComboBox);
		
		JLabel lblSize = new JLabel("Size:");
		lblSize.setBounds(12, 315, 56, 16);
		drinksPanel.add(lblSize);
		
		JComboBox drinkSizeComboBox = new JComboBox();
		drinkSizeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Small", "Medium", "Large"}));
		drinkSizeComboBox.setSelectedIndex(1);
		drinkSizeComboBox.setBounds(101, 312, 224, 22);
		drinksPanel.add(drinkSizeComboBox);
		panel.setLayout(null);
		
		JLabel lblDrinkError = new JLabel("");
		lblDrinkError.setForeground(Color.RED);
		lblDrinkError.setBounds(38, 480, 254, 16);
		drinksPanel.add(lblDrinkError);
		
		JButton btnAddDrink = new JButton("Add Drink to Order");
		btnAddDrink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typ = (String)drinkComboBox.getSelectedItem();
				String fou = (String)fountainComboBox.getSelectedItem();
				String cof = (String)coffeeComboBox.getSelectedItem();
				String siz = (String)drinkSizeComboBox.getSelectedItem();
				addDrinkToOrder(typ, fou, cof, siz);
				
				drinkComboBox.setSelectedIndex(0);
				fountainComboBox.setSelectedIndex(0);
				coffeeComboBox.setSelectedIndex(0);
				drinkSizeComboBox.setSelectedIndex(1);
				
			}
		});
		btnAddDrink.setBounds(38, 509, 254, 25);
		drinksPanel.add(btnAddDrink);
		
		
		
	// Customer Info
		JLabel lblCustomerName = new JLabel("Customer Name:");
		lblCustomerName.setBounds(394, 19, 104, 16);
		frmPos.getContentPane().add(lblCustomerName);
		
		custName = new JTextField();
		custName.setBounds(510, 16, 360, 22);
		frmPos.getContentPane().add(custName);
		custName.setColumns(10);
		
		
	// The lists
		//setup the JList
		sandwiches = new DefaultListModel<Sandwich>(); 
		
		/**
		 * Does this work?
		 * 
		JScrollPane scrollPane = new JScrollPane();
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JPanel container = new JPanel();
		JScrollPane scrPane = new JScrollPane(container);
		add(scrPane); // similar to getContentPane().add(scrPane);
		// Now, you can add whatever you want to the container
		STARTING  POINT
		*/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(366, 48, 303, 392);
		frmPos.getContentPane().add(scrollPane);

		JList list_1 = new JList(sandwiches);
		scrollPane.setViewportView(list_1);
		list_1.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		list_1.setBackground(Color.WHITE);
		
		drinks = new DefaultListModel<Drink>();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(681, 48, 189, 392);
		frmPos.getContentPane().add(scrollPane_1);
		
		JList drinksList = new JList(drinks);
		scrollPane_1.setViewportView(drinksList);
		drinksList.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		drinksList.setBackground(Color.WHITE);
		
		
	// Total
		JLabel lblTotal = new JLabel("Total:");
		lblTotal.setBounds(417, 568, 34, 22);
		frmPos.getContentPane().add(lblTotal);
		
		totalPrice = new JTextField();
		double price = 0.00;
		String priceString = String.valueOf(price);
		totalPrice.setText(priceString);
		totalPrice.setBounds(463, 568, 116, 22);
		frmPos.getContentPane().add(totalPrice);
		totalPrice.setColumns(10);
		
		
		
	// Delete items
		JButton btnDeleteItem = new JButton("Delete Sandwhich");
		btnDeleteItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = list_1.getSelectedIndex();
				if (selected != -1) {
					int option = JOptionPane.showConfirmDialog(null, "Delete this item?");
					if (option == 0) {
						sandwiches.remove(selected);
						updatePrice();
					}
				}
			}
		});
		btnDeleteItem.setBounds(366, 453, 303, 25);
		frmPos.getContentPane().add(btnDeleteItem);
		frmPos.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tabbedPane}));
		
		JButton btnDeleteDrink = new JButton("Delete Drink");
		btnDeleteDrink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int selected = drinksList.getSelectedIndex();
				if (selected != -1) {
					int option = JOptionPane.showConfirmDialog(null, "Delete this drink?");
					if (option == 0) {
						drinks.remove(selected);
						updatePrice();
					}
				}
			}
		});
		btnDeleteDrink.setBounds(681, 453, 189, 25);
		frmPos.getContentPane().add(btnDeleteDrink);
		
		
	// Finalize Order
		JLabel finalErrorLabel = new JLabel("");
		finalErrorLabel.setForeground(Color.RED);
		finalErrorLabel.setBounds(591, 539, 279, 16);
		frmPos.getContentPane().add(finalErrorLabel);
		
		JButton btnFinalize = new JButton("Finalize Order");
		btnFinalize.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				finalErrorLabel.setVisible(false);
				String missingName = "Please Enter a Customer Name";
				String noOrder = "Nothing is Ordered";
				String customerName = custName.getText();
				if (custName.getText().equals("")) {
					finalErrorLabel.setText(missingName);
					finalErrorLabel.setVisible(true);
				}
				else if (sandwiches.isEmpty() && drinks.isEmpty()) {
					finalErrorLabel.setText(noOrder);
					finalErrorLabel.setVisible(true);
					
				}
				else {
					String cash = JOptionPane.showInputDialog("Cash:");
					String notMoney = "That is not an amount";
					if (isDouble(cash)){
						String noMoney = "Not enough money";
						double dCash = Double.parseDouble(cash);
						double totalAmt = Double.parseDouble(totalPrice.getText());
						if (dCash >= totalAmt) {
							finalizeOrder(dCash, totalAmt);
						}
						else {
							finalErrorLabel.setText(noMoney);
							finalErrorLabel.setVisible(true);
							
						}
					}
					else {
						finalErrorLabel.setText(notMoney);
						finalErrorLabel.setVisible(true);
						
					}
				}
			}
		});
		btnFinalize.setForeground(new Color(220, 20, 60));
		btnFinalize.setBounds(591, 568, 279, 22);
		frmPos.getContentPane().add(btnFinalize);
	}
	
	
//methods for other things
	
	//add a sandwich
	public void addSandToOrder(String typ, String bun, String che, boolean let, boolean tom, boolean pic, boolean oni, boolean may, boolean ket) { 
		// Order of ingredients: tbc,ltpomk
		Sandwich san = new Sandwich(typ, bun, che, let, tom, pic, oni, may, ket);
		//System.out.println(san.toString());
		sandwiches.add(sandwiches.size(), san);
		updatePrice();
	}
	
	//add a drink
	public void addDrinkToOrder(String type, String fountain, String coffee, String size) {
		double price = 0.00;
		if (type == "Fountain Drink") {
			Drink drink = new Drink(type, fountain, size);
			drinks.add(drinks.size(), drink);
			updatePrice();
		}
		else if (type == "Coffee") {
			Drink drink = new Drink(type, coffee, size);
			drinks.add(drinks.size(), drink);
			updatePrice();
		}
		else {
			System.out.println("Error");
		}
	}
	
	
	//update the total box
	public void updatePrice() {
		double price = 0.00;
		for (int i = 0; i < sandwiches.getSize(); i++) {
			double cost = sandwiches.getElementAt(i).getCost();
			price += cost;
		}
		for (int i = 0; i < drinks.getSize(); i++) {
			double cost = drinks.getElementAt(i).getPrice();
			price += cost;
		}
		double totPrice = Math.round(price * 100);
		String priceString = String.valueOf(totPrice / 100);
		totalPrice.setText(priceString);
	}
	
	/*
	 * @param testCase see if it's a number
	 */
	public boolean isDouble(String testCase) {
		try {
			Double.parseDouble(testCase);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 
	 * @param cash the amount the customer gives you
	 * @param total the order total
	 */
	public void finalizeOrder(double cash, double total) {
		double preChange = cash - total;
		// round the change to 2 decimal places
		double change = Math.round(preChange * 100);
		String changeString = String.valueOf(change / 100);
		// show the change
		JOptionPane.showMessageDialog(null, "Your Change is: $" + changeString);
		// Save to the database
		String customerName = custName.getText();
		Database.saveToDatabase(total, customerName, sandwiches, drinks);
		
		fileWrite();
		drinks.clear();
		sandwiches.clear();
		custName.setText("");
		updatePrice();
	}
/*
	// Saves the order to a database Code pieces from https://nodehead.com/java-how-to-connect-to-xampps-mysql-in-eclipse/
	public void saveToDatabase(double total) {
		String host = "jdbc:mysql:http://localhost:3306";
		String username = "root";
		String password = "";
		//connect to the database
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con = DriverManager.getConnection(host, username, password);
			// creates a statement that can be used over and over again for running sql commands
			Statement stmt = con.createStatement();
			
			//Name and Total
            String orderQuery = "INSERT INTO pos_orders (name, total) VALUES" + 
                    "(" + custName + "," + total + ")";
            int orderNum = stmt.executeUpdate(orderQuery, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < sandwiches.getSize(); i++) {			
            	stmt.execute("INSERT INTO sandwiches (order_id, type, bun, cheese, lettuce, tomato, pickle, onion, ketchup, mayo, cost) VALUES" + 
                    "(" + orderNum + "," + sandwiches.getElementAt(i).getType() +
                    "," + sandwiches.getElementAt(i).getBun() +
                    "," + sandwiches.getElementAt(i).getCheese() +
                    "," + sandwiches.getElementAt(i).getLettuce() + 
                    "," + sandwiches.getElementAt(i).getTomato() + 
                    "," + sandwiches.getElementAt(i).getPickle() + 
                    "," + sandwiches.getElementAt(i).getOnion() + 
                    "," + sandwiches.getElementAt(i).getKetchup() + 
                    "," + sandwiches.getElementAt(i).getMayo() + 
                    "," + sandwiches.getElementAt(i).getCost() + ")");
			}
            for (int i = 0; i < drinks.getSize(); i++) {			
            	stmt.execute("INSERT INTO sandwiches (order_id, type, style, size, price) VALUES" + 
                    "(" + orderNum + "," + drinks.getElementAt(i).getType() +
                    "," + drinks.getElementAt(i).getStyle() +
                    "," + drinks.getElementAt(i).getSize() +
                    "," + drinks.getElementAt(i).getPrice() + ")");
			}
			
		}
		catch (Exception err) { //or fail
			err.printStackTrace();
		}
	}
*/
	public void fileWrite() {
		String date = new Date().toString();
		Date fileDate = new Date();
		DateFormat sfnd = new SimpleDateFormat("yyyyMMdd");
		String fileName =  "recLog/" + sfnd.format(fileDate) + ".txt";
		try {
			FileWriter file = new FileWriter(fileName, true);
			System.out.println("WRITING!!!");
			file.write(date);
			file.write(System.lineSeparator());
			file.write(custName.getText());
			file.write(System.lineSeparator());

			for (int i = 0; i < sandwiches.getSize(); i++) {
				file.write(sandwiches.getElementAt(i).getType() + " Sandwich on a " + sandwiches.getElementAt(i).getBun() + " Bun with ");
				if (sandwiches.getElementAt(i).getCheese() == "None") { // cheese
					file.write("no cheese");
					file.write(System.lineSeparator());
				}
				else {
					file.write(sandwiches.getElementAt(i).getCheese());
					file.write(System.lineSeparator());
				}
				if (sandwiches.getElementAt(i).getLettuce()) { // lettuce
					file.write("   lettuce,");
				}
				if (sandwiches.getElementAt(i).getTomato()) { // tomato
					file.write("  tomato,");
				}
				if (sandwiches.getElementAt(i).getPickle()) { // pickle
					file.write("  pickle,");
				}
				if (sandwiches.getElementAt(i).getOnion()) { // onion
					file.write("  onion,");
				}
				if (sandwiches.getElementAt(i).getMayo()) { // mayo
					file.write("  mayo,");
				}
				if (sandwiches.getElementAt(i).getKetchup()) { // ketchup
					file.write("  ketchup");
				}
				//add cost here
				file.write(System.lineSeparator());
				file.write(System.lineSeparator());
				
			}
			for (int i = 0; i < drinks.getSize(); i++) {
				file.write(drinks.getElementAt(i).getSize() + " " + drinks.getElementAt(i).getType() + " " + drinks.getElementAt(i).getStyle());
				file.write(System.lineSeparator());
				file.write(System.lineSeparator());
				
			}
			//add total and change here
			file.write("Total Cost: $" + totalPrice.getText());
			file.write(System.lineSeparator());
			file.write(System.lineSeparator());
			
			file.write("END ORDER");
			file.write(System.lineSeparator());
			file.write(System.lineSeparator());
			file.write(System.lineSeparator());
			file.close();
		} catch(IOException e) {
			//e.printStackTrace();
			System.out.println("Could not write to file");
		}
		
		
		
	}
}
