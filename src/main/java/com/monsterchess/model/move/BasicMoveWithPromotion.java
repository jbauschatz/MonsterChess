
package com.monsterchess.model.move;

import com.monsterchess.model.Square;
import com.monsterchess.model.piece.Pawn;
import com.monsterchess.model.piece.Piece;

/**
 *
 */
public class BasicMoveWithPromotion implements Move {

	@Override
	public String toString() {
		return (mover instanceof Pawn
				? to.toString()
				: mover.getShorthand() + to)
				+ "(Q)"; // TODO use the correct notation based on the promotion type
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof BasicMoveWithPromotion))
			return false;

		BasicMoveWithPromotion otherMove = (BasicMoveWithPromotion)other;
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

	public Square getFrom() {
		return from;
	}

	public Piece getMovingPiece() {
		return mover;
	}

	public Piece.Type getPromotionType() {
		return promotionType;
	}

	public BasicMoveWithPromotion(Piece mover, Square from, Square to, Piece.Type promotionType) {
		this.mover = mover;
		this.from = from;
		this.to = to;
		this.promotionType = promotionType;
	}

	private final Piece mover;
	private final Square from;
	private final Square to;
	private final Piece.Type promotionType;
}
