package realEstateOffice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class CondoTest {
	@Test
	public void testGetFloorLvl() {
		Condo con = new Condo("0", 99999, 2, 2, 3800, 2);
		assertEquals(con.getFloorLvl(), 2);
	}
	@Test
	public void testSetFloorLvl() {
		Condo con = new Condo("0", 99999, 2, 2, 3800, 2);
		con.setFloorLvl(3);
		assertEquals(con.getFloorLvl(), 3);
	}
	@Test
	public void testApprasal() {
		Condo con = new Condo("42241", 84404, 2, 2, 1200, 2);
		assertEquals(146600, con.calculateAppraisalPrice());
	}
}
