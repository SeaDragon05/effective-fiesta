package realEstateOffice;

public class Condo extends Residential {
	private int floorLvl;
	public Condo(String addr, int zip, int bedCount, double bathCount, double sqFootage, int floorLvl) {
		super(addr, zip, bedCount, bathCount, sqFootage);
		this.floorLvl = floorLvl;
		this.setAppraisalPrice(this.calculateAppraisalPrice());
	}
	public Condo() {
		super();
		floorLvl = 0;
	}
	public int getFloorLvl() {
		return floorLvl;
	}
	public void setFloorLvl(int floorLvl) {
		this.floorLvl = floorLvl;
	}
	@Override
	public double calculateAppraisalPrice() {
		double result = 0;
		result += (88 * this.getSize());
		result += (8000 * this.getBed());
		result += (10000 * this.getBath());
		result += (5000 * (this.getFloorLvl() - 1));
		return result;
	}
	@Override
	public String toString() {
		String result = "";
		result += "-------------------------------------------------------------------------------------------------------\n";
		result += "Residence Type: Condo           Address: " + this.getStreetAddress() + "            Zip Code: " + this.getZip() + "\n";
		result += "-------------------------------------------------------------------------------------------------------\n";
		result += "Sq Footage: " + this.getSize() + "\n";
		result += "Bedrooms: " + this.getBed() + "\n";
		result += "Bathrooms: " + this.getBath() + "\n";
		result += "Floor Level: " + this.getFloorLvl() + "\n";
		result += "------------------------------------------\n";
		result += "Apprasial Price: $" + this.getApraisalPrice() + "\n";
		result += "List price: $" + this.getListPrice() + "\n";
		result += "------------------------------------------\n";
		return result;
	}
}