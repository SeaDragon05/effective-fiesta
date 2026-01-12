package lab3;

public class Sundae extends IceCream {
	private String toppingName;
	private double toppingPrice;
	private String packaging;
	public Sundae() {
		super();
		toppingName = "";
		toppingPrice = 0;
		packaging = "";
	}
	public Sundae(String name, int sc, double pps, String tn, double tp) {
		super(name, sc, pps);
		toppingName = tn;
		toppingPrice = tp;
		packaging = "Boat";
	}
	public String getToppingName() {
		return toppingName;
	}
	public void setToppingName(String toppingName) {
		this.toppingName = toppingName;
	}
	public double getToppingPrice() {
		return toppingPrice;
	}
	public void setToppingPrice(double toppingPrice) {
		this.toppingPrice = toppingPrice;
	}
	@Override
	public double calculateCost() {
		return (scoopCount * pricePerScoop) + toppingPrice;
	}
	@Override
	public String toString() {
		String line1 = this.getToppingName() + " " + this.getName() + " Sundae" + " (" + packaging + ")";
		String line2Pt1 = "" + this.getScoopCount() + " scoops of " + this.getName() + " icecream @ $" + String.format("%.2f", this.getPricePerScoop()) + "/scoop" + "\n\t" + this.getToppingName() + " topping @ $" + this.getToppingPrice();
		String line2Pt2 = "$" + String.format("%.2f", this.calculateCost());
		String line2Pt3 = "[Tax: $" + String.format("%.2f", this.calculateTax()) + "]";
		return String.format("%s\n\t%-89s%s%17s", line1, line2Pt1, line2Pt2, line2Pt3);
	}
	@Override
	public String getPackaging() {
		// TODO Auto-generated method stub
		return packaging;
	}
	@Override
	public void setPackaging(String value) {
		// TODO Auto-generated method stub
		this.packaging = value;
	}
}
