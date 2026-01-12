package realEstateOffice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Listings implements Listable {
	private HashMap<String, Residential> listings;
	public Listings() {
		listings = new HashMap<String, Residential>();
	}
	@Override
	public HashMap<String, Residential> getListings() {
		return listings;
	}

	@Override
	public Residential getListing(String key) {
		return listings.get(key);
	}

	@Override
	public Set<String> getStreetAddress() {
		Set<String> result = new HashSet<String>();
		for (Residential i : listings.values()) {
			result.add(i.getStreetAddress());
		}
		return result;
	}

	@Override
	public Collection<Residential> getResidences() {
		ArrayList<Residential> result = new ArrayList<Residential>();
		for (Residential i : listings.values()) {
			result.add(i);
		}
		return result;
	}

	@Override
	public int getListingCount() {
		return listings.size();
	}
	@Override
	public void addListing(String key, Residential resident) {
		listings.put(key, resident);
	}

}