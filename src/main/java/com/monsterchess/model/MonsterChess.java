
package com.monsterchess.model;

import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.move.Move;
import com.monsterchess.model.piece.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 */
public class MonsterChess {

	public Player getPlayerToMove() {
		return playerToMove;
	}

	public Piece getPiece(Square square) {
		return board[square.getRank()][square.getFile()];
	}

	public boolean isEmpty(Square square) {
		return board[square.getRank()][square.getFile()] == null;
	}

	public Stream<Square> getSquares() {
		List<Square> squares = new LinkedList<>();

		for (int rank = 0; rank<8; ++rank)
			for (int file = 0; file<8; ++file)
				squares.add(new Square(rank, file));

		return squares.stream();
	}

	public Set<Move> getLegalMoves() {
		return legalMoves;
	}

	public boolean isLegal(BasicMove move) {
		return legalMoves.contains(move);
	}

	public void makeMove(Move move) {
		if (!legalMoves.contains(move)) {
			throw new IllegalArgumentException("Illegal Move: " + move);
		}

		// Move the piece
		move.execute(this);

		// Change turns
		++movesMade;
		playerToMove = movesMade % 3 == 2 ? Player.BLACK : Player.WHITE;

		cacheLegalMoves();

		// Notify listeners
		for (ChessEventListener listener : listeners)
			listener.processEvent();
	}

	public void movePiece(Piece piece, Square square) {
		board[piece.getSquare().getRank()][piece.getSquare().getFile()] = null;
		board[square.getRank()][square.getFile()] = piece;
		piece.setSquare(square);
		piece.setHasMoved(true);
	}

	public void addListener(ChessEventListener listener) {
		listeners.add(listener);
	}

	private void newGame() {
		board = new Piece[8][8];
		pieces = new LinkedList<>();
		movesMade = 0;
		playerToMove = Player.WHITE;

		// Black pawns
		for (int file = 0; file<8; ++file)
			addPiece(new Pawn(this, Player.BLACK, new Square(6, file)));

		// Black rooks
		addPiece(new Rook(this, Player.BLACK, new Square(7, 0)));
		addPiece(new Rook(this, Player.BLACK, new Square(7, 7)));

		// Black knights
		addPiece(new Knight(this, Player.BLACK, new Square(7, 1)));
		addPiece(new Knight(this, Player.BLACK, new Square(7, 6)));

		// Black bishops
		addPiece(new Bishop(this, Player.BLACK, new Square(7, 2)));
		addPiece(new Bishop(this, Player.BLACK, new Square(7, 5)));

		// Black queen
		addPiece(new Queen(this, Player.BLACK, new Square(7, 3)));

		// Black king
		addPiece(new King(this, Player.BLACK, new Square(7, 4)));

		// White pawns
		for (int file = 3; file<6; ++file)
			addPiece(new Pawn(this, Player.WHITE, new Square(1, file)));

		// White king
		addPiece(new King(this, Player.WHITE, new Square(0, 4)));

		cacheLegalMoves();
	}

	public MonsterChess() {
		listeners = new LinkedList<>();

		newGame();
	}

	private void addPiece(Piece piece) {
		pieces.add(piece);
		board[piece.getSquare().getRank()][piece.getSquare().getFile()] = piece;
	}

	private void cacheLegalMoves() {
		legalMoves = new HashSet<>();

		for (Piece piece : pieces) {
			if (piece.getPlayer() == playerToMove)
				legalMoves.addAll(piece.getThreatenedMoves());
		}

		// TODO filter out moves that result in check or other illegal conditions

		System.out.println(playerToMove + " to move: "
				+ legalMoves.stream().map(Object::toString).collect(Collectors.joining(", ")));
	}

	/**
	 * Index by (rank, file)
	 *
	 * a1 is (0, 0)
	 */
	private Piece[][] board;

	private List<Piece> pieces;

	private Player playerToMove;

	private Set<Move> legalMoves;

	private List<ChessEventListener> listeners;

	private int movesMade;
}
