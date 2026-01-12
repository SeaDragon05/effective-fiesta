import java.util.Scanner;

public class Lab2c {
   public static void main(String[] args) {
      Scanner scnr = new Scanner(System.in);
      int sqWidth = 0;
      int recWidth = 0;
      int recHeight = 0;
      System.out.println("Please enter the width of the square: ");
      sqWidth = scnr.nextInt();
      System.out.println("Please enter width of the rectangle: ");
      recWidth = scnr.nextInt();
      System.out.println("Please enter height of the rectangle: ");
      recHeight = scnr.nextInt();
      int sqArea = sqWidth * sqWidth;
      int recArea = recWidth * recHeight;
      System.out.println("The area of a square with a width of " + sqWidth + " is " + sqArea);
      System.out.println("The area of a rectangle with a width of " + recWidth + " and a height of " + recHeight + " is " + recArea);
      
   }
}