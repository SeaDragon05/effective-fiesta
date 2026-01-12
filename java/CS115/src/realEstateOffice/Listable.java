package realEstateOffice;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public interface Listable {
	abstract HashMap<String, Residential> getListings();
	abstract Residential getListing(String key);
	abstract Set<String> getStreetAddress();
	abstract Collection<Residential> getResidences();
	abstract int getListingCount();
	abstract void addListing(String key, Residential resident);
}