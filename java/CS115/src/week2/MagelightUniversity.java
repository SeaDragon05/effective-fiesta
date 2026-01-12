package week2;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class MagelightUniversity {
	static ArrayList<Person> list = new ArrayList<Person>();
	public static void main(String[] args) {
		list.add(new Person("Rachel", "1993-05-12", "F", "801-555-1212"));
		list.add(new Person("Jose", "2004-12-01", "M", "480-555-1212"));
		list.add(new Person("Pyra", "2001-02-27", "F", "385-555-1212"));
		list.add(new Person("Bob", "1984-06-06", "M", "916-555-1212"));
		
		
		System.out.printf("%15s%5s%8s%15s%n","Name", "Age", "Gender", "Phone Number");
		System.out.println("________________________________________________________");
		for (int i = 0; i < list.size(); i++) {
			Period timeDifference = Period.between(list.get(i).getDob(), LocalDate.now());
			int age = timeDifference.getYears();
			System.out.printf("%15s%5d%8s%15s%n", list.get(i).getName(), age, list.get(i).getGender(), list.get(i).getPhone());
		}
	}
}
