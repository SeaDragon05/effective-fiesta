package realEstateOffice;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class REO {
	public static Listings reoListings = new Listings();

	public static void main(String[] args) {
		// debug:
		// System.out.println("Got " + verifSelection(new Scanner(System.in), 4));
		Scanner scnr = new Scanner(System.in);
		int selection = 0;
		while (true) {// init frantic spagetti code that works to the best of my knowledge
			if (selection == 1) {
				int choise = 0;
				do {
					choise = printListingMenu(scnr);
					switch (choise) {
					case (1):
						int menuthing;
						do {
							menuthing = printAddListingMenu(scnr);
							switch (menuthing) {
							case (1):
								addHouse(scnr);
								break;
							case (2):
								addCondo(scnr);
								break;
							}
						} while (menuthing != 0);
						break;
					case (2):
						showListings();
						break;
					case (3):
						autoPopulate();
						break;
					}
				} while (choise != 0);
				selection = 0;
			} else if (selection == 2) {
				int choise = 0;
				do {
					choise = printBidsMenu(scnr);
					switch (choise) {
					case (1):
						printAddBidMenu(scnr);
						break;
					case (2):
						showBids(scnr);
						break;
					case (3):
						autoPopulateBids();
						break;
					}
				} while (choise != 0);
				selection = 0;
			} else {
				do {
					selection = printMenu(scnr);
				} while (selection == 0);
			}
		}

	}

	public static void autoPopulateBids() {
		Random rnd = new Random();
		String[] autoBidders = { "Patric Stewart", "Walter Koenig", "William Shatner", "Leonard Nimoy",
				"DeForect Kelley", "James Doohan", "George Takei", "Majel Barrett", "Nichelle Nichol", "Jonathan Frank",
				"Marina Sirtis", "Brent Spiner", "Gates McFadden", "Michael Dorn", "LeVar Burton", "Wil Wheaton",
				"Colm Meaney", "Michelle Forbes" };
		for (Residential k : reoListings.getListings().values()) {
			int amount = rnd.nextInt(10);
			for (int i = 0; i < amount; i++) {
				int index = rnd.nextInt((autoBidders.length - 1) + 1);
				double maxBid = k.calculateAppraisalPrice() * 1.1;
				double minBid = k.calculateAppraisalPrice() * .9;
				double bidAmount = Math.random() * ((maxBid - minBid) + 1) + minBid;
				k.newBid(autoBidders[index], bidAmount); // evil laughing
			}
			System.out.println(amount + " new bids have been added to listing " + k.getStreetAddress());
		}
	}

	public static void printAddBidMenu(Scanner scnr) {
		while (true) {
			ArrayList<Residential> dalist = new ArrayList<Residential>();
			System.out.println(
					"Current Listings for REO: \n" + "\nNo.      Property (bids)" + "\n---------------------------");
			int j = 1;
			for (Residential i : reoListings.getListings().values()) {
				System.out.println(j + ". " + i.getStreetAddress() + " (" + i.getBidCount() + ")");
				dalist.add(i);
				j++;
			}
			System.out.println("ENTER: Exit back to previous menu");
			int choice = verifSelection(scnr, "\nFor which property would you like to add a bid? :", (j - 1)) - 1;
			if (choice == -1) {
				return;
			}
			System.out.println(dalist.get(choice).toString());
			System.out.print("\n\nPlease enter the name of the bidder: ");
			String name = scnr.nextLine();
			double bidamount = verifDouble(scnr, "\nPlease enter the new bid: ");
			dalist.get(choice).newBid(name, bidamount);
			System.out.println("New bid for property '" + dalist.get(choice).getStreetAddress() + "' added");
		}
	}

	public static void showBids(Scanner scnr) {
		ArrayList<Residential> dalist = new ArrayList<Residential>();
		System.out.println(
				"Current Listings for REO: \n" + "\nNo.      Property (bids)" + "\n---------------------------");
		int j = 1;
		for (Residential i : reoListings.getListings().values()) {
			System.out.println(j + ". " + i.getStreetAddress() + " (" + i.getBidCount() + ")");
			dalist.add(i);
			j++;
		}
		System.out.println("ENTER: Exit back to previous menu\n");
		int choice = verifInt(scnr, "For which property would you like to see the current bids?: ");
		if (choice == 0) {
			return;
		}
		choice--;
		System.out.println(dalist.get(choice).toString());
		System.out.println("\nCurrent bids for this listing: " + "\n---------------------------------------------"
				+ "\n         Bidder                          Bid" + "\n---------------------------------------------");
		for (String i : dalist.get(choice).getBids().keySet()) {
			System.out.printf(i + "   $%.2f%n", dalist.get(choice).getBid(i));
		}
	}

	public static void showListings() {
		System.out.println("\n\nCurrent Listings for REO:");
		int j = 1;
		for (Residential i : reoListings.getListings().values()) {
			System.out.println("Listing No: " + j);
			j++;
			System.out.println(i.toString());
		}
	}

	public static void addHouse(Scanner scnr) {
		System.out.print("\nPlease ener the street addresfor the residence: ");
		String addr = scnr.nextLine();
		int zip = verifInt(scnr, "\nPlease enter the zip code for the residence: ");
		int bed = verifInt(scnr, "\nPlease enter the number of bedrooms: ");
		int bath = verifInt(scnr, "\nPlease enter the number of bathrooms: ");
		double sqft = verifDouble(scnr, "\nPlease enter the square footage of the residence: ");
		double ydac = verifDouble(scnr, "\nPlease enter the size fo the yard in acres: ");
		House pouse = new House(addr, zip, bed, bath, sqft, ydac);
		System.out.println("Appraisal Price for this property is: $" + pouse.calculateAppraisalPrice());
		pouse.setListPrice(verifDouble(scnr, "\nPlease enter the List Price for the property: "));
		System.out.println("\nYou have created a new listing!");
		System.out.println(pouse.toString());
		reoListings.addListing(addr, pouse);
	}

	public static void addCondo(Scanner scnr) {
		System.out.print("\nPlease ener the street addresfor the residence: ");
		String addr = scnr.nextLine();
		int zip = verifInt(scnr, "\nPlease enter the zip code for the residence: ");
		int bed = verifInt(scnr, "\nPlease enter the number of bedrooms: ");
		int bath = verifInt(scnr, "\nPlease enter the number of bathrooms: ");
		double sqft = verifDouble(scnr, "\nPlease enter the square footage of the residence: ");
		int floors = verifInt(scnr, "\nPlease enter the amount of floors: ");
		Condo HawsBox = new Condo(addr, zip, bed, bath, sqft, floors);
		System.out.println("Appraisal Price for this property is: $" + HawsBox.calculateAppraisalPrice());
		HawsBox.setListPrice(verifDouble(scnr, "\nPlease enter the List Price for the property: "));
		System.out.println("\nYou have created a new listing!");
		System.out.println(HawsBox.toString());
		reoListings.addListing(addr, HawsBox);
	}

	public static int verifInt(Scanner scnr, String display) {
		while (true) {
			System.out.print(display);
			String input = scnr.nextLine();
			try {
				int num = Integer.parseInt(input);
				return num;
			} catch (Exception e) {
				System.err.println("Please enter a valid number");
			}
		}
	}

	public static double verifDouble(Scanner scnr, String display) {
		while (true) {
			System.out.print(display);
			String input = scnr.nextLine();
			try {
				double num = Double.parseDouble(input);
				return num;
			} catch (Exception e) {
				System.err.println("Please enter a valid number");
			}
		}
	}

	public static int verifSelection(Scanner scnr, String display, int limit) {
		while (true) {
			System.out.print(display + " (1-" + limit + "): ");
			String input = scnr.nextLine();
			if (input.isEmpty()) {
				return 0;
			}
			int result = 0;
			try {
				result = Integer.parseInt(input);
			} catch (Exception e) {
				System.err.println("Please input a valid number");
			}
			if (result <= limit) {
				return result;
			} else {
				System.err.println("Please input a number between 1 and " + limit);
			}
		}
	}

	public static int verifSelection(Scanner scnr, int limit) {
		while (true) {
			System.out.print("What would you like to do? (1-" + limit + "): ");
			String input = scnr.nextLine();
			if (input.isEmpty()) {
				return 0;
			}
			int result = 0;
			try {
				result = Integer.parseInt(input);
			} catch (Exception e) {
				System.err.println("Please input a valid number");
			}
			if (result <= limit) {
				return result;
			} else {
				System.err.println("Please input a number between 1 and " + limit);
			}
		}
	}

	public static void autoPopulate() {
		House house1 = new House("34 Elm", 95129, 3, 2, 2200, .2);
		house1.setListPrice(house1.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("34 Elm", house1);
		House house2 = new House("42 Hitchhikers", 95136, 4, 3, 2800, .3);
		house2.setListPrice(house2.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("42 Hitchhikers", house2);
		Condo condo1 = new Condo("4876 Industrial", 95177, 3, 1, 1800, 3);
		condo1.setListPrice(condo1.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("4876 Industrial", condo1);
		House house3 = new House("2654 Oak", 84062, 5, 53, 4200, .5);
		house3.setListPrice(house3.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("2654 Oak", house3);
		Condo condo2 = new Condo("9875 Lexington", 84063, 2, 1, 1500, 1);
		condo2.setListPrice(condo2.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("9875 Lexington", condo2);
		Condo condo3 = new Condo("3782 Market", 84066, 3, 1, 1800, 2);
		condo3.setListPrice(condo3.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("3782 Market", condo3);
		House house4 = new House("7608 Glenwood", 84055, 6, 3, 3900, .4);
		house4.setListPrice(house4.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("7608 Glenwood", house4);
		House house5 = new House("1220 Apple", 84057, 8, 7, 7900, 1);
		house5.setListPrice(house5.calculateAppraisalPrice() * 1.1);
		reoListings.addListing("1220 Apple", house5);
	}

	public static int printMenu(Scanner scnr) {
		System.out.println("\n----------------------------------------");
		System.out.println("                Main Menu               ");
		System.out.println("----------------------------------------\n");
		System.out.println("1. Listings");
		System.out.println("2. Bids\n");
		return verifSelection(scnr, 2);
	}

	public static int printListingMenu(Scanner scnr) {
		System.out.println("\n----------------------------------------");
		System.out.println("               Listing Menu             ");
		System.out.println("----------------------------------------\n");
		System.out.println("1. Add listing");
		System.out.println("2. Show listing");
		System.out.println("3. Auto Populate Listings (Dev tool)");
		System.out.println("ENTER: Exit back to previous menu\n");
		return verifSelection(scnr, 3);
	}

	public static int printAddListingMenu(Scanner scnr) {
		System.out.println("\n----------------------------------------");
		System.out.println("             Add Listing Menu           ");
		System.out.println("----------------------------------------\n");
		System.out.println("1. Add House");
		System.out.println("2. Add Condo");
		System.out.println("ENTER: Exit back to previous menu\n");
		return verifSelection(scnr, 2);
	}

	public static int printBidsMenu(Scanner scnr) {
		System.out.println("\n----------------------------------------");
		System.out.println("                 Bids Menu              ");
		System.out.println("----------------------------------------\n");
		System.out.println("1. Add New Bid");
		System.out.println("2. Show Existing Bids");
		System.out.println("3. Auto Populate Bids (Dev tool)");
		System.out.println("ENTER: Exit back to previous menu\n");
		return verifSelection(scnr, 3);
	}

}