package pos;

public class Drink {

	private String type;
	private String style;
	private String size;
	private double price;
	
	public Drink(String type, String style, String size) {
		this.type = type;
		this.style = style;
		this.size = size;
		setPrice();
	}
	
	//getters
	public String getType() {
		return this.type;
	}
	
	public String getStyle() {
		return this.style;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public String getSize() {
		return this.size;
	}
	
	public String toString() {
		return "<html>" + this.size + "&nbsp;" + this.type + "<br>" + this.style + "<br><div style=\"text-align:right\">Price: " + this.price + "</div><br></html>";
	}
	
	
	//setters
	public void setType(String type) {
		this.type = type;
	}
	
	public void setStyle(String style) {
		this.style = style;
	}
	
	public void setPrice() {
		if (type == "Fountain Drink") {
			if (size == "Small") {
				this.price = 1.00;
			}
			else if (size == "Medium") {
				this.price = 1.25;
			}
			else {
				this.price = 1.50;
			}
		}
		else {
			if (size == "Small") {
				this.price = 0.80;
			}
			else if (size == "Medium") {
				this.price = 1.05;
			}
			else {
				this.price = 1.30;
			}
		}
	}
	
	
}
