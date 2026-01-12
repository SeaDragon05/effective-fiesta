package realEstateOffice;

import java.util.HashMap;
import java.util.Set;

abstract class Residential extends Property implements Biddable {
	public Residential(String addr, int zip, int bedCount, double bathCount, double sqFootage) {
		super(addr, zip);
		this.bedCount = bedCount;
		this.bathCount = bathCount;
		this.sqFootage = sqFootage;
	}
	public Residential() {
		super();
		bedCount = 0;
		bathCount = 0;
		sqFootage = 0;
	}
	private HashMap<String, Double> bids = new HashMap<String, Double>();
	private int bedCount;
	private double bathCount;
	private double sqFootage;
	public int getBed() {
		return bedCount;
	}
	public void setBed(int bedCount) {
		this.bedCount = bedCount;
	}
	public double getBath() {
		return bathCount;
	}
	public void setBath(double bathCount) {
		this.bathCount = bathCount;
	}
	public double getSize() {
		return sqFootage;
	}
	public void setSize(double sqFootage) {
		this.sqFootage = sqFootage;
	}
	@Override
	public HashMap<String, Double> getBids() {
		return bids;
	}
	@Override
	public double getBid(String value) {
		return bids.get(value);
	}
	@Override
	public Set<String> getBidders() {
		return bids.keySet();
	}
	@Override
	public int getBidCount() {
		return bids.size();
	}
	@Override
	public void newBid(String str, double value) {
		bids.put(str, value);
	}
	abstract double calculateAppraisalPrice();
}