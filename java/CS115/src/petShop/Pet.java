package petShop;

import java.time.LocalDate;

public class Pet {
	public Pet(String name, String voice, double cost, String dOB) {
		this.name = name;
		this.voice = voice;
		this.cost = cost;
		DOB = LocalDate.parse(dOB);
	}
	public Pet() {
		// TODO Auto-generated constructor stub
	}
	private String name;
	private String voice;
	private double cost;
	private LocalDate DOB;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public LocalDate getDOB() {
		return DOB;
	}
	public void setDOB(LocalDate dOB) {
		DOB = dOB;
	}

}
