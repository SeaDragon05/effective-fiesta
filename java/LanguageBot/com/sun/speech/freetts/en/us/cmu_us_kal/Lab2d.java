package week2;
import java.util.Scanner;
/**
 * @author theni
 *
 */
public class Lab2d {
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
      System.out.println("How much money would you like to deposite each month?");
		float cash = scnr.nextFloat();
      System.out.println("What be the interest rate?");
      float rate = scnr.nextFloat();
      rate = 1.000f + ((rate / 100.000f) / 12.000f);
      float month1 = (cash * rate);
      float month2 = ((month1 + cash) * rate);
      float month3 = ((month2 + cash) * rate);
      float month4 = ((month3 + cash) * rate);
      float month5 = ((month4 + cash) * rate);
      float month6 = ((month5 + cash) * rate);
      System.out.println("your ending balance is a whopping $" + month6);
   }
}
