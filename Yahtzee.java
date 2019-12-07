/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	private static final int turns = 3;
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		
		for (int i = 0; i < turns; i++) {
			display.waitForPlayerToClickRoll(nPlayers);
			makeDiceValues();
			display.waitForPlayerToSelectDice();
			makeDiceValuesLoop();
			}
		}
	
	
	private void makeDiceValues() {
		
		for(int i = 0; i < N_DICE; i++) {
			diceVals[i] = rgen.nextInt(1, 6);
		}
		
		display.displayDice(diceVals);
		
	}
	
	private void makeDiceValuesLoop() {
//		if(display.isDieSelected(nPlayers)) {
//			display.waitForPlayerToClickRoll(nPlayers);
//			makeDiceValues();
//	
//		}
		
		
		for (int i=0; i<N_DICE; i++) {
			if (display.isDieSelected(i)) {
			diceVals[i] = rgen.nextInt(1,6);
			
		}
	}
		display.displayDice(diceVals);
		display.waitForPlayerToSelectCategory();
		display.updateScorecard(N_SCORING_CATEGORIES, nPlayers, Score );
	}
		
	private void readArray() {
		for (int j = 0; j<N_DICE; j++) {
			diceVals[j] += Score;
		}
	}
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] diceVals = new int[N_DICE];
	private int Score;
}
