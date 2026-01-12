package lab3;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SundaeTest {

	@Test
	void testGetToppingName() {
		Sundae thing = new Sundae("Cheese", 3, 4.99, "sauce", 2.49);
		assertEquals(thing.getToppingName(), "sauce");
	}

	@Test
	void testSetToppingName() {
		Sundae thing = new Sundae("Cheese", 3, 4.99, "sauce", 2.49);
		thing.setToppingName("sprinkles");
		assertEquals(thing.getToppingName(), "sprinkles");
	}

	@Test
	void testGetToppingPrice() {
		Sundae thing = new Sundae("Cheese", 3, 4.99, "sauce", 2.49);
		assertEquals(thing.getToppingPrice(), 2.49);
	}

	@Test
	void testSetToppingPrice() {
		Sundae thing = new Sundae("Cheese", 3, 4.99, "sauce", 2.49);
		thing.setToppingPrice(1.89);
		assertEquals(thing.getToppingPrice(), 1.89);
	}

}
