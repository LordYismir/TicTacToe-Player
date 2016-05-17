/**
 * Contains constants that will be used by TicTacToe
 * for controlling some parameters of the program.
 * 
 * DO NOT EDIT THE VARIABLE NAMES OR TYPES 
 * OR ADD VARIABLES TO THIS FILE.
 * This file will not be handed in, we will use our
 * own config file.
 *
 * Values that are defined here must be used by name
 * because we will change these values to ensure that
 * your program works with any settings.
 *
 * You may assume that BOARDSIZE will be a valid positive integer.
 * You may assume that BOARDSIZE will be at least 3.
 * You may assume that BOARDSIZE will be at most 4.
 * You may assume that CONTINUE, SUCCESS, and FAILURE will not change.
 * 
 * The STATICEVALUATION array is for the AI. The higher the number, the more desirable the move.
 *
 * Use these values instead of hard-coding the literal value
 * in places where these values are needed.
 *
 * @author Dillon Skeehan dskeehan@cs.wisc.edu
 */
public class Config {

	public static final int BOARDSIZE = 3;
	
	public static final int CONTINUE = -1;
	public static final int SUCCESS = 1;
	public static final int FAILURE = 0;
	
	public static final char[] PIECES = {'x', 'o'};
	
	public static final int[][][] STATICEVALUATION3 = 
		{{{6,2,6},{2,4,2},{6,2,6}},{{3,1,3},{1,12,1},{3,1,3}},{{6,2,6},{2,4,2},{6,2,6}}};
	
	public static final int[][][] STATICEVALUATION4 = 
		{{{7,4,4,7},{4,3,3,4},{4,3,3,4},{7,4,4,7}},{{4,3,3,4},{3,6,6,3},{3,6,6,3},{4,3,3,4}},
		{{4,3,3,4},{3,6,6,3},{3,6,6,3},{4,3,3,4}},{{7,4,4,7},{4,3,3,4},{4,3,3,4},{7,4,4,7}}};
	
	
	
}
