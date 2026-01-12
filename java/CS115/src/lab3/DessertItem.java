package lab3;

public abstract class DessertItem implements Packaging, Comparable<DessertItem>	{
	private String name;
	private double taxPercent = 0.0725;
	public DessertItem() {
		name = "";
	}
	public DessertItem(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getTaxPercent() {
		return taxPercent;
	}
	public void setTaxPercent(double value) {
		taxPercent = value;
	}
	@Override
	public int compareTo(DessertItem c) {
		if (this.calculateCost() < c.calculateCost()) return -1;
		else if (this.calculateCost() > c.calculateCost()) return 1;									
		else return 0;
	}
	public abstract double calculateCost();
	public abstract double calculateTax();
}