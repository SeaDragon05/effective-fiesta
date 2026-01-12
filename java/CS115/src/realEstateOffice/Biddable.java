package realEstateOffice;

import java.util.HashMap;
import java.util.Set;

public interface Biddable {
	abstract HashMap<String, Double> getBids();
	abstract double getBid(String value);
	abstract Set<String> getBidders();
	abstract int getBidCount();
	abstract void newBid(String str, double value);
}