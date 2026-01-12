package lab3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CandyTest {
	@Test
	void testGetCandyWeight() {
		Candy cndy = new Candy("pop", 4.80,0.99);
		assertEquals(cndy.getCandyWeight(), 4.80);
	}
	@Test
	void testSetCandyWeight() {
		Candy cndy = new Candy("pop", 4.80,0.99);
		cndy.setCandyWeight(1.80);
		assertEquals(cndy.getCandyWeight(), 1.80);
	}
	@Test
	void testGetPricePerPound() {
		Candy cndy = new Candy("pop", 4.80,0.99);
		assertEquals(cndy.getPricePerPound(), 0.99);
	}
	@Test
	void testSetPricePerPound() {
		Candy cndy = new Candy("pop", 4.80,0.99);
		cndy.setPricePerPound(0.89);
		assertEquals(cndy.getPricePerPound(), 0.89);
	}
	@Test
	void testIsSameAsTrue() {
		Candy cnd1 = new Candy("gummies", 2.25, .25);
		Candy cnd2 = new Candy("gummies", 1.25, .25);
		assertEquals(cnd1.isSameAs(cnd2),true);
		
	}
	@Test
	void testIsSameAsFalse() {
		Candy cnd1 = new Candy("gummies", 2.25, .25);
		Candy cnd2 = new Candy("gummies", 2.25, .35);
		assertEquals(cnd1.isSameAs(cnd2),false);
	}

}
