package pos;

public class Sandwich {

	private String type;
	private String bun;
	private String cheese;
	private boolean lettuce;
	private boolean tomato;
	private boolean pickle;
	private boolean onion;
	private boolean mayo;
	private boolean ketchup;
	private double cost;
	
	//makes a sandwhich
	public Sandwich(String type, String bun) {
		this.type = type;
		this.bun = bun;
		this.cheese = "none";

		this.cost = 5.00;
	}
	public Sandwich(String type, String bun, String cheese, boolean lettuce, boolean tomato, boolean pickle, boolean onion, boolean mayo, boolean ketchup) {
		this.type = type;
		this.bun = bun;
		this.cost = 5.00;
		if (cheese != "None") {
			this.cheese = cheese;
			this.cost += 0.50;
		}
		else {
			this.cheese = "None";
		}
		this.lettuce = lettuce;
		this.tomato = tomato;
		this.pickle = pickle;
		this.onion = onion;
		this.mayo = mayo;
		this.ketchup = ketchup;
	}
	
	
	//getters
	public String getType() {
		return this.type;
	}
	
	public String getBun() {
		return this.bun;
	}
	
	public String getCheese() {
		return this.cheese;
	}
	
	public boolean getLettuce() {
		return this.lettuce;
	}
	
	public boolean getTomato() {
		return this.tomato;
	}
	
	public boolean getPickle() {
		return this.pickle;
	}
	
	public boolean getOnion() {
		return this.onion;
	}
	
	public boolean getMayo() {
		return this.mayo;
	}
	
	public boolean getKetchup() {
		return this.ketchup;
	}
	
	public double getCost() {
		return this.cost;
	}

	// big getter
	public String toString() {
		String sanOrder = "<html>" + this.type + " Sandwich on a " + this.bun + " bun";
		if (this.cheese != "None") { // cheese
			sanOrder += " with " + this.cheese + "<br>";
		}
		else {
			sanOrder += "<br>";
		}
		if (this.lettuce) { // lettuce
			sanOrder += "&nbsp;&nbsp; Lettuce<br>";
		}
		if (this.tomato) { // tomato
			sanOrder += "&nbsp;&nbsp; Tomato<br>";
		}
		if (this.pickle) { // pickle
			sanOrder += "&nbsp;&nbsp; Pickle<br>";
		}
		if (this.onion) { // onion
			sanOrder += "&nbsp;&nbsp; Onion<br>";
		}
		if (this.mayo) { // mayo
			sanOrder += "&nbsp;&nbsp; Mayo<br>";
		}
		if (this.ketchup) { // ketchup
			sanOrder += "&nbsp;&nbsp; Ketchup<br>";
		}
		sanOrder += "<div style=\"text-align:right\">Price: " + this.cost + "</div><br></html>";
		
		return sanOrder;
	}
	
	
	
	
	// setters
	public void setBun(String set) {
		this.bun = set;
	}
		
	public void setCheese(String set) {
		// if it already has cheese on it...
		if (this.cheese != "None") {
			if (set == "None") { // if you are removing cheese
				this.cheese = set;
				this.cost -= 0.50;
			}
			else { // changing cheese
				this.cheese = set;
			}
		}
		else { // no cheese on it before
			this.cheese = set;
			this.cost +=0.50;
		}
	}

	public void setLettuce() {
		if (this.lettuce == true) {
			this.lettuce = false;
		}
		else {
			this.lettuce = true;
		}
	}

	public void setTomato() {
		if (this.tomato == true) {
			this.tomato = false;
		}
		else {
			this.tomato = true;
		}
	}

	public void setPickle() {
		if (this.pickle == true) {
			this.pickle = false;
		}
		else {
			this.pickle = true;
		}
	}

	public void setOnion() {
		if (this.onion == true) {
			this.onion = false;
		}
		else {
			this.onion = true;
		}
	}

	public void setMayo() {
		if (this.mayo == true) {
			this.mayo = false;
		}
		else {
			this.mayo = true;
		}
	}

	public void setKetchup() {
		if (this.ketchup == true) {
			this.ketchup = false;
		}
		else {
			this.ketchup = true;
		}
	}
	
}
