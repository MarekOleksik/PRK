package pl.akademiakodu;

import java.util.Scanner;

public class Task4 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Podaj liczb� ca�kowit�: ");
		int number1 = input.nextInt();
		System.out.println("Podaj drug� liczb� ca�kowit�: ");
		int number2 = input.nextInt();
		double iloraz = number1 * number2;
		System.out.println("" + number1 + " * " + number2 + " = " + iloraz);
		input.close();
	}

}
