/*
 * 
 */


import acm.program.*;

public class Recursion extends ConsoleProgram{
	String main = "";
	int sum; 
	String bin = ""; 
	private String ReverseString(String str) {
		
		if (str.length() == 1) {
			main += str.substring(0, 1);
			return main; 
		} else {
			main += str.substring(str.length()-1, str.length());
			str = str.substring(0, str.length()-1);
			ReverseString(str);
		}
		return main; 
	}

	private int SumOfDigits(int x) {
		
		
		if (x < 10) {
			sum += x;
			return sum;
		} else {
			sum += x%10; 
			x = x/10; 
			SumOfDigits(x);
		}
		return sum;
	}
	
	private String printInBinary(int number) {
		if (number < 1) {
			return bin;
		} else {
	
			bin = number%2 + bin; 
			number =number/2;
			printInBinary(number);
		}
		return bin;
	}
	public void run() {
		println(ReverseString("string"));
		println(SumOfDigits(15704));
		println(printInBinary(35));
	}
}
