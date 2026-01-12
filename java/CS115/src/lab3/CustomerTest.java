package lab3;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CustomerTest {
	@Test
	void testGetID() {
		Customer notKaren = new Customer("Karen");
		assertEquals(notKaren.getID(), 1000);
	}
	@Test
	void testGetName() {
		Customer notKaren = new Customer("Karen");
		assertEquals(notKaren.getName(), "Karen");
	}
	@Test
	void testSetName() {
		Customer notKaren = new Customer("Karen");
		notKaren.setName("Caren");
		assertEquals(notKaren.getName(), "Caren");
	}
	@Test
	void testAddToHistory() {
		Customer notKaren = new Customer("Karen");
		Order orda = new Order();
		notKaren.addToHistory(orda);
		assertEquals(notKaren.getOrderHistory().get(0), orda);
	}
	@Test
	void testGetOrderHistory() {
		Customer notKaren = new Customer("Karen");
		assertEquals(notKaren.getOrderHistory(), new ArrayList<Order>());
	}
}
