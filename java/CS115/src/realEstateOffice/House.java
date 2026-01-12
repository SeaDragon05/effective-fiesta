package realEstateOffice;

public class House extends Residential {
	private double yardAcres;
	public House(String addr, int zip, int bedCount, double bathCount, double sqFootage, double yardAcres) {
		super(addr, zip, bedCount, bathCount, sqFootage);
		this.yardAcres = yardAcres;
		this.setAppraisalPrice(this.calculateAppraisalPrice());
	}
	public House() {
		super();
		yardAcres = 0;
	}
	public double getYardAcres() {
		return yardAcres;
	}
	public void setYardAcres(double yardAcres) {
		this.yardAcres = yardAcres;
	}
	@Override
	public double calculateAppraisalPrice() {
		double result = 0;
		result += (97 * this.getSize());
		result += (10000 * this.getBed());
		result += (12000 * this.getBath());
		result += (460000 * this.getYardAcres());
		return result;
	}
	@Override
	public String toString() {
		String result = "";
		result += "-------------------------------------------------------------------------------------------------------\n";
		result += "Residence Type: House           Address: " + this.getStreetAddress() + "            Zip Code: " + this.getZip() + "\n";
		result += "-------------------------------------------------------------------------------------------------------\n";
		result += "Sq Footage: " + this.getSize() + "\n";
		result += "Bedrooms: " + this.getBed() + "\n";
		result += "Bathrooms: " + this.getBath() + "\n";
		result += "Yard Size (Acres): " + this.getYardAcres() + "\n";
		result += "------------------------------------------\n";
		result += "Apprasial Price: $" + this.getApraisalPrice() + "\n";
		result += "List price: $" + this.getListPrice() + "\n";
		result += "------------------------------------------\n";
		return result;
	}
}