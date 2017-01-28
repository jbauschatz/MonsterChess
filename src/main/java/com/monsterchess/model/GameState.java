
package com.monsterchess.model;

import com.monsterchess.model.move.Move;
import com.monsterchess.model.piece.*;

import java.util.*;
import java.util.stream.Stream;

/**
 * Immutable representation of a state of a game of Monster Chess
 */
public class GameState {

	/**
	 * Factory method to produce a valid initial state of the game
	 */
	public static GameState createInitialState() {
		GameState state = new GameState();

		// Black pawns
		for (int file = 0; file<8; ++file)
			state.addPiece(new Pawn(Player.BLACK), new Square(6, file));

		// Black rooks
		state.addPiece(new Rook(Player.BLACK), new Square(7, 0));
		state.addPiece(new Rook(Player.BLACK), new Square(7, 7));

		// Black knights
		state.addPiece(new Knight(Player.BLACK), new Square(7, 1));
		state.addPiece(new Knight(Player.BLACK), new Square(7, 6));

		// Black bishops
		state.addPiece(new Bishop(Player.BLACK), new Square(7, 2));
		state.addPiece(new Bishop(Player.BLACK), new Square(7, 5));

		// Black queen
		state.addPiece(new Queen(Player.BLACK), new Square(7, 3));

		// Black king
		state.addPiece(new King(Player.BLACK), new Square(7, 4));

		// White pawns
		for (int file = 3; file<6; ++file)
			state.addPiece(new Pawn(Player.WHITE), new Square(1, file));

		// White king
		state.addPiece(new King(Player.WHITE), new Square(0, 4));

		return state;
	}

	/**
	 * The player whose turn it is in this position
	 */
	public Player getPlayerToMove() {
		return playerToMove;
	}

	/**
	 * The piece on the board at the given square
	 */
	public Piece getPiece(Square square) {
		return board[square.getRank()][square.getFile()];
	}

	/**
	 * Whether the given square is empty (ie, does not contain any piece)
	 */
	public boolean isEmpty(Square square) {
		return board[square.getRank()][square.getFile()] == null;
	}

	/**
	 * A Stream of all squares on the board
	 */
	public Stream<Square> getSquares() {
		List<Square> squares = new LinkedList<>();

		for (int rank = 0; rank<8; ++rank)
			for (int file = 0; file<8; ++file)
				squares.add(new Square(rank, file));

		return squares.stream();
	}

	public List<Move> getThreatenedMoves() {
		return threatenedMoves;
	}

	public GameState makeMove(Move move) {
		return this;
	}

	/**
	 * Place a piece at the given position.
	 *
	 * As this mutates the GameState, it must only be used in a very trusted context
	 * like serialization or to prepare unit tests
	 */
	public void addPiece(Piece piece, Square position) {
		pieces.add(piece);
		piecePositions.put(piece, position);
		board[position.getRank()][position.getFile()] = piece;

		cacheThreatenedMoves();
	}

	public GameState() {
		board = new Piece[8][8];
		pieces = new LinkedList<>();
		piecePositions = new HashMap<>();
		movesMade = 0;
		playerToMove = Player.WHITE;

		cacheThreatenedMoves();
	}

	/**
	 * Calculate all moves that are currently threatened by the moving player.
	 *
	 * This does not consider whether the moves are legal to make in terms of Check, etc.
	 * It is simply the moves that each piece is intrinsically capable of.
	 */
	private void cacheThreatenedMoves() {
		threatenedMoves = new LinkedList<>();

		pieces.stream()
				.filter(piece -> piece.getPlayer() == playerToMove)
				.forEach(piece -> {
					Square position = piecePositions.get(piece);
					threatenedMoves.addAll(piece.getThreatenedMoves(position, this));
				});
	}

	/**
	 * Index by (rank, file)
	 *
	 * a1 is (0, 0)
	 */
	private Piece[][] board;

	private List<Piece> pieces;

	private HashMap<Piece, Square> piecePositions;

	private Player playerToMove;

	private List<Move> threatenedMoves;

	private int movesMade;
}
