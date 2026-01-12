package lab3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class IceCreamTest {

	@Test
	void testGetScoopCount() {
		IceCream flavor = new IceCream("vanilla", 3, 12.99);
		assertEquals(flavor.getScoopCount(), 3);
	}

	@Test
	void testSetScoopCount() {
		IceCream flavor = new IceCream("vanilla", 3, 12.99);
		flavor.setScoopCount(1500);
		assertEquals(flavor.getScoopCount(), 1500);
	}

	@Test
	void testGetPricePerScoop() {
		IceCream flavor = new IceCream("vanilla", 3, 12.99);
		assertEquals(flavor.getPricePerScoop(), 12.99);
	}

	@Test
	void testSetPricePerScoop() {
		IceCream flavor = new IceCream("vanilla", 3, 12.99);
		flavor.setPricePerScoop(99.98);
		assertEquals(flavor.getPricePerScoop(), 99.98);
	}

}
