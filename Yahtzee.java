
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
		initializeArrays();
		player = 1;
		playGame();
	}

	// Take the array of player names with a length defined as nPlayers and sets
	// the current player as the one at an indicated index
	private void setCurrentPlayer() {
		String currentPlayer = playerNames[player - 1];
		display.printMessage(currentPlayer + "’s turn.  Roll the dice!");
	}

	/*
	 * Creates a 2D array that stores the used and unused categories for each player
	 * so no one can re-use any category, as well as the player's total score array
	 * by setting all the stored values to zero initially
	 */
	private void initializeArrays() {

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
		upperScoreUpdate = new int[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			upperScoreUpdate[i] = 0;
		}
		lowerScoreUpdate = new int[nPlayers];
		for (int i = 0; i < nPlayers; i++) {
			lowerScoreUpdate[i] = 0;
		}
	}

	// Plays the game immediately proceeding the run() method
	private void playGame() {

		for (int i = 0; i < (nPlayers * N_SCORING_CATEGORIES); i++) {
			setCurrentPlayer();
			firstRoll();
			secondAndThirdRoll();
			selectACategory();
			setNextPlayer();
		}

		declareWinner();
	}

	// Takes the array of possible dice values for the assigned dice
	// and creates a random number so as to simulate randomized rolls.
	// only for the first roll.
	private void firstRoll() {

		display.waitForPlayerToClickRoll(player);
		for (int i = 0; i < N_DICE; i++) {
			diceVals[i] = rgen.nextInt(1, 6);
		}

		display.displayDice(diceVals);

	}

	// Proceeds the first roll() method and urges user to select dice and roll
	// again.
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

	// Allows the user to select a category after the third roll. Also checks if the
	// dice roll is valid
	private void selectACategory() {

		category = display.waitForPlayerToSelectCategory();

		if (usedCategories[player - 1][category] == 1) {
			while (usedCategories[player - 1][category] == 1) {
				display.printMessage("This category has been used. Please choose another.");
				category = display.waitForPlayerToSelectCategory();
			}
		}

		if (checkCategory(category) == true) {

			if (category <= 6) {
				for (int k = 0; k < diceVals.length; k++) {
					if (diceVals[k] == category) {
						Score += diceVals[k];
					}
				}
			} else if (category == 9 || category == 10 || category == 15) {
				for (int j = 0; j < diceVals.length; j++) {
					Score += diceVals[j];
				}
			} else if (category >= 11 || category <= 14) {
				switch (category) {
				case 11:
					Score = 25;
					break;
				case 12:
					Score = 30;
					break;
				case 13:
					Score = 40;
					break;
				case 14:
					Score = 50;
					break;
				}
			}
		} else {
			Score = 0;
		}

		display.updateScorecard(category, player, Score);
		if (category <= 6) {
			upperScoreUpdate[player - 1] += Score;
			updateUpperTotal();
			Score = 0;
		}
		if (category >= 9 && category <= 15) {
			lowerScoreUpdate[player - 1] += Score;
			updateLowerTotal();
			Score = 0;
		}

	}

	// This method is called inside selectACategory() and it checks if the dice
	// rolled
	// is valid for whatever category the user selects
	private boolean checkCategory(int category) {

		if (category <= 6) {
			usedCategories[player - 1][category] = 1;

			for (int i = 0; i < N_DICE; i++) {
				if (diceVals[i] == category) {
					return true;
				}
			}
		} else if (category == 9) {
			usedCategories[player - 1][category] = 1;

			for (int j = 0; j < N_DICE; j++) {
				for (int k = j + 1; k < N_DICE; k++) {
					for (int l = k + 1; l < N_DICE; l++) {
						if (diceVals[j] == diceVals[k] && diceVals[k] == diceVals[l])
							return true;
					}
				}
			}
		} else if (category == 10) {
			usedCategories[player - 1][category] = 1;
			int fourOfAKindCounter = 0;

			for (int i = 0; i < N_DICE - 1; i++) {
				if (diceVals[i] == diceVals[i + 1]) {
					fourOfAKindCounter++;
					if (fourOfAKindCounter >= 3) {
						return true;
					}
				}
			}
		} else if (category == 11) {
			usedCategories[player - 1][category] = 1;

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
							return true;

					}
				}
			}
		} else if (category == 12) {
			usedCategories[player - 1][category] = 1;

			for (int m = 0; m < N_DICE; m++) {
				for (int n = 0; n < N_DICE; n++) {
					for (int o = 0; o < N_DICE; o++) {
						for (int p = 0; p < N_DICE; p++) {
							if (diceVals[n] == diceVals[m] + 1 && diceVals[o] == diceVals[n] + 1
									&& diceVals[p] == diceVals[o] + 1)
								return true;
						}
					}
				}
			}
		} else if (category == 13) {
			usedCategories[player - 1][category] = 1;

			for (int m = 0; m < N_DICE; m++) {
				for (int n = 0; n < N_DICE; n++) {
					for (int o = 0; o < N_DICE; o++) {
						for (int p = 0; p < N_DICE; p++) {
							for (int q = 0; q < N_DICE; q++) {
								if (diceVals[n] == diceVals[m] + 1 && diceVals[o] == diceVals[n] + 1
										&& diceVals[p] == diceVals[o] + 1 && diceVals[q] == diceVals[p] + 1)
									return true;
							}
						}
					}
				}
			}
		} else if (category == 14) {
			usedCategories[player - 1][category] = 1;
			int yahtzeeCounter = 0;

			for (int i = 0; i < N_DICE - 1; i++) {
				if (diceVals[i] == diceVals[i + 1]) {
					yahtzeeCounter++;
					if (yahtzeeCounter == 4) {
						return true;
					}
				}
			}
		} else if (category == 15) {
			usedCategories[player - 1][category] = 1;

			return true;
		}
		return false;
	}

	// This method checks the upperScoreUpdate array value to see if the
	// player has a valid score for the upper bonus (35 points)
	// Also is a live update for the upper total score; a sum of the first six categories
	private void updateUpperTotal() {

		playersTotalScore[player - 1] += Score;

		display.updateScorecard(UPPER_SCORE, player, upperScoreUpdate[player - 1]);

		if (upperScoreUpdate[player - 1] >= 63) {
			UpperBonus = 35;
			display.updateScorecard(UPPER_BONUS, player, UpperBonus);
		}

		display.updateScorecard(TOTAL, player, (playersTotalScore[player - 1]) + UpperBonus);

	}

	// Is a live update for the lower total score; a sum of the last seven categories
	private void updateLowerTotal() {
		playersTotalScore[player - 1] += Score;

		display.updateScorecard(LOWER_SCORE, player, lowerScoreUpdate[player - 1]);

		display.updateScorecard(TOTAL, player, playersTotalScore[player - 1]);
	}

	// Uses a player counter to transition to the next player
	// after a category is chosen
	// Resets player to one after the last player's turn
	private void setNextPlayer() {

		if (player < nPlayers) {
			player++;
		} else {
			player = 1;
		}

	}

	// Checks to see the greatest value of the total score inside the
	// playersTotalScore array
	// then prints out the player's name with said score
	private void declareWinner() {
		topScore = 0;

		for (int i = 0; i < nPlayers; i++) {
			if (playersTotalScore[i] > topScore)
				topScore = playersTotalScore[i];
		}
		for (int i = 0; i < nPlayers; i++) {
			if (playersTotalScore[i] == topScore)
				display.printMessage(playerNames[i] + " WINS!");
		}
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
	private int[] upperScoreUpdate;
	private int[] lowerScoreUpdate;
	private int UpperBonus = 0;
	private int player;
	private int topScore;

}
