/*
 * Rosnel Leyva and Tory Hansen 
 * 
 */



import acm.program.*;

public class Recursion extends ConsoleProgram {

	// serial ID
	private static final long serialVersionUID = 1L;

	String main = "";
	int sum;
	String bin = "";
	int n;

	// Problem 1
	private String ReverseString(String str) {

		if (str.length() == 1) {
			main += str.substring(0, 1);
			return main;
		} else {
			main += str.substring(str.length() - 1, str.length());
			str = str.substring(0, str.length() - 1);
			ReverseString(str);
		}
		return main;
	}

	// Problem 2
	private int SumOfDigits(int x) {

		if (x < 10) {
			sum += x;
			return sum;
		} else {
			sum += x % 10;
			x = x / 10;
			SumOfDigits(x);
		}
		return sum;
	}

	// problem 3
	private String printInBinary(int number) {
		if (number < 1) {
			return bin;
		} else {

			bin = number % 2 + bin;
			number = number / 2;
			printInBinary(number);
		}
		return bin;
	}
	
	//problem 4
	private int GCD(int x, int y) {
		if(x%y == 0) {
			return y;
		}else {
			n = x%y;
			GCD(n,y);
		}
		return n;
	
	}

	// testing problem outputs
	public void run() {
		println(ReverseString("string"));
		println(SumOfDigits(15704));
		println(printInBinary(35));
		println(GCD(6,3));
	}
}
