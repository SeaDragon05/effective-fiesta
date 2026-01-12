package lab3;

import java.util.ArrayList;

public class Order implements Payable {
	private ArrayList<DessertItem> list;
	private PayType payMethod;
	public ArrayList<DessertItem> getOrderList() {
		return list;
	}
	public void addOrder(DessertItem item) {
		boolean shouldAdd = true;
		if (list.isEmpty()) {
			list.add(item);
			//System.out.println("ADDED " + item.getName());
		} else {
			//System.out.println("LOL");
			for (int i = 0; i < list.size(); i++) {
				if (item instanceof Candy) {
					if (list.get(i) instanceof Candy) {
						if (((Candy) list.get(i)).isSameAs((Candy) item)) {
							System.out.println(list.get(i).getName() + " is same as " + item.getName() + " because "
									+ ((Candy) list.get(i)).getPricePerPound() + " is the same as " + ((Candy) item).getPricePerPound()
									);
							((Candy) list.get(i)).setCandyWeight(((Candy) list.get(i)).getCandyWeight() + ((Candy) item).getCandyWeight());
							shouldAdd = false;
						}
					}
				} else if (item instanceof Cookie) {
					if (list.get(i) instanceof Cookie) {
						if (((Cookie) list.get(i)).isSameAs((Cookie) item)) {
							//System.out.println(list.get(i).getName() + " is same as " + item.getName());
							((Cookie) list.get(i)).setCookieQty(((Cookie) list.get(i)).getCookieQty() + ((Cookie) item).getCookieQty());
							shouldAdd = false;
						}
					}
				} else {
					//System.out.println("Item " + item.getName() + " is not a candy nor a cookie");
					
					//break;
				}
			}
			if (shouldAdd) list.add(item);
			//System.out.println("Since I didn't find an instance of: " + item.getName() + ", I added it");
		}
	}
	public int itemCount() {
		return list.size();
	}
	public Order() {
		this.list = new ArrayList<DessertItem>();
		this.payMethod = PayType.CASH;
	}
	public double orderCost() {
		double totalCost = 0;
		for (int i = 0; i < list.size(); i ++) {
			totalCost += list.get(i).calculateCost();
		}
		return totalCost;
	}
	public double orderTax() {
		double totalTax = 0;
		for (int i = 0; i < list.size(); i++) {
			totalTax += list.get(i).calculateTax();
		}
		return totalTax;
	}
	public String antiestablishmentarianism() {
		String line1 = "Order Subtotals:";
		String line2 = "$" + String.format("%.2f", this.orderCost());
		String line3 = "[Tax: $" + String.format("%.2f",  this.orderTax()) + "]";
		String result = String.format("%s%42s%17s", line1, line2, line3) + "\n";
		return result;
	}
	public String antiestablishmentarianism2() {
		String line1 = "Order Total:";
		String line2 = "$" + String.format("%.2f", orderCost() + orderTax());
		String result = String.format("%s%46s", line1, line2);
		return result;
	}
	@Override
	public String toString() {
		String result = "----------------------------------------------------\n";
		for (int i = 0; i < this.list.size(); i++) {
			result += list.get(i) + "\n";
		}
		result += "---------------------------------------------------------";
		result += "\nTotal items: " + this.list.size() + "\n";
		//result += "Order Subtotals: " + String.format("%-45.2f%17.2f", orderCost(), orderTax()) + "\n";
		result += antiestablishmentarianism();
		result += "\n" + antiestablishmentarianism2();
		result += "\n---------------------------------------------------------";
		result += "\nPaid for with " + getPayType();
		return result;
	}
	@Override
	public PayType getPayType() {
		return payMethod;
	}
	@Override
	public void setPayType(PayType type) {
		payMethod = type;
	}
}
