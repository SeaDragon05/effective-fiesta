package AI;

import java.util.ArrayList;
import java.util.Random;

import objects.Factory;
import objects.GameBoard;
import objects.GameData;
import objects.SimpleSlots;
import objects.Tile;
import objects.Tile.Tile_Type;

public class AIMaster {
	public static boolean debug = true;
	/**
	 * 5x5 float matrix to keep values for each tile type in the specified location
	 */
	private Matrix<Float> right_table_weights;
	/**
	 * 5x3 float matrix to keep values for each tile type from each row from the
	 * right table weights
	 */
	private Matrix<Float> left_table_weights;
	/**
	 * 5x5 float matrix that contains 25 random values between -0.1 to 0.1 to help
	 * the AI not be as predictable
	 */
	private Matrix<Float> random_weights;
	/**
	 * 5x3 float matrix to store possible move values for factories and the void
	 */
	private Matrix<Float> move_weights;
	/**
	 * AI's personal board. It says "you cannot touch it... YOU SHALL NOT TOUCH IT!"
	 */
	private GameBoard board;
	// standard things to help my feeble mind comprehend my adhd ideas
	static final boolean LEFT = true, RIGHT = false;
	static final int WEIGHT = 0, TYPE = 1, AMOUNT = 2;
	// WEIGHTS: playing with these is dangerous without knowledge on what AI weights
	// are and what they do
	// Adjusting these weights may allow the AI make to unusual choices. Lmao, give
	// it a try
	// KEEP WEIGHTS BETWEEN -1 AND 1 FOR BEST RESULTS
	// negative numbers are penalties, positive numbers are rewards
	static final float base = 0.2f;
	static final float inc_r = 0.12f;
	static final float inc_c = 0.08f;
	static final float dec_r = -0.06f;
	static final float dec_c = -0.03f;
	static final float[] left_row = { 0.61f, 0.45f, 0.21f, 0.19f, 0.21f };
	static final float voidPointPenalty = -0.11f;
	static final float f_uc_penalty = -0.14f;
	static final float f_oc_penalty = -0.18f;
	static final float f_wtt_penalty = -0.09f;
	static final float f_ctt_penalty = 0.15f;// pleaseDontMakeThisNegative

	/**
	 * Constructs and AI object for the game. To use the AI for the game, call
	 * takeTurn()
	 */
	public AIMaster() {
		right_table_weights = new Matrix<Float>(5, 5, Float.class) {
			@Override
			public void add(int y, int x, Float value) {
				matrix[y][x] += value;
			}
		};
		left_table_weights = new Matrix<Float>(5, 3, Float.class);
		move_weights = new Matrix<Float>(5, 3, Float.class);
		random_weights = new Matrix<Float>(5, 5, Float.class);
		for (int i = 0; i < 5; i++) {
			right_table_weights.setRow(i, 0.0f);
			left_table_weights.setRow(i, 0.0f);
			move_weights.setRow(i, 0.0f);
		}
		board = new GameBoard();
	}

	/**
	 * Forces the AI to think and make a move.
	 */
	public void takeTurn() {
		randomize_table();
		reset_weights();
		CheckOwnRightBoard();
		CheckOwnLeftBoard();
		calculateMove();
	}

	/**
	 * Sets the randomized weights for AI unpredictability
	 */
	private void randomize_table() {
		Random rnd = new Random();
		for (int Y = 0; Y < 5; Y++) {
			for (int X = 0; X < 5; X++) {
				random_weights.set(Y, X, 0.1f - rnd.nextFloat() / 10);
			}
		}
	}

	/**
	 * Give the AI its board or it can't play :(
	 * 
	 * @param board
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}

	/**
	 * Checks the right board 5x5 matrix
	 */
	private void CheckOwnRightBoard() {
		if (board == null)
			throw new IllegalStateException();
		checkRightRow(0);
		checkRightRow(1);
		checkRightRow(2);
		checkRightRow(3);
		checkRightRow(4);
	}

	/**
	 * Checks the left board non-standard 5xWhatEver matrix
	 */
	private void CheckOwnLeftBoard() {
		if (board == null)
			throw new IllegalStateException();
		checkLeftRow(0);
		checkLeftRow(1);
		checkLeftRow(2);
		checkLeftRow(3);
		checkLeftRow(4);

	}

	/**
	 * Tests the AI with a given situation
	 */
	public void Test() {
		/*----------------setup----------------*/

		if (board == null)
			throw new IllegalStateException();
		// tile definitions
		Tile Tile_Blue__ = new Tile(Tile.Tile_Type.Blue);
		Tile Tile_Orange = new Tile(Tile.Tile_Type.Orange);
		Tile Tile_Red___ = new Tile(Tile.Tile_Type.Red);
		Tile Tile_Black_ = new Tile(Tile.Tile_Type.Black);
		Tile Tile_Frost_ = new Tile(Tile.Tile_Type.Frost);
		// right side
		// add tiles to test situation
		board.getRow(RIGHT, 0).set(Tile_Blue__);
		board.getRow(RIGHT, 0).set(Tile_Red___);
		board.getRow(RIGHT, 3).set(Tile_Black_);
		board.getRow(RIGHT, 3).set(Tile_Frost_);
		board.getRow(LEFT, 0).set(Tile_Orange);
		board.getRow(LEFT, 4).set(Tile_Red___);

		/*----------------AI test----------------*/

		// randomized seed for the ai:
		randomize_table();
		reset_weights();
		// checking its own board and setting weights
		CheckOwnRightBoard();
		CheckOwnLeftBoard();

		/*----------------AI test----------------*/

		/*----------------AI results----------------*/
		System.out.println("Right board:");
		for (int Y = 0; Y < 5; Y++) {
			for (int X = 0; X < 4; X++) {
				if (right_table_weights.get(Y, X) > 0.0f)
					System.out.printf(" %.2f :", right_table_weights.get(Y, X));
				else if (right_table_weights.get(Y, X) < 0.0f)
					break;
				else
					System.out.print(" N/A  :");
			}
			if (right_table_weights.get(Y, 4) > 0.0f)
				System.out.printf(" %.2f\n", right_table_weights.get(Y, 4));
			else if (right_table_weights.get(Y, 4) < 0.0f)
				System.out.print(" Left is full!\n");
			else
				System.out.print(" N/A \n");
		}

		System.out.println("\nLeft board:");
		for (int Y = 0; Y < 5; Y++) {
			float weight = left_table_weights.get(Y, 0);
			if (weight != 0.0f) {
				System.out.printf(" %.2f : ", weight);
				System.out.print(Tile.getTypeStr((int) Math.floor(left_table_weights.get(Y, 1))));
				System.out.println(" : " + board.getRow(LEFT, Y).getOccupiedCount() + " / " +board.getRow(LEFT, Y).getLength());
			} else
				System.out.print(" Row is full, not calculating\n");
		}

		// comparing the left weight table to factories
		calculateMove();

		/*----------SECOND TURN IN ROUND 1  TEST----------*/

		System.out.println("\n\n\n\n\n\nSECOND TEST");
		for (int i = 0; i < 4; i++) {
			// randomized seed for the ai:
			randomize_table();
			reset_weights();
			// checking its own board and setting weights
			CheckOwnRightBoard();
			CheckOwnLeftBoard();

			/*----------------AI test----------------*/

			/*----------------AI results----------------*/
			System.out.println("Right board:");
			for (int Y = 0; Y < 5; Y++) {
				for (int X = 0; X < 4; X++) {
					if (right_table_weights.get(Y, X) > 0.0f)
						System.out.printf(" %.2f :", right_table_weights.get(Y, X));
					else if (right_table_weights.get(Y, X) < 0.0f)
						break;
					else
						System.out.print(" N/A  :");
				}
				if (right_table_weights.get(Y, 4) > 0.0f)
					System.out.printf(" %.2f\n", right_table_weights.get(Y, 4));
				else if (right_table_weights.get(Y, 4) < 0.0f)
					System.out.print(" Left is full!\n");
				else
					System.out.print(" N/A \n");
			}
			System.out.println("\nLeft board:");
			for (int Y = 0; Y < 5; Y++) {
				float weight = left_table_weights.get(Y, 0);
				if (weight != 0.0f) {
					System.out.printf(" %.2f : ", weight);
					System.out.print(Tile.getTypeStr((int) Math.floor(left_table_weights.get(Y, 1))));
					System.out.println(" : " + board.getRow(LEFT, Y).getOccupiedCount() + " / " +board.getRow(LEFT, Y).getLength());
				} else
					System.out.print(" Row is full, not calculating\n");
			}

			// comparing the left weight table to factories
			calculateMove();
		}

	}

	private void reset_weights() {
		for (int i = 0; i < 5; i++) {
			right_table_weights.setRow(i, 0.0f);
		}
		for (int i = 0; i < 3; i++) {
			left_table_weights.setRow(i, 0.0f);
		}

	}

	private void checkRightRow(int rowNumber) {
		// data sanitization
		if (rowNumber < 0 || rowNumber >= 5)
			throw new IndexOutOfBoundsException("Row number must be between 0 and 4.");

		// return if full, nothing to do
		if (board.getRow(LEFT, rowNumber).isFull()) {
			for (int i = 0; i < 5; i++) {
				right_table_weights.set(rowNumber, i, -1.0f);// -1.0f means that it was skipped
			}
			return;
		}

		/*-----Weight distribution-----*/

		// check left side and finalize with just one possible choice
		if (board.getRow(LEFT, rowNumber).isOccupied()) {
			Tile.Tile_Type type = board.getRow(LEFT, rowNumber).getLockedTileType();
			float lockedWeight = board.getRow(LEFT, rowNumber).AI_getLockedWeight();
			right_table_weights.set(rowNumber, Tile.indexLookUpTable[rowNumber][Tile.getTypeInt(type)],
					lockedWeight + base);
			return;
		}

		// easy access pointers for current row
		Tile[] tiles_Right = board.getRow(RIGHT, rowNumber).getTiles();
		Tile.Tile_Type[] tiles_Right_Rules = board.getRow(RIGHT, rowNumber).getTileRules();

		// check right and weigh possible weights at base level with possible
		// connections
		for (int i = 0; i < 5; i++) {
			if (tiles_Right[i] == null) {
				// Assign base weight for unoccupied tiles
				right_table_weights.set(rowNumber, Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]),
						base + random_weights.get(rowNumber, i));
				// check to see if it can create possible connections:
				// check the right to it first
				if (i < 4) {
					for (int j = i + 1; j < 5; j++) {
						if (tiles_Right[j] != null) {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), inc_r);
						} else {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), dec_r);
							break;
						}
					}
				}
				// check the left to it second
				if (i > 0) {
					for (int j = i - 1; j >= 0; j--) {
						if (tiles_Right[j] != null) {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), inc_r);
						} else {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), dec_r);
							break;
						}
					}
				}
				// check above
				if (rowNumber > 0) {
					for (int j = rowNumber - 1; j > 0; j++) {
						if (board.getRow(RIGHT, j).get(i) != null) {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), inc_c);
						} else {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), dec_c);
							break;
						}
					}
				}
				// check below
				if (rowNumber < 4) {
					for (int j = rowNumber + 1; j < 4; j++) {
						if (board.getRow(RIGHT, j).get(i) != null) {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), inc_c);
						} else {
							right_table_weights.add(rowNumber,
									Tile.GetTileIndexForRow(tiles_Right_Rules, tiles_Right_Rules[i]), dec_c);
							break;
						}
					}
				}
			}
		}
	}

	private void checkLeftRow(int rowNumber) {
		/*---optimization flow---*/

		// data sanitization
		if (rowNumber < 0 || rowNumber >= 5)
			throw new IndexOutOfBoundsException("Row number must be between 0 and 4.");

		// return if full, nothing to do
		if (board.getRow(LEFT, rowNumber).isFull())
			return;

		// find the max from the row and type
		float max = 0.0f;
		Tile_Type type = Tile_Type.NOTYPE;
		for (int X = 0; X < 5; X++) {
			if (right_table_weights.get(rowNumber, X) > max) {
				max = right_table_weights.get(rowNumber, X);
				type = Tile.getTypeRowAtIndex(rowNumber, X);
			}
		}
		if (max == 0.0f) {
			throw new IllegalStateException("Your dumbass 'AI' was about to divide by zero, homes!");// THIS ACTUALLY
																										// HAPPENED
		}
		left_table_weights.set(rowNumber, 0, (left_row[rowNumber] * board.getRow(LEFT, rowNumber).getLength())
				/ (board.getRow(LEFT, rowNumber).getOccupiedCount() + 1) * max);
		left_table_weights.set(rowNumber, 1, (float) Tile.getTypeInt(type));// lazy programmer moment
		left_table_weights.set(rowNumber, 2, (float) board.getRow(LEFT, rowNumber).getUnoccupiedCount());// lazy
																											// programmer
																											// moment x2

	}

	public void calculateMove() {

		class Choice {
			private Tile_Type type;
			private int amount;
			private float weight;

			public Choice(int a, float w, Tile_Type t) {
				amount = a;
				weight = w;
				type = t;
			}

			public float getWeight() {
				return weight;
			}

			public Tile_Type getType() {
				return type;
			}

			public int getAmount() {
				return amount;
			}
		}
		// get all possible moves
		ArrayList<Choice> choices = new ArrayList<Choice>();
		for (int i = 0; i < 5; i++) {
			if (left_table_weights.get(i, AMOUNT) > 0.0f) {
				choices.add(new Choice((int) Math.floor(left_table_weights.get(i, AMOUNT)),
						left_table_weights.get(i, WEIGHT),
						Tile.TILETYPE[(int) Math.floor(left_table_weights.get(i, TYPE))]));
			} else {
				choices.add(null);
			}
		}

		/*-----------Factory search-----------*/

		// check all factories that have stuff in them and weight each need
		// then, check the void and weight each need
		// the heaviest weight wins and that move is used
		ArrayList<Factory> factories = GameData.AI_getAvailableFactories();
		if (factories.size() == 0)
			return;
		// weigh each choice with the factories
		Matrix<Float> choiceMaker = new Matrix<Float>(choices.size(), factories.size(), Float.class);
		if (debug)
			System.out.println("\nThe AI has " + choices.size() + " choices for the board");

		for (int F = 0; F < factories.size(); F++) {
			for (int C = 0; C < choices.size(); C++) {
				try {
					int tileTypeCount = factories.get(F).getTileTypeCount(choices.get(C).getType());
					float weight = (tileTypeCount / choices.get(C).getAmount());
					weight *= (choices.get(C).getWeight() + (f_ctt_penalty * tileTypeCount));
					if (choices.get(C).amount > tileTypeCount) {
						weight -= f_uc_penalty * (tileTypeCount - choices.get(C).getAmount());
					} else if (choices.get(C).amount < tileTypeCount) {
						weight -= f_oc_penalty * (choices.get(C).getAmount() - tileTypeCount);
					}
					weight -= (f_wtt_penalty * 4 - tileTypeCount);
					choiceMaker.set(C, F, weight);
				} catch (NullPointerException e) {// empty objects
					continue;
				}
			}
		}

		if (debug) {
			for (int Y = 0; Y < choiceMaker.getRows(); Y++) {
				for (int X = 0; X < choiceMaker.getColumns() - 1; X++) {
					System.out.printf(" %.2f : ", choiceMaker.get(Y, X));
				}
				System.out.printf(" %.2f\n", choiceMaker.get(Y, choiceMaker.getColumns() - 1));
			}
		}

		float greatest_weight = 0.0f;
		int factory = 0, choice = 0;

		// make the choice with factories lmao
		for (int Y = 0; Y < choiceMaker.getRows(); Y++) {
			for (int X = 0; X < choiceMaker.getColumns(); X++) {
				try {
					if (choiceMaker.get(Y, X) > greatest_weight) {
						factory = X;
						choice = Y;
						greatest_weight = choiceMaker.get(Y, X);
					}
				} catch (Exception e) {
					continue;
				}
			}
		}

		if (debug)
			System.out.println("\nThe AI has made its choice! It will:\n" + "1. Take "
					+ factories.get(factory).getTileTypeCount(choices.get(choice).getType()) + "\n2. Tiles of type "
					+ Tile.getTypeStr(choices.get(choice).type) + "\n3. From factory number " + (factory + 1)
					+ "\n4. For row number " + (choice + 1));

		/*--------------MAKING THE MOVE--------------*/

		makeMove(factories.get(factory).getTileTypeCount(choices.get(choice).getType()), choices.get(choice).getType(),
				factory, choice);

	}

	private void makeMove(int tileCount, Tile.Tile_Type tileType, int factoryLoc, int rowNumber) {
		Tile[] recievedTiles = GameData.getTilesFromFactory(tileType, factoryLoc, tileCount);
		if (debug) System.out.println("Recieved " + recievedTiles.length + " Tiles of type " + tileType.toString());
		board.getRow(LEFT, rowNumber).set(recievedTiles);
	}
}
