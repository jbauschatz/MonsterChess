
package com.monsterchess.model;

import com.monsterchess.model.move.*;
import com.monsterchess.gamestate.GameStateSerializer;
import com.monsterchess.gamestate.GameStateSerializer_Path;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.move.Capture;
import com.monsterchess.model.move.Move;
import com.monsterchess.model.piece.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		GameStateSerializer<Path> serializer = new GameStateSerializer_Path(Paths.get("src/main/resources"));
		return serializer.from(Paths.get("initial-monster-chess.mc"));
	}

	public Stream<Piece> getPieces(Player player, Piece.Type type) {
		return pieces.stream()
				.filter(p -> p.getPlayer() == player && p.getType() == type);
	}

	public Stream<Piece> getPieces(Player player) {
		return pieces.stream()
				.filter(p -> p.getPlayer() == player);
	}

	/**
	 * The player whose turn it is in this position
	 */
	public Player getPlayerToMove() {
		return playerToMove;
	}

	public TurnCycle getTurnCycle() {
		return turnCycle;
	}

	public int getMoveNumber() {
		return movesMade;
	}

	public List<Piece> getPieces() {
		return pieces;
	}

	/**
	 * The piece on the board at the given square
	 */
	public Piece getPiece(Square square) {
		return board[square.getRank()][square.getFile()];
	}

	public King getWhiteKing() {
		return whiteKing;
	}

	public King getBlackKing() {
		return blackKing;
	}

	public Square getPosition(Piece piece) {
		return piecePositions.get(piece);
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

	public Stream<Move> getThreatenedMoves(Player player) {
		return pieces.stream()
				.filter(piece -> piece.getPlayer() == player)
				.flatMap(piece -> getThreatenedMoves().stream());
	}

	public boolean isThreatened(Piece piece) {
		Square piecePosition = piecePositions.get(piece);

		Stream<Move> possibleThreats = piece.getPlayer() == Player.WHITE
				? getThreatenedMoves(Player.BLACK)
				: getThreatenedMoves(Player.WHITE);

		return possibleThreats.filter(m -> m.getOperativeSquare().equals(piecePosition))
				.findAny()
				.isPresent();
	}

	public GameState makeMove(Move move) {
		return new GameState(this, move);
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

		if (piece instanceof King) {
			if (piece.getPlayer() == Player.WHITE) {
				whiteKing = (King)piece;
			} else {
				blackKing = (King)piece;
			}
		}

		cacheThreatenedMoves();
	}

	public GameState() {
		board = new Piece[8][8];
		pieces = new LinkedList<>();
		piecePositions = new HashMap<>();
		movesMade = 0;
		playerToMove = Player.WHITE;
		turnCycle = TurnCycle.WHITE_FIRST_MOVE;

		cacheThreatenedMoves();
	}

	private GameState(GameState parent, Move move) {
		// Directly copy state from parent
		board = new Piece[8][8];
		for (int rank = 0; rank<8; ++rank) {
			for (int file = 0; file<8; ++file) {
				board[rank][file] = parent.board[rank][file];
			}
		}
		piecePositions = new HashMap<>(parent.piecePositions);
		whiteKing = parent.whiteKing;
		blackKing = parent.blackKing;

		// Derive state specific to this position
		movesMade = parent.movesMade + 1;
		if (movesMade % 3 == 0) {
			playerToMove = Player.WHITE;
			turnCycle = TurnCycle.WHITE_FIRST_MOVE;
		} else if (movesMade % 3 == 1) {
			playerToMove = Player.WHITE;
			turnCycle = TurnCycle.WHITE_SECOND_MOVE;
		} else {
			playerToMove = Player.BLACK;
			turnCycle = TurnCycle.BLACK_MOVE;
		}

		// Execute the side effects of the originating move
		if (move instanceof BasicMove) {
			BasicMove basicMove = (BasicMove)move;
			piecePositions.put(move.getMovingPiece(), move.getOperativeSquare());
			board[basicMove.getFrom().getRank()][basicMove.getFrom().getFile()] = null;
			board[basicMove.getOperativeSquare().getRank()][basicMove.getOperativeSquare().getFile()] = basicMove.getMovingPiece();

			pieces = parent.pieces;
		} else if (move instanceof Capture) {
			Capture capture = (Capture)move;

			// Move the moving piece
			piecePositions.put(move.getMovingPiece(), move.getOperativeSquare());
			board[capture.getFrom().getRank()][capture.getFrom().getFile()] = null;
			board[capture.getOperativeSquare().getRank()][capture.getOperativeSquare().getFile()] = capture.getMovingPiece();

			// Remove the captured piece
			piecePositions.remove(capture.getCapturedPiece());
			pieces = new LinkedList<>(parent.pieces);
			pieces.remove(capture.getCapturedPiece());
		} else if (move instanceof CaptureWithPromotion) {
			CaptureWithPromotion capture = (CaptureWithPromotion) move;

			pieces = new LinkedList<>(parent.pieces);

			// REMOVE the capturing pawn
			piecePositions.remove(capture.getMovingPiece());
			pieces.remove(capture.getMovingPiece());
			board[capture.getFrom().getRank()][capture.getFrom().getFile()] = null;

			// PLACE a new Queen
			// TODO - it's not always a queen
			Queen queen = new Queen(capture.getMovingPiece().getPlayer());
			pieces.add(queen);
			piecePositions.put(queen, move.getOperativeSquare());
			board[capture.getOperativeSquare().getRank()][capture.getOperativeSquare().getFile()] = queen;

			// Remove the captured piece
			piecePositions.remove(capture.getCapturedPiece());
			pieces.remove(capture.getCapturedPiece());
		} else if (move instanceof BasicMoveWithPromotion) {
			BasicMoveWithPromotion promote = (BasicMoveWithPromotion) move;

			pieces = new LinkedList<>(parent.pieces);

			// REMOVE the moving pawn
			piecePositions.remove(promote.getMovingPiece());
			pieces.remove(promote.getMovingPiece());
			board[promote.getFrom().getRank()][promote.getFrom().getFile()] = null;

			// PLACE a new Queen
			// TODO - it's not always a queen
			Queen queen = new Queen(promote.getMovingPiece().getPlayer());
			piecePositions.put(queen, move.getOperativeSquare());
			pieces.add(queen);
			board[promote.getOperativeSquare().getRank()][promote.getOperativeSquare().getFile()] = queen;
		} else {
			throw new IllegalArgumentException("Unhandled move: " + move);
		}

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

	private TurnCycle turnCycle;

	private King whiteKing;

	private King blackKing;
}
