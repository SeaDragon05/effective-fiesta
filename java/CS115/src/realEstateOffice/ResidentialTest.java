package realEstateOffice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;

public class ResidentialTest {
	@Test
	public void testGetBed() {
		House res = new House("0", 99999, 2, 2, 3800, 0);
		assertEquals(res.getBed(), 2);
	}
	@Test
	public void testSetBed() {
		House res = new House("0", 99999, 2, 2, 3800, 0);
		res.setBed(4);
		assertEquals(res.getBed(), 4);
	}
	@Test
	public void testGetBath() {
		House res = new House("0", 99999, 2, 2, 3800, 0);
		assertEquals(res.getBath(), 2);
	}
	@Test
	public void testSetBath() {
		House res = new House("0", 99999, 2, 2, 3800, 0);
		res.setBath(4);
		assertEquals(res.getBath(), 4);
	}
	@Test
	public void testGetSize() {
		House res = new House("0", 99999, 2, 2, 3800, 0);
		assertEquals(res.getSize(), 3800);
	}
	@Test
	public void testSetSize() {
		House res = new House("0", 99999, 2, 2, 3800, 0);
		res.setSize(3200);
		assertEquals(res.getSize(), 3200);
	}
}
