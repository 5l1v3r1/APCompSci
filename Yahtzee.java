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
	
	private void setCurrentPlayer() {
		String currentPlayer = playerNames[playerCounter-1];
		display.printMessage(currentPlayer + "’s turn.  Roll the dice!");
		}
	
	private void initializeUsedCategories() {
		/*Creates a 2D array that stores the used and unused categories for each player 
		 * so no one can re-use any category.  Also initializes the "total score" category for each player.*/
	
	usedCategories  = new int[nPlayers][N_CATEGORIES];
	for(int i = 0; i < nPlayers; i++){
		for(int y = 0; y < N_CATEGORIES; y++){
			usedCategories[i][y] = -1;
		}
	}
	playersTotalScore = new int[nPlayers];
	for(int i = 0; i < nPlayers; i++){
		playersTotalScore[i]=0;
	}
}
	

	private void playGame() {
		

	for(int i = 0; i < (nPlayers * N_SCORING_CATEGORIES); i++){
			
	setCurrentPlayer();
//			firstRoll();
//			secondAndThirdRoll();
//			selectACategory();
//			evaluteDice(diceArray);
//			checkCategory(category);
//			display.updateScorecard(category, playerCounter, score);
//			updateRunningTotal();
//			setNextPlayer();
//			
}
		
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
		

/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] diceVals = new int[N_DICE];
	private int Score;
	private int playerCounter;
	private int[][] usedCategories;
	private int[] playersTotalScore;
}
