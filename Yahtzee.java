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
		String currentPlayer = playerNames[1];
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

		for (int i = 0; i < (nPlayers * N_SCORING_CATEGORIES); i++) {
			setCurrentPlayer();
			firstRoll();
			secondAndThirdRoll();
			selectACategory();
			updateTotal();
//			setNextPlayer();
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

		category = display.waitForPlayerToSelectCategory();
		
		/*
		 * FIX THIS
		 */
		
		//while (usedCategories[nPlayers][category] == 1) {
//			if (usedCategories[nPlayers][category] == 0) {
				if (checkCategory(category)) {
		
					if (category <= 10 || category == 15) {
						for (int k = 0; k < diceVals.length; k++) {
							if (diceVals[k] == category) {
								Score += diceVals[k];
							}
						}
					} else if (category >= 11 || category <= 14) {
						switch(category) {
						case 11: Score = 25;
						case 12: Score = 30;
						case 13: Score = 40;
						case 14: Score = 50;
						}
					}
				} else {
					Score = 0;
				}
		
				display.updateScorecard(category, nPlayers, Score);
				updateTotal();
				Score = 0;
	} 
				//else {
//				display.printMessage("This category has been used.");
//				display.printMessage("Choose Another");
//			}
//		}
		
	//}

	private boolean checkCategory(int category) {

		if (category <= 6) {
			for (int i = 0; i < N_DICE; i++) {
				if (diceVals[i] == category) {
					//usedCategories[nPlayers][category] = 1;
					return true;
				}
			}
			return false;

		} else if (category == 9) {

			for (int j = 0; j < N_DICE; j++) {
				for (int k = j + 1; k < N_DICE; k++) {
					for (int l = k + 1; l < N_DICE; l++) {
						if (diceVals[j] == diceVals[k] && diceVals[k] == diceVals[l])
							//usedCategories[nPlayers][category] = 1;
							return true;
					}
				}
			}

		} else if (category == 10) {

			for (int i = 0; i < N_DICE; i++) {
				int currentFourOfAKindValue = diceVals[i];
				if(diceVals[i] == currentFourOfAKindValue) fourOfAKindChecker++;
				if (fourOfAKindChecker++ >= 4) {
					//usedCategories[nPlayers][category] = 1;
					return true;
				}
			}
			for (int i = 1; i < N_DICE; i++) {
				int currentFourOfAKindValue = diceVals[i];
				if(diceVals[i] == currentFourOfAKindValue) fourOfAKindChecker++;
				if (fourOfAKindChecker++ >= 4) {
					//usedCategories[nPlayers][category] = 1;
					return true;
				}
			}
			
		} else if (category == 11) {
			int threeSame = 0;

			for (int j = 0; j < N_DICE; j++) {
				for (int k = j + 1; k < N_DICE; k++) {
					for (int l = k + 1; l < N_DICE; l++) {
						if (diceVals[j] == diceVals[k] && diceVals[k] == diceVals[l]) {
							threeSame++;
							fullHouseChecker = diceVals[j];
						}
					}
				}
			}

			if (threeSame == 1) {
				for (int i = 0; i < N_DICE; i++) {
					for (int j = i + 1; j < N_DICE; j++) {
						if (diceVals[i] != fullHouseChecker && diceVals[i] == diceVals[j]) 
							//usedCategories[nPlayers][category] = 1;
							return true;
						
					}
				}
			}
		} else if (category == 12) {
			for (int m = 0; m < N_DICE; m++) {
				for (int n = 0; n < N_DICE; n++) {
					for (int o = 0; o < N_DICE; o++) {
						for (int p = 0; p < N_DICE; p++) {
							if (diceVals[n] == diceVals[m] + 1 && diceVals[o] == diceVals[n] + 1 && diceVals[p] == diceVals[o] +1)
//								usedCategories[nPlayers][category] = 1;
								return true;
						}
					}
				}
			}
		} else if (category == 13) {
			for (int m = 0; m < N_DICE; m++) {
				for (int n = 0; n < N_DICE; n++) {
					for (int o = 0; o < N_DICE; o++) {
						for (int p = 0; p < N_DICE; p++) {
							for (int q = 0; q < N_DICE; q++) {
								if (diceVals[n] == diceVals[m] + 1 && diceVals[o] == diceVals[n] + 1 && diceVals[p] == diceVals[o] +1 && diceVals[q] == diceVals[p] + 1)
									//usedCategories[nPlayers][category] = 1;
									return true;
							}
						}
					}
				}
			}
		} else if (category == 14) {
			for (int i =0; i< N_DICE-1; i++) {
				if (diceVals[i] == diceVals[i+1]) {
					//usedCategories[nPlayers][category] = 1;
					return true;
				}
			}
			
//			for (int m = 0; m < N_DICE; m++) {
//				for (int n = m + 1; n < N_DICE; n++) {
//					for (int o = m + 1; o < N_DICE; o++) {
//						for (int p = o + 1; p < N_DICE; p++) {
//							for (int q = p + 1; q < N_DICE; q++) {
//								if (diceVals[m] == diceVals[n] && diceVals[n] == diceVals[o] && diceVals[o] == diceVals[p] && diceVals[p] == diceVals[q])
//									return true;
//							}
//						}
//					}
//				}
//			}
		} 
		else if (category == 15) {
			return true;
		}
		return false;
	}

	private void updateTotal() {
		
	//playersTotalScore[1] += Score;
	
	int totalingCounter = 0;
	for(int i = 1; i<= 6; i++){
		playersTotalScore[nPlayers-1] += Score;
		if(usedCategories[nPlayers-1][i] == 1){
			totalingCounter += usedCategories[nPlayers-1][i];
			
		}
	}
	display.updateScorecard(UPPER_SCORE, nPlayers, totalingCounter);
	
	if(totalingCounter >= 63){ //upper bonus checker
		int bonus = 35;
		display.updateScorecard(UPPER_BONUS, nPlayers, bonus);
		playersTotalScore[nPlayers] += bonus;
		display.updateScorecard(TOTAL, nPlayers, (playersTotalScore[nPlayers-1]));
	}
	
	display.updateScorecard(TOTAL, nPlayers, (playersTotalScore[nPlayers-1]));
	
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
	private int fullHouseChecker;
	private int fourOfAKindChecker;
	private String winnerOfgame;
}
