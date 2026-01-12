package lab3;

public class Cookie extends DessertItem implements SameItem<Cookie> {
	private int cookieQty;
	private double pricePerDozen;
	private String packaging;
	public Cookie() {
		super();
		cookieQty = 0;
		pricePerDozen = 0;
	}
	public Cookie(String name, int cq, double ppd) {
		super(name);
		cookieQty = cq;
		pricePerDozen = ppd;
		packaging = "Box";
	}
	public int getCookieQty() {
		return cookieQty;
	}
	public void setCookieQty(int cookieQty) {
		this.cookieQty = cookieQty;
	}
	public double getPricePerDozen() {
		return pricePerDozen;
	}
	public void setPricePerDozen(double pricePerDozen) {
		this.pricePerDozen = pricePerDozen;
	}
	@Override
	public double calculateCost() {
		return (cookieQty * pricePerDozen / 12);// * getTaxPercent();
	}
	@Override
	public double calculateTax() {
		return calculateCost() * getTaxPercent();
	}
	@Override
	public String toString() {
		String line1 = this.getName() + " (" + packaging + ")";
		String line2Pt1 = "" + this.getCookieQty() + " cookies @ $" + String.format("%.2f", this.getPricePerDozen()) + "/dozen";
		String line2Pt2 = "$" + String.format("%.2f", this.calculateCost());
		String line2Pt3 = "[Tax: $" + String.format("%.2f", this.calculateTax()) + "]";
		return String.format("%s\n\t%-45s%s%17s", line1, line2Pt1, line2Pt2, line2Pt3);
	}
	@Override
	public String getPackaging() {
		return packaging;
	}
	@Override
	public void setPackaging(String value) {
		// TODO Auto-generated method stub
		this.packaging = value;
	}
	@Override
	public boolean isSameAs(Cookie k) {
		return (this.getPricePerDozen() == k.getPricePerDozen()) &&
				(this.getName().equals(k.getName()));}
}
