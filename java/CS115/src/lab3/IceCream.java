package lab3;

public class IceCream extends DessertItem {
	protected int scoopCount;
	protected double pricePerScoop;
	private String packaging;
	public IceCream() {
		super();
		scoopCount = 0;
		pricePerScoop = 0;
		packaging = "";
	}
	public IceCream(String name, int sc, double pps) {
		super(name);
		scoopCount = sc;
		pricePerScoop = pps;
		packaging = "Bowl";
	}
	public int getScoopCount() {
		return scoopCount;
	}
	public void setScoopCount(int scoopCount) {
		this.scoopCount = scoopCount;
	}
	public double getPricePerScoop() {
		return pricePerScoop;
	}
	public void setPricePerScoop(double pricePerScoop) {
		this.pricePerScoop = pricePerScoop;
	}
	@Override
	public double calculateTax() {
		return calculateCost() * getTaxPercent();
	}
	@Override
	public double calculateCost() {
		// TODO Auto-generated method stub
		return (scoopCount * pricePerScoop);
	}
	@Override
	public String toString() {
		String line1 = this.getName() + " (" + packaging + ")";
		String line2Pt1 = "" + this.getScoopCount() + " scoops @ $" + String.format("%.2f", this.getPricePerScoop()) + "/scoop";
		String line2Pt2 = "$" + String.format("%.2f", this.calculateCost());
		String line2Pt3 = "[Tax: $" + String.format("%.2f", this.calculateTax()) + "]";
		return String.format("%s\n\t%-45s%s%17s", line1, line2Pt1, line2Pt2, line2Pt3);
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
