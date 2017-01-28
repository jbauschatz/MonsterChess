
package com.monsterchess.model.piece;

import com.monsterchess.model.GameState;
import com.monsterchess.model.Player;
import com.monsterchess.model.Square;
import com.monsterchess.model.move.BasicMove;
import com.monsterchess.model.move.Capture;
import com.monsterchess.model.move.Move;

import java.util.List;
import java.util.function.Function;

/**
 *
 */
public abstract class Piece {

	public enum Type {
		KING,
		QUEEN,
		ROOK,
		BISHOP,
		KNIGHT,
		PAWN;
	}

	/**
	 * Moves that this piece "threatens" to make
	 *
	 * These moves may not be legal to execute - for example, the rules of Check may prohibit a move
	 * These moves represent the intrinsic movement and capture rules of the piece.
	 */
	public abstract List<Move> getThreatenedMoves(Square currentPosition, GameState gameState);

	public Type getType() {
		return type;
	}

	public String getShorthand() {
		return shorthand;
	}

	public Player getPlayer() {
		return player;
	}

	protected void tryMoveOrCapture(List<Move> moves, Square currentPosition, Square tryPosition, GameState state) {
		if (!tryPosition.isOnBoard()) {
			return;
		}

		if (state.isEmpty(tryPosition)) {
			moves.add(new BasicMove(this, currentPosition, tryPosition));
		} else {
			Piece otherPiece = state.getPiece(tryPosition);
			if (player != otherPiece.player) {
				moves.add(new Capture(this, otherPiece, currentPosition, tryPosition));
			}
		}
	}

	protected void tryMoveOrCaptureUntilBlocking(List<Move> moves, Square currentPosition,
				Function<Square, Square> nextSquare, GameState state) {
		for (Square destination = nextSquare.apply(currentPosition);
				destination.isOnBoard();
				destination = nextSquare.apply(destination)) {
			if (state.isEmpty(destination)) {
				moves.add(new BasicMove(this, currentPosition, destination));
			} else {
				Piece blockingPiece = state.getPiece(destination);
				if (blockingPiece.getPlayer() != player) {
					moves.add(new Capture(this, blockingPiece, currentPosition, destination));
				}
				break;
			}
		}
	}

	protected Piece(Type type, String shorthand, Player player) {
		this.type = type;
		this.shorthand = shorthand;
		this.player = player;
	}

	protected final Player player;
	protected Type type;
	private final String shorthand;
}
