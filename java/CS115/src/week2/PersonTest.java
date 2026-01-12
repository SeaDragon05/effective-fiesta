package week2;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class PersonTest {

	@Test
	void testGetName() {
		Person p1 = new Person("Name", "2020-01-01",null,null);
		String result = p1.getName();
		assertEquals("Name", result);
	}

	@Test
	void testSetName() {
		Person p2 = new Person("null", "2020-01-01",null,null);
		p2.setName("Name");
		String result = p2.getName();
		assertEquals("Name", result);
	}

	@Test
	void testGetDob() {
		Person p2 = new Person(null, "2020-01-01",null,null);
		LocalDate result = p2.getDob();
		assertEquals(LocalDate.parse("2020-01-01"), result);
	}

	@Test
	void testSetDob() {
		Person p2 = new Person(null, "2020-01-01",null,null);
		p2.setDob("2019-05-15");
		assertEquals(LocalDate.parse("2019-05-15"), p2.getDob());
	}

	@Test
	void testGetGender() {
		Person p2 = new Person(null, "2020-01-01","Male",null);
		String result = p2.getGender();
		assertEquals("Male", result);
	}

	@Test
	void testSetGender() {
		Person p2 = new Person("null", "2020-01-01","Male",null);
		p2.setGender("Attack Helicopter");
		String result = p2.getGender();
		assertEquals("Attack Helicopter", result);
	}

	@Test
	void testGetPhone() {
		Person p2 = new Person(null, "2020-01-01","Male","000-000-0000");
		String result = p2.getPhone();
		assertEquals("000-000-0000", result);
	}

	@Test
	void testSetPhone() {
		Person p2 = new Person("null", "2020-01-01","Male",null);
		p2.setPhone("000-000-0000");
		String result = p2.getPhone();
		assertEquals("000-000-0000", result);
	}

}
