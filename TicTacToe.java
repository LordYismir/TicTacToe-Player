///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            TicTacToe
// Files:            TicTacToe, Config, AIPlayer
// Semester:         CS302 Summer 2015
//
// Author:           Dillon Skeehan
// Email:            dskeehan@cs.wisc.edu
// CS Login:         dskeehan
// Lecturer's Name:  Deb Deppeler
// Lab Section:      Epic Summer Session
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.Scanner;

/**
 * 
 * A 3D version of the classic board game Tic-Tac-Toe
 *
 */
public class TicTacToe {

	/**
	 * 
	 * @param args Command line input arguments (none for this project)
	 */
	public static void main(String[] args) {
		
		//declare variables
		Scanner scnr = new Scanner(System.in);
		char tictactoe[][][] = new char[Config.BOARDSIZE][Config.BOARDSIZE][Config.BOARDSIZE];
		int choice = Config.CONTINUE;
		
		//loop to print out game choices or exit.
		while (choice != 3) {
			
			//welcome message section
			System.out.println("Welcome to 3D Tic Tac Toe!");
			System.out.println("Do you want to play a 1 player game (against an A.I.) or a 2 player game (against another human opponent)?");
			System.out.println(" 1. Single player game");
			System.out.println(" 2. Two player game");
			System.out.println(" 3. Exit");
			
			//get the user input section
			choice = queryUser(scnr, "Choice: ", 1, 3);
			
			//branch out on the user input.
			if (choice == 1) {
				playAI(scnr, tictactoe);
			} else if (choice == 2) {
				playHuman(scnr, tictactoe);
			} else if (choice == 3) {
				System.out.println("Goodbye!");
			}
		}
		
	}
	
	/**
	 * Runs the game between an AIplayer and a human player. The AIplayer goes first.
	 * @param scnr The scanner in order to grab the user input from the keyboard
	 * @param game The array representation of the board state. 
	 */
	public static void playAI(Scanner scnr, char[][][] game) {
		
		//initialize the game and the ai player
		initializeTicTacToe(game);
		AIPlayer aiPlayer = new AIPlayer(3);
		
		int board =0;
		int row = 0;
		int column = 0;
		int player = 0;
		
		boolean piecePlaced = false;
		boolean hasWinner = false;
		
		printGame(game);
		
		//Runs the game while a winner hasn't been found.
		while (hasWinner == false) {
			//the human player is player 2.
			if (player == 1) {
				//Prompt a user until a piece is placed in a valid position.
				while (piecePlaced == false) {
					System.out.println("Player " + (player+1) + ": place your piece.");
					board = queryUser(scnr, "Enter board: ", 1, Config.BOARDSIZE);
					row = queryUser(scnr, "Enter row: ", 1, Config.BOARDSIZE);
					column = queryUser(scnr, "Enter column: ", 1, Config.BOARDSIZE);
					
					//check to make sure the piece was placed in a valid position
					if (placePiece(Config.PIECES[player],game, board-1, row-1, column-1)) {
						piecePlaced = true;
					} else {
						System.out.println("Piece already there!");
					}
					printGame(game);
				}	
				//AI section.
			} else {
				System.out.println("AI is deciding...");
				Move move = aiPlayer.makeMove(game, player);
				placePiece(Config.PIECES[player], game, move.getBoard(), move.getRow(), move.getColumn());
				printGame(game);
				System.out.println("AI placed piece on board: " + (move.getBoard()+1) + ", row: " + (move.getRow()+1) + ", column: " + (move.getColumn()+1) +"\n");
			}
			
			//reset variables.
			piecePlaced = false;
			
			//check for winner.
			if (isWinner(game, player)) {
				System.out.println("Player " + (player+1) + " won!");
				hasWinner = true;
			}
			player = (player+1) % 2;
		}
	}
	
	
	
	/**
	 * This method runs a game between two human players.
	 * @param scnr The scanner in order to grab the user input from the keyboard
	 * @param game The array representation of the board state. 
	 */
	public static void playHuman(Scanner scnr, char[][][] game) {
		
		//initialize the game
		initializeTicTacToe(game);
		
		int board =0;
		int row = 0;
		int column = 0;
		int player = 0;
		
		boolean piecePlaced = false;
		boolean hasWinner = false;
		
		printGame(game);
		
		//Runs the game while a winner hasn't been found.
		while (hasWinner == false) {
			//Prompt a user until a piece is placed in a valid position.
			while (piecePlaced == false) {
				System.out.println("Player " + (player+1) + ": place your piece.");
				board = queryUser(scnr, "Enter board: ", 1, Config.BOARDSIZE);
				row = queryUser(scnr, "Enter row: ", 1, Config.BOARDSIZE);
				column = queryUser(scnr, "Enter column: ", 1, Config.BOARDSIZE);
				
				//make sure the piece was placed in a valid position
				if (placePiece(Config.PIECES[player],game, board-1, row-1, column-1)) {
					piecePlaced = true;
				} else {
					System.out.println("Piece already there!");
				}
				printGame(game);
			}
			
			piecePlaced = false;
			
			//checks for win condition.
			if (isWinner(game, player)) {
				System.out.println("Player " + (player+1) + " won!");
				hasWinner = true;
			}
			//advance player
			player = (player+1) % 2;	
		}
	}
	
	/**
	 * Initialize the tic tac toe board to an empty board, i.e one filled with dots.
	 * @param game is the declared but uninitialized array representation of the tic tac toe game.
	 */
	public static void initializeTicTacToe(char[][][] game) {
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					game[i][j][k] = '.';
				}
			}
		}
	}
	/**
	 * Handles the user input. 
	 * @param scnr Grabs the user input
	 * @param message String to inform the user about the choices they have and what to enter.
	 * @param min Minimum user input allowed
	 * @param max Maximum user input allowed
	 * @return The integer choice selected by the user constrained to be between min and max.
	 */
	public static int queryUser(Scanner scnr, String message, int min, int max) {
		int value = -1;
		while (value == Config.CONTINUE) {
			System.out.print(message);
			if (scnr.hasNextInt()) {
				value = scnr.nextInt();
				scnr.nextLine();
				if ((value> max) || (value < min)) {
					System.out.println("Please enter a value in range [" + min + "," + max + "]");
					value = Config.CONTINUE;
				}
			} else {
				scnr.nextLine();
				System.out.println("Please enter a value in range [" + min + "," + max + "]");
				value = Config.CONTINUE;
			}
		}
		return value;
	}
	
	/**
	 * Places a piece on the gameboard if possible.
	 * @param piece The piece to be played, e.g. x or o
	 * @param game The array representation of the gameboard.
	 * @param board The board to put the piece in.
	 * @param row The row to put the piece in.
	 * @param column The column to put the piece in.
	 * @return True if the piece is able to be placed, otherwise false.
	 */
	public static boolean placePiece(char piece, char[][][] game, int board, int row, int column) {
		if (game[board][row][column] == '.') {
			game[board][row][column] = piece;
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Checks the game for all possible win conditions to see if there is a winner.
	 * @param game The array representation of the board state. 
	 * @param player The current player
	 * @return The total number of winning lines the most recent move made.
	 */
	public static boolean isWinner(char[][][] game, int player) {
		boolean result = false;
		result = result || checkCubeDiagonal(game,player);
		result = result || checkStraights(game,player);
		result = result || checkFaceDiagonal(game,player);
		return result;
	}
	
	/**
	 * Checks the cube diagonal to see if there is a winner.
	 * @param game The array representation of the board state. 
	 * @param player The current player
	 * @return The total number of winning lines the most recent move made.
	 */
	
	public static boolean checkCubeDiagonal(char [][][] game, int player) {
		boolean result = true;
		int number = 0;
		for (int i =0; i<Config.BOARDSIZE; i++) {
			result = result && (game[i][i][i] == Config.PIECES[player]);
		}
		if (result == true) {
			return result;
		} else {
			result = true;
		}
		
		for (int i =0; i<Config.BOARDSIZE; i++) {
			result = result && (game[i][i][Config.BOARDSIZE-i-1] == Config.PIECES[player]);
		}
		if (result == true) {
			return result;
		} else {
			result = true;
		}
			
		for (int i =0; i<Config.BOARDSIZE; i++) {
			result = result && (game[i][Config.BOARDSIZE-i-1][i] == Config.PIECES[player]);
		}
		if (result == true) {
			return result;
		} else {
			result = true;
		}
		for (int i =0; i<Config.BOARDSIZE; i++) {
			result = result && (game[i][Config.BOARDSIZE-i-1][Config.BOARDSIZE-i-1] == Config.PIECES[player]);
		}
		if (result == true) {
			return result;
		} else {
			result = true;
		}
		return false;
		
	}
	
	/**
	 * Checks the face diagonal to see if there is a winner.
	 * @param game The array representation of the board state. 
	 * @param player The current player
	 * @return The total number of winning lines the most recent move made.
	 */
	public static boolean checkFaceDiagonal(char[][][] game, int player) {
		boolean result = true;
		int number = 0;
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				result = result && (game[i][j][j] == Config.PIECES[player]);
			}
			if (result == true) {
				return result;
			} else {
				result = true;
			}
		}
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				result = result && (game[i][j][Config.BOARDSIZE-j-1] == Config.PIECES[player]);
			}
			if (result == true) {
				return result;
			} else {
				result = true;
			}
		}
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				result = result && (game[j][i][j] == Config.PIECES[player]);
			}
			if (result == true) {
				return result;
			} else {
				result = true;
			}
		}
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				result = result && (game[j][i][Config.BOARDSIZE-j-1] == Config.PIECES[player]);
			}
			if (result == true) {
				return result;
			} else {
				result = true;
			}
		}
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				result = result && (game[j][j][i] == Config.PIECES[player]);
			}
			if (result == true) {
				return result;
			} else {
				result = true;
			}
		}
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				result = result && (game[j][Config.BOARDSIZE-j-1][i] == Config.PIECES[player]);
			}
			if (result == true) {
				return result;
			} else {
				result = true;
			}
		}
		return false;
	}
	
	/**
	 * Checks the straights to see if there is a winner.
	 * @param game The array representation of the board state. 
	 * @param player The current player
	 * @return The total number of winning lines the most recent move made.
	 */
	public static boolean checkStraights(char[][][] game, int player) {
		boolean result = true;
		int number = 0;
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					result = result && (game[i][j][k] == Config.PIECES[player]);
				}
				if (result == true) {
					return result;
				} else {
					result = true;
				}
			}
		}
		
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					result = result && (game[i][k][j] == Config.PIECES[player]);
				}
				if (result == true) {
					return result;
				} else {
					result = true;
				}
			}
		}
		
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					result = result && (game[k][i][j] == Config.PIECES[player]);
				}
				if (result == true) {
					return result;
				} else {
					result = true;
				}
			}
		}
		
		return false;
	}
	
	/* This section deals with the methods to the display the TicTacToe game to the user. 
	 * PrintGame is the main method for this section, with the other print methods helping
	 * to simplify the logic of the code.
	 */
	
	
	/**
	 * Prints out the gameboard
	 * @param game The array representation of the 3d tic tac toe game.
	 */
	public static void printGame(char[][][] game) {
		printBoardLabel();
		
		//this prints out one horizontal border, game row pair.
		for (int i =0; i<Config.BOARDSIZE; i++) {
			printHorizontalSpacing();
			
			//prints out the entire border for all three boards.
			for (int j =0; j<Config.BOARDSIZE; j++) {
				printHorizontalBorder();
				printHorizontalSpacing();
			}
			
			printNewLine();
			
			//print the board row
			for (int j =0; j<Config.BOARDSIZE; j++) {
				//since our outer loop is the row and our inner loop is the board number
				//we need to pass j to printBoardRow's board variable and i to the row variable.
				printRowLabel(i);
				printBoardRow(game,j,i);
			}
			printNewLine();
		}
		
		//print the final horizontal border
		printHorizontalSpacing();
		for (int i =0; i<Config.BOARDSIZE; i++) {
			printHorizontalBorder();
			printHorizontalSpacing();
		}
		printNewLine();
		printColumnLabel();
		printNewLine();
	}
	
	/**
	 * Helper method that prints out the Board Labels in order to easily identify where to put a piece.
	 */
	public static void printBoardLabel() {
		for (int i =0; i<Config.BOARDSIZE; i++) {
			printHorizontalSpacing();
			System.out.print("Board " + (i+1));
		}
		printNewLine();
	}
	
	/**
	 * Helper method that prints out the Board Labels in order to easily identify where to put a piece.
	 * @param row the row number to print
	 */
	public static void printRowLabel(int row) {
		System.out.print("    " + (row+1));
	}
	
	/**
	 * Helper method that prints out the Board Labels in order to easily identify where to put a piece.
	 */
	public static void printColumnLabel() {
		for (int i = 0; i<Config.BOARDSIZE; i++) {
			printHorizontalSpacing();
			for (int j =0; j<Config.BOARDSIZE; j++) {
				System.out.print(" " + (j+1));
			}
			System.out.print(" ");
		}
		printNewLine();
	}
	
	/**
	 * Helper method to print out a specific row of a specific game board.
	 * @param game This is the array representation of the tic tac toe board
	 * @param board The board to access.
	 * @param row The row to access.
	 */
	public static void printBoardRow(char [][][] game, int board, int row) {
		for (int column =0; column<Config.BOARDSIZE-1; column++) {
			System.out.print("|" + game[board][row][column]);
		}
		System.out.print("|" + game[board][row][Config.BOARDSIZE-1]+"|");
	}
	
	/**
	 * Helper method to print out the gameboard. It prints a horizontal border for one game board.
	 */
	public static void printHorizontalBorder() {
		for (int i =0; i<2*Config.BOARDSIZE+1; i++) {
			System.out.print("-");
		}
	}
	
	/**
	 * Helper method to print out the correct Horizontal spacing. It should print out 5 spaces.
	 */
	public static void printHorizontalSpacing() {
		System.out.print("     ");
	}
	
	/**
	 * Helper method to print out a new line. 
	 */
	public static void printNewLine() {
		System.out.println("");
	}

}
