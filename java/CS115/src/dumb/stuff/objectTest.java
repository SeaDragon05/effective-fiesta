package dumb.stuff;

import java.util.ArrayList;

public class objectTest {
	private ArrayList<Integer> lol;
	enum thingy {
		small,
		medium,
		large,
		xlarge,
		xxlarge		
	}
	@SuppressWarnings("serial")
	public objectTest() {
		this.lol = new ArrayList<Integer>() {
			@Override
			public String toString() {
				return "nO";
			}
			@Override
			@SuppressWarnings("rawtypes") 
			public void sort(java.util.Comparator c) {
				System.out.println("This shouldn't be sorted, so I removed this option you type writter monkey\nGo write shakespear");
			}
			@Override
			public boolean isEmpty() {
				try {
					@SuppressWarnings("unused")
					int i = this.get(0);
					return false;
				} catch (IndexOutOfBoundsException e) {
					System.err.print(e);
					return true;
				}
			}
		};
	}
	private ArrayList<Integer> in() {
		return this.lol;//returns memory address
	}
	public static void main(String[] args) {
		objectTest lmao = new objectTest();
		System.out.println(lmao.in().toString());
		System.out.println("\n" + lmao.in());
		lmao.in().sort(null);
		//Scanner scnr = new Scanner(System.in);
		lmao.in().isEmpty();
	}
}
