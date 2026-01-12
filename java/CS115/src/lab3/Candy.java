package lab3;

public class Candy extends DessertItem implements SameItem<Candy> {
	private double candyWeight;
	private double pricePerPound;
	private String packaging;
	public Candy() {
		super();
		candyWeight = 0;
		pricePerPound = 0;
		packaging = "";
	}
	public Candy(String name, double cw, double ppp) {
		super(name);
		candyWeight = cw;
		pricePerPound = ppp;
		packaging = "Bag";
	}
	public double getCandyWeight() {
		return candyWeight;
	}
	public void setCandyWeight(double candyWeight) {
		this.candyWeight = candyWeight;
	}
	public double getPricePerPound() {
		return pricePerPound;
	}
	public void setPricePerPound(double pricePerPound) {
		this.pricePerPound = pricePerPound;
	}
	@Override
	public double calculateCost() {
		return (candyWeight * pricePerPound);
	}
	@Override
	public double calculateTax() {
		return calculateCost() * getTaxPercent();
	}
	@Override
	public String toString() {
		String line1 = this.getName() + " (" + packaging + ")";
		String line2Pt1 = "" + this.getCandyWeight() + " lbs. @ $" + String.format("%.2f", this.getPricePerPound()) + "/lb";
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
	@Override
	public boolean isSameAs(Candy k) { 
		return (this.getPricePerPound() == k.getPricePerPound()) &&
				(this.getName().equals(k.getName()));
	}
}
