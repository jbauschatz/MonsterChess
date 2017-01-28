
package com.monsterchess.model.move;

import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Pawn;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public class Capture implements Move {

	@Override
	public String toString() {
		return mover instanceof Pawn
			? from.getFileLetter() + " x " + to
			: mover.getShorthand() + " x " + to;
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Capture))
			return false;

		Capture otherMove = (Capture)other;
		return mover.equals(otherMove.mover)
				&& captured.equals(otherMove.captured)
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

	public Square getFrom() {
		return from;
	}

	public Piece getMovingPiece() {
		return mover;
	}

	public Piece getCapturedPiece() {
		return captured;
	}

	public Capture(Piece mover, Piece captured, Square from, Square to) {
		this.mover = mover;
		this.captured = captured;
		this.from = from;
		this.to = to;
	}

	private final Piece mover;
	private final Piece captured;
	private final Square from;
	private final Square to;
}
