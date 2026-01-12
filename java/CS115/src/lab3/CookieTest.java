package lab3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CookieTest {
	@Test
	void testGetCookieQty() {
		Cookie cki = new Cookie("Plain", 12, 3.89);
		assertEquals(cki.getCookieQty(), 12);
	}

	@Test
	void testSetCookieQty() {
		Cookie cki = new Cookie("Plain", 12, 3.89);
		cki.setCookieQty(8);
		assertEquals(cki.getCookieQty(), 8);
	}

	@Test
	void testGetPricePerDozen() {
		Cookie cki = new Cookie("Plain", 12, 3.89);
		assertEquals(cki.getPricePerDozen(), 3.89);
	}

	@Test
	void testSetPricePerDozen() {
		Cookie cki = new Cookie("Plain", 12, 3.89);
		cki.setPricePerDozen(4.29);
		assertEquals(cki.getPricePerDozen(), 4.29);
	}
	@Test
	void testCalculateCost() {
		Cookie cki = new Cookie("Plain", 12, 3.89);
		assertEquals(cki.getPricePerDozen(), cki.calculateCost());
	}
	@Test
	void testCalculateTax() {
		Cookie cki = new Cookie("Plain", 12, 3.89);
		assertEquals(3.89 * 0.0725, cki.calculateTax());
	}
	@Test
	void testIsSameAsTrue() {
		Cookie cky1 = new Cookie("gummies", 5, 4.25);
		Cookie cky2 = new Cookie("gummies", 5, 1.25);
		assertEquals(cky1.isSameAs(cky2),true);
		
	}
	@Test
	void testIsSameAsFalse() {
		Cookie cky1 = new Cookie("gummies", 3, 1.25);
		Cookie cky2 = new Cookie("gummies", 5, 1.25);
		assertEquals(cky1.isSameAs(cky2),false);
		
	}
}
