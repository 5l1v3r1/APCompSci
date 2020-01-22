
/*
 * Rosnel Leyva and Tory Hansen 
 * 
 */

import java.util.ArrayList;

import acm.program.*;

public class Recursion extends ConsoleProgram {

	// serial ID
	private static final long serialVersionUID = 1L;

	// Problem 1
	private String ReverseString(String str) {
		if (str.length() == 1) {
			return str;
		}
		return (ReverseString(str.substring(1)) + str.substring(0, 1));
	}

	// Problem 2
	private int SumOfDigits(int x) {

		if (x < 10) {
			return x;
		} else {
			return ((x % 10) + (SumOfDigits(x / 10)));
		}
	}

	// problem 3
	private void printInBinary(int number) {
		if (number < 2) {
			print(number);
		} else {
			printInBinary(number / 2);
			print(number % 2);
		}
	}

	// problem 4
	private int GCD(int x, int y) {
		if (x % y == 0) {
			return y;
		} else {
			return GCD(y, x % y);
		}

	}



	//Problem 5 
	private boolean Solvable(int start, ArrayList<Integer> squares) {
		if (start < 0 || start > squares.size()) {
			return false;
		} else if (squares.get(start) == 0) {
			return true;
		} else if (start == squares.get(start)) {
			return false;
		} else if (Solvable(start + squares.get(start), squares) == false) {
			return Solvable(start - squares.get(start), squares);
		} else {
			return Solvable(start + squares.get(start), squares);
		}
	}
	
	//problem 6 
	
	private void towersHanoi(int n, String one , String two, String three) {
        if (n == 0) {
        	return;
        } else if((n > 0)) { 
        towersHanoi(n-1, one, two, three);
        println(" Move one disk from pole " + one + " to pole " + two); 
        towersHanoi(n-1, three, one, two);
        }
	}

	// testing problem outputs
	public void run() {

		squares.add(2);
		squares.add(3);
		squares.add(4);
		squares.add(4);
		squares.add(5);
		squares.add(4);
		squares.add(1);
		squares.add(6);
		squares.add(9);
		squares.add(0);

		// Uncomment to test output

		// println(ReverseString("string"));
		// println(SumOfDigits(15704));
		// printInBinary(35);
		// println(GCD(1000, 99));
		// println(Solvable(0, squares));
		//towersHanoi(3,"A","B","C");
	}

	private ArrayList<Integer> squares = new ArrayList<Integer>();
