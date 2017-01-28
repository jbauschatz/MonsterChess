
package com.monsterchess.model.move;

import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Pawn;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public class BasicMove implements Move {

	@Override
	public String toString() {
		return mover instanceof Pawn
				? to.toString()
				: mover.getShorthand() + to;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BasicMove))
			return false;

		BasicMove otherMove = (BasicMove)other;
		return mover.equals(otherMove.mover)
				&& to.equals(otherMove.to)
				&& from.equals(otherMove.from);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public Square getOperativeSquare() {
		return to;
	}

	public Piece getMovingPiece() {
		return mover;
	}

	public BasicMove(Piece mover, Square from, Square to) {
		this.mover = mover;
		this.from = from;
		this.to = to;
	}

	private final Piece mover;
	private final Square from;
	private final Square to;

}
