package dumb.stuff;

import lab3.*;

public class shopTester {
	public static void main(String[] args) {
	    Sundae thing = new Sundae("Vanilla", 3, .69, "Hot Fudge", 1.29);
	    System.out.println(thing.calculateCost());
	    System.out.println(3.0 * .69);
	}
}
