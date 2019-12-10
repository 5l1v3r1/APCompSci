/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {

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
		initializeUsedCategories();
		playGame();
	}

	private void setCurrentPlayer() {
		String currentPlayer = playerNames[nPlayers - 1];
		display.printMessage(currentPlayer + "’s turn.  Roll the dice!");
	}

	private void initializeUsedCategories() {
		/*
		 * Creates a 2D array that stores the used and unused categories for each player
		 * so no one can re-use any category. Also initializes the "total score"
		 * category for each player.
		 */

		usedCategories = new int[nPlayers][N_CATEGORIES];
		for (int i = 0; i < nPlayers; i++) {
			for (int y = 0; y < N_CATEGORIES; y++) {
				usedCategories[i][y] = 0;
			}
		}
		playersTotalScore = new int[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			playersTotalScore[i] = 0;
		}
	}

	private void playGame() {

for(int i = 0; i < (nPlayers * N_SCORING_CATEGORIES); i++){

		setCurrentPlayer();
		firstRoll();
		secondAndThirdRoll();
		selectACategory();
//		display.updateScorecard(category, playerCounter, score);
//		updateRunningTotal();
//		setNextPlayer();
//	

	}

	}

	private void firstRoll() {

		display.waitForPlayerToClickRoll(nPlayers);
		for (int i = 0; i < N_DICE; i++) {
			diceVals[i] = rgen.nextInt(1, 6);
		}

		display.displayDice(diceVals);

	}

	private void secondAndThirdRoll() {

		for (int i = 0; i < 2; i++) {
			display.waitForPlayerToSelectDice();

			for (int j = 0; j < N_DICE; j++) {
				if (display.isDieSelected(j) == true) {
					diceVals[j] = rgen.nextInt(1, 6);
				}
			}
			display.displayDice(diceVals);
		}

	}
	
	private void selectACategory() {
		
		
		while (true) {
			category = display.waitForPlayerToSelectCategory();
			if(checkCategory(category)) break;
		}
		
		if (category <= 6) {
			for (int k = 0; k<diceVals.length; k++) {
				if (diceVals[k] == category) {
					Score += diceVals[k];
				}
			}
		} else if (category >= 9 || category <= 16) {
			for (int l = 0; l < diceVals.length; l++) {
				Score += diceVals[l];
			}
		}
		
		display.updateScorecard(category, nPlayers, Score);
	}
	
	private boolean checkCategory(int category) {
		
		if (category <= 6) {
			for (int i = 0; i<N_DICE; i++) {
				if (diceVals[i] == category) return true;
			}
			return false;
			
		} else if (category == 9) {
			
			for (int j = 0; j<N_DICE; j++) {
				for (int k = j+1; k<N_DICE; k++) {
					for (int l = k+1; l<N_DICE; l++) {
						if (diceVals[j] == diceVals[k] && diceVals[k] == diceVals[l]) return true;
					}
				}
			}
			
		} else if (category == 10) {
			
			for (int m = 0; m<N_DICE; m++) {
				for (int n = m+1; n<N_DICE; n++) {
					for (int o = n+1; o<N_DICE; o++) {
						for (int p = o+1; p<N_DICE; p++) {
							if (diceVals[m] == diceVals[n] && diceVals[n] == diceVals[o] && diceVals[o] == diceVals[p]) return true;
						}
					}
				}
			}
		} 
//			else if (category == 11) {
//			
//		} else if (category == 12) {
//			
//		} else if (category == 13) {
//			
//		} else if (category == 14) {
//			
//		} else if (category == 15) {
//			
//		}
		
		return false;
		
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int[] diceVals = new int[N_DICE];
	private int Score;
	private int[][] usedCategories;
	private int[] playersTotalScore;
	private int category;
}
