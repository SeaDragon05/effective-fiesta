package petShop;

import java.util.ArrayList;

public class Main {
	public static ArrayList<Pet> list = new ArrayList<Pet>();
	public static void main(String[] args) {
		list.add(new Pet("Josie", "bark", 1500.00, "2019-05-15"));
		list.add(new Pet());
		System.out.println(list.toString());
		list.get(0).getDOB().getMonth();
	}
}
