package lab3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DessertItemTest {

	@Test
	void testGetName() {
		Candy item = new Candy("Cheese", 1.0, 1.0);
		assertEquals(item.getName(), "Cheese");
	}

	@Test
	void testSetName() {
		Candy item = new Candy("Cheese", 1.0, 1.0);
		item.setName("Brownies");
		assertEquals(item.getName(), "Brownies");

	}
	@Test
	void testSort1() {
		Candy item1 = new Candy("Cheese", 1.0, 1.0);
		assertEquals(-1, item1.compareTo(new Candy("lmao", 2.0,2.0)));	
	}
	@Test
	void testSort2() {
		Candy item1 = new Candy("Cheese", 1.0, 1.0);
		assertEquals(0, item1.compareTo(new Candy("lmao", 1.0,1.0)));	
	}
	@Test
	void testSort3() {
		Candy item1 = new Candy("Cheese", 4.0, 4.0);
		assertEquals(1, item1.compareTo(new Candy("lmao", 1.0,1.0)));	
	}
}
