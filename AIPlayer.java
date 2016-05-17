///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  TicTacToe
// File:             AIPlayer
// Semester:         CS302 Summer 2015
//
// Author:           Dillon Skeehan dskeehan@cs.wisc.edu
// CS Login:         dskeehan
// Lecturer's Name:  Deb Deppeler
// Lab Section:      Epic Summer Session
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.util.ArrayList;

/**
 * 
 * @author Dillon Skeehan
 *
 */
public class AIPlayer {

	private int depthLimit;
	
	/**
	 * The constructor for the AI player. The only thing that needs to be set is the depth for the alpha beta minimax algorithm.
	 * @param depthLimit how many levels to recurse too. Can be set for different difficulties.
	 */
	public AIPlayer(int depthLimit ) {
		this.depthLimit = depthLimit;
	}
	
	/**
	 * Gets all the possible moves for the given game state.
	 * @param state the current game state of the tic tac toe game
	 * @return The list of possible moves given this board state.
	 */
	public ArrayList<Move> getMoves(char[][][] state) {
		
		ArrayList<Move> moves = new ArrayList<Move>();
		
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					if (state[i][j][k] == '.') {
						moves.add(new Move(i,j,k));
					}
				}
			}
		}
		
		return moves;
	}
	
	/**
	 * returns an updated game state with the Move move placed by the player.
	 * @param state The current game state.
	 * @param move The move to be added.
	 * @param player The player adding the move.
	 * @return The updated game state with the moved added.
	 */
	public char[][][] update(char[][][] state, Move move, int player) {
		char[][][] updatedState = new char[Config.BOARDSIZE][Config.BOARDSIZE][Config.BOARDSIZE];
		copyState(state, updatedState);
		updatedState[move.getBoard()][move.getRow()][move.getColumn()] = Config.PIECES[player];
		return updatedState;
	}
	
	/**
	 * Copies the original board state to a copy. (deep copy)
	 * @param original The original game state
	 * @param update The copy of the original state.
	 */
	public void copyState(char[][][] original, char[][][] copy) {
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					copy[i][j][k] = original[i][j][k];
				}
			}
		}
	}
	
	/**
	 * function to call the recursive alpha beta algorithm, with alpha and beta being very low.
	 * @param state The current game state.
	 * @param player The current player.
	 * @return The best possible move to make.
	 */
	public Move makeMove(char[][][] state, int player) {
		return alphabeta(state, 0, -1000000.0, 1000000.0, player);
	}
	
	/**
	 * Implementation of the standard minimax algorithm with alpha beta pruning: https://en.wikipedia.org/wiki/Minimax
	 * @param state The current state of the game
	 * @param depth The depth to recurse to
	 * @param alpha used for pruning
	 * @param beta used for pruning
	 * @param player The current player.
	 * @return The best possible move given the current game state and optimal play by both sides.
	 */
	public Move alphabeta(char[][][] state, int depth, double alpha, double beta, int player) {
		
		if (depth >= depthLimit) {
			return heuristic(state,player);
		} else if (TicTacToe.isWinner(state, 0)) {
			Move move = heuristic(state,player);
			move.setValue(move.getValue()+1000.0);
			return move;
		} else if (TicTacToe.isWinner(state,1)) {
			Move move = heuristic(state,player);
			move.setValue(move.getValue()-1000.0);
			return move;
		}
		
		Move best = new Move(-1,-1,-1);
		
		ArrayList<Move> possibleMoves = getMoves(state);
		
		best = possibleMoves.get(0);
		
		for (Move m : possibleMoves) {
			if (depth == 0) {
				System.out.println("Considering: " + m.toString());	
			}
			
			if (player == 0) {
				char[][][] childState = update(state, m, 0);
				Move recurse = alphabeta(childState, depth+1, alpha, beta, 1);
				if (recurse.getValue() > alpha) {
					System.out.println("found new max: (" + recurse.getValue() + ", " + beta +")");
					alpha = recurse.getValue();
					best = m;
				}
				
			} else if (player == 1) {
				char[][][] childState = update(state, m, 1);
				Move recurse = alphabeta(childState, depth+1, alpha, beta, 0);
				if (recurse.getValue() < beta) {
					System.out.println("found new min: (" + recurse.getValue() + ", " + beta +")");
					beta = recurse.getValue();
					best = m;
				}
			}
			
			if (beta <= alpha) {
				break;
			}
			
		}
		
		if (player == 0) {
			best.setValue(alpha);
		} else {
			best.setValue(beta);
		}
		
		System.out.println("choosing " + best.toString());
		return best;
		
		
		
	}
	
	/**
	 * Heuristic function for alpha beta pruning. This is the area that can be most improved!
	 * @param state The current state of the game
	 * @param player The current player
	 * @return A move (holding a value) that gives an indication of how good or bad this move would be.
	 */
	public Move heuristic(char[][][] state, int player) {
		Move move = new Move(-1,-1,-1);
		double value = 0.0;
		for (int i =0; i<Config.BOARDSIZE; i++) {
			for (int j =0; j<Config.BOARDSIZE; j++) {
				for (int k =0; k<Config.BOARDSIZE; k++) {
					if (state[i][j][k] == Config.PIECES[0]) {
						value += staticEvaluation(i,j,k);
					} else if (state[i][j][k] == Config.PIECES[1]){
						value -= staticEvaluation(i,j,k);
					}
				}
			}
		}
		
		move.setValue(value);
		return move;
	}
	
	public double staticEvaluation(int board, int row, int column) {
		if (Config.BOARDSIZE%2 == 1) {
			return Config.STATICEVALUATION3[board][row][column];
		} else {
			return Config.STATICEVALUATION4[board][row][column];
		}
	}
	
}

/**
 * Helper class to encapsulate the move data.
 * @author Dillon Skeehan
 *
 */
class Move {
	private int board;
	private int row;
	private int column;
	private double value;
	
	/**
	 * Constructor for the move class.
	 * @param board the board to place a piece on.
	 * @param row The row to place a piece on.
	 * @param column The column to place a piece on.
	 */
	Move(int board, int row, int column) {
		this.board = board;
		this.row = row;
		this.column = column;
		this.value = 0;
	}

	/**
	 * Gets the 
	 * @return
	 */
	public int getBoard() {
		return board;
	}

	public void setBoard(int board) {
		this.board = board;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "[b" + board +",r" + row +",c" + column +": " + value +"]";
	}
	
	
	
}
