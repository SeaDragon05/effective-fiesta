package lab3;

import java.util.ArrayList;

public class Customer {
	private String custName;
	private ArrayList<Order> orderHistory;
	private int custID;
	static int nextCustID = 1000;
	public Customer(String custName) {
		this.custName = custName;
		this.orderHistory = new ArrayList<Order>();
		this.custID = nextCustID;
		nextCustID += 1;
	}
	public String getName() {
		return custName;
	}
	public int getID() {
		return custID;
	}
	public ArrayList<Order> getOrderHistory() {
		return orderHistory;
	}
	public void setName(String name) {
		this.custName = name;
	}
	public void addToHistory(Order o) {
		orderHistory.add(o);
	}
	
}
