package realEstateOffice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class HouseTest {

	@Test
	public void testGetYardAcres() {
		House hos = new House("0", 99999, 2, 2, 3800, 1.5);
		assertEquals(hos.getYardAcres(), 1.5);
	}
	@Test
	public void testSetYardAcres() {
		House hos = new House("0", 99999, 2, 2, 3800, 1.5);
		hos.setYardAcres(1.7);
		assertEquals(hos.getYardAcres(), 1.7);
	}
	@Test
	public void testApprasal() {
		House granateHouse = new House("granate street, AZ", 94404, 3, 3, 1800, 0.5);
		assertEquals(470600, granateHouse.calculateAppraisalPrice());
	}
}
