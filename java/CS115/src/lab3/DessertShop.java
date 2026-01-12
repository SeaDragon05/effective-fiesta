package lab3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

import lab3.Payable.PayType;

@SuppressWarnings("resource") // scanner leak
public class DessertShop {
	static HashMap<String, Customer> customerDB = new HashMap<String, Customer>() {
		private static final long serialVersionUID = 1L;
		@Override
		public String toString() {
			if (this.isEmpty()) {
				return "Customer DB is empty";
			}
			String result = "";
			for (int i = 0; i < this.size(); i ++) {
				Object[] keys = this.keySet().toArray();
				result += "Customer Name: " + keys[i] + " Customer ID: " + this.get(keys[i]).getID() + "\n";
			}
			return result;
		}
	};
	public static void main(String[] args) {
		do {
			Order orders = new Order();
			Scanner sIn = new Scanner(System.in);
			String choice;
			DessertItem orderItem;

			boolean done = false;
			while (!done) {
				System.out.println("\n1: Candy");
				System.out.println("2: Cookie");
				System.out.println("3: Ice Cream");
				System.out.println("4: Sunday");
				System.out.println("5: Admin Module");

				System.out.print("\nWhat would you like to add to the order? (1-4, Enter for done): ");
				choice = sIn.nextLine();

				if (choice.equals("")) {
					done = true;
				} else {
					switch (choice) {
					case "1":
						orderItem = userPromptCandy();
						orders.addOrder(orderItem);
						break;
					case "2":
						orderItem = userPromptCookie();
						orders.addOrder(orderItem);
						break;
					case "3":
						orderItem = userPromptIceCream();
						orders.addOrder(orderItem);
						break;
					case "4":
						orderItem = userPromptSundae();
						orders.addOrder(orderItem);
						break;
					case "5":
						adminModule();
						break;
					}
				}
			}
			orders.addOrder(new Candy("Candy Corn", 1.5, .25));
			orders.addOrder(new Candy("Gummy Bears", .25, .35));
			orders.addOrder(new Cookie("Chocolate Chip", 6, 3.99));
			orders.addOrder(new IceCream("Pistachio", 2, .79));
			orders.addOrder(new Sundae("Vanilla", 3, .69, "Hot Fudge", 1.29));
			orders.addOrder(new Cookie("Oatmeal Raisin", 2, 3.45));
			System.out.print("\nEnter the name of the customer: ");
			String name = sIn.nextLine();
			do {
				System.out.println("\nWhat form of payment will be used? (CASH, CARD, PHONE):");
				String in = sIn.nextLine();
				if (in.equalsIgnoreCase("cash")) {
					orders.setPayType(PayType.CASH);
					break;
				} else if (in.equalsIgnoreCase("card")) {
					orders.setPayType(PayType.CARD);
					break;
				} else if (in.equalsIgnoreCase("phone")) {
					orders.setPayType(PayType.PHONE);
					break;
				} else {
					System.out.println("That's not a valid form of payment.");
				}
			} while (true);
			
			
			orders.getOrderList().sort(Comparator.naturalOrder());
			Customer customer;// = new Customer(name);
			if (customerDB.containsKey(name)) {
				customer = customerDB.get(name);
			} else {
				customer = new Customer(name);
			}
			customer.addToHistory(orders);
			customerDB.put(name, customer);
			System.out.println(orders);
			System.out.println("---------------------------------------------------------");
			String line = String.format("%s%30s%17s", "Customer Name: " + name, "Customer ID: " + customer.getID(), "Total Orders: " + customer.getOrderHistory().size());
			
			
			System.out.println(line + "\nHit enter to start a new order");
			if (!sIn.nextLine().isEmpty()) {
				break;
			}
		} while (true);
	}
	private static void adminModule() {
		Scanner scnr = new Scanner(System.in);
		do {
			System.out.println("\n1: Shop Customer List\n"
					+ "2: Customer Order History\n"
					+ "3: Best Customer\n"
					+ "4: Exit Admin Module");
			System.out.print("What would you like to do?:");
			String choice = scnr.nextLine();
			System.out.println();
			switch(choice) {
				case "1":
					System.out.println(customerDB.toString());
					break;
				case "2":
					System.out.print("\nEnter the customer name: ");
					String in = scnr.nextLine();
					try {
						String customer = "Customer Name: " + in + " Customer ID: " + customerDB.get(in).getID() + "\n";
						for (int i = 0; i < customerDB.get(in).getOrderHistory().size(); i ++) {
							customer += "\nOrder #: " + (i + 1) + "\n" + customerDB.get(in).getOrderHistory().get(i) + "\n";
						}
						System.out.println("\n" + customer + "\n");
					} catch (Exception e) {
						System.err.println("\nError: Customer with name " + in + " does not exist! \n");
					}
					break;
				case "3":
					System.out.println(bestCustomer());
					break;
				case "4":
					return;
			}
		} while (true);
	}
	public static String bestCustomer() {
		if (customerDB.isEmpty()) {
			return "Customer DB is empty";
		}
		String result = "";
		Customer customer = customerDB.get(customerDB.keySet().toArray()[0]);
		Object[] keys = customerDB.keySet().toArray();
		for (int i = 0; i < customerDB.size(); i ++) {
			if (customer.getOrderHistory().size() < customerDB.get(keys[i]).getOrderHistory().size()) {
				customer = customerDB.get(keys[i]);
			}
		}
		result += "------------------------------------\n";
		result += "The Dessert Shop's most valued customer is: " + customer.getName()+ "!\n";
		result += "------------------------------------\n";
		return result;
	}
	private static DessertItem userPromptCandy() {
		Scanner scnr = new Scanner(System.in);
		System.out.print("\nEnter the type of candy: ");
		String name = scnr.nextLine();
		double weight = getValidDouble("\nEnter the number of pounds: ");
		double ppd = getValidDouble("\nEnter the price per pound: ");
		return new Candy(name, weight, ppd);
	}

	private static DessertItem userPromptCookie() {
		Scanner scnr = new Scanner(System.in);
		System.out.print("\nEnter the type of cookie: ");
		String name = scnr.nextLine();
		int qty = getValidInt("\nEnter the quantity purchased: ");
		double ppd = getValidDouble("\nEnter the price per dozen: ");
		return new Cookie(name, qty, ppd);
	}

	private static DessertItem userPromptIceCream() {
		Scanner scnr = new Scanner(System.in);
		System.out.print("\nEnter the type of icecream: ");
		String name = scnr.nextLine();
		int qty = getValidInt("\nEnter the quantity of scoops: ");
		double ppd = getValidDouble("\nEnter the price per scoop: ");
		return new IceCream(name, qty, ppd);
	}

	private static DessertItem userPromptSundae() {
		Scanner scnr = new Scanner(System.in);
		System.out.print("\nEnter the type of icecream: ");
		String name = scnr.nextLine();
		int qty = getValidInt("\nEnter the quantity of scoops: ");
		double ppd = getValidDouble("\nEnter the price per scoop: ");
		System.out.print("\nEnter the name of the topping: ");
		String tn = scnr.nextLine();
		double priceoftopping = getValidDouble("\nEnter the price of the topping: ");
		return new Sundae(name, qty, ppd, tn, priceoftopping);
	}

	public static int getValidInt(String prompt) {
		Scanner scnr = new Scanner(System.in);
		int result = 0;
		do {
			System.out.print(prompt);
			String input = scnr.next();
			try {
				result = Integer.parseInt(input);
				break;
			} catch (Exception e) {
				System.err.println("Error: Please input a valid number.");
			}
		} while (true);
		return result;
	}

	public static double getValidDouble(String prompt) {
		Scanner scnr = new Scanner(System.in);
		double result = 0;
		do {
			System.out.print(prompt);
			String input = scnr.next();
			try {
				result = Double.parseDouble(input);
				break;
			} catch (Exception e) {
				System.err.println("Error: Please input a valid number.");
			}
		} while (true);
		return result;
	}
}
