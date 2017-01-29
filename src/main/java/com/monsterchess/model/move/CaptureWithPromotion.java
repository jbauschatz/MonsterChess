
package com.monsterchess.model.move;

import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Pawn;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public class CaptureWithPromotion implements Move {

	@Override
	public String toString() {
		return (mover instanceof Pawn
			? from.getFileLetter() + " x " + to
			: mover.getShorthand() + " x " + to)
			+ "(Q)"; // TODO use the correct notation based on the promotion type
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof CaptureWithPromotion))
			return false;

		CaptureWithPromotion otherMove = (CaptureWithPromotion)other;
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

	public Piece.Type getPromotionType() {
		return promotionType;
	}

	public CaptureWithPromotion(Piece mover, Piece captured, Square from, Square to, Piece.Type promotionType) {
		this.mover = mover;
		this.captured = captured;
		this.from = from;
		this.to = to;
		this.promotionType = promotionType;
	}

	private final Piece mover;
	private final Piece captured;
	private final Square from;
	private final Square to;
	private final Piece.Type promotionType;
}
