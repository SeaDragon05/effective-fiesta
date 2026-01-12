package realEstateOffice;

abstract class Property {
	private String streetAddress;
	private int zip;
	private double listPrice;
	private double apprasialPrice;
	public Property(String addr, int zip) {
		streetAddress = addr;
		this.zip = zip;
	}
	public Property() {
		streetAddress = "";
		zip = 0;
		listPrice = 0;
		apprasialPrice = 0;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public int getZip() {
		return zip;
	}
	public void setZip(int zip) {
		this.zip = zip;
	}
	public double getListPrice() {
		return listPrice;
	}
	public void setListPrice(double value) {
		listPrice = value;
	}
	public double getApraisalPrice() {
		return apprasialPrice;
	}
	protected void setAppraisalPrice(double value) {
		apprasialPrice = value;
	}
	abstract double calculateAppraisalPrice();
	
}
