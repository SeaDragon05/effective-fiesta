package realEstateOffice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class PropertyTest {

	@Test
	public void testGetStreetAddress() {
		House prop = new House("0", 99999, 0, 0, 0, 0);
		assertEquals(prop.getStreetAddress(), "0");
	}
	@Test
	public void testSetStreetAddress() {
		House prop = new House("0", 99999, 0, 0, 0, 0);
		prop.setStreetAddress("14512 s, 64326 e");
		assertEquals(prop.getStreetAddress(), "14512 s, 64326 e");
	}
	@Test
	public void testGetZip() {
		House prop = new House("0", 99999, 0, 0, 0, 0);
		assertEquals(prop.getZip(), 99999);
	}
	@Test
	public void testSetZip() {
		House prop = new House("0", 99999, 0, 0, 0, 0);
		prop.setZip(84404);
		assertEquals(prop.getZip(), 84404);
	}
	@Test
	public void testGetListPrice() {
		House prop = new House("granate street, Gilbert, AZ", 94404, 3, 3, 1800, 0.5);//mission house that I lived in
		prop.setListPrice(prop.calculateAppraisalPrice());
		assertEquals(prop.getListPrice(), prop.calculateAppraisalPrice());
	}
	@Test
	public void testSetListPrice() {
		House prop = new House("granate street, Gilbert, AZ", 94404, 3, 3, 1800, 0.5);//mission house that I lived in
		prop.setListPrice(69420);
		assertEquals(69420, prop.getListPrice());
	}
	@Test
	public void testSetApprasialPrice() {
		House prop = new House("granate street, Gilbert, AZ", 94404, 3, 3, 1800, 0.5);//mission house that I lived in
		prop.setAppraisalPrice(400000);
		assertEquals(400000, prop.getApraisalPrice());
	}
	@Test
	public void testGetApprasialPrice() {
		House prop = new House("granate street, Gilbert, AZ", 94404, 3, 3, 1800, 0.5);//mission house that I lived in
		assertEquals(prop.calculateAppraisalPrice(), prop.getApraisalPrice());
	}
}
